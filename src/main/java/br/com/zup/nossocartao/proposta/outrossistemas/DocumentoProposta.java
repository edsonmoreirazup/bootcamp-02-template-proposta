package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.cadastroproposta.validation.CpfCnpj;

import javax.validation.constraints.NotBlank;

public class DocumentoProposta {

	@NotBlank
	@CpfCnpj
	private String documentoProposta;
	
	@Deprecated
	public DocumentoProposta() {

	}
	
	public DocumentoProposta(String documento) {
		this.documentoProposta = documento;
	}

	public void setDocumentoProposta(String documentoProposta) {
		this.documentoProposta = documentoProposta;
	}
	
	public String getDocumentoProposta() {
		return documentoProposta;
	}
}
