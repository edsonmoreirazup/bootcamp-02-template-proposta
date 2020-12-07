package br.com.zup.nossocartao.proposta.associacartao;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CartaoResponse {

    private String numeroCartao;

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public CartaoResponse(@JsonProperty("id") String numeroCartao) {
        this.numeroCartao = numeroCartao;
    }
}
