package view;

import controller.SistemaSGEAController;
import entity.Participante;
import repository.EventoRepository;
import repository.ParticipanteRepository;

import java.util.Scanner;

public class View {
    public static void main(String[] args) {
        //criação do 'BD'
        EventoRepository eventoRepository = new EventoRepository();
        ParticipanteRepository participanteRepository = new ParticipanteRepository();
        //chamada do controller principal do sistema
        SistemaSGEAController sistema = new SistemaSGEAController(eventoRepository, participanteRepository);

        //adiciona uma base de dados ao sistema
        Util.Iniciar(sistema);

        Scanner scanner = new Scanner(System.in);
        boolean executando = true;

        while (executando) {
            System.out.println("=== SGEA - Sistema de Gerenciamento de Eventos Acadêmicos ===");
            System.out.println("1. Criar conta de participante");
            System.out.println("2. Entrar com conta existente");
            System.out.println("3. Sair");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n--- Cadastro de Participante ---");
                    System.out.print("Nome completo: ");
                    String nome = scanner.nextLine();
                    System.out.print("E-mail: ");
                    String email = scanner.nextLine();
                    System.out.print("Instituição: ");
                    String instituicao = scanner.nextLine();

                    try {
                        Participante participante = sistema.cadastrarParticipante(nome, email, instituicao);
                        System.out.println("Conta criada com sucesso! Bem-vindo(a), " + participante.getNome() + "!\n");
                        menuParticipante(sistema, scanner, participante);

                    } catch (Exception e) {
                        System.out.println("Erro ao criar conta: " + e.getMessage() + "\n");
                    }
                    break;
                case "2":
                    System.out.print("\nDigite o e-mail cadastrado: ");
                    String emailLogin = scanner.nextLine();
                    var participanteOpt = sistema.buscarParticipantePorEmail(emailLogin);
                    if (participanteOpt.isPresent()) {
                        Participante participante = participanteOpt.get();
                        System.out.println("Bem-vindo(a) de volta, " + participante.getNome() + "!\n");
                        menuParticipante(sistema, scanner, participante);
                    } else {
                        System.out.println("Participante não encontrado. Verifique o e-mail digitado.\n");
                    }
                    break;
                case "3":
                    System.out.println("Saindo do sistema. Até logo!");
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.\n");
            }
        }
        scanner.close();
    }


    private static void menuParticipante(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== Menu do Participante ===");
            System.out.println("1. Listar eventos disponíveis para inscrição");
            System.out.println("2. Listar eventos que estou inscrito");
            System.out.println("3. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n--- Eventos Disponíveis ---");
                    var eventos = sistema.listarEventos();
                    boolean encontrou = false;
                    for (var evento : eventos) {
                        System.out.println("Evento: " + evento.getNome());
                        System.out.println("Descrição: " + evento.getDescricao());
                        System.out.println("Data: " + evento.getDataInicio() + " a " + evento.getDataFim());
                        System.out.println("Local: " + evento.getLocal());
                        System.out.println("Capacidade: " + evento.getCapacidadeMaxima());
                        System.out.println("-----------------------------");
                        encontrou = true;
                    }
                    if (!encontrou) {
                        System.out.println("Nenhum evento disponível para inscrição no momento.");
                    } else {
                        System.out.print("Digite o nome do evento para se inscrever (ou deixe em branco para cancelar): ");
                        String nomeEvento = scanner.nextLine();
                        if (!nomeEvento.isBlank()) {
                            var eventoOpt = eventos.stream()
                                    .filter(e -> e.getNome().equalsIgnoreCase(nomeEvento))
                                    .findFirst();
                            if (eventoOpt.isPresent()) {
                                try {
                                    sistema.inscreverParticipanteEmEvento(participante, eventoOpt.get());
                                    System.out.println("Inscrição realizada com sucesso!");
                                } catch (Exception e) {
                                    System.out.println("Erro ao inscrever: " + e.getMessage());
                                }
                            } else {
                                System.out.println("Evento não encontrado.");
                            }
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n--- Eventos que você está inscrito ---");
                    var inscricoes = sistema.listarInscricoes(participante.getEmail());
                    if (inscricoes.isEmpty()) {
                        System.out.println("Você não está inscrito em nenhum evento.");
                    } else {
                        inscricoes.forEach(inscricao -> {
                            var evento = inscricao.getEvento();
                            System.out.println("Evento: " + evento.getNome());
                            System.out.println("Descrição: " + evento.getDescricao());
                            System.out.println("Data: " + evento.getDataInicio() + " a " + evento.getDataFim());
                            System.out.println("Local: " + evento.getLocal());
                            System.out.println("Status da inscrição: " + inscricao.getStatus());
                            System.out.println("Organizador: " + evento.getOrganizador().getNome());
                            System.out.print("Avaliadores: ");
                            if (evento.getAvaliadores().isEmpty()) {
                                System.out.print("Nenhum avaliador designado.");
                            } else {
                                String nomesAvaliadores = evento.getAvaliadores().stream()
                                        .map(av -> av.getNome())
                                        .reduce((a, b) -> a + ", " + b)
                                        .orElse("");
                                System.out.print(nomesAvaliadores);
                            }
                            System.out.println("\n-----------------------------");
                        });
                    }
                    break;
                case "3":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
