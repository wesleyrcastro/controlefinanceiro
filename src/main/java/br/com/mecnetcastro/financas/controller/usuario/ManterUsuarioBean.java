package br.com.mecnetcastro.financas.controller.usuario;

import java.io.Serializable;

import javax.inject.Inject;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.model.enumeration.Cargo;
import br.com.mecnetcastro.financas.service.UsuarioService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.security.TransformaStringSHA512;

@ViewController
public class ManterUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioService usuarioService;

	private Usuario usuario = new Usuario();

	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Informe uma senha valida!")
	@Length(min = 8, max = 20, message = "A senha deve ter entre {min} e {max} caracteres")
	private String senha;
	@SafeHtml(whitelistType = WhiteListType.NONE, message = "Informe uma confirmação de senha valida!")
	@Length(min = 8, max = 20, message = "A confirmação da senha deve ter entre {min} e {max} caracteres")
	private String confirmacaoSenha;

	public void salva() {
		if (senha.equals(confirmacaoSenha)) {
			senha = TransformaStringSHA512.sha512(senha);
			usuario.setSenha(senha);

			this.usuarioService.salvar(usuario);
			limpaFormulario();

			FacesUtil.addInfoMessage("Usuário salvo com sucesso!");
		} else {
			FacesUtil.addErrorMessage("As senhas não conferem!");

		}

	}

	private void limpaFormulario() {
		this.usuario = new Usuario();
		senha = null;
		confirmacaoSenha = null;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getConfirmacaoSenha() {
		return confirmacaoSenha;
	}

	public void setConfirmacaoSenha(String confirmacaoSenha) {
		this.confirmacaoSenha = confirmacaoSenha;
	}

	public Cargo[] getCargos() {
		return Cargo.values();
	}

}
