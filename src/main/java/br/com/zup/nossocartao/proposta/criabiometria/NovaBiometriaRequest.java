package br.com.zup.nossocartao.proposta.criabiometria;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import org.bouncycastle.util.encoders.Base64;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

public class NovaBiometriaRequest {

    @NotBlank
    private String fingerprint;

    public NovaBiometriaRequest() {
    }

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaBiometriaRequest(@NotBlank String fingerprint) {
        this.fingerprint = fingerprint;
    }

    public BiometriaEntity toModel(UUID biometriaId, CartaoEntity cartao) {
        return new BiometriaEntity(Base64.decode(fingerprint), cartao);
    }
}
