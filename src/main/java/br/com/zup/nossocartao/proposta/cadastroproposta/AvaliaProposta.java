package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.outrossistemas.AnaliseFinanceiraFeign;
import br.com.zup.nossocartao.proposta.outrossistemas.PropostaAnaliseRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {
	
	private AnaliseFinanceiraFeign analiseFinanceiraFeign;

	public AvaliaProposta(AnaliseFinanceiraFeign analiseFinanceiraFeign) {
		this.analiseFinanceiraFeign = analiseFinanceiraFeign;
	}

	public StatusAvaliacaoProposta executaAvaliacao(@NotNull @Validated PropostaEntity proposta) {

		PropostaAnaliseResponse propostaAnaliseResponse = analiseFinanceiraFeign
				.analisaProposta(new PropostaAnaliseRequest(proposta));

		return RespostaStatusAvaliacao.valueOf(propostaAnaliseResponse.getStatusAvaliacaoValue()).getStatusAvaliacao();
	}


}
