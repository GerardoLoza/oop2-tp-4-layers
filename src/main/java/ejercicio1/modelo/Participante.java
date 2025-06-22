package ejercicio1.modelo;

public class Participante {
    private String nombre;
    private String telefono;
    private String region;

    public Participante(String nombre, String telefono, String region) {
        validarNombre(nombre);
        validarTelefono(telefono);
        validarRegion(region);

        this.nombre = nombre;
        this.telefono = telefono;
        this.region = region;
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

    // Getters
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getRegion() {
        return region;
    }

    @Override
    public String toString() {
        return "Participante{" +
                "nombre='" + nombre + '\'' +
                ", telefono='" + telefono + '\'' +
                ", region='" + region + '\'' +
                '}';
    }
}
