package controller;

import entity.Evento;
import entity.Inscricao;
import service.EventoService;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class EventoController {
    private final EventoService service;

    public EventoController() {
        this.service = new EventoService();
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

    public void adicionarInscricao(Evento evento, Inscricao inscricao) throws Exception {
        service.adicionarInscricao(evento, inscricao);
    }

    public void removerInscricao(Evento evento, Inscricao inscricao) throws Exception {
        service.removerInscricao(evento, inscricao);
    }

    public void editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal) throws Exception {
        service.editarEvento(nomeEvento, novoNome, novaDescricao, novaDataInicio, novaDataFim, novoLocal);
    }

}
