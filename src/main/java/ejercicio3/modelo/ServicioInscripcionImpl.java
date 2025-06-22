package ejercicio3.modelo;

import java.util.List;

public class ServicioInscripcionImpl implements ServicioInscripcion {
    private final ConcursoRepository concursoRepository;
    private final InscripcionRepository inscripcionRepository;

    public ServicioInscripcionImpl(ConcursoRepository concursoRepository,
                                   InscripcionRepository inscripcionRepository) {
        this.concursoRepository = concursoRepository;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    public List<Concurso> obtenerConcursosDisponibles() {
        return concursoRepository.todosLosConcursos();
    }

    @Override
    public void inscribirParticipante(String apellido, String nombre, String dni,
                                      String telefono, String email, int idConcurso) {
        // Validar que el concurso existe y está abierto
        List<Concurso> concursosAbiertos = concursoRepository.todosLosConcursos();
        boolean concursoValido = concursosAbiertos.stream()
                .anyMatch(c -> c.getId() == idConcurso);

        if (!concursoValido) {
            throw new IllegalArgumentException("El concurso seleccionado no está disponible");
        }

        Inscripcion inscripcion = new Inscripcion(apellido, nombre, dni, telefono, email, idConcurso);
        inscripcionRepository.saveInscription(inscripcion);
    }
}

