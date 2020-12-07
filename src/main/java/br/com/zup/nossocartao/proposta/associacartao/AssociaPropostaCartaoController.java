package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaRepository;
import br.com.zup.nossocartao.proposta.cadastroproposta.StatusAvaliacaoProposta;
import br.com.zup.nossocartao.proposta.compartilhado.ExecutorTransacao;
import br.com.zup.nossocartao.proposta.outrossistemas.ContaFeign;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class AssociaPropostaCartaoController {

    private final ContaFeign contaFeign;
    private final ExecutorTransacao executorTransacao;
    private final PropostaRepository propostaRepository;

    public AssociaPropostaCartaoController(ContaFeign contaFeign, ExecutorTransacao executorTransacao,
                                           PropostaRepository propostaRepository) {
        this.contaFeign = contaFeign;
        this.executorTransacao = executorTransacao;
        this.propostaRepository = propostaRepository;
    }

    private static final Logger log = LoggerFactory
            .getLogger(AssociaPropostaCartaoController.class);

    @Transactional
    @Scheduled(fixedDelayString = "${scheduled.associa-cartao}")
    @GetMapping("/associa-cartao-proposta")
    public void associa() {
        Optional<List<PropostaEntity>> propostas = propostaRepository.buscaTodasPropostasSemCartao(StatusAvaliacaoProposta.elegivel);

        if (propostas.isPresent()) {
            log.info("Existem {} propostas para avaliar", propostas.get().size());
            for (PropostaEntity proposta : propostas.get()) {
                CartaoResponse cartaoResponse = contaFeign.getNumeroCartaoGerado(proposta.getPropostaId());

                //Se por a caso o serviço de analise financeira não gerar o numero do cartão, então geramos agora
                if(cartaoResponse == null){
                    cartaoResponse = contaFeign.geraNumeroCartaoGerado(new PropostaGeraCartaoRequest(proposta));
                }

                proposta.associaCartao(cartaoResponse.getNumeroCartao());
                executorTransacao.atualizaEComita(proposta);
                log.info("Proposta [{}] teve cartão associada", proposta.getPropostaId());
            }
        }else{
            log.info("Não existe nenhuma proposta para avaliar");
        }
    }
}
