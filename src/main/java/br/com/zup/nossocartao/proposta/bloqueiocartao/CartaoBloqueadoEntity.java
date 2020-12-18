package br.com.zup.nossocartao.proposta.bloqueiocartao;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "cartao_bloqueado")
public class CartaoBloqueadoEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID cartaoBloqueadoId;

    @CreationTimestamp
    private LocalDateTime dataBloqueio;

    @NotBlank
    private String requestIp;

    @NotBlank
    private String requestUserAgent;

    @NotNull
    @OneToOne(optional = false)
    private CartaoEntity cartao;

    @Deprecated
    public CartaoBloqueadoEntity(){

    }

    public CartaoBloqueadoEntity(@NotBlank String requestIp, @NotBlank String requestUserAgent, @NotNull CartaoEntity cartao) {

        Assert.hasText(requestIp, "O ip do usuario não pode ser em branco");
        Assert.hasText(requestUserAgent, "Request user agent não pode ser em branco");
        Assert.notNull(cartao, "Cartão não pode ser nulo");

        this.requestIp = requestIp;
        this.requestUserAgent = requestUserAgent;
        this.cartao = cartao;
    }

    public UUID getCartaoBloqueadoId() {
        return cartaoBloqueadoId;
    }

    public LocalDateTime getDataBloqueio() {
        return dataBloqueio;
    }

    public String getRequestIp() {
        return requestIp;
    }

    public String getRequestUserAgent() {
        return requestUserAgent;
    }

    public CartaoEntity getCartao() {
        return cartao;
    }
}
