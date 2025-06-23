package ejercicio3.persistencia;


import ejercicio3.log.Log;
import ejercicio3.modelo.Concurso;
import ejercicio3.modelo.ConcursoRepository;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ConcursoRepositoryArchivo implements ConcursoRepository {
    private final String rutaArchivo;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

    public ConcursoRepositoryArchivo(String rutaArchivo) {
        this.rutaArchivo = rutaArchivo;
    }

    @Log
    @Override
    public List<Concurso> todosLosConcursos() {
        List<Concurso> todos = obtenerTodos();
        return todos.stream()
                .filter(Concurso::estaAbiertaInscripcion)
                .toList();
    }

    @Override
    public List<Concurso> obtenerTodos() {
        List<Concurso> concursos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(rutaArchivo))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                if (!linea.trim().isEmpty()) {
                    Concurso concurso = parsearLinea(linea);
                    if (concurso != null) {
                        concursos.add(concurso);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error al leer el archivo de concursos: " + e.getMessage(), e);
        }

        return concursos;
    }

    @Override
    public void guardar(Concurso concurso) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(rutaArchivo, true))) {
            writer.println(concurso.toFileFormat());
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar el concurso: " + e.getMessage(), e);
        }
    }

    private Concurso parsearLinea(String linea) {
        try {
            String[] partes = linea.split(",");
            if (partes.length != 4) {
                System.err.println("Línea con formato incorrecto: " + linea);
                return null;
            }

            int id = Integer.parseInt(partes[0].trim());
            String nombre = partes[1].trim();
            LocalDate fechaInicio = LocalDate.parse(partes[2].trim(), formatter);
            LocalDate fechaFin = LocalDate.parse(partes[3].trim(), formatter);

            return new Concurso(id, nombre, fechaInicio, fechaFin);
        } catch (Exception e) {
            System.err.println("Error al parsear línea: " + linea + " - " + e.getMessage());
            return null;
        }
    }
}