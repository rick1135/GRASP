package view;

import controller.SistemaSGEAController;
import entity.*;
import repository.EventoRepository;

import java.time.LocalDate;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        EventoRepository eventoRepository = new EventoRepository();
        SistemaSGEAController sistema = new SistemaSGEAController(eventoRepository);


        // UC1: Cadastro de participante
        System.out.println("UC1 - Cadastro de Participante");
        Participante p1 = sistema.cadastrarParticipante("Alice Silva", "alice@ifnmg.edu.br", "IFNMG");
        Participante p2 = sistema.cadastrarParticipante("Bruno Souza", "bruno@gmail.com", "UFV");
        Participante p3 = sistema.cadastrarParticipante("Carlos Lima", "carlos@externo.com", "Unimontes");
        System.out.println("Participantes cadastrados: " + p1.getNome() + ", " + p2.getNome() + ", " + p3.getNome());

        // UC4: Organizador cadastra evento
        System.out.println("\nUC4 - Organizador cadastra evento");
        Organizador org = null;
        try {
            org = sistema.cadastrarOrganizador(p1);
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar organizador: " + e.getMessage());
        }
        LocalDate hoje = LocalDate.now();
        Evento evento = sistema.criarEvento(
                org,
                "Semana Acadêmica IFNMG",
                "Evento anual com palestras e minicursos",
                hoje.plusDays(10),
                hoje.plusDays(13),
                "Auditório IFNMG",
                10,
                hoje.plusDays(1),
                hoje.plusDays(5)
        );
        System.out.println("Evento criado: " + evento.getNome());

        // UC2: Participante visualiza eventos e se inscreve
        System.out.println("\nUC2 - Participante visualiza eventos e se inscreve");
        List<Evento> eventos = sistema.listarEventos();
        eventos.forEach(ev -> System.out.println("Evento disponível: " + ev.getNome()));
        try {
            sistema.inscreverParticipanteEmEvento(p2, evento);
            sistema.inscreverParticipanteEmEvento(p3, evento);
            // Tentativa de inscrição além da capacidade
            sistema.inscreverParticipanteEmEvento(p1, evento);
        } catch (Exception e) {
            System.out.println("Inscrição falhou: " + e.getMessage());
        }

        // UC5: Organizador visualiza inscrições e confirma presença
        System.out.println("\nUC5 - Organizador visualiza inscrições e confirma presença");
        for (Inscricao inscricao : evento.getInscricoes()) {
            System.out.println("Inscrito: " + inscricao.getParticipante().getNome());
            try {
                sistema.confirmarPresencaEmEvento(inscricao);
                System.out.println("Presença confirmada para: " + inscricao.getParticipante().getNome());
            } catch (Exception e) {
                System.out.println("Erro ao confirmar presença: " + e.getMessage());
            }
        }

        // UC3: Participante submete trabalho
        System.out.println("\nUC3 - Participante submete trabalho");
        List<Participante> autores = Arrays.asList(p2, p3);
        for (Participante autore : autores) {
            System.out.println("Autor: " + autore.getNome());
        }
        Trabalho trabalho = sistema.submeterTrabalho(
                "Uso de GRASP em Projetos Java",
                autores,
                evento,
                "grasp_trabalho.pdf"
        );
        if (trabalho != null) {
            System.out.println("Trabalho submetido: " + trabalho.getTitulo());
        } else {
            System.out.println("Falha ao submeter trabalho.");
        }

        // UC6: Organizador designa avaliador para trabalho
        System.out.println("\nUC6 - Organizador designa avaliador para trabalho");
        Avaliador avaliador = null;
        try {
            avaliador = sistema.designarAvaliadorParaEvento(evento, p1);
            System.out.println("Avaliador designado: " + avaliador.getNome());
        } catch (Exception e) {
            System.out.println("Erro ao designar avaliador: " + e.getMessage());
        }

        // UC7: Avaliador registra avaliação
        System.out.println("\nUC7 - Avaliador registra avaliação");
        sistema.designarTrabalho(avaliador, trabalho);
        sistema.registrarAvaliacao(avaliador, trabalho, 8.5, "Ótimo uso dos princípios GRASP!");

        System.out.println("Avaliação registrada para trabalho: " + trabalho.getTitulo());

        // Simula fim do evento para permitir emissão de certificados
        evento.setDataFim(hoje.minusDays(1));

        // UC8: Emissão de certificados
        System.out.println("\nUC8 - Emissão de certificados");
        for (Inscricao inscricao : evento.getInscricoes()) {
            try {
                Certificado cert = sistema.emitirCertificadoParticipacao(inscricao.getParticipante(), evento, LocalDate.now());
                System.out.println("Certificado emitido: " + cert);
            } catch (Exception e) {
                System.out.println("Erro ao emitir certificado: " + e.getMessage());
            }
        }
        try {
            List<Certificado> certsApresentacao = sistema.emitirCertificadoApresentacao(trabalho, LocalDate.now());
            certsApresentacao.forEach(cert -> System.out.println("Certificado de apresentação emitido: " + cert));
        } catch (Exception e) {
            System.out.println("Erro ao emitir certificado de apresentação: " + e.getMessage());
        }

        // UC5: Cancelamento de inscrição (fora do prazo)
        System.out.println("\nUC5 - Cancelamento de inscrição (fora do prazo)");
        try {
            sistema.cancelarInscricaoEmEvento(evento.getInscricoes().get(0));
        } catch (Exception e) {
            System.out.println("Cancelamento falhou: " + e.getMessage());
        }

        System.out.println("\nFluxo completo de casos de uso executado.");
    }
}