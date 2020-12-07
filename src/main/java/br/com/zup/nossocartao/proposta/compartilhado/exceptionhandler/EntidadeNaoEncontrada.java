package br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler;

public class EntidadeNaoEncontrada extends RuntimeException {

    public EntidadeNaoEncontrada(String mensagem) {
        super(mensagem);
    }
}
