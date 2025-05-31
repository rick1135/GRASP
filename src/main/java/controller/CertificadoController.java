package controller;

import entity.Certificado;
import entity.Evento;
import entity.Participante;
import entity.Trabalho;
import service.CertificadoService;
import service.ParticipanteService;
import service.TrabalhoService;

import java.time.LocalDate;
import java.util.List;

public class CertificadoController {
    private final CertificadoService service;

    public CertificadoController(TrabalhoService trabalhoService, ParticipanteService participanteService) {
        this.service = new CertificadoService(trabalhoService, participanteService);
    }

    public Certificado emitirCertificadoParticipacao(Participante participante, Evento evento, LocalDate dataEmissao){
        return service.emitirCertificadoParticipacao(participante, evento, dataEmissao);
    }

    public List<Certificado> emitirCertificadoApresentacao(Trabalho trabalho, LocalDate dataEmissao){
        return service.emitirCertificadoApresentacao(trabalho, dataEmissao);
    }

    public List<Certificado> listarCertificadosEmitidos(){
        return service.listarCertificadosEmitidos();
    }

    public List<Certificado> listarCertificadosPorParticipante(Participante participante){
        return service.listarCertificadosPorParticipante(participante);
    }
}
