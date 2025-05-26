package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Organizador extends Participante{
    private List<Evento> eventosCriados;

    public Organizador(String nome, String email, String instituicao) {
        super(nome, email, instituicao);
        this.eventosCriados = new ArrayList<>();
    }

    public Evento criarEvento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local, int capacidadeMaxima, int prazoCancelamento){
        Evento evento = new Evento(nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, prazoCancelamento);
        eventosCriados.add(evento);
        return evento;
    }

    public List<Evento> getEventosCriados(){
        return eventosCriados;
    }
}
