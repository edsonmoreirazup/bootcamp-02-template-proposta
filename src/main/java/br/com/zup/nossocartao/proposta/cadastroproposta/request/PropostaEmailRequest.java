package br.com.zup.nossocartao.proposta.cadastroproposta.request;

import br.com.zup.nossocartao.proposta.compartilhado.validation.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.NotBlank;

public class PropostaEmailRequest {

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;
    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PropostaEmailRequest(@NotBlank String cpfCnpj) {
        this.cpfCnpj = cpfCnpj;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }
}
