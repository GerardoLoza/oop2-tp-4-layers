package ejercicio3.main;

import ejercicio3.modelo.ConcursoRepository;
import ejercicio3.modelo.InscripcionRepository;
import ejercicio3.modelo.ServicioInscripcion;
import ejercicio3.modelo.ServicioInscripcionImpl;
import ejercicio3.persistencia.ConcursoRepositoryJDBC;
import ejercicio3.persistencia.InscripcionRepositoryJDBC;
import ejercicio3.ui.RadioCompetitionFrame;

import javax.swing.SwingUtilities;

public class MainBaseDatos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Configuración de base de datos
                    String url = "jdbc:derby:radiocompetition;create=true";;
                    String usuario = "app";
                    String password = "app";

                    // Inyección de dependencias
                    ConcursoRepository concursoRepository = new ConcursoRepositoryJDBC(url, usuario, password);
                    InscripcionRepository inscripcionRepository = new InscripcionRepositoryJDBC(url, usuario, password);
                    ServicioInscripcion servicioInscripcion = new ServicioInscripcionImpl(concursoRepository, inscripcionRepository);

                    // Crear y mostrar la interfaz
                    new RadioCompetitionFrame(servicioInscripcion);

                } catch (Exception e) {
                    System.err.println("Error al inicializar la aplicación: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        });
    }
}
