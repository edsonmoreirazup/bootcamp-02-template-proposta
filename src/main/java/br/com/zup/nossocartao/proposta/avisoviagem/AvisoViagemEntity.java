package br.com.zup.nossocartao.proposta.avisoviagem;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "aviso_viagem")
public class AvisoViagemEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    @NotNull
    private UUID avisoViagemId;

    @NotBlank
    private String destino;

    @NotNull
    @FutureOrPresent
    private LocalDate dataFinal;

    @NotNull
    @CreationTimestamp
    private LocalDateTime dataCriacao;

    @NotBlank
    private String requestIp;

    @NotBlank
    private String requestUserAgent;

    @NotNull
    @ManyToOne(optional = false)
    private CartaoEntity cartao;

    @Deprecated
    public AvisoViagemEntity(){}

    public AvisoViagemEntity(@NotBlank String destino, @NotNull @FutureOrPresent LocalDate dataFinal,
                             @NotBlank String requestIp, @NotBlank String requestUserAgent, @NotNull CartaoEntity cartao) {

        Assert.notNull(cartao, "Cartão de credito não pode ser nulo");
        Assert.hasText(destino, "Destino não pode ser em branco");
        Assert.notNull(dataFinal, "Data final da viagem não pode ser nula");
        Assert.hasText(requestIp, "Request IP não pode ser branco");
        Assert.hasText(requestUserAgent, "Request User Agent não pode ser branco");

        this.destino = destino;
        this.dataFinal = dataFinal;
        this.requestIp = requestIp;
        this.requestUserAgent = requestUserAgent;
        this.cartao = cartao;
    }

    public UUID getAvisoViagemId() {
        return avisoViagemId;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AvisoViagemEntity)) return false;
        AvisoViagemEntity that = (AvisoViagemEntity) o;
        return Objects.equals(destino, that.destino) && dataFinal.equals(that.dataFinal) && dataCriacao.equals(that.dataCriacao) && requestIp.equals(that.requestIp) && requestUserAgent.equals(that.requestUserAgent) && cartao.equals(that.cartao);
    }

    @Override
    public int hashCode() {
        return Objects.hash(destino, dataFinal, dataCriacao, requestIp, requestUserAgent, cartao);
    }
}
