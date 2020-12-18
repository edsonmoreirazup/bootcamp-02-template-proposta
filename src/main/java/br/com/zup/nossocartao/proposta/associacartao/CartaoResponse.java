package br.com.zup.nossocartao.proposta.associacartao;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public class CartaoResponse {

    private String numeroCartao;

    private LocalDateTime dataCriacao;

    private String titularCartao;

    public CartaoResponse(@JsonProperty("id") String numeroCartao, @JsonProperty("titular") String titularCartao,
                          @JsonProperty("emitidoEm") LocalDateTime dataCriacao) {
        this.numeroCartao = numeroCartao;
        this.dataCriacao = dataCriacao;
        this.titularCartao = titularCartao;
    }

    public String getNumeroCartao() {
        return numeroCartao;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public String getTitularCartao() {
        return titularCartao;
    }
}
