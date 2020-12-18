package br.com.zup.nossocartao.proposta.associacarteiradigital;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class NovaCarteiraDigitalRequest {

    @NotBlank
    @Email
    private String email;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public NovaCarteiraDigitalRequest(@NotBlank @Email String email) {
        this.email = email;
    }

    public CarteiraDigitalEntity toModel(ProvedorCarteiraDigital provedor, CartaoEntity cartao){
        return new CarteiraDigitalEntity(email, provedor, cartao);
    }

    public String getEmail() {
        return email;
    }
}
