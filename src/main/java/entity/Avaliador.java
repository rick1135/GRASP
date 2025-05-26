package entity;

import java.util.*;

public class Avaliador extends Participante{
    private List<Trabalho> trabalhosDesignados;

    public Avaliador(String nome, String email, String instituicao) {
        super(nome, email, instituicao);
        this.trabalhosDesignados = new ArrayList<>();
    }

    public boolean designarTrabalho(Trabalho trabalho, double nota, String parecer){
        if(!trabalhosDesignados.contains(trabalho)){
            return false;
        }
        Avaliacao avaliacao = new Avaliacao(nota, parecer, this);
        trabalho.setAvaliacao(avaliacao);
        return true;
    }

    public List<Trabalho> getTrabalhosDesignados(){
        return trabalhosDesignados;
    }
}
