package br.com.mecnetcastro.financas.service;

import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.model.LoginBloqueado;
import br.com.mecnetcastro.financas.repository.LoginBloqueadoRepository;

public class LoginBloqueadoService {

	@Inject
	private LoginBloqueadoRepository bloqueadoRepository;

	@Transactional
	public void salvar(LoginBloqueado loginBloqueado) {
			this.bloqueadoRepository.adiciona(loginBloqueado);

	}

	@Transactional
	public void excluir(LoginBloqueado loginBloqueado) {
		loginBloqueado = this.bloqueadoRepository.buscaPorId(loginBloqueado.getId());
			this.bloqueadoRepository.remove(loginBloqueado);
		}
	}
