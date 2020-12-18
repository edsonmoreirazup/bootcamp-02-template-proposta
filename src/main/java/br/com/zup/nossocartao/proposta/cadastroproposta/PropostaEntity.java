package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import br.com.zup.nossocartao.proposta.associacartao.StatusCartao;
import br.com.zup.nossocartao.proposta.compartilhado.AttributeEncryptor;
import br.com.zup.nossocartao.proposta.compartilhado.validation.CpfCnpj;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "proposta")
public class PropostaEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private @NotNull UUID propostaId;

    @NotBlank
    @CpfCnpj
    @Convert(converter = AttributeEncryptor.class)
    private  String cpfCnpj;

    private @NotBlank @Email String email;
    private @NotBlank String nome;
    private @NotBlank String endereco;
    private @NotNull @Positive BigDecimal salario;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAvaliacaoProposta statusAvaliacao;

    @OneToOne(mappedBy = "proposta")
    CartaoEntity cartao;

    @Deprecated
    public PropostaEntity() {

    }

    public PropostaEntity(@NotBlank String cpfCnpj, @NotBlank @Email String email, @NotBlank String nome,
                          @NotBlank String endereco, @NotNull @Positive BigDecimal salario) {

        Assert.hasText(cpfCnpj, "Cpf ou Cnpj inválido");
        Assert.hasText(email, "Email Inválido");
        Assert.hasText(nome, "Nome inválido");
        Assert.hasText(endereco, "Endereço inválido");
        Assert.notNull(salario, "Salario inválido");
        Assert.isTrue(salario.compareTo(BigDecimal.ZERO) >= 0, "Salario não pode ser negativo");

        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
        this.statusAvaliacao = StatusAvaliacaoProposta.NAO_ELEGIVEL;
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

    public boolean pertenceAoUsuario(String email) {
        return this.email.equalsIgnoreCase(email);
    }

    public void atualizaStatus(StatusAvaliacaoProposta statusAvaliacao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.ELEGIVEL), "uma vez que a proposta é elegível não pode mais trocar");
        this.statusAvaliacao = statusAvaliacao;
    }

    public void associaCartao(String numero, String titularCartao, LocalDateTime dataCriacao, StatusCartao statusCartao) {
        Assert.isTrue(this.statusAvaliacao.equals(StatusAvaliacaoProposta.NAO_ELEGIVEL),"Não é possivel associar o cartão com uma proposta não elegeivel");
        this.cartao = new CartaoEntity(numero,titularCartao,dataCriacao,this, statusCartao);
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
