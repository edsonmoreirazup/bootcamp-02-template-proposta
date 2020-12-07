package br.com.zup.nossocartao.proposta.compartilhado.validation;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import br.com.zup.nossocartao.proposta.cadastroproposta.request.PropostaRequest;
import org.springframework.stereotype.Component;

@Component
public class BloqueiaDocumentoIgualValidator {

	@PersistenceContext
	private EntityManager manager;

	public boolean estaValido(PropostaRequest request) {
		return manager.createQuery(
				"select p.cpfCnpj from PropostaEntity p where p.cpfCnpj = :cpfCnpj")
				.setParameter("cpfCnpj", request.getCpfCnpj())
				.getResultList().isEmpty();
	}

}
