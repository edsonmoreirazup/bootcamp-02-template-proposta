package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import org.hibernate.annotations.common.util.StringHelper;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cartao")
public class CartaoEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private @NotNull UUID cartaoId;

    @NotBlank
    private String numero;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "proposta_id")
    private PropostaEntity proposta;

    @Deprecated
    public CartaoEntity() {

    }

    public CartaoEntity(@NotBlank String numero, @NotNull @Valid PropostaEntity proposta) {
        this.numero = numero;
        this.proposta = proposta;
    }

    public UUID getCartaoId() {
        return cartaoId;
    }

    public String getNumero() {
        return numero;
    }

    public PropostaEntity getProposta() {
        return proposta;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CartaoEntity)) return false;
        CartaoEntity that = (CartaoEntity) o;
        return numero.equals(that.numero) && proposta.equals(that.proposta);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero, proposta);
    }

    @Override
    public String toString() {
        return "CartaoEntity{" +
                "cartaoId=" + cartaoId +
                ", numero='" + numero + '\'' +
                ", proposta=" + proposta +
                '}';
    }
}
