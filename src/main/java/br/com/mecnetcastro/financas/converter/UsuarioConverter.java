package br.com.mecnetcastro.financas.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.repository.UsuarioRepository;
import br.com.mecnetcastro.financas.util.cdi.CDILocator;

@FacesConverter(forClass = Usuario.class)
public class UsuarioConverter implements Converter {

	private UsuarioRepository repository;

	public UsuarioConverter() {
		this.repository = CDILocator.getBean(UsuarioRepository.class);
	}

	@Override
	public Object getAsObject(FacesContext ctx, UIComponent comp, String value) {
		if (value == null || value.isEmpty() || "null".equals(value)) {
			return null;
		}
		Integer id = Integer.valueOf(value);
		Usuario usuario = this.repository.buscaPorId(id);
		return usuario;
	}

	@Override
	public String getAsString(FacesContext ctx, UIComponent comp, Object value) {
		Usuario usuario = (Usuario) value;
		if (usuario == null || usuario.getId() == null) {
			return null;
		}
		return String.valueOf(usuario.getId());
	}

}
