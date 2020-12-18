package br.com.zup.nossocartao.proposta.cadastroproposta;

public enum RespostaStatusAvaliacao {

	COM_RESTRICAO(StatusAvaliacaoProposta.NAO_ELEGIVEL),SEM_RESTRICAO(StatusAvaliacaoProposta.ELEGIVEL);
	
	StatusAvaliacaoProposta statusAvaliacao;

	RespostaStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}
	
	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}
}
