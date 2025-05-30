package service;

import entity.Evento;
import entity.Organizador;

import java.time.LocalDate;
import java.util.*;

public class OrganizadorService {
    private List<Organizador> organizadores;

    public OrganizadorService() {
        this.organizadores = new ArrayList<>();
    }

    public boolean cadastrarOrganizador(Organizador organizador){
        if(organizador==null || buscarOrganizadorPorEmail(organizador.getEmail()).isPresent())
            return false;
        organizadores.add(organizador);
        return true;
    }

    public Optional<Organizador> buscarOrganizadorPorEmail(String email){
        return organizadores.stream()
                .filter(o -> o.getEmail().equals(email))
                .findFirst();
    }

    public Evento criarEvento(Organizador organizador, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local,
                               int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao){
        if(organizador==null)
            return null;

        Evento evento = organizador.criarEvento(nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, dataInicioSubmissao, dataFimSubmissao);
        return evento;
    }

    public List<Evento> listarEventosCriados(Organizador organizador){
        if(organizador==null)
            return Collections.emptyList();
        return organizador.getEventosCriados();
    }
}
