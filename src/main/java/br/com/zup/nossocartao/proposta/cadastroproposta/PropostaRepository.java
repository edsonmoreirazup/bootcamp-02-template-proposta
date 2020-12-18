package br.com.zup.nossocartao.proposta.cadastroproposta;

import br.com.zup.nossocartao.proposta.cadastroproposta.PropostaEntity;
import br.com.zup.nossocartao.proposta.cadastroproposta.StatusAvaliacaoProposta;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PropostaRepository extends CrudRepository<PropostaEntity, UUID> {

    @Query("select p from PropostaEntity p left join p.cartao c where p.statusAvaliacao = :status and c.cartaoId is null")
    Optional<List<PropostaEntity>> buscaTodasPropostasSemCartao(@Param("status") StatusAvaliacaoProposta statusAvaliacao);

    List<PropostaEntity> findAllByStatusAvaliacao(StatusAvaliacaoProposta status, Pageable pageable);

    Optional<PropostaEntity> findPropostaEntitiesByCpfCnpj(String cpfCnpj);

}
