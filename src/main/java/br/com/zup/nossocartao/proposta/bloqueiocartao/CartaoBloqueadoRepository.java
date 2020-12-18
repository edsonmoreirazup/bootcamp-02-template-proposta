package br.com.zup.nossocartao.proposta.bloqueiocartao;

import br.com.zup.nossocartao.proposta.associacartao.StatusCartao;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CartaoBloqueadoRepository extends JpaRepository<CartaoBloqueadoEntity, UUID> {

    Optional<CartaoBloqueadoEntity> findByCartaoCartaoId(UUID id);

    List<CartaoBloqueadoEntity> findAllByCartao_StatusCartao(StatusCartao status, Pageable pageable);
}
