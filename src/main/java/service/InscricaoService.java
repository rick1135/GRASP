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
    public boolean criarInscricao(Participante participante, Evento evento){
        if(participante == null || evento == null) return false;
        boolean jaInscrito = participante.isInscritoEmEvento(evento);

        if(jaInscrito) return false;

        Inscricao inscricao = new Inscricao(participante, evento);
        inscricoes.add(inscricao);
        participante.adicionarInscricao(inscricao);
        evento.adicionarInscricao(inscricao);
        return true;
    }

    public boolean cancelarInscricao(Inscricao inscricao){
        if(inscricao==null) return false;

        boolean cancelado = inscricao.getParticipante().cancelarInscricao(inscricao);

        if(cancelado){
            inscricao.getEvento().removerInscricao(inscricao);
            inscricao.getParticipante().cancelarInscricao(inscricao);
        }
        return cancelado;
    }

    public boolean confirmarPresenca(Inscricao inscricao){
        if(inscricao==null || !inscricao.isAtiva())
            return false;

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
