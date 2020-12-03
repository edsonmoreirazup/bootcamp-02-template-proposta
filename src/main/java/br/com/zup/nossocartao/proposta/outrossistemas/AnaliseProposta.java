package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(url = "${enderecos-externos.base-url}", name = "analiseProposta")
public interface AnaliseProposta {

	@PostMapping(path ="/api/solicitacao", consumes = "application/json", produces = "application/json")
	PropostaAnaliseResponse analisaProposta(@RequestBody PropostaAnaliseRequest request);

}
