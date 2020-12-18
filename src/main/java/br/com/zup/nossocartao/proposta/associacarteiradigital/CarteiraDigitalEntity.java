package br.com.zup.nossocartao.proposta.associacarteiradigital;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import org.springframework.util.Assert;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "carteira_digital")
public class CarteiraDigitalEntity {

    @Id
    @GeneratedValue
    @Column(columnDefinition = "BINARY(16)")
    private UUID carteiraDigitalId;

    @NotBlank
    @Email
    private String email;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ProvedorCarteiraDigital provedor;

    @NotNull
    @ManyToOne(optional = false)
    private CartaoEntity cartao;

    @Deprecated
    public CarteiraDigitalEntity(){

    }

    public CarteiraDigitalEntity(@NotBlank @Email String email, @NotNull ProvedorCarteiraDigital provedor,
                                 @NotNull CartaoEntity cartao) {

        Assert.hasText(email, "Email não pode ser em branco");
        Assert.notNull(provedor, "O provedor da carteira digital não pode ser nulo");
        Assert.notNull(cartao, "O cartao de credito não pode ser nulo");

        this.email = email;
        this.provedor = provedor;
        this.cartao = cartao;
    }

    public UUID getCarteiraDigitalId() {
        return carteiraDigitalId;
    }

    public String getEmail() {
        return email;
    }

    public ProvedorCarteiraDigital getProvedor() {
        return provedor;
    }

    public CartaoEntity getCartao() {
        return cartao;
    }
}
