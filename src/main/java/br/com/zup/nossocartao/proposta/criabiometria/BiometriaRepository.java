package br.com.zup.nossocartao.proposta.criabiometria;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BiometriaRepository extends JpaRepository<BiometriaEntity, UUID> {

}
