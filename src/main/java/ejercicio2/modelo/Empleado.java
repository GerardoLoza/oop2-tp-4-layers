package ejercicio2.modelo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Empleado {
    private final String apellido;
    private final String nombre;
    private final LocalDate fechaNacimiento;
    private final String email;

    public Empleado(String apellido, String nombre, String fechaNacimiento, String email) {
        this.apellido = validarTexto(apellido, "Apellido");
        this.nombre = validarTexto(nombre, "Nombre");
        this.fechaNacimiento = parsearFecha(fechaNacimiento);
        this.email = validarEmail(email);
    }

    private String validarTexto(String texto, String campo) {
        if (texto == null || texto.trim().isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacío");
        }
        return texto.trim();
    }

    private LocalDate parsearFecha(String fecha) {
        if (fecha == null || fecha.trim().isEmpty()) {
            throw new IllegalArgumentException("Fecha de nacimiento no puede estar vacía");
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
            return LocalDate.parse(fecha.trim(), formatter);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Use yyyy/MM/dd");
        }
    }

    private String validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email no puede estar vacío");
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(emailRegex)) {
            throw new IllegalArgumentException("Formato de email inválido");
        }

        return email.trim();
    }

    public boolean cumpleHoy() {
        LocalDate hoy = LocalDate.now();
        return fechaNacimiento.getMonth() == hoy.getMonth() &&
                fechaNacimiento.getDayOfMonth() == hoy.getDayOfMonth();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s",
                apellido, nombre, fechaNacimiento.format(DateTimeFormatter.ofPattern("yyyy/MM/dd")), email);
    }
}
