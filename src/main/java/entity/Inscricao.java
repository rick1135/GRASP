package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Inscricao {
    public enum StatusInscricao{
        ATIVA,
        CANCELADA,
        CONFIRMADA
    }

    private final Participante participante;
    private final Evento evento;
    private final LocalDate dataInscricao;
    private StatusInscricao status;
    private boolean presencaConfirmada;

    public Inscricao(Participante participante, Evento evento) {
        this.participante = participante;
        this.evento = evento;
        this.dataInscricao = LocalDate.now();
        this.status = StatusInscricao.ATIVA;
        this.presencaConfirmada = false;
    }

    public Participante getParticipante() {
        return participante;
    }

    public Evento getEvento() {
        return evento;
    }

    public LocalDate getDataInscricao() {
        return dataInscricao;
    }

    public StatusInscricao getStatus() {
        return status;
    }

    public boolean isPresencaConfirmada() {
        return presencaConfirmada;
    }

    public boolean cancelar(){
        LocalDate limiteCancelamento = evento.getDataInicio().minusDays(evento.getPrazoCancelamentoDias());
        if(!LocalDate.now().isBefore(limiteCancelamento))
            return false;
        this.status = StatusInscricao.CANCELADA;
        return true;
    }

    public void confirmarPresenca(){
        if(status==StatusInscricao.ATIVA){
            this.presencaConfirmada = true;
            this.status = StatusInscricao.CONFIRMADA;
        }
    }

    public boolean podeEmitirCertificadoParticipacao(){
        return presencaConfirmada && LocalDate.now().isAfter(evento.getDataFim());
    }

    public boolean isAtiva(){
        return status==StatusInscricao.ATIVA;
    }
}
