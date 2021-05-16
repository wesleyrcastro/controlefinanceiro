package br.com.mecnetcastro.financas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mecnetcastro.financas.model.Subcategoria;
import br.com.mecnetcastro.financas.repository.SubcategoriaRepository;
import br.com.mecnetcastro.financas.util.cdi.CDILocator;

@FacesConverter(forClass = Subcategoria.class)
public class SubcategoriaConverter implements Converter {

	private SubcategoriaRepository repository;

	public SubcategoriaConverter() {
		this.repository = CDILocator.getBean(SubcategoriaRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		if (value == null || value.isEmpty() || "null".equals(value)) {
			return null;
		}
		Integer id = Integer.valueOf(value);
		Subcategoria categoria = this.repository.buscaPorId(id);
		return categoria;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		Subcategoria categoria = (Subcategoria) value;
		if (categoria == null || categoria.getId() == null) {
			return null;
		}
		return String.valueOf(categoria.getId());
	}

}
