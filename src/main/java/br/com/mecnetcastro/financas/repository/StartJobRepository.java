package br.com.mecnetcastro.financas.repository;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.com.mecnetcastro.financas.model.Tarefa;

public class StartJobRepository {

	@Inject
	private EntityManager manager;

	public List<Tarefa> tarefasPendentes() {
		String jpql = "SELECT t FROM Tarefa t JOIN FETCH t.conta JOIN FETCH t.usuario "
							 + "WHERE t.status = 'PENDENTE' "
							 + "AND t.conta.id = 1 AND YEAR(t.dataVencimento) = :pAnoVencimento AND MONTH(t.dataVencimento) = :pMesVencimento";
		TypedQuery<Tarefa> query = this.manager.createQuery(jpql, Tarefa.class);
		query.setParameter("pAnoVencimento", Calendar.getInstance().get(Calendar.YEAR));
		query.setParameter("pMesVencimento", GregorianCalendar.getInstance().get(Calendar.MONTH) + 1);

		return query.getResultList();
	}

}
