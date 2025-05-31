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

    public boolean adicionarInscricao(Evento evento, Inscricao inscricao) throws Exception {
        if(evento == null || inscricao == null)
            throw new Exception("Evento ou inscrição inválidos!");
        if(!podeInscrever(evento))
            throw new Exception("Evento cheio!");
        return evento.getInscricoes().add(inscricao);
    }

    public boolean removerInscricao(Evento evento, Inscricao inscricao) throws Exception {
        if(evento == null || inscricao == null)
            throw new Exception("Evento ou inscrição inválidos!");
        return evento.removerInscricao(inscricao);
    }

    //verifica se ainda está no período para submeter trabalhos
    public boolean validarPeriodoSubmissao(Evento evento){
        if(evento == null) return false;
        LocalDate hoje = LocalDate.now();
        return (hoje.isEqual(evento.getDataInicioSubmissao()) || hoje.isAfter(evento.getDataInicioSubmissao())) &&
                (hoje.isEqual(evento.getDataFimSubmissao()) || hoje.isBefore(evento.getDataFimSubmissao()));
    }

    public void editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal) throws Exception {
       Optional<Evento> eventoOpt = buscarEventoPornome(nomeEvento);
       if(!eventoOpt.isPresent()){
           throw new Exception("Evento não encontrado!");
       }
       Evento evento = eventoOpt.get();
       if(LocalDate.now().isAfter(evento.getDataInicio()))
           throw new Exception("Não é possível editar evento após seu início!");
       if(novaDataInicio.isAfter(novaDataFim))
           throw new Exception("Data inválida!");
       evento.setNome(novoNome);
       evento.setDescricao(novaDescricao);
       evento.setDataInicio(novaDataInicio);
       evento.setDataFim(novaDataFim);
       evento.setLocal(novoLocal);

    }
}
