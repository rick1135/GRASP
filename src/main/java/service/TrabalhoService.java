package service;

import entity.Evento;
import entity.Participante;
import entity.Trabalho;
import entity.Avaliacao;

import java.util.*;

public class TrabalhoService {
    private List<Trabalho> trabalhos;

    public TrabalhoService() {
        this.trabalhos = new ArrayList<>();
    }

    public boolean cadastrarTrabalho(String titulo, List<Participante> autores, Evento evento, String arquivo){
        if(titulo==null || autores==null || autores.isEmpty() || evento==null || arquivo==null) throw new IllegalArgumentException("Dados não podem ser nulos!");

        for(Participante autor : autores){
            if(!autor.isInscritoEmEvento(evento))
                return false;
        }

        Optional<Trabalho> existente = buscarTrabalhoPorTitulo(titulo);
        if(existente.isPresent()) return false;

        Trabalho trabalho = new Trabalho(titulo, autores.get(0), evento, arquivo);

        for(int i=1; i<autores.size(); i++){
            trabalho.adicionarAutor(autores.get(i));
        }

        trabalhos.add(trabalho);

        for(Participante autor : autores){
            autor.adicionarTrabalho(trabalho);
        }
        evento.adicionarTrabalho(trabalho);

        return true;
    }

    public Optional<Trabalho> buscarTrabalhoPorTitulo(String titulo){
        return trabalhos.stream()
                .filter(t -> t.getTitulo().equalsIgnoreCase(titulo))
                .findFirst();
    }

    public List<Trabalho> listarTrabalhos(){
        return new ArrayList<>(trabalhos);
    }

    public boolean adicionarAvaliacao(Trabalho trabalho, Avaliacao avaliacao){
        if(trabalho==null || avaliacao==null)
            return false;
        trabalho.adicionarAvaliacao(avaliacao);
        return true;
    }

    public boolean isAprovado(Trabalho trabalho){
        return trabalho!=null && trabalho.isAprovado();
    }

    public boolean podeEmitirCertificadoApresentacao(Trabalho trabalho){
        return trabalho!=null && trabalho.podeEmitirCertificadoApresentacao();
    }

}
