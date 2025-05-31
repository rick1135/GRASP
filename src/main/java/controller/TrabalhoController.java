package controller;

import entity.Avaliacao;
import entity.Evento;
import entity.Participante;
import entity.Trabalho;
import service.AvaliacaoService;
import service.TrabalhoService;

import java.util.List;
import java.util.Optional;

public class TrabalhoController {
    private final TrabalhoService service;
    private final AvaliacaoService avaliacaoService;

    public TrabalhoController() {
        this.service = new TrabalhoService();
        this.avaliacaoService = new AvaliacaoService();
    }

    public boolean cadastrarTrabalho(String titulo, List<Participante> autores, Evento evento, String arquivo) {
        return service.cadastrarTrabalho(titulo, autores, evento, arquivo);
    }

    public Optional<Trabalho> buscarTrabalhoPorTitulo(String titulo) {
        return service.buscarTrabalhoPorTitulo(titulo);
    }

    public List<Trabalho> listarTrabalhos(){
        return service.listarTrabalhos();
    }

    public boolean adicionarAvaliacao(Trabalho trabalho, Avaliacao avaliacao){
        return service.adicionarAvaliacao(trabalho, avaliacao);
    }

    public boolean podeEmitirCertificadoApresentacao(Trabalho trabalho){
        if(service.isAprovado(trabalho)){
            return service.podeEmitirCertificadoApresentacao(trabalho);
        }
        return false;
    }

    public List<Avaliacao> listarAvaliacoesPorTrabalho(Trabalho trabalho){
        return avaliacaoService.listarAvaliacoesPorTrabalho(trabalho);
    }
}
