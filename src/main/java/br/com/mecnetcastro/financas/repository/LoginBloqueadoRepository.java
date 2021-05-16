package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

import br.com.mecnetcastro.financas.model.LoginBloqueado;

public class LoginBloqueadoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adiciona(LoginBloqueado loginBloqueado) {
		this.manager.persist(loginBloqueado);
	}

	public void remove(LoginBloqueado loginBloqueado) {
		this.manager.remove(loginBloqueado);
	}

	public LoginBloqueado buscaPorId(Integer id) {
		return this.manager.find(LoginBloqueado.class, id);
	}

	public List<LoginBloqueado> lista() {
		CriteriaQuery<LoginBloqueado> query = this.manager.getCriteriaBuilder().createQuery(LoginBloqueado.class);
		query.select(query.from(LoginBloqueado.class));

		return this.manager.createQuery(query).getResultList();
	}

	public boolean ipExiste(String ip) {
		Query query = this.manager.createQuery("select count(l) from LoginBloqueado l where l.ip = :ip", Long.class);
		query.setParameter("ip", ip);
		Integer count = ((Long) query.getSingleResult()).intValue();
		return count > 0;
	}

}
