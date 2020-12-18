package br.com.zup.nossocartao.proposta.bloqueiocartao;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import br.com.zup.nossocartao.proposta.associacartao.CartaoRepository;
import br.com.zup.nossocartao.proposta.associacartao.StatusCartao;
import br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler.ApiErrorException;
import br.com.zup.nossocartao.proposta.outrossistemas.CartaoFeign;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/bloqueios", produces = MediaType.APPLICATION_JSON_VALUE)
public class BloqueioCartaoController {

    private final CartaoRepository cartaoRepository;
    private final CartaoBloqueadoRepository cartaoBloqueadoRepository;
    private final TransactionTemplate transactionTemplate;
    private final CartaoFeign cartaoFeign;

    private Logger logger = LoggerFactory.getLogger(BloqueioCartaoController.class);

    public BloqueioCartaoController(CartaoRepository cartaoRepository, CartaoBloqueadoRepository cartaoBloqueadoRepository, TransactionTemplate transactionTemplate, CartaoFeign cartaoFeign) {
        this.cartaoRepository = cartaoRepository;
        this.cartaoBloqueadoRepository = cartaoBloqueadoRepository;
        this.transactionTemplate = transactionTemplate;
        this.cartaoFeign = cartaoFeign;
    }

    @Transactional
    @PostMapping(path = "/{cartaoBloqueadoId}/bloquear_cartao/")
    public ResponseEntity<?> bloquearCartao(@PathVariable(name = "cartaoBloqueadoId") UUID id,
                                      @RequestHeader(name="user-agent") @NotBlank String userAgent,
                                      HttpServletRequest request,
                                      @AuthenticationPrincipal Jwt jwt){

        Optional<CartaoEntity> cartaoOptional = cartaoRepository.findById(id);
        CartaoEntity cartao = cartaoOptional.orElseThrow(() -> {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Nenhum cartão de credito encontrado para esse id");
        });

        if(cartao.isBlocked(cartaoBloqueadoRepository)){
            throw new ApiErrorException(HttpStatus.UNPROCESSABLE_ENTITY, "Cartão de credito já está bloqueado.");
        }

        if(!cartao.pertenceAoUsuario(jwt.getClaimAsString("email"))){
            throw new ApiErrorException(HttpStatus.FORBIDDEN, "O cartão de credito não pertence a esse usuário");
        }

        CartaoBloqueadoEntity cartaoBloqueado = new CartaoBloqueadoEntity(request.getRemoteAddr(), userAgent, cartao);
        cartaoBloqueadoRepository.save(cartaoBloqueado);

        return ResponseEntity.ok().build();
    }

    @Scheduled(fixedDelayString = "${scheduled.bloqueia-cartao}")
    protected void informaBloqueioCartao(){
        InformaBloqueioCartaoRequest request = new InformaBloqueioCartaoRequest("propostas");

        List<CartaoBloqueadoEntity> cartoesDesbloqueadosParaBloquear = cartaoBloqueadoRepository.findAllByCartao_StatusCartao(StatusCartao.DESBLOQUEADO,
                PageRequest.of(0, 100));

        for(CartaoBloqueadoEntity cartaoBloqueado : cartoesDesbloqueadosParaBloquear){
            try {
                CartaoEntity cartao = cartaoBloqueado.getCartao();
                Map<String, String> response = cartaoFeign.informaBloqueioCartao(cartao.getNumero(), request);
                if (response.get("resultado").equalsIgnoreCase("BLOQUEADO")) {
                    cartao.setStatusCartao(StatusCartao.BLOQUEADO);
                    transactionTemplate.execute(status -> {
                        cartaoRepository.save(cartao);
                        return true;
                    });
                } else {
                    logger.error("Retorno inválido na API de Informa Bloqueio de Cartão - Body: {}", response);
                }
            } catch (FeignException feignException){
                logger.error("Erro na API de Informa Bloqueio de Cartão - Status code: {}, Body: {}, Message: {}",
                        feignException.status(),
                        feignException.contentUTF8(),
                        feignException.getMessage());
            }
        }
    }
}
