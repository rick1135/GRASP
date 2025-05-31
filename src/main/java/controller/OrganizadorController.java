package controller;

import entity.Evento;
import entity.Organizador;
import service.OrganizadorService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class OrganizadorController {
    private final OrganizadorService service;


    public OrganizadorController() {
        this.service = new OrganizadorService();
    }

    public void cadastrarOrganizador(Organizador organizador) throws Exception {
        service.cadastrarOrganizador(organizador);
    }

    public Optional<Organizador> buscarOrganizadorPorEmail(String email){
        return service.buscarOrganizadorPorEmail(email);
    }

    public Evento criarEvento(Organizador organizador, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local,
                              int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao){
        return service.criarEvento(organizador, nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, dataInicioSubmissao, dataFimSubmissao);
    }

    public List<Evento> listarEventosCriados(Organizador organizador){
        return service.listarEventosCriados(organizador);
    }
}
