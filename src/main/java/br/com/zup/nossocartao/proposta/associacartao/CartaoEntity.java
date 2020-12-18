package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.bloqueiocartao.CartaoBloqueadoRepository;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
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

    @NotBlank
    private String titularCartao;

    @NotNull
    private LocalDateTime dataCriacao;

    @NotNull
    @Valid
    @OneToOne
    @JoinColumn(name = "proposta_id")
    private PropostaEntity proposta;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusCartao statusCartao;

    @Deprecated
    public CartaoEntity() {

    }

    public CartaoEntity(@NotBlank String numero, @NotBlank String titularCartao, @NotNull LocalDateTime dataCriacao,
                        @NotNull @Valid PropostaEntity proposta, @NotNull StatusCartao statusCartao) {

        Assert.hasText(numero, "Numero cartão não pode ser em branco");
        Assert.hasText(titularCartao, "Titular do cartão não pode ser em branco");
        Assert.notNull(proposta, "Proposta não pode ser nulo");
        Assert.notNull(dataCriacao, "A data de criação não pode ser nula");
        Assert.notNull(statusCartao, "O status do cartão não pode ser nulo");

        this.numero = numero;
        this.titularCartao = titularCartao;
        this.dataCriacao = dataCriacao;
        this.proposta = proposta;
        this.statusCartao = statusCartao;
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

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public StatusCartao getStatusCartao() {
        return statusCartao;
    }

    public String getTitularCartao() {
        return titularCartao;
    }

    public boolean pertenceAoUsuario(String email){
        return this.proposta.getEmail().equalsIgnoreCase(email);
    }

    public boolean isBlocked(CartaoBloqueadoRepository cartaoBloqueadoRepository) {
        return cartaoBloqueadoRepository.findByCartaoCartaoId(cartaoId).isPresent();
    }

    public void setStatusCartao(StatusCartao statusCartao) {
        this.statusCartao = statusCartao;
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
