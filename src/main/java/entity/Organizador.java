package entity;

import java.time.LocalDate;
import java.util.*;

public class Organizador extends Participante{
    private List<Evento> eventosCriados;

    public Organizador(String nome, String email, String instituicao) {
        super(nome, email, instituicao);
        this.eventosCriados = new ArrayList<>();
    }

    public Evento criarEvento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local, int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao, Organizador organizador){
        Evento evento = new Evento(nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, dataInicioSubmissao, dataFimSubmissao, organizador);
        eventosCriados.add(evento);
        return evento;
    }

    public List<Evento> getEventosCriados() {
        return Collections.unmodifiableList(eventosCriados);
    }
}
