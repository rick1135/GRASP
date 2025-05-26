package entity;

import java.time.LocalDate;
import java.util.List;

public class Trabalho {
    private String titulo;
    private Participante autor;
    private Evento evento;
    private String arquivo; //nome ou caminho
    private List<Avaliacao> avaliacoes;
    private double notaFinal;

    public Trabalho(String titulo, Participante autor, Evento evento, String arquivo) {
        this.titulo = titulo;
        this.autor = autor;
        this.evento = evento;
        this.arquivo = arquivo;
        this.avaliacoes = new java.util.ArrayList<>();
        this.notaFinal = 0.0;
    }

    public String getTitulo() {
        return titulo;
    }

    public Participante getAutor() {
        return autor;
    }

    public void setAutor(Participante autor) {
        this.autor = autor;
    }

    public Evento getEvento() {
        return evento;
    }

    public String getArquivo() {
        return arquivo;
    }

    public List<Avaliacao> getAvaliacoes() {
        return avaliacoes;
    }

    public double getNotaFinal() {
        return notaFinal;
    }

    private void adicionarAvaliacao(Avaliacao avaliacao){
        avaliacoes.add(avaliacao);
        atualizarNotaFinal();
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

    public boolean isAprovado(){
        if(avaliacao==null){
            return false;
        }
        return notaFinal>=7.0;
    }

    public boolean podeEmitirCertificadoApresentacao(){
        return isAprovado() && LocalDate.now().isAfter(evento.getDataFim());
    }

    @Override
    public String toString() {
        return "Trabalho{" +
                "titulo='" + titulo + '\'' +
                ", autor=" + autor +
                ", notaFinal=" + notaFinal +
                '}';
    }
}
