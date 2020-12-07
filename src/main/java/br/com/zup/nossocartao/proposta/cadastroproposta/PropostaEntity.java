package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "proposta")
public class PropostaEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private @NotNull UUID propostaId;
    private @NotBlank String cpfCnpj;
    private @NotBlank @Email String email;
    private @NotBlank String nome;
    private @NotBlank String endereco;
    private @NotNull @Positive BigDecimal salario;

    @Enumerated(EnumType.STRING)
    private StatusAvaliacaoProposta statusAvaliacao;

    @OneToOne(mappedBy = "proposta", cascade = CascadeType.MERGE)
    CartaoEntity cartao;

    @Deprecated
    public PropostaEntity() {

    }

    public PropostaEntity(@NotBlank String cpfCnpj, @NotBlank @Email String email, @NotBlank String nome,
                          @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = StatusAvaliacaoProposta.nao_elegivel;
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

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.nao_elegivel), "uma vez que a proposta é elegível não pode mais trocar");
        this.statusAvaliacao = statusAvaliacao;
    }

    public void associaCartao(String numero) {
        Assert.isNull(cartao,"ja associou o cartao");
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.elegivel),"nao rola associar cartao com proposta nao elegivel");
        this.cartao = new CartaoEntity(numero, this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PropostaEntity)) return false;
        PropostaEntity that = (PropostaEntity) o;
        return cpfCnpj.equals(that.cpfCnpj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cpfCnpj);
    }

    @Override
    public String toString() {
        return "PropostaEntity{" +
                "propostaId=" + propostaId +
                ", cpfCnpj='" + cpfCnpj + '\'' +
                ", email='" + email + '\'' +
                ", nome='" + nome + '\'' +
                ", endereco='" + endereco + '\'' +
                ", salario=" + salario +
                ", statusAvaliacao=" + statusAvaliacao +
                '}';
    }
}
