package ejercicio1.modelo;

public interface ParticipanteService extends Subject {
    void agregarParticipante(String nombre, String telefono, String region, String email) throws Exception;
}
