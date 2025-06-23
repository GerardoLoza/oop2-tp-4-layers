package ejercicio1.modelo;

public class Participante {
    private String nombre;
    private String telefono;
    private String region;
    private String email;

    public Participante(String nombre, String telefono, String region, String email) {
        validarNombre(nombre);
        validarTelefono(telefono);
        validarRegion(region);
        validarEmail(email);

        this.nombre = nombre;
        this.telefono = telefono;
        this.region = region;
        this.email = email;
    }

    private void validarNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe cargar un nombre");
        }
    }

    private void validarTelefono(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe cargar un telefono");
        }

        String regex = "\\d{4}-\\d{6}";
        if (!telefono.matches(regex)) {
            throw new IllegalArgumentException("El teléfono debe ingresarse de la siguiente forma: NNNN-NNNNNN");
        }
    }

    private void validarRegion(String region) {
        if (region == null || region.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe cargar una región");
        }

        if (!region.equals("China") && !region.equals("US") && !region.equals("Europa")) {
            throw new IllegalArgumentException("Region desconocida. Las conocidas son: China, US, Europa");
        }
    }

    private void validarEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Debe cargar un email");
        }

        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!email.matches(regex)) {
            throw new IllegalArgumentException("El email debe tener un formato válido");
        }
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRegion() {
        return region;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", region='" + region + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
