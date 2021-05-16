package br.com.mecnetcastro.financas.controller.relatorio;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.report.UtilReport;

@ViewController
public class MovimentacoesPorValorETipoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MovimentacaoRepository movimentacaoRepository;

	private Conta conta = new Conta();
	private BigDecimal valor;
	private TipoMovimentacao tipoMovimentacao;

	private List<Movimentacao> movimentacoes;

	public void lista() {
		this.movimentacoes = this.movimentacaoRepository
				.listaMovimentacoesPorValoeETipo(conta, valor, tipoMovimentacao);
		if (this.movimentacoes.isEmpty() || this.movimentacoes == null) {
			FacesUtil.addWarnMessage("Registro não encontrado!");
		}
	}

	@SuppressWarnings("rawtypes")
	public void relatorioPorValorETipo() {
		HashMap parametros = new HashMap();
		UtilReport.imprimeRelatorio("movimentacaoPorValorETipo", parametros, movimentacoes);
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
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

}
