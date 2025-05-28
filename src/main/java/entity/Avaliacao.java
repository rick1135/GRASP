package entity;

public class Avaliacao {
    private Avaliador avaliador;
    private Trabalho trabalho;
    private double nota;
    private String comentario;

    public Avaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario) {
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

    public String getComentario() {
        return comentario;
    }

    public void setNota(double nota) {
        if(nota<0 || nota>10)
            throw new IllegalArgumentException("Neta deve ser entre 0 e 10");
        this.nota = nota;
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
}
