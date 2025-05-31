package repository;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;

import java.util.*;
import java.util.stream.Collectors;

public class InscricaoRepository {
    private final List<Inscricao> inscricoes;

    public InscricaoRepository() {
        this.inscricoes = new ArrayList<>();
    }

    public void salvar(Inscricao inscricao) {
        Optional<Inscricao> existente = encontrarPorParticipanteEEvento(inscricao.getParticipante(), inscricao.getEvento());
        if (existente.isPresent()) {
            inscricoes.remove(existente.get());
        }
        inscricoes.add(inscricao);
    }

    public Optional<Inscricao> encontrarPorParticipanteEEvento(Participante participante, Evento evento) {
        return inscricoes.stream()
                .filter(i -> i.getParticipante().equals(participante) && i.getEvento().equals(evento))
                .findFirst();
    }

    public List<Inscricao> encontrarPorParticipante(Participante participante) {
        return inscricoes.stream()
                .filter(i -> i.getParticipante().equals(participante))
                .collect(Collectors.toList());
    }

    public List<Inscricao> encontrarPorEvento(Evento evento) {
        return inscricoes.stream()
                .filter(i -> i.getEvento().equals(evento))
                .collect(Collectors.toList());
    }

    public List<Inscricao> listarInscricoes() {
        return Collections.unmodifiableList(inscricoes);
    }

    public void remover(Inscricao inscricao) {
        inscricoes.remove(inscricao);
    }
}