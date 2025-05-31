package repository;

import entity.Avaliacao;
import entity.Avaliador;
import entity.Trabalho;

import java.util.*;
import java.util.stream.Collectors;

public class AvaliacaoRepository {
    private final List<Avaliacao> avaliacoes;

    public AvaliacaoRepository() {
        this.avaliacoes = new ArrayList<>();
    }

    public void salvar(Avaliacao avaliacao){
        Optional<Avaliacao> existente = encontrarPorAvaliacaoETrabalho(avaliacao.getAvaliador(), avaliacao.getTrabalho());
        if(existente.isPresent())
            avaliacoes.remove(existente.get());
        avaliacoes.add(avaliacao);
    }

    public Optional<Avaliacao> encontrarPorAvaliacaoETrabalho(Avaliador avaliador, Trabalho trabalho){
        return avaliacoes.stream()
                .filter(a -> a.getAvaliador().equals(avaliador) && a.getTrabalho().equals(trabalho))
                .findFirst();
    }

    public List<Avaliacao> encontrarPorTrabalho(Trabalho trabalho){
        return avaliacoes.stream()
                .filter(a -> a.getTrabalho().equals(trabalho))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> encontrarPorAvaliador(Avaliador avaliador){
        return avaliacoes.stream()
                .filter(a -> a.getAvaliador().equals(avaliador))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> listarAvaliacoes(){
        return new ArrayList<>(avaliacoes);
    }

    public void remover(Avaliacao avaliacao){
        avaliacoes.remove(avaliacao);
    }
}
