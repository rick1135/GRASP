package entity;

import java.time.LocalDate;
import java.util.*;

public class Evento {
    private String nome;
    private String descricao;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String local;
    private int capacidadeMaxima;
    private LocalDate dataInicioSubmissao;
    private LocalDate dataFimSubmissao;
    private List<Inscricao> inscricoes;
    private List<Trabalho> trabalhos;
    private List<Avaliador> avaliadores;
    private Organizador organizador;
    private final int PRAZO_CANCELAMENTO_DIAS = 2;

    public Evento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local, int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao, Organizador organizador) {
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.local = local;
        this.capacidadeMaxima = capacidadeMaxima;
        this.dataInicioSubmissao = dataInicioSubmissao;
        this.dataFimSubmissao = dataFimSubmissao;
        this.inscricoes = new ArrayList<>();
        this.trabalhos = new ArrayList<>();
        this.avaliadores = new ArrayList<>();
        this.organizador = organizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public int getCapacidadeMaxima() {
        return capacidadeMaxima;
    }

    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }

    public void setDataInicioSubmissao(LocalDate dataInicioSubmissao) {
        this.dataInicioSubmissao = dataInicioSubmissao;
    }

    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }

    public void setDataFimSubmissao(LocalDate dataFimSubmissao) {
        this.dataFimSubmissao = dataFimSubmissao;
    }

    public List<Inscricao> getInscricoes() {
        return Collections.unmodifiableList(inscricoes);
    }



    public List<Trabalho> getTrabalhos() {
        if(trabalhos.isEmpty())
            throw new NoSuchElementException("Não há trabalhos disponíveis para esse evento!");
        return Collections.unmodifiableList(trabalhos);
    }

    public List<Avaliador> getAvaliadores() {
        return avaliadores;
    }

    public void setAvaliadores(List<Avaliador> avaliadores) {
        this.avaliadores = avaliadores;
    }

    public int getPrazoCancelamentoDias() {return PRAZO_CANCELAMENTO_DIAS;}

    public boolean temVaga(){
        return inscricoes.size()<capacidadeMaxima;
    }

    public int vagasRestantes(){
        return capacidadeMaxima-inscricoes.size();
    }

    public boolean adicionarInscricao(Inscricao inscricao){
        if(isEventoIniciado()) throw new IllegalStateException("Evento iniciado!");
        if(!podeInscrever(inscricao)) throw new IllegalStateException("Não foi possivel inscrever!");
        inscricoes.add(inscricao);
        return true;
    }

    public boolean removerInscricao(Inscricao inscricao){
        return inscricoes.remove(inscricao);
    }

    public boolean podeCancelarInscricao(LocalDate dataAtual){
        LocalDate limiteCancelamento = dataInicio.minusDays(PRAZO_CANCELAMENTO_DIAS);
        return dataAtual.isBefore(limiteCancelamento) || dataAtual.isEqual(limiteCancelamento);
    }

    public boolean adicionarTrabalho(Trabalho trabalho){
        if(trabalhos.contains(trabalho)) return false;
        return trabalhos.add(trabalho);
    }

    public boolean estaNoPeriodoSubmissao(LocalDate dataAtual){
        return (dataAtual.isEqual(dataInicioSubmissao) || dataAtual.isAfter(dataInicioSubmissao))
                && (dataAtual.isEqual(dataFimSubmissao) || dataAtual.isBefore(dataFimSubmissao));
    }

    public boolean estaInscrito(Participante participante){
        return inscricoes.stream().anyMatch(inscricao -> inscricao.getParticipante().equals(participante) && inscricao.isAtiva());
    }

    public boolean podeInscrever(Inscricao inscricao){
        return !inscricoes.contains(inscricao) && temVaga();
    }

    public boolean isEventoIniciado() {
        return LocalDate.now().isAfter(dataInicio) || LocalDate.now().isEqual(dataInicio);
    }

    public boolean isCapacidadeDisponivel() {
        return this.getInscricoes().size() < this.getCapacidadeMaxima();
    }

    public void adicionarAvaliador(Avaliador avaliador){
        avaliadores.add(avaliador);
    }

    public Organizador getOrganizador() {
        return organizador;
    }

    public void setOrganizador(Organizador organizador) {
        this.organizador = organizador;
    }
}