package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.associacartao.CartaoResponse;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(url = "${enderecos-externos.analise-financeira}", name = "analiseFinanceira")
public interface AnaliseFinanceiraFeign {

	@PostMapping(path ="/api/solicitacao", consumes = "application/json", produces = "application/json")
	PropostaAnaliseResponse analisaProposta(@RequestBody PropostaAnaliseRequest request);

}
