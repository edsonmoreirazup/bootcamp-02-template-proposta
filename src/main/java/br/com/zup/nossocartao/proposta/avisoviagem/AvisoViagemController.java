package br.com.zup.nossocartao.proposta.avisoviagem;

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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/viagens", produces = MediaType.APPLICATION_JSON_VALUE)
public class AvisoViagemController {

    private final AvisoViagemRepository avisoViagemRepository;
    private final CartaoRepository cartaoRepository;
    private final TransactionTemplate transactionTemplate;
    private final CartaoFeign cartaoFeign;

    private Logger logger = LoggerFactory.getLogger(AvisoViagemController.class);

    public AvisoViagemController(AvisoViagemRepository avisoViagemRepository, CartaoRepository cartaoRepository, TransactionTemplate transactionTemplate, CartaoFeign cartaoFeign) {
        this.avisoViagemRepository = avisoViagemRepository;
        this.cartaoRepository = cartaoRepository;
        this.transactionTemplate = transactionTemplate;
        this.cartaoFeign = cartaoFeign;
    }

    @PostMapping(path = "/{cartaoId}/avisos_viagens/")
    public ResponseEntity<?> avisoViagem(@PathVariable(name = "cartaoId") UUID cartaoId,
                                          @RequestBody @Valid NovoAvisoViagemRequest novoAvisoViagemRequest,
                                          @RequestHeader(name="user-agent") @NotBlank String userAgent,
                                          HttpServletRequest request,
                                          @AuthenticationPrincipal Jwt jwt){

        Optional<CartaoEntity> optionalCreditCard = cartaoRepository.findById(cartaoId);
        CartaoEntity cartao = optionalCreditCard.orElseThrow(() -> {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "No credit card found for this ID");
        });

        if(!cartao.pertenceAoUsuario(jwt.getClaimAsString("email"))){
            throw new ApiErrorException(HttpStatus.FORBIDDEN, "O cartão de credito não pertence ao usuário");
        }

        boolean success = informarViagem(cartao, novoAvisoViagemRequest);
        if(success){
            AvisoViagemEntity avisoViagem = novoAvisoViagemRequest.toModel(cartao, request.getRemoteAddr(), userAgent);
            transactionTemplate.execute(status -> {
                avisoViagemRepository.save(avisoViagem);
                return true;
            });
        } else {
            throw new ApiErrorException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro ao processar essa requisição");
        }

        return ResponseEntity.ok().build();
    }

    public boolean informarViagem(CartaoEntity cartao, @Valid NovoAvisoViagemRequest novoAvisoViagemRequest){
        InformaViagemCartaoRequest informaViagemCartaoRequest = new InformaViagemCartaoRequest(novoAvisoViagemRequest.getDestino(),
                novoAvisoViagemRequest.getDataFinal());

        try {
            Map<String, String> response = cartaoFeign.avisoViagemCartao(cartao.getNumero(), informaViagemCartaoRequest);
            if (response.get("resultado").equalsIgnoreCase("CRIADO")) {
                return true;
            } else {
                logger.error("Retorno inválido na API Informa Viagem Cartão - Body: {}", response);
                return false;
            }
        } catch (FeignException feignException){
            logger.error("Erro na API Informa Viagem Cartão - Status code: {}, Body: {}, Message: {}",
                    feignException.status(),
                    feignException.contentUTF8(),
                    feignException.getMessage());
            return false;
        }
    }
}
