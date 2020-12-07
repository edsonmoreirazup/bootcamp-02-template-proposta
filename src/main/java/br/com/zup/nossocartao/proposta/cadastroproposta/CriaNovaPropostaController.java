package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.cadastroproposta.request.PropostaEmailRequest;
import br.com.zup.nossocartao.proposta.cadastroproposta.request.PropostaRequest;
import br.com.zup.nossocartao.proposta.compartilhado.ExecutorTransacao;
import br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler.EntidadeNaoEncontrada;
import br.com.zup.nossocartao.proposta.compartilhado.validation.BloqueiaDocumentoIgualValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping(path = "/propostas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriaNovaPropostaController {

    private final ExecutorTransacao executorTransacao;
    private final BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;
    private final AvaliaProposta avaliaProposta;
    private final PropostaRepository propostaRepository;

    private static final String MSG_PROPOSTA_NAO_ENCONTRADA
            = "Proposta de cpf ou cnpj %s não foi encontrado";

    public CriaNovaPropostaController(ExecutorTransacao executorTransacao,
                                      BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator,
                                      AvaliaProposta avaliaProposta,
                                      PropostaRepository propostaRepository) {
        this.executorTransacao = executorTransacao;
        this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
        this.avaliaProposta = avaliaProposta;
        this.propostaRepository = propostaRepository;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<?> cria(@RequestBody @Valid PropostaRequest propostaRequest, UriComponentsBuilder builder) {

        if (!bloqueiaDocumentoIgualValidator.estaValido(propostaRequest)) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }

        PropostaEntity novaProposta = propostaRequest.toModel();
        executorTransacao.salvaEComita(novaProposta);

        StatusAvaliacaoProposta avaliacao = avaliaProposta.executaAvaliacao(novaProposta);
        novaProposta.atualizaStatus(avaliacao);

        executorTransacao.atualizaEComita(novaProposta);

        URI enderecoConsulta = builder.path("/propostas/{id}")
                .build(novaProposta.getPropostaId());
        return ResponseEntity.created(enderecoConsulta).build();
    }

    @GetMapping
    public PropostaEntity findPropostaCpfCnpj(@RequestBody @Valid PropostaEmailRequest proposta) {
        return propostaRepository.findPropostaEntitiesByCpfCnpj(proposta.getCpfCnpj()).orElseThrow(
                () -> new EntidadeNaoEncontrada(String.format(MSG_PROPOSTA_NAO_ENCONTRADA,proposta.getCpfCnpj())));
    }

}
