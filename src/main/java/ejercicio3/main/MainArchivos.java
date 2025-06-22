package ejercicio3.main;

import ejercicio3.modelo.ConcursoRepository;
import ejercicio3.modelo.InscripcionRepository;
import ejercicio3.modelo.ServicioInscripcion;
import ejercicio3.modelo.ServicioInscripcionImpl;
import ejercicio3.persistencia.ConcursoRepositoryArchivo;
import ejercicio3.persistencia.InscripcionRepositoryArchivo;
import ejercicio3.ui.RadioCompetitionFrame;

import javax.swing.SwingUtilities;

public class MainArchivos {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Configuración de rutas de archivos
                    String rutaConcursos = "concursos.txt";
                    String rutaInscripciones = "inscriptos.txt";

                    // Inyección de dependencias
                    ConcursoRepository concursoRepository = new ConcursoRepositoryArchivo(rutaConcursos);
                    InscripcionRepository inscripcionRepository = new InscripcionRepositoryArchivo(rutaInscripciones);
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
