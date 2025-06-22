package ejercicio2.modelo;

import java.util.List;

public interface EmpleadoRepository {
    List<Empleado> obtenerTodos() throws Exception;
}
