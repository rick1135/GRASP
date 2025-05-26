package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Evento {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalDate dataSubmissaoInicio; //todo
    private LocalDate dataSubmissaoFim; //todo
    private String local;
    private int capacidadeMaxima;
    private static final int prazoCancelamento = 2; //dias antes do evento para cancelamento permitido
    private List<Certificado> certificados;
    private List<Inscricao> inscricoes;
    private List<Trabalho> trabalhosSubmetidos;
    private boolean iniciado; //indica se o evento já começou


    public Evento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local, int capacidadeMaxima, int prazoCancelamento) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.prazoCancelamento = prazoCancelamento;
        this.inscricoes = new ArrayList<>(); //todo
        this.trabalhosSubmetidos = new ArrayList<>();
        this.iniciado = false;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public String getLocal() {
        return local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public int getPrazoCancelamento() {
        return prazoCancelamento;
    }

    public List<Inscricao> getInscritos() {
        return inscricoes;
    }

    public List<Trabalho> getTrabalhosSubmetidos() {
        return trabalhosSubmetidos;
    }

    public void iniciarEvento(){
        this.iniciado=true;
    }

    public boolean isIniciado(){
        return iniciado;
    }

    public boolean isInscrito(Participante participante){
        return inscricoes.contains(participante);
    }

    public boolean inscreverParticipante(Participante p, LocalDate dataAtual){
        if(iniciado) return false; //proibido inscrição após inicio
        if(estaCheio()) return false;
        if(isInscrito(p)) return false;

        inscricoes.add(p);
        return true;
    }

//    public boolean cancelarInscricao(Participante participante, LocalDate dataAtual){
//        if(iniciado) return false; //não pode cancelar após inicio
//        if(!isInscrito(participante)) return false;
//        LocalDate prazoCancelamentoData = dataInicio.minusDays(prazoCancelamento);
//        if(dataAtual.isAfter(prazoCancelamentoData)) return false; //fora do prazo de cancelamento
//
//        inscricoes.remove(participante);
//        return true;
//    } todo

    public boolean submeterTrabalho(Trabalho trabalho, Participante participante, LocalDate dataAtual, LocalDate inicioSubmissao, LocalDate fimSubmissao) {
        if (!isInscrito(participante)) {
            return false; //participante deve estar inscrito
        }
        if (dataAtual.isBefore(inicioSubmissao) || dataAtual.isAfter(fimSubmissao)) {
            return false; //fora do período de submissão
        }

        trabalhosSubmetidos.add(trabalho);
        return true;
    }

    public int quantidadeInscritos(){
        return inscricoes.size();
    }

    public boolean estaCheio(){
        return inscricoes.size()>=capacidadeMaxima;
    }
}
