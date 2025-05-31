package controller;

import entity.*;
import repository.EventoRepository;
import repository.ParticipanteRepository;
import service.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class SistemaSGEAController {
    private final ParticipanteService pService;
    private final InscricaoService iService;
    private final EventoService eService;
    private final TrabalhoService tService;
    private final OrganizadorService oService;
    private final AvaliadorService aService;
    private final CertificadoService cService;

    public SistemaSGEAController(EventoRepository eventoRepository, ParticipanteRepository participanteRepository) {
        this.pService = new ParticipanteService(participanteRepository);
        this.iService = new InscricaoService();
        this.eService = new EventoService(eventoRepository);
        this.tService = new TrabalhoService();
        this.oService = new OrganizadorService(eventoRepository);
        this.aService = new AvaliadorService();
        this.cService = new CertificadoService(tService, pService);
    }

    public Participante cadastrarParticipante(String nome, String email, String instituicao) {
        return pService.cadastrarParticipante(nome, email, instituicao);
    }

    public List<Evento> listarEventos() {
        return eService.listarEventos();
    }

    public void inscreverParticipanteEmEvento(Participante participante, Evento evento) throws Exception {
        iService.criarInscricao(participante, evento);
    }

    //CORRIGIR
    public void cancelarInscricaoEmEvento(Inscricao inscricao) throws Exception{
        iService.cancelarInscricao(inscricao);
    }

    public void confirmarPresencaEmEvento(Inscricao inscricao) throws Exception {
        iService.confirmarPresenca(inscricao);
    }

    public Trabalho submeterTrabalho(String titulo, List<Participante> autores, Evento evento, String arquivo) {
        return tService.cadastrarTrabalho(titulo, autores, evento, arquivo);
    }

    public Organizador cadastrarOrganizador(Participante participante) throws Exception {
        return oService.cadastrarOrganizador(participante);
    }

    //VERIFICAR COMO CRIAR EVENTO A LOGICA DOS PARAMETROS VAI TA ERRADA
    public Evento criarEvento(Organizador organizador, String nome, String descricao, LocalDate dataInicio, LocalDate dataFim, String local,
                              int capacidadeMaxima, LocalDate dataInicioSubmissao, LocalDate dataFimSubmissao){
        return oService.criarEvento(organizador, nome, descricao, dataInicio, dataFim, local, capacidadeMaxima, dataInicioSubmissao, dataFimSubmissao);
    }

    public List<Evento> listarEventosCriados(Organizador organizador){
        return oService.listarEventosCriados(organizador);
    }

    public void editarEvento(String nomeEvento, String novoNome, String novaDescricao, LocalDate novaDataInicio, LocalDate novaDataFim, String novoLocal) throws Exception {
        eService.editarEvento(nomeEvento,novoNome,novaDescricao,novaDataInicio,novaDataFim,novoLocal);
    }

    //VERIFICAR ISSO AQUI
    public Avaliador designarAvaliadorParaEvento(Evento evento, Participante participante) throws Exception {
        Avaliador avaliador = aService.cadastrarAvaliador(participante);
        eService.designarAvaliador(evento, avaliador);
        return avaliador;
    }

    //VERIFICAR SE O FLUXO ESTA CORRETO
    public void registrarAvaliacao(Avaliador avaliador, Trabalho trabalho, double nota, String comentario){
        aService.registrarAvaliacao(avaliador, trabalho, nota, comentario);
    }

    public boolean designarTrabalho(Avaliador avaliador, Trabalho trabalho){
        return aService.designarTrabalho(avaliador, trabalho);
    }

    //NAO ESTA SENDO VERIFICADA A PRESENÇA DO PARTICIPANTE
    public Certificado emitirCertificadoParticipacao(Participante participante, Evento evento, LocalDate dataEmissao){
        return cService.emitirCertificadoParticipacao(participante, evento, dataEmissao);
    }

    public List<Certificado> emitirCertificadoApresentacao(Trabalho trabalho, LocalDate dataEmissao){
        return cService.emitirCertificadoApresentacao(trabalho, dataEmissao);
    }

    public Optional<Participante> buscarParticipantePorEmail(String email){
        return pService.buscarParticipantePorEmail(email);
    }

    public List<Inscricao> listarInscricoes(String emailParticipante){
        return pService.listarInscricoes(emailParticipante);
    }
}
