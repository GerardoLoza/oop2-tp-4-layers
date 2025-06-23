package ejercicio1.main;

import ejercicio1.modelo.*;
import ejercicio1.persistencia.ParticipanteRepositoryJDBC;
import ejercicio1.servicios.EmailServiceMailTrap;
import ejercicio1.ui.AgregarParticipanteFrame;
import ejercicio1.ui.ParticipanteView;
import ejercicio2.servicios.EmailServiceMailTrap1;

import java.awt.EventQueue;

public class Main {
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String url = "jdbc:derby://localhost:1527/participantes";
                    String user = "app";
                    String password = "app";

                    ParticipanteRepository repository = new ParticipanteRepositoryJDBC(url, user, password);
                    ParticipanteService service = new ParticipanteDefault(repository);

                    EmailService emailService = new EmailServiceMailTrap(
                            "sandbox.smtp.mailtrap.io",
                            2525,
                            "4d4ad4081c22c6",
                            "1602d032d7d98b",
                            "noreply@participantes.com"
                    );

                    EmailObserver emailObserver = new EmailObserver(emailService);
                    service.addObserver(emailObserver);

                    ParticipanteView view = new AgregarParticipanteFrame(service);
                    view.mostrar();
                } catch (Exception e) {
                    System.out.println("Error al inicializar la aplicación: " + e);
                    e.printStackTrace();
                }
            }
        });
    }
}
