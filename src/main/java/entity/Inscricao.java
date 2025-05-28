package entity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Inscricao {
    private Participante participante;
    private Evento evento;
    private LocalDate dataInscricao;
    private String status;
    private boolean presencaConfirmada;

    public Inscricao(Participante participante, Evento evento) {
        this.participante = participante;
        this.evento = evento;
        this.dataInscricao = LocalDate.now();
        this.status = "ativa";
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

    public String getStatus() {
        return status;
    }

    public boolean isPresencaConfirmada() {
        return presencaConfirmada;
    }

        public boolean cancelar(){
        long diasRest = ChronoUnit.DAYS.between(LocalDate.now(), evento.getDataInicio());
        if(diasRest<2){
            return false; //cancelamento somente até 2 dias antes
        }
        this.status = "cancelada";
        return true;
    }

    public void confirmarPresenca(){
        if(status.equals("ativa")){
            this.presencaConfirmada = true;
            this.status = "confirmada";
        }
    }

    public boolean podeEmitirCertificadoParticipacao(){
        return presencaConfirmada && LocalDate.now().isAfter(evento.getDataFim());
    }

    public boolean isAtiva(){
        return status.equals("ativa");
    }
}
