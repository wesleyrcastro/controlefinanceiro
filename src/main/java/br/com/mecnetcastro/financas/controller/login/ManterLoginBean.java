package br.com.mecnetcastro.financas.controller.login;

import java.io.Serializable;
import java.util.Date;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.deltaspike.jpa.api.transaction.Transactional;

import br.com.mecnetcastro.financas.annotation.stereotype.SessionController;
import br.com.mecnetcastro.financas.model.LoginBloqueado;
import br.com.mecnetcastro.financas.model.Usuario;
import br.com.mecnetcastro.financas.repository.LoginBloqueadoRepository;
import br.com.mecnetcastro.financas.repository.UsuarioRepository;
import br.com.mecnetcastro.financas.util.jsf.FacesUtil;
import br.com.mecnetcastro.financas.util.security.UsuarioLogado;

@SessionController
public class ManterLoginBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Log logger = LogFactory.getLog(ManterLoginBean.class);

	@Inject
	private UsuarioLogado usuarioLogado;
	@Inject
	private UsuarioRepository usuarioRepository;
	@Inject
	private LoginBloqueadoRepository loginBloqueadoRepository;

	private LoginBloqueado loginBloqueado = new LoginBloqueado();
	private Usuario usuario = new Usuario();

	private int tentativasErradas = 0;
	private static String[] mensagens = { "Ops..., você errou. Verifique os dados e tente novamente.",
			"Esqueceu sua senha?", "Se errar de novo, te bloqueio." };

	@Transactional
	public String efetuaLogin() {
		HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
				.getRequest();
		String ipAddress = request.getHeader("X-FORWARDED-FOR");

		if (ipAddress == null) {
			ipAddress = request.getRemoteAddr();
		}

		boolean isLoginBloqueado = this.loginBloqueadoRepository.ipExiste(ipAddress);

		if (isLoginBloqueado) {
			FacesUtil.addWarnMessage("Usuário bloqueado. Contate o administrador.");

			logger.info("USUÁRIO BLOQUEADO: " + usuario.getLogin());

			return "/view/index?faces-redirect=true";
		}

		logger.info("USUÁRIO TENTANDO ACESSAR A APLICAÇÃO: " + usuario.getLogin());
		Usuario loginUsuario = this.usuarioRepository.usuarioExiste(usuario);

		if (loginUsuario != null) {
			usuarioLogado.logar(loginUsuario);
			logger.info("USUÁRIO ACESSOU A APLICAÇÃO: " + usuario.getLogin());

			return "/view/home?faces-redirect=true";
		} else {
			tentativasErradas++;

			if (tentativasErradas > 3) {
				loginBloqueado.setData(new Date());
				loginBloqueado.setUsuario(usuario.getLogin());
				loginBloqueado.setIp(ipAddress);

				this.loginBloqueadoRepository.adiciona(loginBloqueado);
				tentativasErradas = 0;
			} else {
				FacesUtil.addWarnMessage(mensagens[tentativasErradas - 1]);

				return "/view/index?faces-redirect=true";
			}

			usuarioLogado.deslogar();
			usuario = new Usuario();

			FacesUtil.addErrorMessage("Login Invalido");

			logger.info("USUÁRIO FOI BLOQUEADO: " + usuario.getLogin());
		}

		return "/view/index?faces-redirect=true";
	}

	public String logout() {
		logger.info("USUÁRIO SAIU DA APLICAÇÃO: " + usuario.getLogin());
		setUsuario(null);
		FacesContext.getCurrentInstance().getExternalContext().invalidateSession();

		return "/view/index?faces-redirect=true";
	}

	public LoginBloqueado getLoginBloqueado() {
		return loginBloqueado;
	}

	public void setLoginBloqueado(LoginBloqueado loginBloqueado) {
		this.loginBloqueado = loginBloqueado;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getTentativasErradas() {
		return tentativasErradas;
	}

	public void setTentativasErradas(int tentativasErradas) {
		this.tentativasErradas = tentativasErradas;
	}

}
