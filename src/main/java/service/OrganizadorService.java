package service;

import entity.Evento;
import entity.Organizador;
import entity.Participante;

import java.time.LocalDate;
import java.util.*;

public class OrganizadorService {
    private List<Organizador> organizadores;

    public OrganizadorService() {
        this.organizadores = new ArrayList<>();
    }

    public Organizador cadastrarOrganizador(Participante participante) throws Exception {
        if(participante==null) //verificar se esse participante ja é organizador
            throw new Exception("Organizador não encontrado!");

        Organizador organizador = new Organizador(participante.getNome(), participante.getEmail(), participante.getInstituicao());
        organizadores.add(organizador);
        return organizador;
    }

    public Optional<Organizador> buscarOrganizadorPorEmail(String email){
        return organizadores.stream()
                .filter(o -> o.getEmail().equals(email))
                .findFirst();
    }

    public Evento criarEvento(Organizador organizador, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local,
                               int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao){
        if(organizador==null)
            throw new IllegalArgumentException("Organizador não pode ser nulo!");

        Evento evento = organizador.criarEvento(nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, dataInicioSubmissao, dataFimSubmissao, organizador);
        return evento;
    }

    public List<Evento> listarEventosCriados(Organizador organizador){
        if(organizador==null)
            return Collections.emptyList();
        return organizador.getEventosCriados();
    }
}
