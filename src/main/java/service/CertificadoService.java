package service;

import entity.*;
import repository.CertificadoRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CertificadoService {
    private final CertificadoRepository certificadoRepository;
    private TrabalhoService trabalhoService;
    private ParticipanteService participanteService;

    public CertificadoService(CertificadoRepository certificadoRepository,TrabalhoService trabalhoService, ParticipanteService participanteService) {
        this.certificadoRepository = certificadoRepository;
        this.trabalhoService = trabalhoService;
        this.participanteService = participanteService;
    }

    public Certificado emitirCertificadoParticipacao(Participante participante, Evento evento, LocalDate dataEmissao){
        if(participante==null || evento==null || dataEmissao==null) {
            System.out.println("Erro: Participante, evento ou data de emissão não pode ser nulo");
            return null;
        }
        if(!dataEmissao.isAfter(evento.getDataFim())) {
            System.out.println("Certificado só pode ser emitido após o fim do evento");
            return null;
        }
        Optional<Inscricao> inscricaoOpt = participante.getInscricoes().stream()
                .filter(i -> i.getEvento().equals(evento) && i.isAtiva())
                .findFirst();
        if(inscricaoOpt.isEmpty()){
            System.out.println("Erro: participante não possui uma inscrição ativa para o evento");
            return null;
        }

        Inscricao inscricao = inscricaoOpt.get();

        if(!inscricao.isPresencaConfirmada()){
            System.out.println("Certificado de participação requer confirmação de presença");
            return null;
        }

        Certificado certificado = new Certificado(participante, evento, dataEmissao);
        certificadoRepository.salvar(certificado);
        participante.adicionarCertificado(certificado);
        System.out.println("Certificado emitido com sucesso para " + participante.getNome() + " no evento " + evento.getNome());
        return certificado;
    }

    public List<Certificado> emitirCertificadoApresentacao(Trabalho trabalho, LocalDate dataEmissao){
        List<Certificado> certificadosEmitidos = new ArrayList<>();
        if(trabalho==null || dataEmissao==null) {
            System.out.println("Erro: Trabalho ou data de emissão não pode ser nulo");
            return certificadosEmitidos;
        }

        if(!trabalhoService.isAprovado(trabalho)){
            System.out.println("Certificado de apresentação só é emitido para trabalhos aprovados");
            return certificadosEmitidos;
        }
        if(!dataEmissao.isAfter(trabalho.getEvento().getDataFim())){
            System.out.println("Certificado só é emitido após o fim do evento");
            return certificadosEmitidos;
        }

        for(Participante autor : trabalho.getAutores()){
            Optional<Participante> autorOpt = participanteService.buscarParticipantePorEmail(autor.getEmail());
            if(autorOpt.isPresent()){
                Participante autorRegistrado = autorOpt.get();
                Certificado certificado = new Certificado(autorRegistrado, trabalho.getEvento(), trabalho, dataEmissao);
                certificadoRepository.salvar(certificado);
                autor.adicionarCertificado(certificado);
                certificadosEmitidos.add(certificado);
                System.out.println("Certificado emitido com sucesso para " + autor.getNome() + " no evento " + trabalho.getEvento().getNome());
            } else System.out.println("Autor " + autor.getNome() + "do trabalho " + trabalho.getTitulo() + "não encontrado");
        }
        return certificadosEmitidos;
    }

    public List<Certificado> listarCertificadosEmitidos(){
        return certificadoRepository.listarCertificados();
    }

    public List<Certificado> listarCertificadosPorParticipante(Participante participante){
        if(participante==null) return new ArrayList<>();
        return certificadoRepository.encontrarPorParticipante(participante);
    }
}
