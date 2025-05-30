package service;

import entity.Evento;
import entity.Inscricao;

import java.time.LocalDate;
import java.util.*;

public class EventoService {
    private List<Evento> eventos;

    public EventoService(){
        this.eventos = new ArrayList<>();
    }

    public void criarEvento(Evento evento) throws Exception {
        if(evento == null || buscarEventoPornome(evento.getNome()).isPresent()){
            throw new Exception("Evento inválido ou já criado!");
        }
        eventos.add(evento);
    }

    public List<Evento> listarEventos(){
        return new ArrayList<>(eventos);
    }

    public Optional<Evento> buscarEventoPornome(String nome){
        return eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    public boolean podeInscrever(Evento evento){
        if(evento == null) return false;
        return evento.isCapacidadeDisponivel();
    }

    public boolean adicionarInscricao(Evento evento, Inscricao inscricao){
        if(evento == null || inscricao == null)
            return false;
        if(!podeInscrever(evento))
            return false; //evento cheio
        return evento.getInscricoes().add(inscricao);
    }

    public boolean removerInscricao(Evento evento, Inscricao inscricao){
        if(evento == null || inscricao == null)
            return false;
        return evento.removerInscricao(inscricao);
    }

    //verifica se ainda está no período para submeter trabalhos
    public boolean validarPeriodoSubmissao(Evento evento){
        if(evento == null) return false;
        LocalDate hoje = LocalDate.now();
        return (hoje.isEqual(evento.getDataInicioSubmissao()) || hoje.isAfter(evento.getDataInicioSubmissao())) &&
                (hoje.isEqual(evento.getDataFimSubmissao()) || hoje.isBefore(evento.getDataFimSubmissao()));
    }

    public boolean editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal){
       Optional<Evento> eventoOpt = buscarEventoPornome(nomeEvento);
       if(eventoOpt.isPresent()){
           Evento evento = eventoOpt.get();
           if(LocalDate.now().isAfter(evento.getDataInicio()))
               return false; //não pode editar após evento ter iniciado
           if(novaDataInicio.isAfter(novaDataFim))
               return false;
           evento.setNome(novoNome);
           evento.setDescricao(novaDescricao);
           evento.setDataInicio(novaDataInicio);
           evento.setDataFim(novaDataFim);
           evento.setLocal(novoLocal);
           return true;
       }
       return false;
    }
}
