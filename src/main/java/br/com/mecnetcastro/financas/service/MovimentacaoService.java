package br.com.mecnetcastro.financas.service;

import javax.inject.Inject;

import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;

public class MovimentacaoService {

	@Inject
	private MovimentacaoRepository movimentacaoRepository;

	@Inject
	private ContaRepository contaRepository;

	@Transactional
	public void salva(Movimentacao movimentacao) {
		Conta conta = this.contaRepository.buscaPorId(movimentacao.getConta().getId());
		movimentacao.setConta(conta);

		this.movimentacaoRepository.adiciona(movimentacao);
	}

	@Transactional
	public void excluir(Movimentacao movimentacao) {
		movimentacao = this.movimentacaoRepository.buscaPorId(movimentacao.getId());
		this.movimentacaoRepository.remove(movimentacao);
	}
}
