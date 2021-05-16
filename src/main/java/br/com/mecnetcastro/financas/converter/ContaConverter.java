package br.com.mecnetcastro.financas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mecnetcastro.financas.model.Conta;
import br.com.mecnetcastro.financas.repository.ContaRepository;
import br.com.mecnetcastro.financas.util.cdi.CDILocator;

@FacesConverter(forClass = Conta.class)
public class ContaConverter implements Converter {

	private ContaRepository repository;

	public ContaConverter() {
		this.repository = CDILocator.getBean(ContaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		if (value == null || value.isEmpty() || "null".equals(value)) {
			return null;
		}
		Integer id = Integer.valueOf(value);
		Conta conta = this.repository.buscaPorId(id);
		return conta;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		Conta conta = (Conta) value;
		if (conta == null || conta.getId() == null) {
			return null;
		}
		return String.valueOf(conta.getId());
	}

}
