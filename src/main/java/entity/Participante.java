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
        return inscricoes;
    }

    public List<Certificado> getCertificados() {
        return certificados;
    }

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public void atualizarDados(String nome, String email, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
    }

    public boolean adicionarInscricao(Inscricao inscricao){
        this.inscricoes.add(inscricao);
        return true;
    }

    public boolean cancelarInscricao(Inscricao inscricao) {
        if(this.inscricoes.contains(inscricao)){
            inscricao.cancelar();
            return true;
        }
        return false;
    }

    public void adicionarTrabalho(Trabalho trabalho){
        trabalhos.add(trabalho);
    }

    public boolean removetrabalho(Trabalho trabalho){
        if(this.trabalhos.contains(trabalho)){
            this.trabalhos.remove(trabalho);
            return true;
        }
        return false;
    }

    public void adicionarCertificado(Certificado certificado){
        certificados.add(certificado);
    }
}
