package view;

import controller.SistemaSGEAController;
import entity.Evento;
import entity.Organizador;
import entity.Participante;
import repository.EventoRepository;
import repository.ParticipanteRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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

    private static void menuOrganizador(SistemaSGEAController sistema, Scanner scanner, entity.Organizador organizador) {
        boolean executando = true;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        while (executando) {
            System.out.println("\n=== Menu do Organizador ===");
            System.out.println("1. Criar novo evento");
            System.out.println("2. Ver eventos criados");
            System.out.println("3. Editar evento");
            System.out.println("4. Confirmar presença dos inscritos");
            System.out.println("5. Designar inscrito como avaliador para trabalho");
            System.out.println("6. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n--- Cadastro de Evento ---");
                    System.out.print("Nome do evento: ");
                    String nome = scanner.nextLine();
                    System.out.print("Descrição: ");
                    String descricao = scanner.nextLine();
                    System.out.print("Data de início (dd/MM/yyyy): ");
                    String dataInicioStr = scanner.nextLine();
                    System.out.print("Data de fim (dd/MM/yyyy): ");
                    String dataFimStr = scanner.nextLine();
                    System.out.print("Local: ");
                    String local = scanner.nextLine();
                    System.out.print("Capacidade máxima: ");
                    int capacidade = Integer.parseInt(scanner.nextLine());
                    System.out.print("Data início submissão (dd/MM/yyyy): ");
                    String dataInicioSub = scanner.nextLine();
                    System.out.print("Data fim submissão (dd/MM/yyyy): ");
                    String dataFimSub = scanner.nextLine();

                    try {
                        LocalDate dataInicio = LocalDate.parse(dataInicioStr, formatter);
                        LocalDate dataFim = LocalDate.parse(dataFimStr, formatter);
                        LocalDate dataInicioSubmissao = LocalDate.parse(dataInicioSub, formatter);
                        LocalDate dataFimSubmissao = LocalDate.parse(dataFimSub, formatter);

                        sistema.criarEvento(
                                organizador,
                                nome,
                                descricao,
                                dataInicio,
                                dataFim,
                                local,
                                capacidade,
                                dataInicioSubmissao,
                                dataFimSubmissao
                        );
                        System.out.println("Evento criado com sucesso!");
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido! Use dd/MM/yyyy.");
                    } catch (Exception e) {
                        System.out.println("Erro ao criar evento: " + e.getMessage());
                    }
                    break;
                case "2":
                    System.out.println("\n--- Eventos Criados ---");
                    var eventosCriados = sistema.listarEventosCriados(organizador);
                    if (eventosCriados.isEmpty()) {
                        System.out.println("Nenhum evento criado ainda.");
                    } else {
                        eventosCriados.forEach(ev -> {
                            System.out.println("Evento: " + ev.getNome());
                            System.out.println("Descrição: " + ev.getDescricao());
                            System.out.println("Data: " + ev.getDataInicio().format(formatter) + " a " + ev.getDataFim().format(formatter));
                            System.out.println("Local: " + ev.getLocal());
                            System.out.println("Capacidade: " + ev.getCapacidadeMaxima());
                            System.out.println("-----------------------------");
                        });
                    }
                    break;
                case "3":
                    System.out.print("Digite o nome do evento que deseja editar: ");
                    String nomeEvento = scanner.nextLine();
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Nova descrição: ");
                    String novaDescricao = scanner.nextLine();
                    System.out.print("Nova data de início (dd/MM/yyyy): ");
                    String novaDataInicio = scanner.nextLine();
                    System.out.print("Nova data de fim (dd/MM/yyyy): ");
                    String novaDataFim = scanner.nextLine();
                    System.out.print("Novo local: ");
                    String novoLocal = scanner.nextLine();
                    System.out.print("Nova data de início de submissão de trabalho (dd/MM/yyyy): ");
                    String novoInicioSubmissao = scanner.nextLine();
                    System.out.print("Nova data de fim de submissão de trabalho (dd/MM/yyyy): ");
                    String novoFimSubmissao = scanner.nextLine();
                    try {
                        LocalDate dataInicio = LocalDate.parse(novaDataInicio, formatter);
                        LocalDate dataFim = LocalDate.parse(novaDataFim, formatter);
                        LocalDate InicioSubmissao = LocalDate.parse(novoInicioSubmissao, formatter);
                        LocalDate FimSubmissao = LocalDate.parse(novoFimSubmissao, formatter);
                        sistema.editarEvento(
                                nomeEvento,
                                novoNome,
                                novaDescricao,
                                dataInicio,
                                dataFim,
                                novoLocal,
                                InicioSubmissao,
                                FimSubmissao
                        );
                        System.out.println("Evento editado com sucesso!");
                    } catch (DateTimeParseException e) {
                        System.out.println("Formato de data inválido! Use dd/MM/yyyy.");
                    } catch (Exception e) {
                        System.out.println("Erro ao editar evento: " + e.getMessage());
                    }
                    break;
                case "4":
                    var eventosCriadosPresenca = sistema.listarEventosCriados(organizador);
                    if (eventosCriadosPresenca.isEmpty()) {
                        System.out.println("Nenhum evento criado ainda.");
                        break;
                    }
                    for (int i = 0; i < eventosCriadosPresenca.size(); i++) {
                        Evento evento = eventosCriadosPresenca.get(i);
                        System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicio().format(formatter), evento.getDataFim().format(formatter));
                    }
                    System.out.print("Escolha o número do evento para confirmar presença dos inscritos (ou 0 para voltar): ");
                    String escolhaEvento = scanner.nextLine();
                    int idxEvento;
                    try {
                        idxEvento = Integer.parseInt(escolhaEvento);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxEvento <= 0 || idxEvento > eventosCriadosPresenca.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    Evento eventoEscolhido = eventosCriadosPresenca.get(idxEvento - 1);
                    var inscricoes = eventoEscolhido.getInscricoes();
                    if (inscricoes.isEmpty()) {
                        System.out.println("Nenhum inscrito neste evento.");
                        break;
                    }
                    for (int i = 0; i < inscricoes.size(); i++) {
                        var inscricao = inscricoes.get(i);
                        System.out.printf("%d. %s - Presença confirmada: %s\n", i + 1, inscricao.getParticipante().getNome(), inscricao.isPresencaConfirmada() ? "Sim" : "Não");
                    }
                    System.out.print("Digite o número do inscrito para confirmar presença (ou 0 para voltar): ");
                    String escolhaInscrito = scanner.nextLine();
                    int idxInscrito;
                    try {
                        idxInscrito = Integer.parseInt(escolhaInscrito);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxInscrito <= 0 || idxInscrito > inscricoes.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    var inscricaoEscolhida = inscricoes.get(idxInscrito - 1);
                    try {
                        sistema.confirmarPresencaEmEvento(inscricaoEscolhida);
                        System.out.println("Presença confirmada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao confirmar presença: " + e.getMessage());
                    }
                    break;
                case "5":
                    var eventosCriadosAvaliador = sistema.listarEventosCriados(organizador);
                    if (eventosCriadosAvaliador.isEmpty()) {
                        System.out.println("Nenhum evento criado ainda.");
                        break;
                    }
                    for (int i = 0; i < eventosCriadosAvaliador.size(); i++) {
                        Evento evento = eventosCriadosAvaliador.get(i);
                        System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicio().format(formatter), evento.getDataFim().format(formatter));
                    }
                    System.out.print("Escolha o número do evento (ou 0 para voltar): ");
                    String escolhaEventoAvaliador = scanner.nextLine();
                    int idxEventoAvaliador;
                    try {
                        idxEventoAvaliador = Integer.parseInt(escolhaEventoAvaliador);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxEventoAvaliador <= 0 || idxEventoAvaliador > eventosCriadosAvaliador.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    Evento eventoEscolhidoAvaliador = eventosCriadosAvaliador.get(idxEventoAvaliador - 1);

                    // Seleciona o trabalho
                    List<entity.Trabalho> trabalhosEvento;
                    try {
                        trabalhosEvento = sistema.listarTrabalhoPorEvento(eventoEscolhidoAvaliador);
                    } catch (Exception e) {
                        System.out.println("Não foi possível listar trabalhos: " + e.getMessage());
                        break;
                    }
                    if (trabalhosEvento == null || trabalhosEvento.isEmpty()) {
                        System.out.println("Nenhum trabalho submetido para este evento.");
                        break;
                    }
                    for (int i = 0; i < trabalhosEvento.size(); i++) {
                        entity.Trabalho trabalho = trabalhosEvento.get(i);
                        System.out.printf("%d. %s\n", i + 1, trabalho.getTitulo());
                    }
                    System.out.print("Escolha o número do trabalho para designar avaliador (ou 0 para voltar): ");
                    String escolhaTrabalho = scanner.nextLine();
                    int idxTrabalho;
                    try {
                        idxTrabalho = Integer.parseInt(escolhaTrabalho);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxTrabalho <= 0 || idxTrabalho > trabalhosEvento.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    entity.Trabalho trabalhoEscolhido = trabalhosEvento.get(idxTrabalho - 1);

                    // Seleciona o inscrito para ser avaliador
                    var inscricoesAvaliador = eventoEscolhidoAvaliador.getInscricoes();
                    if (inscricoesAvaliador.isEmpty()) {
                        System.out.println("Nenhum inscrito neste evento.");
                        break;
                    }
                    for (int i = 0; i < inscricoesAvaliador.size(); i++) {
                        var inscricao = inscricoesAvaliador.get(i);
                        System.out.printf("%d. %s (%s)\n", i + 1, inscricao.getParticipante().getNome(), inscricao.getParticipante().getEmail());
                    }
                    System.out.print("Digite o número do inscrito para designar como avaliador (ou 0 para voltar): ");
                    String escolhaInscritoAvaliador = scanner.nextLine();
                    int idxInscritoAvaliador;
                    try {
                        idxInscritoAvaliador = Integer.parseInt(escolhaInscritoAvaliador);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxInscritoAvaliador <= 0 || idxInscritoAvaliador > inscricoesAvaliador.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    var inscricaoEscolhidaAvaliador = inscricoesAvaliador.get(idxInscritoAvaliador - 1);
                    var participanteAvaliador = inscricaoEscolhidaAvaliador.getParticipante();
                    try {
                        // Verifica se já é avaliador
                        var optAvaliador = sistema.buscarAvaliadorPorEmail(participanteAvaliador.getEmail());
                        entity.Avaliador avaliador;
                        if (optAvaliador.isPresent()) {
                            avaliador = optAvaliador.get();
                        } else {
                            avaliador = sistema.designarAvaliadorParaEvento(eventoEscolhidoAvaliador, participanteAvaliador);
                            System.out.println("Avaliador criado e designado ao evento.");
                        }
                        boolean designado = sistema.designarTrabalho(avaliador, trabalhoEscolhido);
                        if (designado) {
                            System.out.println("Avaliador designado para o trabalho com sucesso!");
                        } else {
                            System.out.println("Avaliador já estava designado para esse trabalho.");
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao designar avaliador: " + e.getMessage());
                    }
                    break;
                case "6":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuParticipante(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        boolean executando = true;

        while (executando) {
            System.out.println("\n=== Menu do Participante ===");
            System.out.println("1. Listar eventos disponíveis para inscrição");
            System.out.println("2. Listar eventos que estou inscrito");
            System.out.println("3. Cancelar inscrição em evento");
            System.out.println("4. Trabalhos (submeter/ver)");
            System.out.println("5. Gerenciar eventos como organizador");
            System.out.println("6. Funções de avaliador");
            System.out.println("7. Certificados");
            System.out.println("8. Voltar ao menu principal");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    System.out.println("\n--- Eventos Disponíveis ---");
                    var eventos = sistema.listarEventos();
                    var inscricoes = sistema.listarInscricoes(participante.getEmail());
                    var optOrg = sistema.buscarOrganizadorPorEmail(participante.getEmail());
                    List<Evento> eventosCriados = new ArrayList<>();
                    if (optOrg.isPresent()) {
                        eventosCriados = sistema.listarEventosCriados(optOrg.get());
                    }
                    boolean encontrou = false;
                    for (var evento : eventos) {
                        boolean jaInscrito = inscricoes.stream()
                                .anyMatch(inscricao -> inscricao.getEvento().equals(evento));
                        boolean criadoPorMim = eventosCriados.stream()
                                .anyMatch(ev -> ev.equals(evento));
                        if (!jaInscrito && !criadoPorMim) {
                            System.out.println("Evento: " + evento.getNome());
                            System.out.println("Descrição: " + evento.getDescricao());
                            System.out.println("Data: " + evento.getDataInicio() + " a " + evento.getDataFim());
                            System.out.println("Local: " + evento.getLocal());
                            System.out.println("Capacidade: " + evento.getCapacidadeMaxima());
                            System.out.println("Vagas Restantes: " + evento.vagasRestantes());
                            System.out.println("-----------------------------");
                            encontrou = true;
                        }
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
                                boolean jaInscrito = inscricoes.stream()
                                        .anyMatch(inscricao -> inscricao.getEvento().equals(eventoOpt.get()));
                                boolean criadoPorMim = eventosCriados.stream()
                                        .anyMatch(ev -> ev.equals(eventoOpt.get()));
                                if (!jaInscrito && !criadoPorMim) {
                                    try {
                                        sistema.inscreverParticipanteEmEvento(participante, eventoOpt.get());
                                        System.out.println("Inscrição realizada com sucesso!");
                                    } catch (Exception e) {
                                        System.out.println("Erro ao inscrever: " + e.getMessage());
                                    }
                                } else {
                                    System.out.println("Você já está inscrito ou é o organizador deste evento.");
                                }
                            } else {
                                System.out.println("Evento não encontrado.");
                            }
                        }
                    }
                    break;
                case "2":
                    System.out.println("\n--- Eventos que você está inscrito ---");
                    var inscricoes2 = sistema.listarInscricoes(participante.getEmail());
                    if (inscricoes2.isEmpty()) {
                        System.out.println("Você não está inscrito em nenhum evento.");
                    } else {
                        inscricoes2.forEach(inscricao -> {
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
                    System.out.println("\n--- Cancelar inscrição em evento ---");
                    var inscricoesCancel = sistema.listarInscricoes(participante.getEmail());
                    if (inscricoesCancel.isEmpty()) {
                        System.out.println("Você não está inscrito em nenhum evento.");
                    } else {
                        for (int i = 0; i < inscricoesCancel.size(); i++) {
                            var evento = inscricoesCancel.get(i).getEvento();
                            System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicio(), evento.getDataFim());
                        }
                        System.out.print("Digite o número do evento para cancelar a inscrição (ou 0 para voltar): ");
                        String escolha = scanner.nextLine();
                        try {
                            int idx = Integer.parseInt(escolha);
                            if (idx > 0 && idx <= inscricoesCancel.size()) {
                                var inscricao = inscricoesCancel.get(idx - 1);
                                try {
                                    sistema.cancelarInscricaoEmEvento(inscricao);
                                    System.out.println("Inscrição cancelada com sucesso!");
                                } catch (Exception e) {
                                    System.out.println("Não foi possível cancelar: " + e.getMessage());
                                }
                            } else if (idx == 0) {
                            } else {
                                System.out.println("Opção inválida.");
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Entrada inválida.");
                        }
                    }
                    break;
                case "4":
                    menuTrabalhos(sistema, scanner, participante);
                    break;
                case "5":
                    var optOrg2 = sistema.buscarOrganizadorPorEmail(participante.getEmail());
                    Organizador organizador;
                    if (optOrg2.isEmpty()) {
                        System.out.println("\nVocê ainda não é organizador.");
                        System.out.print("Deseja se tornar organizador? (s/n): ");
                        String resp = scanner.nextLine();
                        if (resp.equalsIgnoreCase("s")) {
                            try {
                                organizador = sistema.cadastrarOrganizador(participante);
                                System.out.println("Agora você é um organizador!");
                                menuOrganizador(sistema, scanner, organizador);
                            } catch (Exception e) {
                                System.out.println("Erro ao cadastrar organizador: " + e.getMessage());
                            }
                        }
                    } else {
                        organizador = optOrg2.get();
                        menuOrganizador(sistema, scanner, organizador);
                    }
                    break;
                case "6":
                    menuAvaliador(sistema, scanner, participante);
                    break;
                case "7":
                    menuCertificados(sistema, scanner, participante);
                    break;
                case "8":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuAvaliador(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== Menu de Avaliador ===");
            System.out.println("1. Ver eventos/trabalhos designados");
            System.out.println("2. Avaliar trabalho designado");
            System.out.println("3. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    var optAvaliador = sistema.buscarAvaliadorPorEmail(participante.getEmail());
                    if (optAvaliador.isEmpty()) {
                        System.out.println("Você ainda não foi designado como avaliador em nenhum evento.");
                        break;
                    }
                    entity.Avaliador avaliador = optAvaliador.get();
                    List<entity.Trabalho> trabalhosDesignados = avaliador.getTrabalhosDesignados();
                    if (trabalhosDesignados.isEmpty()) {
                        System.out.println("Nenhum trabalho designado para avaliação.");
                    } else {
                        System.out.println("Trabalhos designados:");
                        for (entity.Trabalho trabalho : trabalhosDesignados) {
                            System.out.println("Evento: " + trabalho.getEvento().getNome());
                            System.out.println("Título: " + trabalho.getTitulo());
                            System.out.print("Autores: ");
                            String nomesAutores = trabalho.getAutores().stream()
                                    .map(entity.Participante::getNome)
                                    .reduce((a, b) -> a + ", " + b)
                                    .orElse("");
                            System.out.println(nomesAutores);
                            System.out.println("Arquivo: " + trabalho.getArquivo());
                            System.out.println("Nota Final: " + trabalho.getNotaFinal());
                            System.out.println("Status: " + trabalho.getStatus());
                            System.out.println("Comentários das avaliações:");
                            if (trabalho.getAvaliacoes().isEmpty()) {
                                System.out.println("  Nenhum comentário disponível.");
                            } else {
                                for (entity.Avaliacao avaliacao : trabalho.getAvaliacoes()) {
                                    System.out.println("  Avaliador: " + avaliacao.getAvaliador().getNome());
                                    System.out.println("  Nota: " + avaliacao.getNota());
                                    System.out.println("  Comentário: " + avaliacao.getComentario());
                                    System.out.println("  -------------------------");
                                }
                            }
                            System.out.println("-----------------------------");
                        }
                    }
                    break;
                case "2":
                    var optAvaliador2 = sistema.buscarAvaliadorPorEmail(participante.getEmail());
                    if (optAvaliador2.isEmpty()) {
                        System.out.println("Você ainda não foi designado como avaliador em nenhum evento.");
                        break;
                    }
                    entity.Avaliador avaliador2 = optAvaliador2.get();
                    List<entity.Trabalho> trabalhosDesignados2 = avaliador2.getTrabalhosDesignados();
                    if (trabalhosDesignados2.isEmpty()) {
                        System.out.println("Nenhum trabalho designado para avaliação.");
                        break;
                    }
                    for (int i = 0; i < trabalhosDesignados2.size(); i++) {
                        entity.Trabalho trabalho = trabalhosDesignados2.get(i);
                        System.out.printf("%d. %s (Evento: %s)\n", i + 1, trabalho.getTitulo(), trabalho.getEvento().getNome());
                    }
                    System.out.print("Escolha o número do trabalho para avaliar (ou 0 para voltar): ");
                    String escolha = scanner.nextLine();
                    int idx;
                    try {
                        idx = Integer.parseInt(escolha);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idx <= 0 || idx > trabalhosDesignados2.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    entity.Trabalho trabalhoAvaliar = trabalhosDesignados2.get(idx - 1);
                    System.out.print("Nota (0 a 10): ");
                    String notaStr = scanner.nextLine();
                    double nota;
                    try {
                        nota = Double.parseDouble(notaStr);
                        if (nota < 0 || nota > 10) {
                            System.out.println("Nota deve ser entre 0 e 10.");
                            break;
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Nota inválida.");
                        break;
                    }
                    System.out.print("Comentário: ");
                    String comentario = scanner.nextLine();
                    try {
                        sistema.registrarAvaliacao(avaliador2, trabalhoAvaliar, nota, comentario);
                        System.out.println("Avaliação registrada com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao registrar avaliação: " + e.getMessage());
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

    private static void menuTrabalhos(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== Menu de Trabalhos ===");
            System.out.println("1. Submeter trabalho");
            System.out.println("2. Ver trabalhos submetidos por evento");
            System.out.println("3. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    submeterTrabalho(sistema, scanner, participante);
                    break;
                case "2":
                    verTrabalhosPorEvento(sistema, scanner, participante);
                    break;
                case "3":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void verTrabalhosPorEvento(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        System.out.println("\n--- Trabalhos Submetidos por Evento ---");
        var inscricoes = sistema.listarInscricoes(participante.getEmail());
        if (inscricoes.isEmpty()) {
            System.out.println("Você não está inscrito em nenhum evento.");
            return;
        }
        List<Evento> eventosInscrito = new ArrayList<>();
        for (var inscricao : inscricoes) {
            eventosInscrito.add(inscricao.getEvento());
        }
        for (int i = 0; i < eventosInscrito.size(); i++) {
            Evento evento = eventosInscrito.get(i);
            System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicio(), evento.getDataFim());
        }
        System.out.print("Escolha o número do evento para ver os trabalhos (ou 0 para voltar): ");
        String escolha = scanner.nextLine();
        int idx;
        try {
            idx = Integer.parseInt(escolha);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (idx <= 0 || idx > eventosInscrito.size()) {
            System.out.println("Operação cancelada.");
            return;
        }
        Evento eventoEscolhido = eventosInscrito.get(idx - 1);
        List<entity.Trabalho> trabalhos;
        try {
            trabalhos = sistema.listarTrabalhoPorEvento(eventoEscolhido);
        } catch (Exception e) {
            System.out.println("Não foi possível listar trabalhos: " + e.getMessage());
            return;
        }
        if (trabalhos == null || trabalhos.isEmpty()) {
            System.out.println("Nenhum trabalho submetido para este evento.");
        } else {
            System.out.println("\nTrabalhos submetidos:");
            for (entity.Trabalho trabalho : trabalhos) {
                System.out.println("Título: " + trabalho.getTitulo());
                System.out.print("Autores: ");
                String nomesAutores = trabalho.getAutores().stream()
                        .map(entity.Participante::getNome)
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
                System.out.println(nomesAutores);
                System.out.println("Arquivo: " + trabalho.getArquivo());
                System.out.println("Nota Final: " + trabalho.getNotaFinal());
                System.out.println("Status: " + trabalho.getStatus());
                System.out.println("Comentários das avaliações:");
                if (trabalho.getAvaliacoes().isEmpty()) {
                    System.out.println("  Nenhum comentário disponível.");
                } else {
                    for (entity.Avaliacao avaliacao : trabalho.getAvaliacoes()) {
                        System.out.println("  Avaliador: " + avaliacao.getAvaliador().getNome());
                        System.out.println("  Nota: " + avaliacao.getNota());
                        System.out.println("  Comentário: " + avaliacao.getComentario());
                        System.out.println("  -------------------------");
                    }
                }
                System.out.println("-----------------------------");
            }
        }
    }

    private static void submeterTrabalho(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        System.out.println("\n--- Submeter Trabalho ---");
        var inscricoes = sistema.listarInscricoes(participante.getEmail());
        List<Evento> eventosComSubmissao = new ArrayList<>();
        for (var inscricao : inscricoes) {
            Evento evento = inscricao.getEvento();
            if (evento.estaNoPeriodoSubmissao(java.time.LocalDate.now())) {
                eventosComSubmissao.add(evento);
            }
        }
        if (eventosComSubmissao.isEmpty()) {
            System.out.println("Você não está inscrito em nenhum evento com submissão aberta no momento.");
            return;
        }
        System.out.println("Eventos com submissão aberta:");
        for (int i = 0; i < eventosComSubmissao.size(); i++) {
            Evento evento = eventosComSubmissao.get(i);
            System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicioSubmissao(), evento.getDataFimSubmissao());
        }
        System.out.print("Escolha o número do evento para submeter trabalho (ou 0 para cancelar): ");
        String escolha = scanner.nextLine();
        int idx;
        try {
            idx = Integer.parseInt(escolha);
        } catch (NumberFormatException e) {
            System.out.println("Entrada inválida.");
            return;
        }
        if (idx <= 0 || idx > eventosComSubmissao.size()) {
            System.out.println("Operação cancelada.");
            return;
        }
        Evento eventoEscolhido = eventosComSubmissao.get(idx - 1);

        System.out.print("Título do trabalho: ");
        String titulo = scanner.nextLine();
        System.out.print("Nome(s) dos autores separados por vírgula (deixe em branco para ser apenas você): ");
        String autoresStr = scanner.nextLine();

        List<Participante> autores = new ArrayList<>();
        if (autoresStr.isBlank()) {
            autores.add(participante);
        } else {
            String[] nomes = autoresStr.split(",");
            for (String nomeAutor : nomes) {
                String nomeTrim = nomeAutor.trim();
                if (nomeTrim.equalsIgnoreCase(participante.getNome())) {
                    autores.add(participante);
                } else {
                    var possiveis = sistema.listarEventos().stream()
                            .flatMap(ev -> ev.getInscricoes().stream())
                            .map(insc -> insc.getParticipante())
                            .filter(p -> p.getNome().equalsIgnoreCase(nomeTrim))
                            .findFirst();
                    if (possiveis.isPresent()) {
                        autores.add(possiveis.get());
                    } else {
                        System.out.println("Autor '" + nomeTrim + "' não encontrado ou não inscrito no evento.");
                        return;
                    }
                }
            }
        }

        System.out.print("Nome do arquivo do trabalho (ex: trabalho.pdf): ");
        String arquivo = scanner.nextLine();

        try {
            sistema.submeterTrabalho(titulo, autores, eventoEscolhido, arquivo);
            System.out.println("Trabalho submetido com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao submeter trabalho: " + e.getMessage());
        }
    }

    private static void menuCertificados(SistemaSGEAController sistema, Scanner scanner, Participante participante) {
        boolean executando = true;
        while (executando) {
            System.out.println("\n=== Menu de Certificados ===");
            System.out.println("1. Emitir certificado de participação");
            System.out.println("2. Emitir certificado de apresentação de trabalho");
            System.out.println("3. Ver meus certificados");
            System.out.println("4. Voltar");
            System.out.print("Escolha uma opção: ");
            String opcao = scanner.nextLine();

            switch (opcao) {
                case "1":
                    var inscricoes = sistema.listarInscricoes(participante.getEmail());
                    List<Evento> eventosElegiveis = new ArrayList<>();
                    for (var inscricao : inscricoes) {
                        Evento evento = inscricao.getEvento();
                        // Só permite se já terminou e presença confirmada
                        if (inscricao.isPresencaConfirmada() && java.time.LocalDate.now().isAfter(evento.getDataFim())) {
                            eventosElegiveis.add(evento);
                        }
                    }
                    if (eventosElegiveis.isEmpty()) {
                        System.out.println("Nenhum evento elegível para emissão de certificado de participação.");
                        break;
                    }
                    for (int i = 0; i < eventosElegiveis.size(); i++) {
                        Evento evento = eventosElegiveis.get(i);
                        System.out.printf("%d. %s (%s a %s)\n", i + 1, evento.getNome(), evento.getDataInicio(), evento.getDataFim());
                    }
                    System.out.print("Escolha o número do evento para emitir o certificado (ou 0 para voltar): ");
                    String escolha = scanner.nextLine();
                    int idx;
                    try {
                        idx = Integer.parseInt(escolha);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idx <= 0 || idx > eventosElegiveis.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    Evento eventoEscolhido = eventosElegiveis.get(idx - 1);
                    try {
                        entity.Certificado cert = sistema.emitirCertificadoParticipacao(participante, eventoEscolhido, java.time.LocalDate.now());
                        System.out.println("Certificado emitido com sucesso!");
                        System.out.println(cert);
                    } catch (Exception e) {
                        System.out.println("Erro ao emitir certificado: " + e.getMessage());
                    }
                    break;
                case "2":
                    List<entity.Trabalho> trabalhosAprovados = new ArrayList<>();
                    for (entity.Trabalho trabalho : participante.getTrabalhos()) {
                        if (trabalho.isAprovado() && java.time.LocalDate.now().isAfter(trabalho.getEvento().getDataFim())) {
                            trabalhosAprovados.add(trabalho);
                        }
                    }
                    if (trabalhosAprovados.isEmpty()) {
                        System.out.println("Nenhum trabalho aprovado e elegível para certificado de apresentação.");
                        break;
                    }
                    for (int i = 0; i < trabalhosAprovados.size(); i++) {
                        entity.Trabalho trabalho = trabalhosAprovados.get(i);
                        System.out.printf("%d. %s (Evento: %s)\n", i + 1, trabalho.getTitulo(), trabalho.getEvento().getNome());
                    }
                    System.out.print("Escolha o número do trabalho para emitir o certificado (ou 0 para voltar): ");
                    String escolhaTrabalho = scanner.nextLine();
                    int idxTrabalho;
                    try {
                        idxTrabalho = Integer.parseInt(escolhaTrabalho);
                    } catch (NumberFormatException e) {
                        System.out.println("Entrada inválida.");
                        break;
                    }
                    if (idxTrabalho <= 0 || idxTrabalho > trabalhosAprovados.size()) {
                        System.out.println("Operação cancelada.");
                        break;
                    }
                    entity.Trabalho trabalhoEscolhido = trabalhosAprovados.get(idxTrabalho - 1);
                    try {
                        List<entity.Certificado> certs = sistema.emitirCertificadoApresentacao(trabalhoEscolhido, java.time.LocalDate.now());
                        for (entity.Certificado cert : certs) {
                            if (cert.getParticipante().equals(participante)) {
                                System.out.println("Certificado emitido com sucesso!");
                                System.out.println(cert);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("Erro ao emitir certificado de apresentação: " + e.getMessage());
                    }
                    break;
                case "3":
                    List<entity.Certificado> meusCertificados = participante.getCertificados();
                    if (meusCertificados.isEmpty()) {
                        System.out.println("Você não possui certificados emitidos.");
                    } else {
                        for (entity.Certificado cert : meusCertificados) {
                            System.out.println(cert);
                            System.out.println("-----------------------------");
                        }
                    }
                    break;
                case "4":
                    executando = false;
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
