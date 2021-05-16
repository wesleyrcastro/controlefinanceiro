package br.com.mecnetcastro.financas.controller.start;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.mecnetcastro.financas.service.StartJobService;

@WebServlet(name = "start", urlPatterns = {"/start/*"})
public class StartJob extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	@Inject
	private StartJobService startJobService;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (!"189.38.85.36".equals(req.getRemoteAddr())) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
		}else {
		startJobService.execute();
		}
	}

}
