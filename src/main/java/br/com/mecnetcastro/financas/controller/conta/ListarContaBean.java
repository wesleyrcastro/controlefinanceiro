package br.com.mecnetcastro.financas.controller.conta;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.service.ContaService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ListarContaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContaRepository contaRepository;
	@Inject
	private ContaService contaService;

	private Conta contaSelecionada;

	private List<Conta> contas;
	private List<Conta> listaContasParaCombo;

	private String titularExiste;

	public void excluir() {
		this.contaService.excluir(contaSelecionada);
		listaTodos();

		FacesUtil.addInfoMessage("Conta " + contaSelecionada.getTitular() + " excluído com sucesso!");
	}

	public void pesquisaTitular() {
		contas = this.contaRepository.buscaPorTitular(titularExiste);
		titularExiste = "";

		if (contas.isEmpty() || contas == null) {
			FacesUtil.addWarnMessage("Registro não encontrado!");
		}
	}

	public void listaTodos() {
		contas = this.contaRepository.lista();
	}

	public Conta getContaSelecionada() {
		return contaSelecionada;
	}

	public void setContaSelecionada(Conta contaSelecionada) {
		this.contaSelecionada = contaSelecionada;
	}

	public List<Conta> getContas() {
		return contas;
	}

	public List<Conta> getListaContasParaCombo() {
		if (listaContasParaCombo == null || listaContasParaCombo.isEmpty()) {
			listaContasParaCombo = this.contaRepository.lista();
		}
		return listaContasParaCombo;
	}

	public String getTitularExiste() {
		return titularExiste;
	}

	public void setTitularExiste(String titularExiste) {
		this.titularExiste = titularExiste;
	}

}
