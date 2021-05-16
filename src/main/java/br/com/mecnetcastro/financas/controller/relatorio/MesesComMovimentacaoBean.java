package br.com.mecnetcastro.financas.controller.relatorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;
import br.com.mecnetcastro.financas.model.vo.ValorPorMes;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.report.UtilReport;

@ViewController
public class MesesComMovimentacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MovimentacaoRepository movimentacaoRepository;

	private Conta conta = new Conta();
	private BigDecimal soma = new BigDecimal("0.0");
	private TipoMovimentacao tipoMovimentacao;
	private Integer ano;

	private List<ValorPorMes> valoresPorMeses;
	private List<Integer> anosMovimentacao = new ArrayList<>();

	public void lista() {
		valoresPorMeses = this.movimentacaoRepository.listaMesesComMovimentacoes(conta, tipoMovimentacao, ano);
		somaTotalMov();
		if (valoresPorMeses.isEmpty() || valoresPorMeses == null) {
			FacesUtil.addWarnMessage("Registro não encontrado!");
		}
	}

	@SuppressWarnings("rawtypes")
	public void relatorioMesesComMovimentacao() {
		HashMap parametros = new HashMap();
		UtilReport.imprimeRelatorio("mesesComMovimentacao", parametros, valoresPorMeses);
	}

	private BigDecimal somaTotalMov() {
		soma = new BigDecimal("0.0");
		for (ValorPorMes totalMov : valoresPorMeses) {
			soma = soma.add(totalMov.getValor());
		}
		return soma;
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public TipoMovimentacao getTipoMovimentacao() {
		return tipoMovimentacao;
	}

	public void setTipoMovimentacao(TipoMovimentacao tipoMovimentacao) {
		this.tipoMovimentacao = tipoMovimentacao;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public BigDecimal getSoma() {
		return soma;
	}

	public List<ValorPorMes> getValoresPorMeses() {
		return valoresPorMeses;
	}

	public List<Integer> getAnosMovimentacao() {
		anosMovimentacao = this.movimentacaoRepository.anosMovimentacao();
		return anosMovimentacao;
	}
}
