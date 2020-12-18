package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.associacartao.PropostaAnaliseRequest;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${enderecos-externos.analise-financeira}", name = "analiseFinanceira")
public interface AnaliseFinanceiraFeign {

	@PostMapping(path ="/api/solicitacao", consumes = "application/json", produces = "application/json")
	PropostaAnaliseResponse analisaProposta(@RequestBody PropostaAnaliseRequest request);

}
