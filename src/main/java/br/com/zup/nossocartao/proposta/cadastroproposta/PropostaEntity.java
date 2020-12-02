package br.com.zup.nossocartao.proposta.cadastroproposta;

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
                '}';
    }
}
