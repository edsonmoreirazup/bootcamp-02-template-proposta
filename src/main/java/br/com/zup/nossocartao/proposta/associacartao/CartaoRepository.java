package br.com.zup.nossocartao.proposta.associacartao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface CartaoRepository extends JpaRepository<CartaoEntity,UUID> {


}
