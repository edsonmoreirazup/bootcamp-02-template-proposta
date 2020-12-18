package br.com.zup.nossocartao.proposta.avisoviagem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AvisoViagemRepository extends JpaRepository<AvisoViagemEntity, UUID> {


}
