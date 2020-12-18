package br.com.zup.nossocartao.proposta.avisoviagem;

import br.com.zup.nossocartao.proposta.associacartao.CartaoEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class NovoAvisoViagemRequest {

    @NotBlank
    private String destino;

    @NotNull
    @FutureOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDate dataFinal;

    public NovoAvisoViagemRequest(@NotBlank String destino, @NotNull @FutureOrPresent LocalDate dataFinal) {
        this.destino = destino;
        this.dataFinal = dataFinal;
    }

    public String getDestino() {
        return destino;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public AvisoViagemEntity toModel(CartaoEntity cartao, String requestIp, String requestUserAgent) {
        return new AvisoViagemEntity(destino, dataFinal, requestIp, requestUserAgent, cartao);
    }

    @Override
    public String toString() {
        return "NovoAvisoViagemRequest{" +
                "destino='" + destino + '\'' +
                ", dataFinal=" + dataFinal +
                '}';
    }
}
