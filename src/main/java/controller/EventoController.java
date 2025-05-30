package controller;

import entity.Evento;
import entity.Inscricao;
import service.EventoService;

import java.util.List;
import java.util.Optional;

public class EventoController {
    private final EventoService service;

    public EventoController(EventoService service) {
        this.service = service;
    }

    public void criarEvento(Evento evento) throws Exception {
        service.criarEvento(evento);
    }

    public List<Evento> listarEventos() {
        return service.listarEventos().isEmpty()  ? null : service.listarEventos();
    }

    public Optional<Evento> buscarEventoPorNome(String name) {
        return service.buscarEventoPornome(name);
    }

    public boolean inscrever(Inscricao inscricao) {
        if(!service.podeInscrever(inscricao.getEvento()))
            return false;

        InscricaoController inscricao = new InscricaoController();

        return true;
    }

}
