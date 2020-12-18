package br.com.zup.nossocartao.proposta.associacarteiradigital;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import br.com.zup.nossocartao.proposta.associacartao.CartaoRepository;
import br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler.ApiErrorException;
import br.com.zup.nossocartao.proposta.outrossistemas.CartaoFeign;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/carteiras_digitais", produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociaCarteirasDigitaisController {

    private final CartaoFeign cartaoFeign;
    private final CartaoRepository cartaoRepository;
    private final CarteiraDigitalRepository carteiraDigitalRepository;
    private final TransactionTemplate transactionTemplate;

    private Logger logger = LoggerFactory.getLogger(AssociaCarteirasDigitaisController.class);

    public AssociaCarteirasDigitaisController(CartaoFeign cartaoFeign, CartaoRepository cartaoRepository,
                                              CarteiraDigitalRepository carteiraDigitalRepository,
                                              TransactionTemplate transactionTemplate) {
        this.cartaoFeign = cartaoFeign;
        this.cartaoRepository = cartaoRepository;
        this.carteiraDigitalRepository = carteiraDigitalRepository;
        this.transactionTemplate = transactionTemplate;
    }

    @PostMapping(path = "/{cartaoId}/associa_carteiras_digitais/{provedor}")
    public ResponseEntity<?> registerWallet(@PathVariable(name = "cartaoId") UUID cartaoId,
                                            @PathVariable(name = "provedor") String provedor,
                                            @RequestBody @Valid NovaCarteiraDigitalRequest novaCarteiraDigitalRequest,
                                            UriComponentsBuilder uriComponentsBuilder,
                                            @AuthenticationPrincipal Jwt jwt){

        ProvedorCarteiraDigital provedorCarteiraDigital = ProvedorCarteiraDigital.toEnum(provedor);
        if(provedorCarteiraDigital == null){
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Provedor de carteira digital inválida.");
        }

        Optional<CartaoEntity> optionalCreditCard = cartaoRepository.findById(cartaoId);
        CartaoEntity cartao = optionalCreditCard.orElseThrow(() -> {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Nenhum cartão de credito encontrado para esse Id");
        });

        Optional<CarteiraDigitalEntity>  cartaoJaAssociado = carteiraDigitalRepository.findByCartao_CartaoIdAndProvedor(cartaoId, provedorCarteiraDigital);
        if(cartaoJaAssociado.isPresent()){
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Esse cartão de credito já foi associado a esse provedor");
        }

        if(!cartao.pertenceAoUsuario(jwt.getClaimAsString("email"))){
            throw new ApiErrorException(HttpStatus.FORBIDDEN, "Esse cartão de credito não pertence a esse usuário");
        }

        boolean success = informaAssociacaoCartaoCarteiraDigital(cartao, novaCarteiraDigitalRequest, provedorCarteiraDigital);
        if(success){
            CarteiraDigitalEntity carteiraDigital = novaCarteiraDigitalRequest.toModel(provedorCarteiraDigital, cartao);
            transactionTemplate.execute(status -> {
                carteiraDigitalRepository.save(carteiraDigital);
                return true;
            });

            URI uri = uriComponentsBuilder.path("/{id}/wallets/{wallet}/{id}")
                    .buildAndExpand(cartaoId, provedorCarteiraDigital.name().toLowerCase(), carteiraDigital.getCarteiraDigitalId())
                    .toUri();
            return ResponseEntity.created(uri).build();
        } else {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar essa requisição");
        }
    }

    public boolean informaAssociacaoCartaoCarteiraDigital(CartaoEntity cartao,
                                                 @Valid NovaCarteiraDigitalRequest novaCarteiraDigitalRequest,
                                                 ProvedorCarteiraDigital provedorCarteiraDigital) {
        InformaCarteiraDigitalRequest informaCarteiraDigitalRequest = new InformaCarteiraDigitalRequest(novaCarteiraDigitalRequest.getEmail(), provedorCarteiraDigital);

        try {
            Map<String, String> response = cartaoFeign.informaCarteiraDigital(cartao.getNumero(), informaCarteiraDigitalRequest);

            if(response.get("resultado").equalsIgnoreCase("ASSOCIADA")){
                return true;
            } else {
                logger.error("Retorno inválido na API Informa Associção Cartão de Credito Carteira Digital - Body: {}", response);
                return false;
            }
        } catch (FeignException feignException){
            logger.error("Erro na API Informa Associção Cartão de Credito Carteira Digital - Status code: {}, Body: {}, Message: {}",
                    feignException.status(),
                    feignException.contentUTF8(),
                    feignException.getMessage());
            return false;
        }

    }
}
