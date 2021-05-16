package br.com.mecnetcastro.financas.repository;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.model.Subcategoria;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;
import br.com.mecnetcastro.financas.model.vo.FiltroMovimentacao;
import br.com.mecnetcastro.financas.model.vo.ValorPorMes;
import br.com.mecnetcastro.financas.model.vo.ValorPorSubcategoria;

public class MovimentacaoRepository implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private EntityManager manager;

	public void adiciona(Movimentacao movimentacao) {
		this.manager.persist(movimentacao);
	}

	public Movimentacao buscaPorId(Integer id) {
		return this.manager.find(Movimentacao.class, id);
	}

	public void remove(Movimentacao movimentacao) {
		this.manager.remove(movimentacao);
	}

	public int contaTodos(FiltroMovimentacao filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setProjection(Projections.rowCount());

		return ((Number) criteria.uniqueResult()).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Movimentacao> listaTodosPaginada(FiltroMovimentacao filtro) {
		Criteria criteria = criarCriteriaParaFiltro(filtro);

		criteria.setFirstResult(filtro.getPrimeiroRegistro());
		criteria.setMaxResults(filtro.getQuantidadeRegistros());

		if (filtro.isAscendente() && filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.asc(filtro.getPropriedadeOrdenacao()));
		} else if (filtro.getPropriedadeOrdenacao() != null) {
			criteria.addOrder(Order.desc(filtro.getPropriedadeOrdenacao()));
		}

		return criteria.list();
	}

	private Criteria criarCriteriaParaFiltro(FiltroMovimentacao filtro) {
		Session session = manager.unwrap(Session.class);
		Criteria criteria = session.createCriteria(Movimentacao.class).createAlias("subcategoria", "s");

		if (StringUtils.isNotEmpty(filtro.getSubcategoria())) {
			criteria.add(Restrictions.ilike("s.nome", filtro.getSubcategoria(), MatchMode.ANYWHERE));
		}

		if (filtro.getDataDe() != null) {
			criteria.add(Restrictions.ge("data", filtro.getDataDe()));
		}

		if (filtro.getDataAte() != null) {
			criteria.add(Restrictions.le("data", filtro.getDataAte()));
		}

		return criteria;
	}

	@SuppressWarnings("unchecked")
	public List<Integer> anosMovimentacao() {
		Query query = this.manager.createQuery("SELECT DISTINCT YEAR(m.data) FROM Movimentacao m", Integer.class);

		return query.getResultList();
	}

	public boolean existeMovimentacoesAssociadaAConta(Conta conta) {
		Query query = this.manager.createQuery("SELECT m FROM Movimentacao m JOIN FETCH m.conta JOIN FETCH m.subcategoria WHERE m.conta = :pConta");
		query.setParameter("pConta", conta);

		boolean encontrado = query.getResultList().isEmpty();

		return encontrado;
	}

	public List<ValorPorSubcategoria> graficoLinhasPorSubcategoriaEMes() {
		String jpql = "SELECT "
				+ "new br.com.mecnetcastro.financas.model.vo.ValorPorSubcategoria(SUM(m.valor), m.subcategoria, m.data) "
				+ "FROM Movimentacao m " 
				+ "WHERE YEAR(m.data) = :ano " 
				+ "GROUP BY MONTH(m.data), m.subcategoria.nome";

		TypedQuery<ValorPorSubcategoria> query = this.manager.createQuery(jpql, ValorPorSubcategoria.class);
		query.setParameter("ano", Calendar.getInstance().get(Calendar.YEAR));

		List<ValorPorSubcategoria> results = query.getResultList();
		return results;
	}

	public List<Movimentacao> listaMovimentacoesPorValoeETipo(Conta conta, BigDecimal valor,
			TipoMovimentacao tipoMovimentacao) {
		String jpql = "SELECT m FROM Movimentacao m JOIN FETCH m.conta JOIN FETCH m.subcategoria "
				    + "WHERE m.conta = :pConta AND m.valor <= :pValor AND m.tipoMovimentacao = :pTipoMovimentacao ORDER BY m.data DESC";
		TypedQuery<Movimentacao> query = this.manager.createQuery(jpql, Movimentacao.class);
		query.setParameter("pConta", conta);
		query.setParameter("pValor", valor);
		query.setParameter("pTipoMovimentacao", tipoMovimentacao);

		return query.getResultList();
	}

	public List<ValorPorMes> listaMesesComMovimentacoes(Conta conta, TipoMovimentacao tipoMovimentacao, Integer ano) {
		String jpql = "SELECT new br.com.mecnetcastro.financas.model.vo.ValorPorMes(MONTH(m.data), YEAR(m.data), SUM(m.valor)) "
				+ "FROM Movimentacao m WHERE YEAR(m.data) = :pAno "
				+ "GROUP BY MONTH(m.data), m.conta, m.tipoMovimentacao "
				+ "HAVING m.conta = :pConta AND m.tipoMovimentacao = :pTipoMovimentacao ORDER BY m.data";

		TypedQuery<ValorPorMes> query = this.manager.createQuery(jpql, ValorPorMes.class);
		query.setParameter("pConta", conta);
		query.setParameter("pTipoMovimentacao", tipoMovimentacao);
		query.setParameter("pAno", ano);

		return query.getResultList();
	}

	public List<Movimentacao> pesquisa(Conta conta, Subcategoria subcategoria, TipoMovimentacao tipoMovimentacao,
			Integer mes, Integer ano) {
		CriteriaBuilder cb = this.manager.getCriteriaBuilder();
		CriteriaQuery<Movimentacao> criterio = cb.createQuery(Movimentacao.class);
		Root<Movimentacao> movimentacao = criterio.from(Movimentacao.class);
		Predicate conjunction = cb.conjunction();

		if (conta != null) {
			conjunction = cb.and(conjunction, cb.and(cb.equal(movimentacao.<Conta> get("conta"), conta)));
		}

		if (subcategoria != null) {
			conjunction = cb.and(conjunction,
					cb.and(cb.equal(movimentacao.<Subcategoria> get("subcategoria"), subcategoria)));
		}

		if (tipoMovimentacao != null) {
			conjunction = cb.and(conjunction,
					cb.and(cb.equal(movimentacao.<TipoMovimentacao> get("tipoMovimentacao"), tipoMovimentacao)));
		}

		if (mes != 0) {
			Expression<Integer> expression = cb.function("month", Integer.class, movimentacao.<Date> get("data"));
			conjunction = cb.and(conjunction, cb.equal(expression, mes));
		}

		if (ano != 0) {
			Expression<Integer> expression = cb.function("year", Integer.class, movimentacao.<Date> get("data"));
			conjunction = cb.and(conjunction, cb.equal(expression, ano));
		}

		movimentacao.fetch("conta");
		movimentacao.fetch("subcategoria");
		criterio.where(conjunction);
		return this.manager.createQuery(criterio).getResultList();
	}

}
