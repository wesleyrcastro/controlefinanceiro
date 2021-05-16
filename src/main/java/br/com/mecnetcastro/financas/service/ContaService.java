package br.com.mecnetcastro.financas.service;

import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;
import br.com.mecnetcastro.financas.repository.TarefaRepository;

public class ContaService {

	@Inject
	private ContaRepository contaRepository;
	@Inject
	private MovimentacaoRepository movimentacaoRepository;
	@Inject
	private TarefaRepository tarefaRepository;

	@Transactional
	public void salvar(Conta conta) {
		if (conta.getId() == null) {
			this.contaRepository.adiciona(conta);
		} else {
			this.contaRepository.atualiza(conta);
		}

	}

	@Transactional
	public void excluir(Conta conta) {
		conta = this.contaRepository.buscaPorId(conta.getId());
		boolean existeMovimentacoesComEstaConta = this.movimentacaoRepository.existeMovimentacoesAssociadaAConta(conta);
		boolean existeTarefasComEstaConta = this.tarefaRepository.existeTarefasAssociadaAConta(conta);

		if (existeMovimentacoesComEstaConta && existeTarefasComEstaConta) {
			this.contaRepository.remove(conta);
		} else {
			throw new NegocioException("Existe registro associado a está conta. Não pode excluir, mas pode editar.");
		}
	}
}
