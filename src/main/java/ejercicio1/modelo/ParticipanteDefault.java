package ejercicio1.modelo;

import java.util.ArrayList;
import java.util.List;

public class ParticipanteDefault implements ParticipanteService {
    private final ParticipanteRepository repository;
    private List<Observer> observers;

    public ParticipanteDefault(ParticipanteRepository repository) {
        this.repository = repository;
        this.observers = new ArrayList<>();
    }

    @Override
    public void agregarParticipante(String nombre, String telefono, String region, String email) throws Exception {
        Participante participante = new Participante(nombre, telefono, region, email);
        repository.guardar(participante);
        notifyObservers(participante);
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Participante participante) {
        for (Observer observer : observers) {
            observer.update(participante);
        }
    }
}
