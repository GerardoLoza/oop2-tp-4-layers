package ejercicio2.modelo;

import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class EmpleadoTest {

    @Test
    void crearEmpleado() {
        String apellido = "Young";
        String nombre = "Angus";
        String fechaNacimiento = "1982/10/08";
        String email = "angus@acdc.com";

        Empleado empleado = new Empleado(apellido, nombre, fechaNacimiento, email);

        assertEquals("Young", empleado.getApellido());
        assertEquals("Angus", empleado.getNombre());
        assertEquals(LocalDate.of(1982, 10, 8), empleado.getFechaNacimiento());
        assertEquals("angus@acdc.com", empleado.getEmail());
        assertEquals("Angus Young", empleado.getNombreCompleto());
    }

    @Test
    void apellidoInvalido() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Empleado("", "Angus", "1982/10/08", "angus@acdc.com");
        });
        assertEquals("Apellido no puede estar vacío", exception.getMessage());
    }

    @Test
    void nombreInvalido() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Empleado("Young", "", "1982/10/08", "angus@acdc.com");
        });
        assertEquals("Nombre no puede estar vacío", exception.getMessage());
    }

    @Test
    void fechaInvalida() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Empleado("Young", "Angus", "1982-10-08", "angus@acdc.com");
        });
        assertEquals("Formato de fecha inválido. Use yyyy/MM/dd", exception.getMessage());
    }

    @Test
    void emailInvalido() {
        // Given & When & Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            new Empleado("Young", "Angus", "1982/10/08", "email_invalido");
        });
        assertEquals("Formato de email inválido", exception.getMessage());
    }

    @Test
    void cumpleHoy() {
        // Given
        LocalDate hoy = LocalDate.now();
        String fechaHoy = String.format("%d/%02d/%02d", 1990, hoy.getMonthValue(), hoy.getDayOfMonth());
        Empleado empleado = new Empleado("Young", "Angus", fechaHoy, "angus@acdc.com");

        // When & Then
        assertTrue(empleado.cumpleHoy());
    }

    @Test
    void noCumpleHoy() {
        // Given
        Empleado empleado = new Empleado("Young", "Angus", "1982/01/01", "angus@acdc.com");

        // When & Then
        LocalDate hoy = LocalDate.now();
        if (hoy.getMonthValue() != 1 || hoy.getDayOfMonth() != 1) {
            assertFalse(empleado.cumpleHoy());
        }
    }
}

