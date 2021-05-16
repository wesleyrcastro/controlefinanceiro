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

@FacesValidator("loginESenha")
public class LoginESenhaValidator implements Validator {

	@Inject
	private ComponentResolver resolver;

	@Override
	public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
		String login = resolver.getSubmittedValue("login");
		String senha = resolver.getSubmittedValue("senha");

		if (StringUtils.isNotBlank(login) && StringUtils.isNotBlank(senha) && login.equals(senha)) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_WARN, "Login e Senha não podem ser iguais", null));
		}

	}
}
