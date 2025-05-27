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

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getInstituicao() {
        return instituicao;
    }

    public void setInstituicao(String instituicao) {
        this.instituicao = instituicao;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public boolean inscreverEvento(Evento evento){
        if (evento.temVaga()) {
            Inscricao inscricao = new Inscricao(this, evento);
            boolean inscrito = evento.adicionarInscricao(inscricao);
            return true;
        }
        return false;
    }

    public boolean cancelarInscricao(Evento evento, LocalDate dataAtual) {
        for (Inscricao inscricao : inscricoes) {
            if (inscricao.getEvento().equals(evento)) {
                if(evento.podeCancelarInscricao(dataAtual)){
                    inscricoes.remove(inscricao);
                    evento.removerInscricao(inscricao);
                    return true;
                }
            }
        }
        return false;  //participante não está inscrito no evento ou prazo expirado
    }

    public void adicionarTrabalho(Trabalho trabalho){
        trabalhos.add(trabalho);
    }

    public void adicionarCertificado(Certificado certificado){
        certificados.add(certificado);
    }
}
