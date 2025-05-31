package view;

import controller.SistemaSGEAController;
import entity.*;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Arrays;
import java.util.List;

public class Util {

    public static void Iniciar(SistemaSGEAController sistema) {
        List<Participante> participantes = adicionaParticipante(sistema);
        Participante p1 = participantes.get(0);
        Participante p2 = participantes.get(1);
        Participante p3 = participantes.get(2);
        Evento evento = adicionaEvento(sistema,p1);
        adicionaInscricao(sistema, p1, p2, p3, evento);
        confirmaPresenca(sistema, evento);
        Trabalho trabalho = submeterTrabalhos(sistema, evento, p2, p3);
        Avaliador avaliador = designarAvaliadorParatrabalho(sistema, evento, p1);
        avaliadorRegistraAvaliacao(sistema, avaliador, trabalho, evento);
        emitirCertificadoPresenca(sistema,evento,trabalho);


    }

    public static List<Participante> adicionaParticipante(SistemaSGEAController sistema){
        Participante p1 = sistema.cadastrarParticipante("Alice Silva", "alice@ifnmg.edu.br", "IFNMG");
        Participante p2 = sistema.cadastrarParticipante("Bruno Souza", "bruno@gmail.com", "UFV");
        Participante p3 = sistema.cadastrarParticipante("Carlos Lima", "carlos@externo.com", "Unimontes");

        return List.of(p1, p2, p3);
    }

    public static Evento adicionaEvento(SistemaSGEAController sistema, Participante p1){
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
                hoje.minusDays(2),
                hoje.minusDays(2),
                "Auditório IFNMG",
                10,
                hoje.plusDays(1),
                hoje.plusDays(5)
        );
        return evento;
    }

    public static void adicionaInscricao(SistemaSGEAController sistema, Participante p1, Participante p2, Participante p3, Evento evento){
        try {
            sistema.inscreverParticipanteEmEvento(p2, evento);
            sistema.inscreverParticipanteEmEvento(p3, evento);
            sistema.inscreverParticipanteEmEvento(p1, evento);
        } catch (Exception e) {
            System.out.println("Inscrição falhou: " + e.getMessage());
        }
    }

    public static void confirmaPresenca(SistemaSGEAController sistema, Evento evento){
        for (Inscricao inscricao : evento.getInscricoes()) {
            try {
                sistema.confirmarPresencaEmEvento(inscricao);
            } catch (Exception e) {
                System.out.println("Erro ao confirmar presença: " + e.getMessage());
            }
        }
    }

    public static Trabalho submeterTrabalhos(SistemaSGEAController sistema, Evento evento, Participante p2, Participante p3){
        List<Participante> autores = Arrays.asList(p2, p3);
        for (Participante autore : autores) {
        }
        Trabalho trabalho = sistema.submeterTrabalho(
                "Uso de GRASP em Projetos Java",
                autores,
                evento,
                "grasp_trabalho.pdf"
        );
        return trabalho;
    }
    
    public static Avaliador designarAvaliadorParatrabalho(SistemaSGEAController sistema, Evento evento, Participante p1){
        Avaliador avaliador = null;
        try {
            avaliador = sistema.designarAvaliadorParaEvento(evento, p1);
        } catch (Exception e) {
            System.out.println("Erro ao designar avaliador: " + e.getMessage());
        }
        return avaliador;
    }

    public static void avaliadorRegistraAvaliacao(SistemaSGEAController sistema, Avaliador avaliador, Trabalho trabalho, Evento evento){
        sistema.designarTrabalho(avaliador, trabalho);
        sistema.registrarAvaliacao(avaliador, trabalho, 8.5, "Ótimo uso dos princípios GRASP!");
    }

    public static void emitirCertificadoPresenca(SistemaSGEAController sistema, Evento evento, Trabalho trabalho){
        for (Inscricao inscricao : evento.getInscricoes()) {
            try {
                Certificado cert = sistema.emitirCertificadoParticipacao(inscricao.getParticipante(), evento, LocalDate.now());
            } catch (Exception e) {
                System.out.println("Erro ao emitir certificado: " + e.getMessage());
            }
        }
        try {
            List<Certificado> certsApresentacao = sistema.emitirCertificadoApresentacao(trabalho, LocalDate.now());
        } catch (Exception e) {
            System.out.println("Erro ao emitir certificado de apresentação: " + e.getMessage());
        }
    }
}
