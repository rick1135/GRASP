package service;

import entity.Evento;
import entity.Inscricao;
import entity.Participante;
import entity.Trabalho;
import repository.ParticipanteRepository;

import java.time.LocalDate;
import java.util.*;

public class ParticipanteService {
    private final ParticipanteRepository participanteRepository;

    public ParticipanteService(ParticipanteRepository participanteRepository){
        this.participanteRepository = participanteRepository;
    }

    public Participante cadastrarParticipante(String name, String email, String instituicao){
        if (name == null || name.trim().length() < 3) {
            throw new IllegalArgumentException("Nome deve ter ao menos 3 caracteres.");
        }

        if (name.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Nome não deve conter números.");
        }

        if (email == null || !email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Email inválido.");
        }

        for(Participante p : participanteRepository.listarParticipantes()){
            if(p.getEmail().equals(email)){
                throw new IllegalArgumentException("Email já existente.");
            }
        }

        if (instituicao == null || instituicao.trim().length() < 3) {
            throw new IllegalArgumentException("Instituição deve ter ao menos 3 letras.");
        }

        Participante participante = new Participante(name, email, instituicao);
        participanteRepository.salvar(participante);
        return participante;
    }

    public Optional<Participante> buscarParticipantePorEmail(String email){
        return participanteRepository.listarParticipantes().stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();
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

    public List<Inscricao> listarInscricoes(String emailParticipante){
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        return participanteOpt.map(Participante::getInscricoes).orElse(new ArrayList<>());
    }
}
