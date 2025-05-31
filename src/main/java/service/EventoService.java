package service;

import entity.Evento;
import entity.Inscricao;
import repository.EventoRepository;

import java.time.LocalDate;
import java.util.*;

public class EventoService {
    private final EventoRepository eventoRepository;

    public EventoService(){
        this.eventoRepository = new EventoRepository();
    }

    public void criarEvento(Evento evento) throws Exception {
        if(evento == null || buscarEventoPornome(evento.getNome()).isPresent()){
            return; //evento não pode ser nulo
        }
        if(eventoRepository.listarPorNome(evento.getNome()).isPresent())
            return; //evento ja criado

        eventoRepository.salvar(evento);
    }

    public List<Evento> listarEventos(){
        return eventoRepository.listarEventos();
    }

    public Optional<Evento> buscarEventoPornome(String nome){
        return eventoRepository.listarPorNome(nome);
    }

    public boolean podeInscrever(Evento evento){
        if(evento == null) return false;
        return evento.isCapacidadeDisponivel();
    }

//    public boolean adicionarInscricao(Evento evento, Inscricao inscricao){
//        if(evento == null || inscricao == null)
//            return false;
//        if(!podeInscrever(evento))
//            return false; //evento cheio
//        return evento.getInscricoes().add(inscricao);
//    }
//
//    public boolean removerInscricao(Evento evento, Inscricao inscricao){
//        if(evento == null || inscricao == null)
//            return false;
//        return evento.removerInscricao(inscricao);
//    }

    //verifica se ainda está no período para submeter trabalhos
    public boolean validarPeriodoSubmissao(Evento evento){
        if(evento == null) return false;
        return evento.estaNoPeriodoSubmissao(LocalDate.now());
    }

    public boolean editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal){
       Optional<Evento> eventoOpt = buscarEventoPornome(nomeEvento);
       if(eventoOpt.isPresent()){
           Evento evento = eventoOpt.get();
           if(LocalDate.now().isAfter(evento.getDataInicio()) || LocalDate.now().isEqual(evento.getDataInicio()))
               return false; //não pode editar após evento ter iniciado
           if(novaDataInicio.isAfter(novaDataFim))
               return false;
           evento.setNome(novoNome);
           evento.setDescricao(novaDescricao);
           evento.setDataInicio(novaDataInicio);
           evento.setDataFim(novaDataFim);
           evento.setLocal(novoLocal);
           eventoRepository.salvar(evento);
           return true;
       }
       return false;
    }
}
