package ejercicio2.persistencia;

import ejercicio2.modelo.Empleado;
import ejercicio2.modelo.EmpleadoRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EmpleadoRepositoryArchivo implements EmpleadoRepository {
    private final String rutaArchivo;

    public EmpleadoRepositoryArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public List<Empleado> obtenerTodos() throws Exception {
        List<Empleado> empleados = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            int numeroLinea = 0;

            while ((linea = reader.readLine()) != null) {
                numeroLinea++;
                linea = linea.trim();

                if (linea.isEmpty()) {
                    continue; // Saltar líneas vacías
                }

                try {
                    Empleado empleado = parsearLinea(linea);
                    empleados.add(empleado);
                } catch (Exception e) {
                    throw new Exception("Error en línea " + numeroLinea + ": " + e.getMessage());
                }
            }
        } catch (IOException e) {
            throw new Exception("Error al leer el archivo: " + e.getMessage());
        }

        return empleados;
    }

    private Empleado parsearLinea(String linea) {
        String[] partes = linea.split(",");

        if (partes.length != 4) {
            throw new IllegalArgumentException("Formato inválido. Se esperan 4 campos separados por coma");
        }

        String apellido = partes[0].trim();
        String nombre = partes[1].trim();
        String fechaNacimiento = partes[2].trim();
        String email = partes[3].trim();

        return new Empleado(apellido, nombre, fechaNacimiento, email);
    }
}
