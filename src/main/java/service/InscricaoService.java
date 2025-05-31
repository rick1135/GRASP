package service;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;
import repository.InscricaoRepository;

import java.util.*;
import java.util.stream.Collectors;

public class InscricaoService {
    private final InscricaoRepository inscricaoRepository;

    public InscricaoService(){
        this.inscricaoRepository = new InscricaoRepository();
    }

    public void criarInscricao(Participante participante, Evento evento) throws Exception {
        if(participante == null || evento == null)
            throw new IllegalArgumentException("Os dados não podem ser nulos!");

        if(!evento.temVaga())
            throw new Exception("Evento sem vagas!");

        if(participante.isInscritoEmEvento(evento))
            throw new Exception("Usuario ja inscrito!");

        Inscricao inscricao = new Inscricao(participante, evento);
        inscricaoRepository.salvar(inscricao);

        participante.adicionarInscricao(inscricao);
        evento.adicionarInscricao(inscricao);

    }

    public void cancelarInscricao(Inscricao inscricao) throws Exception {
        if(inscricao==null)
            throw new IllegalArgumentException("Os dados não podem ser nulos!");

        boolean cancelado = inscricao.getParticipante().cancelarInscricao(inscricao);

        if(!cancelado)
            throw new Exception("Inscrição nao encontrada ou esta fora do prazo de cancelamento!");

        inscricao.getParticipante().removerInscricao(inscricao);
        inscricao.getEvento().removerInscricao(inscricao);
    }

    public boolean confirmarPresenca(Inscricao inscricao) throws Exception {
        if(inscricao==null || !inscricao.isAtiva())
            throw new Exception("Inscrição não encontrada, cancelada ou ja foi confirmada!");

        inscricao.confirmarPresenca();
        inscricaoRepository.salvar(inscricao);
        return true;
    }

    //lista inscricoes do participante
    public List<Inscricao> listarPorParticipante(Participante participante){
        if(participante==null) return null;
        return inscricaoRepository.encontrarPorParticipante(participante);
    }

    //lista inscricoes do evento
    public List<Inscricao> listarPorEvento(Evento evento){
        if(evento==null) return null;
        return inscricaoRepository.encontrarPorEvento(evento);
    }

    public Optional<Inscricao> buscarInscricaoPorParticipanteEEvento(Participante participante, Evento evento){
        if(participante==null || evento==null) return Optional.empty();
        return inscricaoRepository.encontrarPorParticipanteEEvento(participante, evento);
    }
}
