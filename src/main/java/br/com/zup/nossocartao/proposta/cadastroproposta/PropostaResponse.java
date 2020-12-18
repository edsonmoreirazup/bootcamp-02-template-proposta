package br.com.zup.nossocartao.proposta.cadastroproposta;

import java.math.BigDecimal;
import java.util.UUID;

public class PropostaResponse {

    private UUID propostaId;
    private String cpfCnpj;
    private String email;
    private String nome;
    private String endereco;
    private BigDecimal salario;
    private StatusAvaliacaoProposta statusAvaliacao;

    public PropostaResponse(UUID propostaId, String cpfCnpj, String email, String nome, String endereco, BigDecimal salario,
                            StatusAvaliacaoProposta statusAvaliacao) {
        this.propostaId = propostaId;
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = statusAvaliacao;
    }
    public static PropostaResponse fromModel(PropostaEntity proposta) {
        return new PropostaResponse(
                proposta.getPropostaId(),
                proposta.getCpfCnpj(),
                proposta.getEmail(),
                proposta.getNome(),
                proposta.getEndereco(),
                proposta.getSalario(),
                proposta.getStatusAvaliacao()
        );
    }

    public UUID getPropostaId() {
        return propostaId;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getEmail() {
        return email;
    }

    public String getNome() {
        return nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public BigDecimal getSalario() {
        return salario;
    }

    public StatusAvaliacaoProposta getStatusAvaliacao() {
        return statusAvaliacao;
    }
}
