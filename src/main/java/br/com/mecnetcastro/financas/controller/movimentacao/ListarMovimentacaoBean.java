package br.com.mecnetcastro.financas.controller.movimentacao;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.model.vo.FiltroMovimentacao;
import br.com.mecnetcastro.financas.repository.MovimentacaoRepository;
import br.com.mecnetcastro.financas.service.MovimentacaoService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ListarMovimentacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MovimentacaoRepository movimentacaoRepository;
	@Inject
	private MovimentacaoService movimentacaoService;

	private Movimentacao movimentacaoSelecionada;
	private FiltroMovimentacao filtro = new FiltroMovimentacao();

	private LazyDataModel<Movimentacao> dataModelMovimentacoes;

	public ListarMovimentacaoBean() {
		dataModelMovimentacoes = new LazyDataModel<Movimentacao>() {

			private static final long serialVersionUID = 1L;

			@Override
			public List<Movimentacao> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				filtro.setPrimeiroRegistro(first);
				filtro.setQuantidadeRegistros(pageSize);
				filtro.setAscendente(SortOrder.ASCENDING.equals(sortOrder));
				filtro.setPropriedadeOrdenacao(sortField);

				setRowCount(movimentacaoRepository.contaTodos(filtro));

				return movimentacaoRepository.listaTodosPaginada(filtro);
			}

		};
	}

	public void excluir() {
		this.movimentacaoService.excluir(movimentacaoSelecionada);
		getDataModelMovimentacoes();

		FacesUtil.addInfoMessage("Movimentação excluído com sucesso!");
	}

	public Movimentacao getMovimentacaoSelecionada() {
		return movimentacaoSelecionada;
	}

	public void setMovimentacaoSelecionada(Movimentacao movimentacaoSelecionada) {
		this.movimentacaoSelecionada = movimentacaoSelecionada;
	}

	public FiltroMovimentacao getFiltro() {
		return filtro;
	}

	public LazyDataModel<Movimentacao> getDataModelMovimentacoes() {
		return dataModelMovimentacoes;
	}

}
