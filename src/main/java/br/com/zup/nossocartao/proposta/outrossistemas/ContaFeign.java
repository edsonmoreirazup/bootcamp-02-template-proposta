package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.associacartao.CartaoResponse;
import br.com.zup.nossocartao.proposta.associacartao.PropostaGeraCartaoRequest;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaAnaliseResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@FeignClient(url = "${enderecos-externos.conta}", name = "conta")
public interface ContaFeign {

	@GetMapping(path ="/api/cartoes", consumes = "application/json", produces = "application/json")
	CartaoResponse getNumeroCartaoGerado(@RequestParam(name = "idProposta") @NotNull UUID propostaId);

	@PostMapping(path ="/api/cartoes", consumes = "application/json", produces = "application/json")
	CartaoResponse geraNumeroCartaoGerado(@Valid PropostaGeraCartaoRequest propostaGeraCartaoRequest);

}
