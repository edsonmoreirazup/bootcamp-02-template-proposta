package br.com.zup.nossocartao.proposta.associacarteiradigital;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class InformaCarteiraDigitalRequest {

    @NotBlank
    @Email
    private String email;

    @NotNull
    private ProvedorCarteiraDigital carteira;

    public InformaCarteiraDigitalRequest(@JsonProperty("email") @NotBlank @Email String email,
                                         @JsonProperty("carteira") @NotNull ProvedorCarteiraDigital carteira) {
        this.email = email;
        this.carteira = carteira;
    }

    public String getEmail() {
        return email;
    }

    public ProvedorCarteiraDigital getCarteira() {
        return carteira;
    }
}
