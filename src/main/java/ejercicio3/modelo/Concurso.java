package ejercicio3.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Concurso {
    private int id;
    private String nombre;
    private LocalDate fechaInicioInscripcion;
    private LocalDate fechaFinInscripcion;

    public Concurso(int id, String nombre, LocalDate fechaInicioInscripcion, LocalDate fechaFinInscripcion) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del concurso no puede ser vacío");
        }
        if (fechaInicioInscripcion == null || fechaFinInscripcion == null) {
            throw new IllegalArgumentException("Las fechas no pueden ser nulas");
        }
        if (fechaInicioInscripcion.isAfter(fechaFinInscripcion)) {
            throw new IllegalArgumentException("La fecha de inicio no puede ser posterior a la fecha de fin");
        }

        this.id = id;
        this.nombre = nombre;
        this.fechaInicioInscripcion = fechaInicioInscripcion;
        this.fechaFinInscripcion = fechaFinInscripcion;
    }

    public boolean estaAbiertaInscripcion() {
        LocalDate hoy = LocalDate.now();
        return !hoy.isBefore(fechaInicioInscripcion) && !hoy.isAfter(fechaFinInscripcion);
    }

    public String toFileFormat() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return String.format("%d, %s, %s, %s",
                id, nombre,
                fechaInicioInscripcion.format(formatter),
                fechaFinInscripcion.format(formatter));
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public LocalDate getFechaInicioInscripcion() { return fechaInicioInscripcion; }
    public LocalDate getFechaFinInscripcion() { return fechaFinInscripcion; }

    @Override
    public String toString() {
        return nombre;
    }
}
