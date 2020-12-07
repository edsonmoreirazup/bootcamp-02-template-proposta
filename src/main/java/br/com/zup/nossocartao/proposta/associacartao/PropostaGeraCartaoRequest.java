package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.compartilhado.validation.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PropostaGeraCartaoRequest {

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    @NotBlank
    private String nome;

    @NotNull
    private UUID propostaId;

    public PropostaGeraCartaoRequest(@JsonProperty("documento") @NotBlank String cpfCnpj, @NotBlank String nome,@JsonProperty("idProposta") @NotNull UUID propostaId) {
        this.cpfCnpj = cpfCnpj;
        this.nome = nome;
        this.propostaId = propostaId;
    }

    public PropostaGeraCartaoRequest(@NotNull PropostaEntity propostaEntity) {
        this.cpfCnpj = propostaEntity.getCpfCnpj();
        this.nome = propostaEntity.getNome();
        this.propostaId = propostaEntity.getPropostaId();
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getNome() {
        return nome;
    }

    public UUID getPropostaId() {
        return propostaId;
    }
}
