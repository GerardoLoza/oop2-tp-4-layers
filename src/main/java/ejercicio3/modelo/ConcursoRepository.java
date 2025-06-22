package ejercicio3.modelo;

import java.util.List;

public interface ConcursoRepository {
    List<Concurso> todosLosConcursos();
    List<Concurso> obtenerTodos();
    void guardar(Concurso concurso);
}
