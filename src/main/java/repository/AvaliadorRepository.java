package repository;

import entity.Avaliador;

import java.util.*;

public class AvaliadorRepository {
    private final List<Avaliador> avaliadores;

    public AvaliadorRepository() {
        this.avaliadores = new ArrayList<>();
    }

    public void salvar(Avaliador avaliador){
        Optional<Avaliador> existente = encontrarPorEmail(avaliador.getEmail());
        if(existente.isPresent())
            avaliadores.remove(existente.get());
        avaliadores.add(avaliador);
    }

    public Optional<Avaliador> encontrarPorEmail(String email){
        return avaliadores.stream()
                .filter(a -> a.getEmail().equals(email))
                .findFirst();
    }

    public List<Avaliador> listarAvaliadores(){
        return Collections.unmodifiableList(avaliadores);
    }

    public void remover(Avaliador avaliador){
        avaliadores.remove(avaliador);
    }
}
