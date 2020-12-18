package br.com.zup.nossocartao.proposta.criabiometria;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import br.com.zup.nossocartao.proposta.associacartao.CartaoRepository;
import br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler.ApiErrorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/biometrias", produces = MediaType.APPLICATION_JSON_VALUE)
public class CadastroBiometriaController {

    private CartaoRepository cartaoRepository;
    private BiometriaRepository biometriaRepository;

    public CadastroBiometriaController(CartaoRepository cartaoRepository, BiometriaRepository biometriaRepository) {
        this.cartaoRepository = cartaoRepository;
        this.biometriaRepository = biometriaRepository;
    }

    @PostMapping(path = "/{biometriaId}/cria_biometria/")
    @Transactional
    public ResponseEntity<?> criarBiometria(@PathVariable(name = "biometriaId") UUID biometriaId,
                                            @RequestBody @Valid NovaBiometriaRequest novaBiometriaRequest,
                                            UriComponentsBuilder uriComponentsBuilder,
                                            @AuthenticationPrincipal Jwt jwt){

        Optional<CartaoEntity> cartaoOptional = cartaoRepository.findById(biometriaId);

        CartaoEntity cartao = cartaoOptional.orElseThrow(() -> {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Nenhum cartão de crédito encontrado para este ID");
        });

        if(!cartao.pertenceAoUsuario(jwt.getClaimAsString("email"))){
            throw new ApiErrorException(HttpStatus.FORBIDDEN, "O cartão de crédito não pertence a este usuário");
        }

        BiometriaEntity biometria = novaBiometriaRequest.toModel(biometriaId, cartao);
        biometriaRepository.save(biometria);

        URI uri = uriComponentsBuilder.path("/cards/{cardId}/biometrics/{biometricId}")
                .buildAndExpand(biometriaId, biometria.getBiometriaId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

}
