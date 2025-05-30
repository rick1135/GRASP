package service;

import entity.Avaliacao;
import entity.Avaliador;
import entity.Trabalho;

import java.util.*;
import java.util.stream.Collectors;

public class AvaliacaoService {
    private List<Avaliacao> avaliacoes;

    public AvaliacaoService() {
        this.avaliacoes = new ArrayList<>();
    }

    public boolean registrarAvaliacao(Trabalho trabalho, Avaliador avaliador, double nota, String comentario){
        if(trabalho==null || avaliador==null || nota<0 || nota>10) return false;

        boolean jaAvaliado = avaliacoes.stream().anyMatch(a -> a.getTrabalho().equals(trabalho) && a.getAvaliador().equals(avaliador));
        if(jaAvaliado) return false;

        Avaliacao avaliacao = new Avaliacao(avaliador, trabalho, nota, comentario);
        avaliacoes.add(avaliacao);
        trabalho.adicionarAvaliacao(avaliacao);
        return true;
    }

    public List<Avaliacao> listarAvaliacoesPorTrabalho(Trabalho trabalho){
        return avaliacoes.stream()
                .filter(a -> a.getTrabalho().equals(trabalho))
                .collect(Collectors.toList());
    }

    public List<Avaliacao> listarAvaliacoesPorAvaliador(Avaliador avaliador){
        return avaliacoes.stream()
                .filter(a -> a.getAvaliador().equals(avaliador))
                .collect(Collectors.toList());
    }
}
