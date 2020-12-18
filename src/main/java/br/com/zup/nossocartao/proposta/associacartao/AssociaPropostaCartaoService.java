package br.com.zup.nossocartao.proposta.associacartao;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaAnaliseResponse;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaRepository;
import br.com.zup.nossocartao.proposta.cadastroproposta.StatusAvaliacaoProposta;
import br.com.zup.nossocartao.proposta.outrossistemas.AnaliseFinanceiraFeign;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
public class AssociaPropostaCartaoService {

    private final AnaliseFinanceiraFeign analiseFinanceiraFeign;
    private TransactionTemplate transactionTemplate;
    private final PropostaRepository propostaRepository;
    private Logger logger = LoggerFactory.getLogger(AssociaPropostaCartaoService.class);

    public AssociaPropostaCartaoService(AnaliseFinanceiraFeign analiseFinanceiraFeign, TransactionTemplate transactionTemplate,
                                        PropostaRepository propostaRepository) {
        this.analiseFinanceiraFeign = analiseFinanceiraFeign;
        this.transactionTemplate = transactionTemplate;
        this.propostaRepository = propostaRepository;
    }

    private static final Logger log = LoggerFactory
            .getLogger(AssociaPropostaCartaoService.class);


    @Scheduled(fixedDelayString = "${scheduled.associa-cartao}")
    public void associaPropostasCartoes(){
        List<PropostaEntity> propostasParaAnalise = propostaRepository.findAllByStatusAvaliacao(StatusAvaliacaoProposta.ABERTA,
                PageRequest.of(0, 10));

        for(PropostaEntity proposta : propostasParaAnalise) {

            PropostaAnaliseRequest propostaAnaliseRequest = new PropostaAnaliseRequest(proposta.getCpfCnpj(),
                    proposta.getNome(),
                    proposta.getPropostaId().toString());

            PropostaAnaliseResponse propostaAnaliseResponse;
            try {
                propostaAnaliseResponse = analiseFinanceiraFeign.analisaProposta(propostaAnaliseRequest);

                if(propostaAnaliseResponse.getStatusAvaliacaoValue().equalsIgnoreCase("SEM_RESTRICAO"))
                    proposta.atualizaStatus(StatusAvaliacaoProposta.ELEGIVEL);

            } catch (FeignException.UnprocessableEntity unprocessableEntity) {
                try {
                    propostaAnaliseResponse = new ObjectMapper().readValue(unprocessableEntity.contentUTF8(), PropostaAnaliseResponse.class);
                    if(propostaAnaliseResponse.getStatusAvaliacaoValue().equalsIgnoreCase("COM_RESTRICAO")){
                        proposta.atualizaStatus(StatusAvaliacaoProposta.NAO_ELEGIVEL);
                    }
                } catch (JsonProcessingException e) {
                    logger.error("Erro na API de Analise de proposta - A análise JSON falhou.");
                }
            } catch (FeignException feignException) {
                logger.error("Erro na API de Analise de proposta - Status code: {}, Body: {}, Message: {}",
                        feignException.status(),
                        feignException.contentUTF8(),
                        feignException.getMessage());
            } finally {
                if(proposta.getStatusAvaliacao().equals(StatusAvaliacaoProposta.ELEGIVEL) ||
                        proposta.getStatusAvaliacao().equals(StatusAvaliacaoProposta.NAO_ELEGIVEL)) {
                    transactionTemplate.execute(status -> {
                        propostaRepository.save(proposta);
                        return true;
                    });
                }
            }
        }
    }
}
