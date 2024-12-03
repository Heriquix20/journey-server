package org.aplication.services;

import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class UserService {

    // Método que cadastra o usuário e solicita os dados no próprio método
    public void cadastrar() throws IOException {
        // Solicita os dados ao usuário
        String name = pedirDados("Digite o nome: ");
        String email = pedirDados("Digite o e-mail: ");
        String password = pedirDados("Digite a senha: ");

        // URL da API
        String urlString = "http://localhost:8080/users";
        URL url = new URL(urlString);

        // Criação da conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Montando o JSON com os dados do usuário
        String jsonInputString = String.format(
                "{\"name\": \"%s\", \"email\": \"%s\", \"password\": \"%s\"}",
                name, email, password
        );

        // Enviando a requisição
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Verificando o código de resposta
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("Usuário cadastrado com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar usuário: " + responseCode);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
            }
        }

        connection.disconnect();
    }

    // Método que realiza o login e retorna o token
    public String login() throws IOException {
        // Solicita os dados de login
        String email = pedirDados("Digite o e-mail para login: ");
        String password = pedirDados("Digite a senha para login: ");

        // URL da API para login
        String urlString = "http://localhost:8080/login";
        URL url = new URL(urlString);

        // Criação da conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        // Montando o JSON com os dados de login
        String jsonInputString = String.format(
                "{\"email\": \"%s\", \"password\": \"%s\"}",
                email, password
        );

        // Enviando a requisição
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Verificando o código de resposta
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Lendo a resposta da API (o token)
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }

                // Parse da resposta para extrair o token
                String responseString = content.toString();
                JSONObject jsonResponse = new JSONObject(responseString);
                String token = jsonResponse.getString("token");

                System.out.println("Login realizado com sucesso!");
                return token;  // Retorna o token

            } catch (Exception e) {
                System.out.println("Erro ao processar o token da resposta.");
                e.printStackTrace();
            }
        } else {
            System.out.println("Erro ao realizar login: " + responseCode);
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                String inputLine;
                StringBuilder content = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    content.append(inputLine);
                }
                System.out.println(content.toString());
            }
        }

        connection.disconnect();
        return null;  // Retorna null caso haja erro
    }

    // Método para pedir dados ao usuário
    private static String pedirDados(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}


