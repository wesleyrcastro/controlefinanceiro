package br.com.mecnetcastro.financas.util.security;

import java.io.Serializable;

import br.com.mecnetcastro.financas.annotation.stereotype.SessionController;
import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.model.enumeration.Cargo;

@SessionController
public class UsuarioLogado implements Serializable {

	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	public Boolean getControleAcesso() throws Exception {
		return (usuario != null && usuario.getCargo() != null && usuario.getCargo().equals(Cargo.GERENTE));
	}
	
	public void logar(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public void deslogar() {
		this.usuario = null;
	}

	public boolean isLogado() {
		return usuario != null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

}
