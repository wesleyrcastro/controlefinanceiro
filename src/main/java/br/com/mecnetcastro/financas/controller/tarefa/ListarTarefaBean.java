package br.com.mecnetcastro.financas.controller.tarefa;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.repository.TarefaRepository;
import br.com.mecnetcastro.financas.service.TarefaService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.report.UtilReport;

@ViewController
public class ListarTarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaRepository tarefaRepository;
	@Inject
	private TarefaService tarefaService;

	private List<Tarefa> tarefas;

	private Tarefa tarefaSelecionada;
	private BigDecimal soma = new BigDecimal("0.0");
	private String titular;
	
	private Date dataInclusaoDe;
	private Date dataInclusaoAte;
	private Date dataVencimentoDe;
	private Date dataVencimentoAte;

	public void excluir() {
		this.tarefaService.excluir(tarefaSelecionada);
		listaTodos();

		FacesUtil.addInfoMessage("Tarefa do mês excluído com sucesso!");
	}

	public void pesquisar() {
		tarefas = this.tarefaRepository.pesquisaTarefas(titular, dataInclusaoDe, dataInclusaoAte, dataVencimentoDe, dataVencimentoAte);
		somaTarefa();
	}
	
	@SuppressWarnings("rawtypes")
	public void relatorioDeTarefas() {
		HashMap parametros = new HashMap();
		UtilReport.imprimeRelatorio("relatorioDeTarefas", parametros, tarefas);
	}

	private BigDecimal somaTarefa() {
		soma = new BigDecimal("0.0");
		for (Tarefa tarefa : tarefas) {
			soma = soma.add(tarefa.getValor());
		}
		return soma;
	}

	public void listaTodos() {
		tarefas = this.tarefaRepository.lista();
		somaTarefa();
	}

	public List<Tarefa> getTarefas() {
		return tarefas;
	}

	public Tarefa getTarefaSelecionada() {
		return tarefaSelecionada;
	}

	public void setTarefaSelecionada(Tarefa tarefaSelecionada) {
		this.tarefaSelecionada = tarefaSelecionada;
	}

	public BigDecimal getSoma() {
		return soma;
	}
	
	public String getTitular() {
		return titular;
	}

	public void setTitular(String titular) {
		this.titular = titular;
	}

	public Date getDataInclusaoDe() {
		return dataInclusaoDe;
	}

	public void setDataInclusaoDe(Date dataInclusaoDe) {
		this.dataInclusaoDe = dataInclusaoDe;
	}

	public Date getDataInclusaoAte() {
		return dataInclusaoAte;
	}

	public void setDataInclusaoAte(Date dataInclusaoAte) {
		this.dataInclusaoAte = dataInclusaoAte;
	}

	public Date getDataVencimentoDe() {
		return dataVencimentoDe;
	}

	public void setDataVencimentoDe(Date dataVencimentoDe) {
		this.dataVencimentoDe = dataVencimentoDe;
	}

	public Date getDataVencimentoAte() {
		return dataVencimentoAte;
	}

	public void setDataVencimentoAte(Date dataVencimentoAte) {
		this.dataVencimentoAte = dataVencimentoAte;
	}

}
