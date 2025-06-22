package ejercicio3.persistencia;

import ejercicio3.modelo.Inscripcion;
import ejercicio3.modelo.InscripcionRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class InscripcionRepositoryArchivo implements InscripcionRepository {
    private final String rutaArchivo;

    public InscripcionRepositoryArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Override
    public void saveInscription(Inscripcion inscripcion) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            writer.println(inscripcion.toFileFormat());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la inscripción: " + e.getMessage(), e);
        }
    }

    @Override
    public List<Inscripcion> obtenerPorConcurso(int idConcurso) {
        return obtenerTodas().stream()
                .filter(i -> i.getIdConcurso() == idConcurso)
                .toList();
    }

    @Override
    public List<Inscripcion> obtenerTodas() {
        List<Inscripcion> inscripciones = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Inscripcion inscripcion = parsearLinea(linea);
                    if (inscripcion != null) {
                        inscripciones.add(inscripcion);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            // Archivo no existe aún, retornar lista vacía
            return inscripciones;
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de inscripciones: " + e.getMessage(), e);
        }

        return inscripciones;
    }

    private Inscripcion parsearLinea(String linea) {
        try {
            String[] partes = linea.split(",");
            if (partes.length != 5) {
                System.err.println("Línea con formato incorrecto: " + linea);
                return null;
            }

            String apellido = partes[0].trim();
            String nombre = partes[1].trim();
            String telefono = partes[2].trim();
            String email = partes[3].trim();
            int idConcurso = Integer.parseInt(partes[4].trim());

            // DNI no se guarda en el archivo según el formato especificado
            return new Inscripcion(apellido, nombre, "00000000", telefono, email, idConcurso);
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + linea + " - " + e.getMessage());
            return null;
        }
    }
}

