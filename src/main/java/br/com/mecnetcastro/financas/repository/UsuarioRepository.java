package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaQuery;

import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.util.security.TransformaStringSHA512;
import br.com.mecnetcastro.financas.util.security.UsuarioLogado;

public class UsuarioRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adiciona(Usuario usuario) {
		this.manager.persist(usuario);
	}

	public void atualiza(Usuario usuario) {
		this.manager.merge(usuario);
	}

	public Usuario buscaPorId(Integer id) {
		return this.manager.find(Usuario.class, id);
	}

	public void remove(Usuario usuario) {
		this.manager.remove(usuario);
	}

	public List<Usuario> lista() {
		CriteriaQuery<Usuario> query = this.manager.getCriteriaBuilder().createQuery(Usuario.class);
		query.select(query.from(Usuario.class));

		return this.manager.createQuery(query).getResultList();
	}
	
	public Long contaUsuarios() {
		Query query = this.manager.createQuery("SELECT COUNT(u) FROM Usuario u");
		Long count = (Long) query.getSingleResult();
		
		return count;
	}

	public List<Usuario> buscaPorNome(String nome) {
		String jpql = "SELECT u FROM Usuario u WHERE UPPER (u.nome) LIKE UPPER (:pUsuario) ORDER BY u.nome";
		TypedQuery<Usuario> query = this.manager.createQuery(jpql, Usuario.class);
		query.setParameter("pUsuario", nome + "%");

		return query.getResultList();
	}

	public Usuario usuarioExiste(Usuario usuario) {
		Query query = this.manager.createNativeQuery(
				"SELECT * FROM USUARIO WHERE BINARY login = :pLogin AND BINARY senha = :pSenha", Usuario.class);
		query.setParameter("pLogin", usuario.getLogin());
		query.setParameter("pSenha", TransformaStringSHA512.sha512(usuario.getSenha()));
		try {
			return (Usuario) query.getSingleResult();
		} catch (NoResultException nre) {
			return null;
		}
	}

	public boolean loginJaExiste(Usuario usuario) {
			Query query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.login = :pLogin" + (usuario.getId() != null ? " AND u.id != " + usuario.getId() : ""));
			query.setParameter("pLogin", usuario.getLogin());

			boolean encontrado = query.getResultList().isEmpty();

			return encontrado;
		}

	public Usuario usuarioLogado(UsuarioLogado usuarioLogado) {
		TypedQuery<Usuario> query = this.manager.createQuery("SELECT u FROM Usuario u WHERE u.login = :pLogin", Usuario.class);
		query.setParameter("pLogin", usuarioLogado.getUsuario().getLogin());
		
		return query.getSingleResult();
	}
}
