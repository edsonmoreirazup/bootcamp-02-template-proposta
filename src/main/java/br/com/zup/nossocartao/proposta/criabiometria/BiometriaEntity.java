package br.com.zup.nossocartao.proposta.criabiometria;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "biometria")
public class BiometriaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "binary(16)")
    private UUID biometriaId;

    @Lob
    @NotNull
    @Column(nullable = false)
    private byte[] fingerprint;

    @CreationTimestamp
    private Timestamp dataCriacao;

    @NotNull
    @ManyToOne
    private CartaoEntity cartao;

    @Deprecated
    public BiometriaEntity(){}

    public BiometriaEntity(@NotNull byte[] fingerprint, @NotNull CartaoEntity cartao) {

        Assert.notNull(fingerprint, "Fingerprint não pode ser nulo");
        Assert.notNull(cartao, "Cartão não pode ser nulo");

        this.fingerprint = fingerprint;
        this.cartao = cartao;
    }

    public UUID getBiometriaId() {
        return biometriaId;
    }

    public void setBiometriaId(UUID biometriaId) {
        this.biometriaId = biometriaId;
    }

    public byte[] getFingerprint() {
        return fingerprint;
    }

    public void setFingerprint(byte[] fingerprint) {
        this.fingerprint = fingerprint;
    }

     public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BiometriaEntity)) return false;
        BiometriaEntity that = (BiometriaEntity) o;
        return biometriaId.equals(that.biometriaId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(biometriaId);
    }
}
