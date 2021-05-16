package br.com.mecnetcastro.financas.controller.relatorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.model.Subcategoria;
import br.com.mecnetcastro.financas.model.enumeration.Mes;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;
import br.com.mecnetcastro.financas.repository.SubcategoriaRepository;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.report.UtilReport;

@ViewController
public class PesquisaMovimentacoesBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MovimentacaoRepository movimentacaoRepository;
	@Inject
	private SubcategoriaRepository categoriaRepository;

	private List<Movimentacao> movimentacoes;
	private List<Subcategoria> categorias;
	private List<Integer> anosMovimentacao = new ArrayList<>();

	private Conta conta = new Conta();
	private Subcategoria categoria = new Subcategoria();
	private Mes mes;
	private Integer ano;
	private TipoMovimentacao tipoMovimentacao;
	private BigDecimal soma = new BigDecimal("0.0");

	public void pesquisa() {
		movimentacoes = this.movimentacaoRepository.pesquisa(conta, categoria, tipoMovimentacao,
				mes != null ? mes.getValor() : 0, ano != null ? ano : 0);
		somaTotalMov();

		if (movimentacoes.isEmpty() || movimentacoes == null) {
			FacesUtil.addWarnMessage("Registro não encontrado!");
		}
	}

	private BigDecimal somaTotalMov() {
		soma = new BigDecimal("0.0");
		for (Movimentacao totalMov : movimentacoes) {
			soma = soma.add(totalMov.getValor());
		}
		return soma;
	}

	@SuppressWarnings("rawtypes")
	public void relatorioPorContaComFiltro() {
		HashMap parametros = new HashMap();
		UtilReport.imprimeRelatorio("relatorioPorContaComFiltro", parametros, movimentacoes);
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Subcategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Subcategoria categoria) {
		this.categoria = categoria;
	}

	public Mes getMes() {
		return mes;
	}

	public void setMes(Mes mes) {
		this.mes = mes;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public List<Movimentacao> getMovimentacoes() {
		return movimentacoes;
	}

	public List<Subcategoria> getCategorias() {
		if (categorias == null || categorias.isEmpty()) {
			categorias = this.categoriaRepository.lista();
		}
		return categorias;
	}

	public List<Integer> getAnosMovimentacao() {
		anosMovimentacao = this.movimentacaoRepository.anosMovimentacao();
		return anosMovimentacao;
	}

	public BigDecimal getSoma() {
		return soma;
	}

	public TipoMovimentacao[] getTipoMovimentacoes() {
		return TipoMovimentacao.values();
	}

	public Mes[] getMeses() {
		return Mes.values();
	}
}
