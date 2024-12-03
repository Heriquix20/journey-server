package org.aplication;

import org.aplication.services.TripService;
import org.aplication.services.UserService;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception {

        // Criar instância dos serviços
        UserService userService = new UserService();
        TripService tripService = new TripService();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Journey");
        System.out.println("********************************************");

        // Variável para armazenar o token
        String token = null;

        // Repetir o login até ser bem-sucedido
        while (token == null || token.isEmpty()) {
            System.out.println("Escolha uma opção:");
            System.out.println("1 - Cadastro");
            System.out.println("2 - Login");

            int opcao = scanner.nextInt();
            scanner.nextLine();  // Consumir o newline que fica após o nextInt()

            try {
                if (opcao == 1) {
                    userService.cadastrar();  // Cadastro de usuário
                } else if (opcao == 2) {
                    // Login e obter o token
                    token = userService.login();
                    if (token != null && !token.isEmpty()) {
                        System.out.println("Token de autenticação: " + token);
                    } else {
                        System.out.println("Erro no login, token não obtido. Tente novamente.");
                    }
                } else {
                    System.out.println("Opção inválida.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Após login bem-sucedido, exibe o menu de viagens
        int menuOpcao;
        do {
            System.out.println("\n--- MENU ---");
            System.out.println("1 - Ver Viagens");
            System.out.println("2 - Cadastrar Viagem");
            System.out.println("3 - Atualizar Viagem");
            System.out.println("4 - Deletar Viagem");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            menuOpcao = scanner.nextInt();
            scanner.nextLine();

            switch (menuOpcao) {
                case 1:
                    // Chama o método listarViagens para exibir as viagens
                    tripService.listarViagens(token);
                    break;
                case 2:
                    // Lógica para cadastrar uma nova viagem
                    System.out.println("Cadastro de viagem...");
                    tripService.cadastrarViagem(token);
                    break;
                case 3:
                    // Lógica para atualizar uma viagem
                    System.out.println("Atualizando viagem...");
                    tripService.atualizarViagem(token);
                    break;
                case 4:
                    // Lógica para deletar uma viagem
                    System.out.println("Deletando viagem...");
                    tripService.deletarViagem(token);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
                    break;
            }
        } while (menuOpcao != 0);

        scanner.close();
    }
}





