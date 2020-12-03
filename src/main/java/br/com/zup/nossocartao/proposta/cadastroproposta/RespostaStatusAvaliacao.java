package br.com.zup.nossocartao.proposta.cadastroproposta;

public enum RespostaStatusAvaliacao {

	COM_RESTRICAO(StatusAvaliacaoProposta.nao_elegivel),SEM_RESTRICAO(StatusAvaliacaoProposta.elegivel);
	
	StatusAvaliacaoProposta statusAvaliacao;

	RespostaStatusAvaliacao(StatusAvaliacaoProposta statusAvaliacao) {
		this.statusAvaliacao = statusAvaliacao;
	}
	
	public StatusAvaliacaoProposta getStatusAvaliacao() {
		return statusAvaliacao;
	}
}
