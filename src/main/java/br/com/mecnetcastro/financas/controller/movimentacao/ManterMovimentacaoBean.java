package br.com.mecnetcastro.financas.controller.movimentacao;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Movimentacao;
import br.com.mecnetcastro.financas.model.Subcategoria;
import br.com.mecnetcastro.financas.model.enumeration.Categoria;
import br.com.mecnetcastro.financas.model.enumeration.TipoMovimentacao;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.repository.SubcategoriaRepository;
import br.com.mecnetcastro.financas.service.MovimentacaoService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;

@ViewController
public class ManterMovimentacaoBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private MovimentacaoService movimentacaoService;
	@Inject
	private ContaRepository contaRepository;
	@Inject
	private SubcategoriaRepository subcategoriaRepository;

	private Movimentacao movimentacao = new Movimentacao();

	private List<Conta> listaContasParaCombo;
	private List<Subcategoria> listaSubcategoriasParaCombo;

	public void salva() {
		this.movimentacaoService.salva(movimentacao);
		limpaFormulario();

		FacesUtil.addInfoMessage("Movimentação salvo com sucesso!");
	}

	public void inicializa() {
		if (FacesUtil.isNotPostback()) {
			listaContasParaCombo = this.contaRepository.lista();
			listaSubcategoriasParaCombo = this.subcategoriaRepository.lista();
		}
	}

	public void limpaFormulario() {
		this.movimentacao = new Movimentacao();
	}

	public Movimentacao getMovimentacao() {
		return movimentacao;
	}

	public void setMovimentacao(Movimentacao movimentacao) {
		this.movimentacao = movimentacao;
	}

	public List<Conta> getListaContasParaCombo() {
		return listaContasParaCombo;
	}

	public List<Subcategoria> getListaSubcategoriasParaCombo() {
		return listaSubcategoriasParaCombo;
	}

	public TipoMovimentacao[] getTipoMovimentacoes() {
		return TipoMovimentacao.values();
	}

	public Categoria[] getCategorias() {
		return Categoria.values();
	}

}
