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
    tste

    public Participante(String nome, String email, String instituicao) {
        this.nome = nome;
        this.email = email;
        this.instituicao = instituicao;
        this.inscricoes = new ArrayList<>();
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

    public void setInscricoes(List<Inscricao> inscricoes) {
        this.inscricoes = inscricoes;
    }

    public boolean isInscritoEmEvento(Evento evento){
        for (Inscricao i : inscricoes) {
            if(i.getEvento().equals(evento))
                return true; //já está inscrito
        }
        return false; //não está
    }

    public boolean inscreverEvento(Evento evento, LocalDate dataAtual){
        if(isInscritoEmEvento(evento)){
            return false; //proibido se inscrever 2x no msm evento
        }
        if(!evento.inscreverParticipante(this, dataAtual)){
            return false; //verifica se a inscrição foi confirmada no evento
        }
        Inscricao novaInscricao = new Inscricao(this, evento);
        inscricoes.add(novaInscricao);
        return true;
    }

    public boolean cancelarInscricao(Evento evento, LocalDate dataAtual) {
        for (Inscricao inscricao : inscricoes) {
            if (inscricao.getEvento().equals(evento)) {
                long diasRestantes = evento.getDataInicio().until(dataAtual, java.time.temporal.ChronoUnit.DAYS);
                if (diasRestantes < 2) {
                    return false;  //proibido cancelar a menos de 2 dias antes do evento
                }
                inscricoes.remove(inscricao);
                evento.cancelarInscricao(this, dataAtual);
                return true;
            }
        }
        return false;  //participante não está inscrito no evento
    }

    public boolean adicionarTrabalho(Trabalho trabalho, Evento evento) {
        if (isInscritoEmEvento(evento)) {
            trabalho.setAutor(this);
            evento.submeterTrabalho(trabalho, this, LocalDate.now(), evento.getDataInicio(), evento.getDataFim());
            return true;
        }
        return false;
    }

}
