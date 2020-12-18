package br.com.zup.nossocartao.proposta.associacarteiradigital;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CarteiraDigitalRepository extends JpaRepository<CarteiraDigitalEntity, UUID> {

    Optional<CarteiraDigitalEntity> findByCartao_CartaoIdAndProvedor(UUID cartaoId, ProvedorCarteiraDigital provedor);
}
