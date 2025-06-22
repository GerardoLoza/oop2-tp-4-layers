package ejercicio1.main;

import ejercicio1.modelo.ParticipanteRepository;
import ejercicio1.modelo.ParticipanteService;
import ejercicio1.modelo.ParticipanteDefault;
import ejercicio1.persistencia.ParticipanteRepositoryJDBC;
import ejercicio1.ui.AgregarParticipanteFrame;
import ejercicio1.ui.ParticipanteView;

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
