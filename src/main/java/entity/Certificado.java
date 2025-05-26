package entity;

import java.time.LocalDate;

public class Certificado {
    public enum TipoCertificado{
        PARTICIPACAO,
        APRESENTACAO_TRABALHO
    }

    private Participante participante;
    private Evento evento;
    private Trabalho trabalho;
    private TipoCertificado tipo;
    private LocalDate dataEmissao;
    private String codigoValidacao; //todo

    public Certificado(Participante participante, Evento evento, TipoCertificado tipo, LocalDate dataEmissao) {
        this.participante = participante;
        this.evento = evento;
        this.tipo = tipo;
        this.dataEmissao = dataEmissao;
    }

    public Participante getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public Trabalho getTrabalho() {
        return trabalho;
    }

    public TipoCertificado getTipo() {
        return tipo;
    }

    public LocalDate getDataEmissao() {
        return dataEmissao;
    }

    public boolean podeEmitirCertificadoParti(){

    }

    @Override
    public String toString() {
        String res = "Certificado de " + (tipo==TipoCertificado.PARTICIPACAO ? "Participação" : "Apresentação de Trabalho")
            + "\nParticipante: " + participante.getNome()
            + "\nEvento: " + evento.getNome()
            + "\nData de Emissão: " + dataEmissao;
        if(tipo==TipoCertificado.APRESENTACAO_TRABALHO && trabalho!=null)
            base+= "\nTrabalho: " + trabalho.getTitulo();

        return res;
    }
}
