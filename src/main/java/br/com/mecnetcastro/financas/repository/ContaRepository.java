package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.mecnetcastro.financas.model.Conta;

public class ContaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adiciona(Conta conta) {
		this.manager.persist(conta);
	}

	public void atualiza(Conta conta) {
		this.manager.merge(conta);
	}

	public void remove(Conta conta) {
		this.manager.remove(conta);
	}

	public Conta buscaPorId(Integer id) {
		return this.manager.find(Conta.class, id);
	}

	public List<Conta> lista() {
		CriteriaQuery<Conta> query = this.manager.getCriteriaBuilder().createQuery(Conta.class);
		query.select(query.from(Conta.class));

		return this.manager.createQuery(query).getResultList();
	}

	public List<Conta> buscaPorTitular(String titular) {
		String jpql = "SELECT c FROM Conta c WHERE UPPER (c.titular) LIKE UPPER (:pConta) ORDER BY c.titular";
		TypedQuery<Conta> query = this.manager.createQuery(jpql, Conta.class);
		query.setParameter("pConta", "%" + titular + "%");

		return query.getResultList();
	}

}
