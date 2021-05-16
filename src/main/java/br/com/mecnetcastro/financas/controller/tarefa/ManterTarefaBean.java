package br.com.mecnetcastro.financas.controller.tarefa;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.mecnetcastro.financas.annotation.stereotype.ViewController;
import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.model.enumeration.Status;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.repository.UsuarioRepository;
import br.com.mecnetcastro.financas.service.TarefaService;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.security.UsuarioLogado;

@ViewController
public class ManterTarefaBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private TarefaService tarefaService;
	@Inject
	private ContaRepository contaRepository;
	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private UsuarioLogado usuarioLogado;

	private Tarefa tarefa = new Tarefa();
	private Usuario usuario;

	private List<Conta> listaContasParaCombo;
	
	public void inicializa() {
		if (FacesUtil.isNotPostback()) {
			listaContasParaCombo = this.contaRepository.lista();
			usuario = this.usuarioRepository.usuarioLogado(usuarioLogado);
		}
	}

	public void salva() {
		tarefa.setUsuario(usuario);
		this.tarefaService.salvar(tarefa);
		limpaFormulario();

		FacesUtil.addInfoMessage("Tarefa do mês salvo com sucesso!");
	}

	public void limpaFormulario() {
		this.tarefa = new Tarefa();
	}

	public Tarefa getTarefa() {
		return tarefa;
	}

	public void setTarefa(Tarefa tarefa) {
		this.tarefa = tarefa;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public List<Conta> getListaContasParaCombo() {
		return listaContasParaCombo;
	}

	public Status[] getStatus() {
		return Status.values();
	}

}
