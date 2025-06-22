package ejercicio3.modelo;

import java.util.List;

public interface ServicioInscripcion {
    List<Concurso> obtenerConcursosDisponibles();
    void inscribirParticipante(String apellido, String nombre, String dni,
                               String telefono, String email, int idConcurso);
}

