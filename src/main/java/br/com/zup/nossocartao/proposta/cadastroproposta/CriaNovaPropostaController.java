package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.cadastroproposta.request.PropostaRequest;
import br.com.zup.nossocartao.proposta.compartilhado.ExecutorTransacao;
import br.com.zup.nossocartao.proposta.compartilhado.exceptionhandler.ApiErrorException;
import br.com.zup.nossocartao.proposta.compartilhado.validation.BloqueiaDocumentoIgualValidator;
import io.opentracing.Span;
import io.opentracing.Tracer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.net.URI;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "/propostas", produces = MediaType.APPLICATION_JSON_VALUE)
public class CriaNovaPropostaController {

    private final ExecutorTransacao executorTransacao;
    private final BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator;
    private final AvaliaProposta avaliaProposta;
    private final PropostaRepository propostaRepository;
    private final Tracer tracer;

    public CriaNovaPropostaController(ExecutorTransacao executorTransacao,
                                      BloqueiaDocumentoIgualValidator bloqueiaDocumentoIgualValidator,
                                      AvaliaProposta avaliaProposta,
                                      PropostaRepository propostaRepository, Tracer tracer) {
        this.executorTransacao = executorTransacao;
        this.bloqueiaDocumentoIgualValidator = bloqueiaDocumentoIgualValidator;
        this.avaliaProposta = avaliaProposta;
        this.propostaRepository = propostaRepository;
        this.tracer = tracer;
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

    @GetMapping(path = "/{propostaId}")
    public ResponseEntity<PropostaResponse> findPropostaId(@PathVariable UUID propostaId, @AuthenticationPrincipal Jwt jwt) {
        Optional<PropostaEntity> propostaOptional = propostaRepository.findById(propostaId);

        PropostaEntity proposta = propostaOptional.orElseThrow(() -> {
            throw new ApiErrorException(HttpStatus.NOT_FOUND, "Proposta não encontrada.");
        });

        if(!proposta.pertenceAoUsuario(jwt.getClaimAsString("email"))){
            throw new ApiErrorException(HttpStatus.FORBIDDEN, "\n" + "A proposta não pertence a este usuário");
        }

        Span activeSpan = tracer.activeSpan();
        activeSpan.setTag("user.email", jwt.getClaimAsString("email"));

        return ResponseEntity.ok(PropostaResponse.fromModel(proposta));
    }
}
