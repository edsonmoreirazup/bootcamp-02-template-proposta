package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.cadastroproposta.validation.CpfCnpj;
import br.com.zup.nossocartao.proposta.compartilhado.UniqueValue;
import com.fasterxml.jackson.annotation.JsonCreator;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

public class PropostaRequest {

    @NotBlank
    @CpfCnpj
    private String cpfCnpj;

    @NotBlank
    @Email
    private String email;
    private @NotBlank String nome;
    private  @NotBlank String endereco;
    private @NotNull @Positive BigDecimal salario;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PropostaRequest(@NotBlank String cpfCnpj, @NotBlank @Email String email, @NotBlank String nome, @NotBlank String endereco,
                           @NotNull @Positive BigDecimal salario) {
        this.cpfCnpj = cpfCnpj;
        this.email = email;
        this.nome = nome;
        this.endereco = endereco;
        this.salario = salario;
    }

    public PropostaEntity toModel() {
        return new PropostaEntity(cpfCnpj,email,nome,endereco,salario);
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
}
