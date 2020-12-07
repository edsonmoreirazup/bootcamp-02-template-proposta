package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class PropostaAnaliseRequest {


	@JsonProperty("documento")
	private @NotBlank String cpfCnpj;

	private @NotBlank String  nome;

	@JsonProperty("idProposta")
	private @NotNull UUID  propostaId;

	public PropostaAnaliseRequest(@NotBlank String cpfCnpj, @NotBlank String nome, @NotNull UUID propostaId) {
		this.cpfCnpj = cpfCnpj;
		this.nome = nome;
		this.propostaId = propostaId;
	}

	public PropostaAnaliseRequest(@NotNull PropostaEntity propostaEntity) {
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
