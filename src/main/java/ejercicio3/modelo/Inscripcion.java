package ejercicio3.modelo;

public class Inscripcion {
    private String apellido;
    private String nombre;
    private String dni;
    private String telefono;
    private String email;
    private int idConcurso;

    public Inscripcion(String apellido, String nombre, String dni, String telefono, String email, int idConcurso) {
        validarCampos(apellido, nombre, dni, telefono, email);

        this.apellido = apellido;
        this.nombre = nombre;
        this.dni = dni;
        this.telefono = telefono;
        this.email = email;
        this.idConcurso = idConcurso;
    }

    private void validarCampos(String apellido, String nombre, String dni, String telefono, String email) {
        if (apellido == null || apellido.trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido no puede ser vacío");
        }
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser vacío");
        }
        if (dni == null || dni.trim().isEmpty()) {
            throw new IllegalArgumentException("El DNI no puede ser vacío");
        }
        if (!validarTelefono(telefono)) {
            throw new IllegalArgumentException("El teléfono debe tener el formato NNNN-NNNNNN");
        }
        if (!validarEmail(email)) {
            throw new IllegalArgumentException("El email debe ser válido");
        }
    }

    private boolean validarTelefono(String telefono) {
        if (telefono == null) return false;
        String regex = "\\d{4}-\\d{6}";
        return telefono.matches(regex);
    }

    private boolean validarEmail(String email) {
        if (email == null) return false;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public String toFileFormat() {
        return String.format("%s, %s, %s, %s, %d", apellido, nombre, telefono, email, idConcurso);
    }

    // Getters
    public String getApellido() { return apellido; }
    public String getNombre() { return nombre; }
    public String getDni() { return dni; }
    public String getTelefono() { return telefono; }
    public String getEmail() { return email; }
    public int getIdConcurso() { return idConcurso; }
}
