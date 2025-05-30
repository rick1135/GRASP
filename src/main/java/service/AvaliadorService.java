package service;

import entity.Avaliador;
import entity.Trabalho;

import java.util.*;

public class AvaliadorService {
    private List<Avaliador> avaliadores;

    public AvaliadorService() {
        this.avaliadores = new ArrayList<>();
    }

    public boolean cadastrarAvaliador(Avaliador avaliador){
        if(avaliador==null || buscarAvaliadorPorEmail(avaliador.getEmail()).isPresent()){
            return false;
        }

        avaliadores.add(avaliador);
        return true;
    }

    public Optional<Avaliador> buscarAvaliadorPorEmail(String email){
        return avaliadores.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

    public boolean designarTrabalho(Avaliador avaliador, Trabalho trabalho){
        if(avaliador==null || trabalho==null) return false;

        return avaliador.designarTrabalho(trabalho);
    }

    public boolean registrarAvaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario){
        if(avaliador==null || trabalho==null) return false;

        return avaliador.registrarAvaliacao(trabalho, nota, comentario);
    }

    public List<Trabalho> listarTrabalhosDesignador(Avaliador avaliador){
        if(avaliador==null) return Collections.emptyList();
        return avaliador.getTrabalhosDesignados();
    }
}
