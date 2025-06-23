package ejercicio1.servicios;

import ejercicio1.modelo.EmailService;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailServiceMailTrap implements EmailService {
    private final String host;
    private final int puerto;
    private final String usuario;
    private final String password;
    private final String emailRemitente;

    public EmailServiceMailTrap(String host, int puerto, String usuario, String password, String emailRemitente) {
        this.host = host;
        this.puerto = puerto;
        this.usuario = usuario;
        this.password = password;
        this.emailRemitente = emailRemitente;
    }

    @Override
    public void enviarEmail(String destinatario, String asunto, String mensaje) throws Exception {
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", puerto);

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(usuario, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailRemitente));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(destinatario));
            message.setSubject(asunto);
            message.setText(mensaje);

            Transport.send(message);

            System.out.println("Email enviado exitosamente a: " + destinatario);

        } catch (MessagingException e) {
            throw new Exception("Error al enviar email: " + e.getMessage(), e);
        }
    }
}
