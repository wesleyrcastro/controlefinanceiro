package br.com.mecnetcastro.financas.controller.usuario;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.repository.UsuarioRepository;
import br.com.mecnetcastro.financas.service.UsuarioService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ListarUsuarioBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private UsuarioService usuarioService;

	private List<Usuario> usuarios;
	private String usuarioExiste;
	private Usuario usuarioSelecionado;

	public void excluir() {
		this.usuarioService.excluir(usuarioSelecionado);
		listaTodos();

		FacesUtil.addInfoMessage("Usuário " + usuarioSelecionado.getNome() + " excluído com sucesso!");
	}

	public void pesquisaUsuario() {
		this.usuarios = usuarioRepository.buscaPorNome(usuarioExiste);
		usuarioExiste = "";

		if (usuarios == null || this.usuarios.isEmpty()) {
			FacesUtil.addWarnMessage("Registro não encontrado!");
		}

	}

	public void listaTodos() {
		usuarios = this.usuarioRepository.lista();
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public String getUsuarioExiste() {
		return usuarioExiste;
	}

	public void setUsuarioExiste(String usuarioExiste) {
		this.usuarioExiste = usuarioExiste;
	}

	public Usuario getUsuarioSelecionado() {
		return usuarioSelecionado;
	}

	public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
		this.usuarioSelecionado = usuarioSelecionado;
	}

}
