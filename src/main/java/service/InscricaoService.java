package service;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;

import java.util.*;
import java.util.stream.Collectors;

public class InscricaoService {
    private List<Inscricao> inscricoes;

    public InscricaoService(){
        this.inscricoes = new ArrayList<>();
    }

    public void criarInscricao(Participante participante, Evento evento) throws Exception {
        if(participante == null || evento == null)
            throw new IllegalArgumentException("Os dados não podem ser nulos!");

        boolean jaInscrito = participante.isInscritoEmEvento(evento);

        if(jaInscrito)
            throw new Exception("Usuario ja inscrito!");

        Inscricao inscricao = new Inscricao(participante, evento);
        inscricoes.add(inscricao);
        participante.adicionarInscricao(inscricao);
        evento.adicionarInscricao(inscricao);
    }

    public void cancelarInscricao(Inscricao inscricao) throws Exception {
        if(inscricao==null)
            throw new IllegalArgumentException("Os dados não podem ser nulos!");

        boolean cancelado = inscricao.getParticipante().cancelarInscricao(inscricao);

        if(!cancelado)
            throw new Exception("Inscrição nao encontrada ou esta fora do prazo de cancelamento!");

        inscricao.getEvento().removerInscricao(inscricao);
        inscricao.getParticipante().cancelarInscricao(inscricao);
    }

    public boolean confirmarPresenca(Inscricao inscricao) throws Exception {
        if(inscricao==null || !inscricao.isAtiva())
            throw new Exception("Inscrição não encontrada, cancelada ou ja foi confirmada!");

        inscricao.confirmarPresenca();
        return true;
    }

    //lista inscricoes do participante
    public List<Inscricao> listarPorParticipante(Participante participante){
        return inscricoes.stream()
                .filter(i -> i.getParticipante().equals(participante))
                .collect(Collectors.toList());
    }

    //lista inscricoes do evento
    public List<Inscricao> listarPorEvento(Evento evento){
        return inscricoes.stream()
                .filter(i -> i.getEvento().equals(evento))
                .collect(Collectors.toList());
    }
}
