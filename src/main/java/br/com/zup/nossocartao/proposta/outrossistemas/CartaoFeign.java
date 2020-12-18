package br.com.zup.nossocartao.proposta.outrossistemas;

import br.com.zup.nossocartao.proposta.associacartao.CartaoResponse;
import br.com.zup.nossocartao.proposta.associacartao.PropostaAnaliseRequest;
import br.com.zup.nossocartao.proposta.associacarteiradigital.InformaCarteiraDigitalRequest;
import br.com.zup.nossocartao.proposta.avisoviagem.InformaViagemCartaoRequest;
import br.com.zup.nossocartao.proposta.bloqueiocartao.InformaBloqueioCartaoRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.UUID;

@FeignClient(url = "${enderecos-externos.conta}", name = "conta")
public interface CartaoFeign {

	@GetMapping(path ="/api/cartoes", consumes = "application/json", produces = "application/json")
	CartaoResponse getNumeroCartaoGerado(@RequestParam(name = "idProposta") @NotNull UUID propostaId);

	@PostMapping(path ="/api/cartoes", consumes = "application/json", produces = "application/json")
	CartaoResponse geraNumeroCartaoGerado(@RequestBody @Valid PropostaAnaliseRequest propostaAnaliseRequest);

	@PostMapping(path = "/api/cartoes/{numeroCartao}/bloqueios", consumes = "application/json", produces = "application/json")
	Map<String, String> informaBloqueioCartao(@PathVariable(name = "numeroCartao") String numeroCartao, @RequestBody @Valid InformaBloqueioCartaoRequest lockRequest);

	@PostMapping(path = "/api/cartoes/{numeroCartao}/avisos", consumes = "application/json", produces = "application/json")
	Map<String, String> avisoViagemCartao(@PathVariable(name = "numeroCartao") String numeroCartao, @RequestBody @Valid InformaViagemCartaoRequest request);

	@PostMapping(path = "/api/cartoes/{numeroCartao}/carteiras", consumes = "application/json", produces = "application/json")
	Map<String, String> informaCarteiraDigital(@PathVariable(name = "numeroCartao") String numeroCartao, @RequestBody @Valid InformaCarteiraDigitalRequest request);

}
