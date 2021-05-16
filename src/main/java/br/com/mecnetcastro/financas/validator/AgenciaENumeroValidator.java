package br.com.mecnetcastro.financas.validator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import br.com.mecnetcastro.financas.util.jsf.ComponentResolver;

@FacesValidator("agenciaENumero")
public class AgenciaENumeroValidator implements Validator {

	@Inject
	private ComponentResolver resolver;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String agencia = resolver.getSubmittedValue("agencia");
		String numero = resolver.getSubmittedValue("nConta");

		if (StringUtils.isNotBlank(agencia) && StringUtils.isNotBlank(numero) && agencia.equals(numero)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Agência e N° da Conta não podem ser iguais", null));
			
		}

	}
}
