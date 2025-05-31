package controller;

import entity.Avaliacao;
import entity.Avaliador;
import entity.Trabalho;
import service.AvaliacaoService;
import service.AvaliadorService;
import service.TrabalhoService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AvaliadorController {
    private final AvaliadorService service;
    private final AvaliacaoService avaliacaoService;

    public AvaliadorController() {
        this.service = new AvaliadorService();
        this.avaliacaoService = new AvaliacaoService();
    }

    public void cadastraAvaliador(Avaliador avaliador) throws Exception {
        service.cadastrarAvaliador(avaliador);
    }

    public Optional<Avaliador> buscarAvaliadorPorEmail(String email) {
        return service.buscarAvaliadorPorEmail(email);
    }

    public void designarAvaliadorParaTrabalho(Avaliador avaliador, Trabalho trabalho) throws Exception {
        service.designarTrabalho(avaliador, trabalho);
    }

    public void registrarAvaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario){
        service.registrarAvaliacao(avaliador, trabalho, nota, comentario);
    }

    public List<Trabalho> listarTrabalhosDesignador(Avaliador avaliador){
        return service.listarTrabalhosDesignador(avaliador).isEmpty() ? null : service.listarTrabalhosDesignador(avaliador);
    }

    public List<Avaliacao> listarAvaliacoesPorAvaliador(Avaliador avaliador){
        return avaliacaoService.listarAvaliacoesPorAvaliador(avaliador);
    }

    public boolean registrarAvaliacao(Trabalho trabalho, Avaliador avaliador, double nota, String comentario){
        return avaliacaoService.registrarAvaliacao(trabalho, avaliador, nota, comentario);
    }

}
