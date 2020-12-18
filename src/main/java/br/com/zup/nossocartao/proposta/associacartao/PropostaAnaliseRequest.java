package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.compartilhado.validation.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PropostaAnaliseRequest {

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    @NotBlank
    private String nome;

    @NotNull
    private String propostaId;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PropostaAnaliseRequest(@JsonProperty("documento") @NotBlank @CpfCnpj String cpfCnpj, @NotBlank String nome, @JsonProperty("idProposta") @NotNull String propostaId) {
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.propostaId = propostaId;
    }

    public PropostaAnaliseRequest(@NotNull PropostaEntity propostaEntity) {
        this.cpfCnpj = propostaEntity.getCpfCnpj();
        this.nome = propostaEntity.getNome();
        this.propostaId = propostaEntity.getPropostaId().toString();
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public String getPropostaId() {
        return propostaId;
    }
}
