package controller;

import entity.Evento;
import entity.Participante;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SistemaSGEA {
    private List<Evento> eventos = new ArrayList<>();
    private List<Participante> participantes = new ArrayList<>();

    public void run(){
        Scanner sc = new Scanner(System.in);
        while (true){
            System.out.println("1. Cadastrar participante");
            System.out.println("2. Criar evento");
            System.out.println("3. Listar eventos");
            System.out.println("4. Inscrever em evento");
            System.out.println("0. Sair");
            int opcao = Integer.parseInt(sc.nextLine());

            switch (opcao){
                case 1:
                    cadastrarParticipante(sc);
                    break;
                case 2:
                    criarEvento(sc);
                    break;
                case 3:
                    listarEventos();
                    break;
                case 4:
                    inscreverEmEvento(sc);
                    break;
                case 0:
                    sc.close();
                    return;
                default:
                    System.out.println("Opção inválida");
            }
        }
    }

    private void cadastrarParticipante(Scanner sc){
        System.out.println("Nome: ");
        String nome= sc.nextLine();
        System.out.println("Email: ");
        String email= sc.nextLine();
        System.out.println("Instituição: ");
        String instituicao= sc.nextLine();

        Participante p = new Participante(nome, email, instituicao);
        participantes.add(p);
        System.out.println("Participante cadastrado");
    }

    private void criarEvento(Scanner sc){

    }

    private void listarEventos(){

    }

    private void inscreverEmEvento(Scanner sc){

    }
}
