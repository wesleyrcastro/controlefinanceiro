package br.com.mecnetcastro.financas.controller.conta;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.enumeration.Banco;
import br.com.mecnetcastro.financas.service.ContaService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ManterContaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ContaService contaService;

	private Conta conta = new Conta();

	public void salva() {
		this.contaService.salvar(conta);
		limpaFormulario();

		FacesUtil.addInfoMessage("Conta salvo com sucesso!");
	}

	public void limpaFormulario() {
		this.conta = new Conta();
	}

	public Conta getConta() {
		return conta;
	}

	public void setConta(Conta conta) {
		this.conta = conta;
	}

	public Banco[] getBancos() {
		return Banco.values();
	}

}
