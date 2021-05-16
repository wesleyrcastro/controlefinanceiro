package br.com.mecnetcastro.financas.service;

import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.repository.TarefaRepository;
import br.com.mecnetcastro.financas.repository.UsuarioRepository;

public class UsuarioService {

	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private TarefaRepository tarefaRepository;

	@Transactional
	public void salvar(Usuario usuario) {
		boolean loginJaExiste = this.usuarioRepository.loginJaExiste(usuario);
		
		if(loginJaExiste){
		if (usuario.getId() == null) {
			this.usuarioRepository.adiciona(usuario);
		} else {
			this.usuarioRepository.atualiza(usuario);
		}
		}else{
			throw new NegocioException("Login já existente!");
		}

	}

	@Transactional
	public void excluir(Usuario usuario) {
		Long count = this.usuarioRepository.contaUsuarios();
		boolean existeUsuarioComEstaTarefa = this.tarefaRepository.existeUsuarioAssociadoATarefa(usuario);
		if (count != 1 && existeUsuarioComEstaTarefa) {
			usuario = this.usuarioRepository.buscaPorId(usuario.getId());
			this.usuarioRepository.remove(usuario);
		} else {
			throw new NegocioException("Não pode excluir!");
			
		}

	}
}
