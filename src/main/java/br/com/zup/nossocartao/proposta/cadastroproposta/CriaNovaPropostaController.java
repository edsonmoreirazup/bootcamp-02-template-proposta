package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.cadastroproposta.validation.BloqueiaDocumentoIgualValidator;
import br.com.zup.nossocartao.proposta.compartilhado.ExecutorTransacao;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/propostas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriaNovaPropostaController {

    private ExecutorTransacao executorTransacao;
    private BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;

    public CriaNovaPropostaController(ExecutorTransacao executorTransacao,
                                      BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator) {
        this.executorTransacao = executorTransacao;
        this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
    }

    @PostMapping
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder builder){

        if (!bloqueiaDocumentoIgualValidator.estaValido(propostaRequest)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }


        PropostaEntity novaProposta = propostaRequest.toModel();
        executorTransacao.salvaEComita(novaProposta);

        executorTransacao.atualizaEComita(novaProposta);

        URI enderecoConsulta = builder.path("/propostas/{id}")
                .build(novaProposta.getPropostaId());
        return ResponseEntity.created(enderecoConsulta).build();
    }

}
