package br.com.mecnetcastro.financas.service;

import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.repository.StartJobRepository;
import br.com.mecnetcastro.financas.util.email.EmailUtil;

public class StartJobService {

	@Inject
	private StartJobRepository repository;

	public void execute() {
		List<Tarefa> tarefas = repository.tarefasPendentes();
		EmailUtil.enviaEmail(tarefas);
	}
}
