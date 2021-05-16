package br.com.mecnetcastro.financas.listener;

import javax.enterprise.event.Observes;
import javax.faces.application.NavigationHandler;
import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.inject.Inject;

import org.apache.deltaspike.jsf.api.listener.phase.AfterPhase;
import org.apache.deltaspike.jsf.api.listener.phase.JsfPhaseId;

import br.com.mecnetcastro.financas.util.security.UsuarioLogado;

public class Autorizador {

	@Inject
	private UsuarioLogado usuarioLogado;

	public void autoriza(@Observes @AfterPhase(JsfPhaseId.RESTORE_VIEW) PhaseEvent event) {
		FacesContext context = event.getFacesContext();

		if ("/view/index.xhtml".equals(context.getViewRoot().getViewId())) {
			return;
		}

		if (!usuarioLogado.isLogado()) {
			NavigationHandler navigationHandler = context.getApplication().getNavigationHandler();

			navigationHandler.handleNavigation(context, null, "/view/index?faces-redirect=true");
			context.renderResponse();
		}

	}

}
