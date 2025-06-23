package ejercicio3.util;

import ejercicio3.modelo.Concurso;
import ejercicio3.modelo.ConcursoRepository;
import ejercicio3.modelo.Inscripcion;
import ejercicio3.modelo.InscripcionRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class DataLoader {

    public static void cargarDatosPrueba(ConcursoRepository concursoRepo,
                                         InscripcionRepository inscripcionRepo) {

        // Cargar concursos de prueba
        List<Concurso> concursos = Arrays.asList(
                new Concurso(1, "Concurso de Verano 2024",
                        LocalDate.of(2024, 1, 1), LocalDate.of(2024, 12, 31)),
                new Concurso(2, "Concurso de Invierno",
                        LocalDate.of(2024, 6, 1), LocalDate.of(2024, 8, 31)),
                new Concurso(3, "Gran Premio Musical",
                        LocalDate.of(2024, 3, 15), LocalDate.of(2024, 9, 30)),
                new Concurso(4, "Concurso Juvenil",
                        LocalDate.of(2024, 7, 1), LocalDate.of(2024, 7, 31)),
                new Concurso(5, "Festival de Talentos",
                        LocalDate.of(2024, 5, 1), LocalDate.of(2024, 11, 30)),
                new Concurso(6, "Concurso Vencido",
                        LocalDate.of(2023, 1, 1), LocalDate.of(2023, 12, 31)),
                new Concurso(7, "Concurso Futuro",
                        LocalDate.of(2025, 1, 1), LocalDate.of(2025, 12, 31))
        );

        // Guardar concursos
        for (Concurso concurso : concursos) {
            try {
                concursoRepo.guardar(concurso);
                System.out.println("Concurso guardado: " + concurso.getNombre());
            } catch (Exception e) {
                System.out.println("Concurso ya existe o error: " + concurso.getNombre());
            }
        }

        // Cargar inscripciones de ejemplo
        List<Inscripcion> inscripciones = Arrays.asList(
                new Inscripcion("García", "María", "12345678", "1234-567890",
                        "maria.garcia@email.com", 1),
                new Inscripcion("López", "Juan", "87654321", "9876-543210",
                        "juan.lopez@email.com", 1),
                new Inscripcion("Martínez", "Ana", "11223344", "1122-334455",
                        "ana.martinez@email.com", 2),
                new Inscripcion("Rodríguez", "Carlos", "55667788", "5566-778899",
                        "carlos.rodriguez@email.com", 3)
        );

        // Guardar inscripciones
        for (Inscripcion inscripcion : inscripciones) {
            try {
                inscripcionRepo.saveInscription(inscripcion);
                System.out.println("Inscripción guardada: " +
                        inscripcion.getNombre() + " " + inscripcion.getApellido());
            } catch (Exception e) {
                System.out.println("Error al guardar inscripción: " + e.getMessage());
            }
        }
    }
}