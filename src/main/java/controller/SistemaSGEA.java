import entity.Avaliador;
import entity.Evento;
import entity.Organizador;
import entity.Participante;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SistemaSGEA {

    private List<Evento> eventos;
    private List<Participante> participantes;
    private List<Organizador> organizadores;
    private List<Avaliador> avaliadores;

    public SistemaSGEA() {
        this.eventos = new ArrayList<>();
        this.participantes = new ArrayList<>();
        this.organizadores = new ArrayList<>();
        this.avaliadores = new ArrayList<>();
    }

    // Cadastro de entidades
    public void cadastrarEvento(Evento evento) {
        eventos.add(evento);
    }

    public void cadastrarParticipante(Participante participante) {
        participantes.add(participante);
    }

    public void cadastrarOrganizador(Organizador organizador) {
        organizadores.add(organizador);
    }

    public void cadastrarAvaliador(Avaliador avaliador) {
        avaliadores.add(avaliador);
    }

    // Listar todos os eventos
    public List<Evento> listarEventos() {
        return new ArrayList<>(eventos);
    }

    // Buscar participante por e-mail ou id (exemplo)
    public Optional<Participante> buscarParticipantePorEmail(String email) {
        return participantes.stream()
                .filter(p -> p.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    // Buscar evento por nome ou id (exemplo)
    public Optional<Evento> buscarEventoPorNome(String nome) {
        return eventos.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nome))
                .findFirst();
    }

    // Inscrever participante em evento
    public boolean inscreverParticipanteEvento(String emailParticipante, String nomeEvento) {
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
        if (participanteOpt.isPresent() && eventoOpt.isPresent()) {
            Participante participante = participanteOpt.get();
            Evento evento = eventoOpt.get();
            return participante.inscreverEmEvento(evento);
        }
        return false;
    }

    // Cancelar inscrição
    public boolean cancelarInscricao(String emailParticipante, String nomeEvento) {
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
        if (participanteOpt.isPresent() && eventoOpt.isPresent()) {
            Participante participante = participanteOpt.get();
            Evento evento = eventoOpt.get();
            return participante.cancelarInscricao(evento, java.time.LocalDate.now());
        }
        return false;
    }

    // Submeter trabalho
    public boolean submeterTrabalho(String emailParticipante, String nomeEvento, Trabalho trabalho) {
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
        if (participanteOpt.isPresent() && eventoOpt.isPresent()) {
            Participante participante = participanteOpt.get();
            Evento evento = eventoOpt.get();
            return participante.adicionarTrabalho(trabalho, evento);
        }
        return false;
    }

    // Designar avaliador para trabalho
    public boolean designarAvaliador(Trabalho trabalho, Avaliador avaliador) {
        if (avaliadores.contains(avaliador)) {
            avaliador.adicionarTrabalhoParaAvaliar(trabalho);
            return true;
        }
        return false;
    }

    // Registrar avaliação
    public boolean registrarAvaliacao(Trabalho trabalho, Avaliacao avaliacao) {
        if (trabalho != null && avaliacao != null) {
            trabalho.adicionarAvaliacao(avaliacao);
            return true;
        }
        return false;
    }

    // Emitir certificado de participação
    public Certificado emitirCertificadoParticipacao(String emailParticipante, String nomeEvento) {
        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
        if (participanteOpt.isPresent() && eventoOpt.isPresent()) {
            Participante participante = participanteOpt.get();
            Evento evento = eventoOpt.get();
            Certificado certificado = new Certificado(participante, evento, TipoCertificado.PARTICIPACAO, java.time.LocalDate.now());
            if (certificado.podeEmitirCertificadoParticipacao()) {
                return certificado;
            }
        }
        return null;
    }

    // Emitir certificado de apresentação
    public Certificado emitirCertificadoApresentacao(Trabalho trabalho) {
        if (trabalho != null) {
            Certificado certificado = new Certificado(trabalho.getAutor(), trabalho.getEvento(), TipoCertificado.APRESENTACAO, java.time.LocalDate.now());
            if (certificado.podeEmitirCertificadoApresentacao()) {
                return certificado;
            }
        }
        return null;
    }
}
