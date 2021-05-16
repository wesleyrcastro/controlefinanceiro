package br.com.mecnetcastro.financas.controller.loginBloqueado;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.LoginBloqueado;
import br.com.mecnetcastro.financas.repository.LoginBloqueadoRepository;
import br.com.mecnetcastro.financas.service.LoginBloqueadoService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ListaLoginBloqueadoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private LoginBloqueadoRepository loginBloqueadoRepository;
	@Inject
	private LoginBloqueadoService loginBloqueadoService;

	private LoginBloqueado loginBloqueadoSelecionado;
	private List<LoginBloqueado> loginsBloqueados;

	public void excluir() {
		this.loginBloqueadoService.excluir(loginBloqueadoSelecionado);
		listaTodos();

		FacesUtil.addInfoMessage("Usuário " + loginBloqueadoSelecionado.getUsuario() + " desbloqueado com sucesso!");
	}

	public void listaTodos() {
		loginsBloqueados = this.loginBloqueadoRepository.lista();
	}

	public LoginBloqueado getLoginBloqueadoSelecionado() {
		return loginBloqueadoSelecionado;
	}

	public void setLoginBloqueadoSelecionado(LoginBloqueado loginBloqueadoSelecionado) {
		this.loginBloqueadoSelecionado = loginBloqueadoSelecionado;
	}

	public List<LoginBloqueado> getLoginsBloqueados() {
		return loginsBloqueados;
	}

}
