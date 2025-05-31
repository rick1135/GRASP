package controller;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;
import service.InscricaoService;

import java.util.List;

public class InscricaoController {
    private final InscricaoService service;

    public InscricaoController() {
        this.service = new InscricaoService();
    }

    public void criarInscricao(Participante participante, Evento evento) throws Exception {
        service.criarInscricao(participante, evento);
    }

    public void removerInscricao(Inscricao inscricao) throws Exception{
        service.cancelarInscricao(inscricao);
    }

    public void confirmarPresenca(Inscricao inscricao) throws Exception {
        service.confirmarPresenca(inscricao);
    }

    public List<Inscricao> listarInscricoesPorParticipante(Participante participante) {
        return service.listarPorParticipante(participante).isEmpty() ? null : service.listarPorParticipante(participante);
    }

    public List<Inscricao> listarInscricoesPorEvento(Evento evento){
        return service.listarPorEvento(evento).isEmpty() ? null : service.listarPorEvento(evento);
    }
}
