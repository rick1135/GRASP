package service;

import entity.Avaliador;
import entity.Trabalho;

import java.util.*;

public class AvaliadorService {
    private List<Avaliador> avaliadores;

    public AvaliadorService() {
        this.avaliadores = new ArrayList<>();
    }

    public void cadastrarAvaliador(Avaliador avaliador) throws Exception {
        if(avaliador==null || buscarAvaliadorPorEmail(avaliador.getEmail()).isPresent()){
            throw new Exception("Avaliador não encontrado!");
        }
        avaliadores.add(avaliador);
    }

    public Optional<Avaliador> buscarAvaliadorPorEmail(String email){
        return avaliadores.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

    public boolean designarTrabalho(Avaliador avaliador, Trabalho trabalho){
        if(avaliador==null || trabalho==null)
            throw new IllegalArgumentException("Entradas nao podem ser nulos!");

        return avaliador.designarTrabalho(trabalho);
    }

    public boolean registrarAvaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario){
        if(avaliador==null || trabalho==null)
            throw new IllegalArgumentException("Entradas nao podem ser nulos!");

        return avaliador.registrarAvaliacao(trabalho, nota, comentario);
    }

    public List<Trabalho> listarTrabalhosDesignador(Avaliador avaliador){
        if(avaliador==null)
            throw new IllegalArgumentException("Entradas nao podem ser nulos!");
        return avaliador.getTrabalhosDesignados();
    }
}
