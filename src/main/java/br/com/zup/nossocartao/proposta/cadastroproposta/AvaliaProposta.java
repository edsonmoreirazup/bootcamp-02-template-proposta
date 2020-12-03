package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.outrossistemas.AnaliseProposta;
import br.com.zup.nossocartao.proposta.outrossistemas.PropostaAnaliseRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

@Service
@Validated
public class AvaliaProposta {
	
	private AnaliseProposta analiseProposta;

	public AvaliaProposta(AnaliseProposta analiseProposta) {
		this.analiseProposta = analiseProposta;
	}

	public StatusAvaliacaoProposta executaAvaliacao(@NotNull @Validated PropostaEntity proposta) {

		PropostaAnaliseResponse propostaAnaliseResponse = analiseProposta
				.analisaProposta(new PropostaAnaliseRequest(proposta));

		return RespostaStatusAvaliacao.valueOf(propostaAnaliseResponse.getResultadoSolicitacao()).getStatusAvaliacao();
	}


}
