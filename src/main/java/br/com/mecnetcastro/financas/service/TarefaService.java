package br.com.mecnetcastro.financas.service;

import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.repository.TarefaRepository;

public class TarefaService {

	@Inject
	private TarefaRepository tarefaRepository;

	@Transactional
	public void salvar(Tarefa tarefa) {
		if (tarefa.getId() == null) {
			this.tarefaRepository.adiciona(tarefa);
		} else {
			this.tarefaRepository.atualiza(tarefa);
		}

	}

	@Transactional
	public void excluir(Tarefa tarefa) {
		tarefa = this.tarefaRepository.buscaPorId(tarefa.getId());
		this.tarefaRepository.remove(tarefa);
	}
}
