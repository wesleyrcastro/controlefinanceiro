package br.com.mecnetcastro.financas.util.email;

import java.io.Serializable;
import java.util.List;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import br.com.mecnetcastro.financas.model.Tarefa;

public class EmailUtil implements Serializable {

	private static final long serialVersionUID = 1L;
	private static Log log = LogFactory.getLog(EmailUtil.class);

	private static final String HOSTNAME = "smtp.comercialmecnetcastro.com.br";
	private static final String PORT = "587";
	private static final String USERNAME = "gerente@comercialmecnetcastro.com.br";
	private static final String PASSWORD = "castro12";
	private static final String EMAILORIGEM = "gerente@comercialmecnetcastro.com.br";
	private static final String DESTINO = "wesleyrcastro@yahoo.com.br";
	private static final String TITULO = "TAREFAS PENDENTES. [Mensagem Enviada Pelo Sistema de Controle Financeiro]";
	private static final String MENSAGEM = "Olá!\n";

	public static void enviaEmail(List<Tarefa> tarefas) {
		if (tarefas != null && !tarefas.isEmpty()) {
			
			Properties props = new Properties();
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.starttls.enable", "true");
			props.put("mail.smtp.host", HOSTNAME);
			props.put("mail.smtp.port", PORT);
	
			Session session = Session.getInstance(props, new javax.mail.Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(USERNAME, PASSWORD);
				}
			});
	
			try {
				Message message = new MimeMessage(session);
						message.setFrom(new InternetAddress(EMAILORIGEM));
						message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(DESTINO));
						message.setSubject(TITULO);

				String mensagem = MENSAGEM + "Existem tarefas pendentes:\n ";

				for (Tarefa tarefa : tarefas) {
					mensagem += "--> " + tarefa.getDescricao() + " - " + tarefa.getValor() + "\n";
				}

				message.setText(mensagem);

				Transport.send(message);
			}catch(Exception e) {
				log.error("Erro de e-mail: " + e.getMessage());
			}
		} else {
			log.warn("Não existem tarefas pendentes...");
		}
	}
}
