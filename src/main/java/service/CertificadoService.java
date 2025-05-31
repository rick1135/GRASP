package service;

import entity.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CertificadoService {
    private List<Certificado> certificados;
    private TrabalhoService trabalhoService;
    private ParticipanteService participanteService;

    public CertificadoService(TrabalhoService trabalhoService, ParticipanteService participanteService) {
        this.certificados = new ArrayList<>();
        this.trabalhoService = trabalhoService;
        this.participanteService = participanteService;
    }

    public Certificado emitirCertificadoParticipacao(Participante participante, Evento evento, LocalDate dataEmissao) {
        if (participante == null || evento == null || dataEmissao == null) {
            throw new IllegalArgumentException("Erro: Participante, evento ou data de emissão não pode ser nulo.");
        }
        if (!LocalDate.now().isAfter(evento.getDataFim())) {
            throw new IllegalStateException("Certificado só pode ser emitido após o fim do evento.");
        }
        Optional<Inscricao> inscricaoOpt = participante.getInscricoes().stream()
                .filter(i -> i.getEvento().equals(evento) && (i.isAtiva() || i.isPresencaConfirmada()))
                .findFirst();

        if (inscricaoOpt.isEmpty()) {
            throw new IllegalStateException("Erro: participante não possui uma inscrição ativa para o evento especificado.");
        }

        Inscricao inscricao = inscricaoOpt.get();

        if(!inscricao.isPresencaConfirmada()){
            throw new IllegalStateException ("Certificado de participação requer confirmação de presença");
        }

        Certificado certificado = new Certificado(participante, evento, dataEmissao);
        certificados.add(certificado);
        participante.adicionarCertificado(certificado);
        System.out.println("Certificado emitido com sucesso para " + participante.getNome() + " no evento " + evento.getNome());
        return certificado;
    }

    public List<Certificado> emitirCertificadoApresentacao(Trabalho trabalho, LocalDate dataEmissao) {
        if (trabalho == null || dataEmissao == null) {
            throw new IllegalArgumentException("Trabalho e data de emissão não podem ser nulos.");
        }

        if (!trabalhoService.isAprovado(trabalho)) {
            throw new IllegalStateException("Certificado de apresentação só pode ser emitido para trabalhos aprovados.");
        }

        if (!LocalDate.now().isAfter(trabalho.getEvento().getDataFim())) {
            throw new IllegalStateException("Certificado só pode ser emitido após o fim do evento.");
        }

        List<Certificado> certificadosEmitidos = new ArrayList<>();

        for (Participante autor : trabalho.getAutores()) {
            Optional<Participante> autorOpt = participanteService.buscarParticipantePorEmail(autor.getEmail());

            if (autorOpt.isEmpty()) {
                throw new IllegalStateException("Autor '" + autor.getNome() + "' do trabalho '" + trabalho.getTitulo() + "' não encontrado.");
            }

            Participante autorRegistrado = autorOpt.get();
            Certificado certificado = new Certificado(autorRegistrado, trabalho.getEvento(), trabalho, dataEmissao);
            certificados.add(certificado);
            autorRegistrado.adicionarCertificado(certificado);
            certificadosEmitidos.add(certificado);
        }

        return certificadosEmitidos;
    }

    public List<Certificado> listarCertificadosEmitidos(){
        return new ArrayList<>(certificados);
    }

    public List<Certificado> listarCertificadosPorParticipante(Participante participante){
        if(participante==null) return new ArrayList<>();
        return certificados.stream()
                .filter(c -> c.getParticipante().equals(participante))
                .collect(Collectors.toList());
    }
}
