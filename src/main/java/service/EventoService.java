package service;

import entity.*;
import repository.EventoRepository;

import java.time.LocalDate;
import java.util.*;

public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(EventoRepository eventoRepository){
        this.eventoRepository = eventoRepository;
    }

    public List<Evento> listarEventos(){
        if(eventoRepository.listarEventos().isEmpty()){
            return new ArrayList<>();
        }
        return eventoRepository.listarEventos();
    }

    public Optional<Evento> buscarEventoPornome(String nome){
        Optional<Evento> eventos = eventoRepository.listarEventos().stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
        if(eventoRepository.listarEventos().isEmpty())
            throw new IllegalArgumentException("Evento não encontrado!");
        return eventos;
    }

    public boolean podeInscrever(Evento evento){
        if(evento == null) return false;
        return evento.isCapacidadeDisponivel();
    }

    public boolean removerInscricao(Evento evento, Inscricao inscricao) throws Exception {
        if(evento == null || inscricao == null)
            throw new Exception("Evento ou inscrição inválidos!");
        return evento.removerInscricao(inscricao);
    }

    public void editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal, LocalDate inicioSubmissao, LocalDate fimSubmissao) throws Exception {
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
       evento.setDataInicioSubmissao(inicioSubmissao);
       evento.setDataFimSubmissao(fimSubmissao);
    }

    public void designarAvaliador(Evento evento, Avaliador avaliador){
        evento.adicionarAvaliador(avaliador);
    }

    public List<Trabalho> listarTrabalhoPorEvento(Evento evento){
        return evento.getTrabalhos();
    }
}
