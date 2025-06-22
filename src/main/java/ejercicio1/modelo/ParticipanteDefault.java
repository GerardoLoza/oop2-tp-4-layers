package ejercicio1.modelo;

public class ParticipanteDefault implements ParticipanteService {
    private final ParticipanteRepository repository;

    public ParticipanteDefault(ParticipanteRepository repository) {
        this.repository = repository;
    }

    @Override
    public void agregarParticipante(String nombre, String telefono, String region) throws Exception {
        Participante participante = new Participante(nombre, telefono, region);
        repository.guardar(participante);
    }
}

