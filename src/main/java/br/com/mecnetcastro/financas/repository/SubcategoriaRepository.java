package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.com.mecnetcastro.financas.model.Subcategoria;

public class SubcategoriaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public Subcategoria buscaPorId(Integer id) {
		return this.manager.find(Subcategoria.class, id);
	}

	public List<Subcategoria> lista() {
		CriteriaQuery<Subcategoria> query = this.manager.getCriteriaBuilder().createQuery(Subcategoria.class);
		query.select(query.from(Subcategoria.class));

		return this.manager.createQuery(query).getResultList();
	}

}
