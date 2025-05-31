package repository;

import entity.Evento;

import java.util.*;

public class EventoRepository {
    private final List<Evento> eventos; //

    public EventoRepository() {
        this.eventos = new ArrayList<>();
    }

    public void salvar(Evento evento) {
        Optional<Evento> existente = listarPorNome(evento.getNome());
        if (existente.isPresent())
            eventos.remove(existente.get());
        eventos.add(evento);
    }

    public Optional<Evento> listarPorNome(String nome) {
        return eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public List<Evento> listarEventos() {
        return Collections.unmodifiableList(eventos);
    }

    public void remover(Evento evento) {
        eventos.remove(evento);
    }
}