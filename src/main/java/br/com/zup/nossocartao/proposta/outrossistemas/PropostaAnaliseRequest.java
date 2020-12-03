package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.cadastroproposta.validation.CpfCnpj;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.UUID;

public class PropostaAnaliseRequest {

	private String documento;
	private String nome;
	private UUID idProposta;

	public PropostaAnaliseRequest(PropostaEntity proposta) {
		this.documento = proposta.getCpfCnpj();
		this.nome = proposta.getNome();
		this.idProposta = proposta.getPropostaId();
	}

	public String getDocumento() {
		return documento;
	}

	public String getNome() {
		return nome;
	}

	public UUID getIdProposta() {
		return idProposta;
	}
}
