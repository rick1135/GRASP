package entity;

import java.util.*;

public class Avaliador extends Participante{
    private List<Trabalho> trabalhosDesignados;

    public Avaliador(String nome, String email, String instituicao) {
        super(nome, email, instituicao);
        this.trabalhosDesignados = new ArrayList<>();
    }

    public boolean designarTrabalho(Trabalho trabalho){
        if(trabalho == null || trabalhosDesignados.contains(trabalho)){
            return false;
        }
        trabalhosDesignados.add(trabalho);
        return true;
    }

    public boolean registrarAvaliacao(Trabalho trabalho, double nota, String comentario){
        if(!trabalhosDesignados.contains(trabalho))
            return false;
        Avaliacao avaliacao = new Avaliacao(this, trabalho, nota, comentario);
        trabalho.adicionarAvaliacao(avaliacao);
        return true;
    }

    public List<Trabalho> getTrabalhosDesignados() {
        return Collections.unmodifiableList(trabalhosDesignados);
    }
}
