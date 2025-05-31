package service;

import entity.Avaliador;
import entity.Trabalho;
import repository.AvaliadorRepository;

import java.util.*;

public class AvaliadorService {
    private final AvaliadorRepository avaliadorRepository;

    public AvaliadorService() {
        this.avaliadorRepository = new AvaliadorRepository();
    }

    public boolean cadastrarAvaliador(Avaliador avaliador){
        if(avaliador==null || avaliadorRepository.encontrarPorEmail(avaliador.getEmail()).isPresent()){
            return false;
        }

        avaliadorRepository.salvar(avaliador);
        return true;
    }

    public Optional<Avaliador> buscarAvaliadorPorEmail(String email){
        return avaliadorRepository.encontrarPorEmail(email);
    }

    public boolean designarTrabalho(Avaliador avaliador, Trabalho trabalho){
        if(avaliador==null || trabalho==null) return false;

        boolean designado = avaliador.designarTrabalho(trabalho);
        if(designado)
            avaliadorRepository.salvar(avaliador);
        return designado;
    }

    public boolean registrarAvaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario){
        if(avaliador==null || trabalho==null) return false;

        boolean registrado = avaliador.registrarAvaliacao(trabalho, nota, comentario);
        if(registrado)
            avaliadorRepository.salvar(avaliador);
        return registrado;
    }

    public List<Trabalho> listarTrabalhosDesignador(Avaliador avaliador){
        if(avaliador==null) return Collections.emptyList();
        return avaliador.getTrabalhosDesignados();
    }
}
