package entity;

import java.time.LocalDate;
import java.util.*;

public class Certificado {
    public enum TipoCertificado{
        PARTICIPACAO,
        APRESENTACAO_TRABALHO
    }

    private final Participante participante;
    private final Evento evento;
    private final Trabalho trabalho;
    private final TipoCertificado tipo;
    private final LocalDate dataEmissao;
    private final String codigoValidacao;

    //constructor para certificade de participação
    public Certificado(Participante participante, Evento evento, LocalDate dataEmissao) {
        if (participante == null) throw new IllegalArgumentException("Participante não pode ser nulo");
        if (evento == null) throw new IllegalArgumentException("Evento não pode ser nulo");
        if (dataEmissao == null) throw new IllegalArgumentException("Data de emissão não pode ser nula");
        this.participante = participante;
        this.evento = evento;
        this.tipo = TipoCertificado.PARTICIPACAO;
        this.dataEmissao = dataEmissao;
        this.trabalho = null;
        this.codigoValidacao = gerarCodigoValidacao();
    }

    //constructor para certificade de apresentação
    public Certificado(Participante participante, Evento evento, Trabalho trabalho, LocalDate dataEmissao) {
        if (participante == null) throw new IllegalArgumentException("Participante não pode ser nulo");
        if (evento == null) throw new IllegalArgumentException("Evento não pode ser nulo");
        if (trabalho == null) throw new IllegalArgumentException("Trabalho não pode ser nulo para certificado de apresentação");
        if (dataEmissao == null) throw new IllegalArgumentException("Data de emissão não pode ser nula");
        this.participante = participante;
        this.evento = evento;
        this.trabalho = trabalho;
        this.tipo = TipoCertificado.APRESENTACAO_TRABALHO;
        this.dataEmissao = dataEmissao;
        this.codigoValidacao = gerarCodigoValidacao();
    }

    private String gerarCodigoValidacao(){
        return UUID.randomUUID().toString();
    }

    public String getCodigoValidacao() {
        return codigoValidacao;
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
        return evento.getDataFim().isBefore(LocalDate.now())
                && participante.getInscricoes().stream()
                .anyMatch(inscricao -> inscricao.getEvento().equals(evento)
                    && inscricao.isAtiva());
    }

    @Override
    public String toString() {
        String res = "Certificado de " + (tipo == TipoCertificado.PARTICIPACAO ? "Participação" : "Apresentação de Trabalho")
                + "\nParticipante: " + participante.getNome()
                + "\nEvento: " + evento.getNome()
                + "\nData de Emissão: " + dataEmissao
                + "\nCódigo de Validação: " + codigoValidacao;
        if (tipo == TipoCertificado.APRESENTACAO_TRABALHO && trabalho != null) {
            res += "\nTrabalho: " + trabalho.getTitulo();
        }
        return res;
    }
}
