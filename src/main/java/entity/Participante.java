package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Participante {
    private String nome;
    private String email;
    private String instituicao;
    private List<Inscricao> inscricoes;
    private List<Certificado> certificados;
    private List<Trabalho> trabalhos;

    public Participante(String nome, String email, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
        this.inscricoes = new ArrayList<>();
        this.certificados = new ArrayList<>();
        this.trabalhos = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public List<Inscricao> getInscricoes() {
        return Collections.unmodifiableList(inscricoes);
    }

    public List<Certificado> getCertificados() {
        return Collections.unmodifiableList(certificados);
    }

    public List<Trabalho> getTrabalhos() {
        return Collections.unmodifiableList(trabalhos);
    }

    public void atualizarDados(String nome, String email, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
    }

    public boolean adicionarInscricao(Inscricao inscricao){
        if(inscricoes.contains(inscricao))
            return false;
        this.inscricoes.add(inscricao);
        return true;
    }

    public boolean cancelarInscricao(Inscricao inscricao) {
        if(this.inscricoes.contains(inscricao)){
            boolean cancelado = inscricao.cancelar();
            if(cancelado){
                this.inscricoes.remove(inscricao);
                return true;
            }
        }
        return false;
    }

    public boolean adicionarTrabalho(Trabalho trabalho){
        if(trabalhos.contains(trabalho))
            return false;
        trabalhos.add(trabalho);
        return true;
    }

    public boolean removerTrabalho(Trabalho trabalho){
        if(this.trabalhos.contains(trabalho)){
            this.trabalhos.remove(trabalho);
            return true;
        }
        return false;
    }

    public boolean adicionarCertificado(Certificado certificado){
        if(certificados.contains(certificado))
            return false;
        certificados.add(certificado);
        return true;
    }

    public boolean isInscritoEmEvento(Evento evento){
        return inscricoes.stream().anyMatch(inscricao -> inscricao.getEvento().equals(evento) && inscricao.isAtiva());
    }

//    public boolean confirmarPresenca(String emailParticipante, String nomeEvento) {
//        Optional<Participante> participanteOpt = buscarParticipantePorEmail(emailParticipante);
//        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
//        if (participanteOpt.isPresent() && eventoOpt.isPresent()) {
//            Participante participante = participanteOpt.get();
//            Evento evento = eventoOpt.get();
//            Optional<Inscricao> inscricaoOpt = participante.getInscricoes().stream()
//                    .filter(i -> i.getEvento().equals(evento))
//                    .findFirst();
//            if (inscricaoOpt.isPresent()) {
//                inscricaoOpt.get().confirmarPresenca();
//                return true;
//            }
//        }
//        return false;
//    }

//    public List<Inscricao> listarInscricoesEvento(String nomeEvento) {
//        Optional<Evento> eventoOpt = buscarEventoPorNome(nomeEvento);
//        if (eventoOpt.isPresent()) {
//            return eventoOpt.get().getInscricoes();
//        }
//        return new ArrayList<>();
//    }

    public boolean removerInscricao(Inscricao inscricao){
        return inscricoes.remove(inscricao);
    }
}
