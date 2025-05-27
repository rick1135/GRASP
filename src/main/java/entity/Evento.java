package entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private static final int PRAZO_CANCELAMENTO_DIAS = 2;

    public Evento(String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local, int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao) {
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

    public LocalDate getDataInicioSubmissao() {
        return dataInicioSubmissao;
    }

    public LocalDate getDataFimSubmissao() {
        return dataFimSubmissao;
    }

    public List<Inscricao> getInscricoes() {
        return inscricoes;
    }

    public List<Trabalho> getTrabalhos() {
        return trabalhos;
    }

    public boolean temVaga(){
        return inscricoes.size()<capacidadeMaxima;
    }

    public boolean adicionarInscricao(Inscricao inscricao){
        if(!temVaga()) return false; //evento com capacidade maxima

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
        return trabalhos.add(trabalho);
    }

    public boolean estaNoPeriodoSubmissao(LocalDate dataAtual){
        return (dataAtual.isEqual(dataInicioSubmissao) || dataAtual.isAfter(dataInicioSubmissao))
                && (dataAtual.isEqual(dataFimSubmissao) || dataAtual.isBefore(dataFimSubmissao));
    }
}
