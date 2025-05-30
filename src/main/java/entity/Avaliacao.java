package entity;

import java.util.Objects;

public class Avaliacao {
    private Avaliador avaliador;
    private Trabalho trabalho;
    private double nota;
    private String comentario;

    public Avaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario) {
        if(avaliador==null) throw new IllegalArgumentException("Avaliador não pode ser nulo");
        if(trabalho==null) throw new IllegalArgumentException("Trabalho não pode ser nulo");
        if(nota<0 || nota>10)
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        this.avaliador = avaliador;
        this.trabalho = trabalho;
        this.nota = nota;
        this.comentario = comentario;
    }

    public Avaliador getAvaliador() {
        return avaliador;
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        if(nota<0 || nota>10)
            throw new IllegalArgumentException("Nota deve ser entre 0 e 10");
        this.nota = nota;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    @Override
    public String toString() {
        return "Avaliacao{" +
                "avaliador=" + avaliador.getNome() +
                ", trabalho=" + trabalho.getTitulo() +
                ", nota=" + nota +
                ", comentario='" + comentario + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Avaliacao)) return false;
        Avaliacao that = (Avaliacao) o;
        return avaliador.equals(that.avaliador) && trabalho.equals(that.trabalho);
    }

    @Override
    public int hashCode() {
        return Objects.hash(avaliador, trabalho);
    }
}