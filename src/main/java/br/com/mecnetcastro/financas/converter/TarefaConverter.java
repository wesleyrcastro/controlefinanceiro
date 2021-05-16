package br.com.mecnetcastro.financas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mecnetcastro.financas.model.Tarefa;
import br.com.mecnetcastro.financas.repository.TarefaRepository;
import br.com.mecnetcastro.financas.util.cdi.CDILocator;

@FacesConverter(forClass = Tarefa.class)
public class TarefaConverter implements Converter {

	private TarefaRepository repository;

	public TarefaConverter() {
		this.repository = CDILocator.getBean(TarefaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		if (value == null || value.isEmpty() || "null".equals(value)) {
			return null;
		}
		Integer id = Integer.valueOf(value);
		Tarefa tarefa = this.repository.buscaPorId(id);
		return tarefa;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		Tarefa tarefa = (Tarefa) value;
		if (tarefa == null || tarefa.getId() == null) {
			return null;
		}
		return String.valueOf(tarefa.getId());
	}

}
