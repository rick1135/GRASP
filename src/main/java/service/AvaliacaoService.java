package service;

import entity.Avaliacao;
import entity.Avaliador;
import entity.Trabalho;
import repository.AvaliacaoRepository;

import java.util.*;

public class AvaliacaoService {
    private final AvaliacaoRepository avaliacaoRepository;

    public AvaliacaoService() {
        this.avaliacaoRepository = new AvaliacaoRepository();
    }

    public boolean registrarAvaliacao(Trabalho trabalho, Avaliador avaliador, double nota, String comentario){
        if(trabalho==null || avaliador==null || nota<0 || nota>10) return false;

        Optional<Avaliacao> jaAvaliado = avaliacaoRepository.encontrarPorAvaliacaoETrabalho(avaliador, trabalho);
        if(jaAvaliado.isPresent()){
            System.out.println("O avaliador " + avaliador.getNome() + " já avaliou o trabalho '" + trabalho.getTitulo());
            return false;
        }

        Avaliacao avaliacao = new Avaliacao(avaliador, trabalho, nota, comentario);
        avaliacaoRepository.salvar(avaliacao);
        trabalho.adicionarAvaliacao(avaliacao);
        return true;
    }

    public List<Avaliacao> listarAvaliacoesPorTrabalho(Trabalho trabalho){
        if(trabalho==null) return Collections.emptyList();
        return avaliacaoRepository.encontrarPorTrabalho(trabalho);
    }

    public List<Avaliacao> listarAvaliacoesPorAvaliador(Avaliador avaliador){
        if(avaliador==null) return Collections.emptyList();
        return avaliacaoRepository.encontrarPorAvaliador(avaliador);
    }
}
