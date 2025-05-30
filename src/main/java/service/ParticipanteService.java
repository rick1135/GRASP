package service;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;
import entity.Trabalho;

import java.time.LocalDate;
import java.util.*;

public class ParticipanteService {
    private List<Participante> participantes;

    public ParticipanteService(){
        this.participantes = new ArrayList<>();
    }

    public boolean cadastrarParticipante(Participante participante){
        if(participante==null || buscarParticipantePorEmail(participante.getEmail()).isPresent()){
            return false;
        }
        participantes.add(participante);
        return true;
    }

    public Optional<Participante> buscarParticipantePorEmail(String email){
        return participantes.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public boolean inscreverEmEvento(String emailParticipante, Evento evento){
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        if(participanteOpt.isPresent() && evento != null){
            Participante participante = participanteOpt.get();

            if(participante.isInscritoEmEvento(evento))
                return false; //já inscrito

            Inscricao inscricao = new Inscricao(participante, evento);
            boolean adicionouNoParticipante = participante.adicionarInscricao(inscricao);
            boolean adicionouNoEvento = evento.adicionarInscricao(inscricao);

            return adicionouNoEvento && adicionouNoParticipante;
        }
        return false;
    }

    public boolean cancelarInscricao(String emailParticipante, Evento evento){
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        if (participanteOpt.isPresent() && evento != null) {
            Participante participante = participanteOpt.get();
            LocalDate hoje = LocalDate.now();

            //procura a inscrição do participante no evento
            Optional<Inscricao> inscricaoOpt = participante.getInscricoes().stream()
                    .filter(i -> i.getEvento().equals(evento))
                    .findFirst();

            if (inscricaoOpt.isPresent()) {
                Inscricao inscricao = inscricaoOpt.get();
                //cancela a inscrição se tiver dentro do prazo
                boolean cancelado = participante.cancelarInscricao(inscricao);
                return cancelado;
            }
        }
        return false;
    }

    public boolean submeterTrabalho(String emailParticipante, Evento evento, Trabalho trabalho){
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        if(participanteOpt.isPresent()){
            Participante participante = participanteOpt.get();
            boolean adicionou = participante.adicionarTrabalho(trabalho);

            if(adicionou) return evento.adicionarTrabalho(trabalho);
        }
        return false;
    }

    public List<Inscricao> listarInscricoes(String emailParticipante){
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        return participanteOpt.map(Participante::getInscricoes).orElse(new ArrayList<>());
    }
}
