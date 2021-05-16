package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;

import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.model.Usuario;

public class TarefaRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adiciona(Tarefa tarefa) {
		this.manager.persist(tarefa);
	}

	public void atualiza(Tarefa tarefa) {
		this.manager.merge(tarefa);
	}

	public void remove(Tarefa tarefa) {
		this.manager.remove(tarefa);
	}

	public Tarefa buscaPorId(Integer id) {
		return this.manager.find(Tarefa.class, id);
	}

	public List<Tarefa> lista() {
		return this.manager.createQuery("SELECT t FROM Tarefa t JOIN FETCH t.conta JOIN FETCH t.usuario ORDER BY t.dataInclusao desc", Tarefa.class).getResultList();
	}
	
	public boolean existeTarefasAssociadaAConta(Conta conta) {
		Query query = this.manager.createQuery("SELECT t FROM Tarefa t JOIN FETCH t.conta JOIN FETCH t.usuario WHERE t.conta = :pConta");
		query.setParameter("pConta", conta);

		boolean encontrado = query.getResultList().isEmpty();

		return encontrado;
	}
	
	public boolean existeUsuarioAssociadoATarefa(Usuario usuario) {
		Query query = this.manager.createQuery("SELECT t FROM Tarefa t JOIN FETCH t.usuario JOIN FETCH t.conta WHERE t.usuario = :pUsuario");
		query.setParameter("pUsuario", usuario);

		boolean encontrado = query.getResultList().isEmpty();

		return encontrado;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tarefa> pesquisaTarefas(String titular, Date dataInclusaoDe, Date dataInclusaoAte, Date dataVencimentoDe, Date dataVencimentoAte) {
		
		Session session = this.manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Tarefa.class);
		
		
		if (StringUtils.isNotBlank(titular)) {
			criteria.createAlias("conta", "c");
			criteria.add(Restrictions.ilike("c.titular", titular, MatchMode.ANYWHERE));
		}

//		if (dataInclusaoDe != null) {
//			criteria.add(Restrictions.ge("dataInclusao", dataInclusaoDe));
//		}
//
//		if (dataInclusaoAte != null) {
//			criteria.add(Restrictions.le("dataInclusao", dataInclusaoAte));
//		}

		
		if (dataInclusaoDe != null && dataInclusaoAte != null) {
			criteria.add(Restrictions.between("dataInclusao", dataInclusaoDe, dataInclusaoAte));
		}
		
		if (dataVencimentoDe != null && dataVencimentoAte != null) {
			criteria.add(Restrictions.between("dataVencimento", dataVencimentoDe, dataVencimentoAte));
		}
		
		return criteria.list();
	}
	
}
