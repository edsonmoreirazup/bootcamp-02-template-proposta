package br.com.zup.nossocartao.proposta.avisoviagem;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class InformaViagemCartaoRequest {

    @NotBlank
    private String destino;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate validoAte;

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public InformaViagemCartaoRequest(@JsonProperty("destino") @NotBlank String destino, @JsonProperty("validoAte")
                                      @NotNull @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING) LocalDate validoAte) {
        this.destino = destino;
        this.validoAte = validoAte;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getValidoAte() {
        return validoAte;
    }

    @Override
    public String toString() {
        return "InformTravelCreditCardRequest{" +
                "destino='" + destino + '\'' +
                ", validoAte=" + validoAte +
                '}';
    }
}
