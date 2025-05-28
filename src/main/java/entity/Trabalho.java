package entity;

import java.time.LocalDate;
import java.util.*;

public class Trabalho {
    public enum StatusTrabalho{
        SUBMETIDO,
        APROVADO,
        REPROVADO
    }

    private String titulo;
    private String arquivo; //nome ou caminho para facilitar
    private List<Participante> autores;
    private Evento evento;
    private List<Avaliacao> avaliacoes;
    private double notaFinal;
    private StatusTrabalho status;

    public Trabalho(String titulo, Participante autor, Evento evento, String arquivo) {
        this.titulo = titulo;
        this.autores = new ArrayList<>();
        adicionarAutor(autor);
        this.evento = evento;
        this.arquivo = arquivo;
        this.avaliacoes = new ArrayList<>();
        this.notaFinal = 0.0;
        this.status = StatusTrabalho.SUBMETIDO;
    }

    public String getTitulo() {
        return titulo;
    }

    public List<Participante> getAutores() {
        return Collections.unmodifiableList(autores);
    }

    public List<Avaliacao> getAvaliacoes() {
        return Collections.unmodifiableList(avaliacoes);
    }

    public void adicionarAutor(Participante autor){
        if(autor!=null && !autores.contains(autor))
            autores.add(autor);
    }

    public Evento getEvento() {
        return evento;
    }

    public String getArquivo() {
        return arquivo;
    }


    public double getNotaFinal() {
        return notaFinal;
    }

    public StatusTrabalho getStatus() {
        return status;
    }

    public void adicionarAvaliacao(Avaliacao avaliacao){
        if(avaliacao!=null) {
            avaliacoes.add(avaliacao);
            atualizarNotaFinal();
            atualizarStatus();
        }
    }

    private void atualizarNotaFinal(){
        if(avaliacoes.isEmpty()){
            notaFinal = 0.0;
            return;
        }
        double soma = 0.0;

        for(Avaliacao a : avaliacoes){
            soma+=a.getNota();
        }
        notaFinal = soma/avaliacoes.size();
    }

    private void atualizarStatus(){
        if(notaFinal>=7.0)
            status = StatusTrabalho.APROVADO;
        else
            status = StatusTrabalho.REPROVADO;
    }

    public boolean isAprovado(){
        return status==StatusTrabalho.APROVADO;
    }

    public boolean podeEmitirCertificadoApresentacao(){
        return isAprovado() && LocalDate.now().isAfter(evento.getDataFim());
    }

    @Override
    public String toString() {
        return "Trabalho{" +
                "titulo='" + titulo + '\'' +
                ", autores=" + Arrays.toString(autores.toArray()) +
                ", notaFinal=" + notaFinal +
                ", status=" + status +
                '}';
    }
}