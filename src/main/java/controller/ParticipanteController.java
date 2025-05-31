package controller;

import entity.Certificado;
import entity.Evento;
import entity.Participante;
import service.CertificadoService;
import service.ParticipanteService;

import java.time.LocalDate;

public class ParticipanteController {
    private final ParticipanteService service;

    public ParticipanteController() {
        this.service = new ParticipanteService();
    }

    public Participante criarParticipante(String nome, String email, String instituicao) {
        return service.cadastrarParticipante(nome, email, instituicao);
    }
}
