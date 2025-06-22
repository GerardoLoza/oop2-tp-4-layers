package ejercicio3.modelo;

import java.util.List;

public interface InscripcionRepository {
    void saveInscription(Inscripcion inscripcion);
    List<Inscripcion> obtenerPorConcurso(int idConcurso);
    List<Inscripcion> obtenerTodas();
}
