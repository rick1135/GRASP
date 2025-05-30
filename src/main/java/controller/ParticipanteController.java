package controller;

import entity.Participante;
import service.ParticipanteService;

public class ParticipanteController {
    private final ParticipanteService service;

    public ParticipanteController(ParticipanteService service) {
        this.service = service;
    }

    public Participante criarParticipante(String nome, String email, String instituicao) {
        return service.cadastrarParticipante(nome, email, instituicao);
    }


}
