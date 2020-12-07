package br.com.zup.nossocartao.proposta.cadastroproposta;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PropostaAnaliseResponse {

    private String statusAvaliacaoValue;

    public PropostaAnaliseResponse(@JsonProperty("resultadoSolicitacao") String statusAvaliacaoValue) {
        this.statusAvaliacaoValue = statusAvaliacaoValue;
    }

    public String getStatusAvaliacaoValue() {
        return statusAvaliacaoValue;
    }
}
