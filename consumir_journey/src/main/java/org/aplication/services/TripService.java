package org.aplication.services;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;


public class TripService {

    // Método para cadastrar a viagem
    public void cadastrarViagem(String token) throws IOException {
        // Solicita os dados ao usuário
        String destination = pedirDados("Digite o destino da viagem: ");
        String startsAt = pedirDados("Digite a data de início (formato: dd/MM/aaaa): ");
        String endsAt = pedirDados("Digite a data de término (formato: dd/MM/aaaa): ");

        String nomeUsuario = "Nome Usuário";
        String usuarioId = "123";

        // URL da API para cadastro de viagem
        String urlString = "http://localhost:8080/trips";
        URL url = new URL(urlString);

        // Criação da conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token);  // Passando o token no cabeçalho
        connection.setDoOutput(true);

        // Montando o JSON com os dados da viagem
        String jsonInputString = String.format(
                "{\"destination\": \"%s\", \"startsAt\": \"%s\", \"endsAt\": \"%s\", \"userId\": \"%s\", \"userName\": \"%s\"}",
                destination, startsAt, endsAt, usuarioId, nomeUsuario
        );

        // Enviando a requisição
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Verificando o código de resposta
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            System.out.println("Viagem cadastrada com sucesso!");
        } else {
            System.out.println("Erro ao cadastrar viagem: " + responseCode);
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

    // Método para listar viagens
    public void listarViagens(String token) throws Exception {
        // URL da API de viagens
        String urlString = "http://localhost:8080/trips";
        URL url = new URL(urlString);

        // Abrindo a conexão com a API
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Adicionando o token ao cabeçalho da requisição
        connection.setRequestProperty("Authorization", "Bearer " + token);

        // Verificando a resposta da API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            // Lendo a resposta da API (as viagens)
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder content = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            // Processando o JSON da resposta
            String responseString = content.toString();
            JSONArray viagensArray = new JSONArray(responseString);

            // Exibindo as viagens
            System.out.println("Lista de Viagens:");
            for (int i = 0; i < viagensArray.length(); i++) {
                JSONObject viagem = viagensArray.getJSONObject(i);
                int id = viagem.getInt("id");  // Usando getInt() para id
                String destination = viagem.getString("destination");
                String startsAt = viagem.getString("startsAt");
                String endsAt = viagem.getString("endsAt");
                String ownerName = viagem.getString("ownerName");  // Obtendo o nome do dono da viagem

                System.out.println("--------------------");
                System.out.println("Viagem ID: " + id);
                System.out.println("Destino: " + destination);
                System.out.println("Início: " + startsAt);
                System.out.println("Fim: " + endsAt);
                System.out.println("Nome do proprietário: " + ownerName);
                System.out.println("--------------------");
            }
        } else {
            System.out.println("Erro ao listar viagens. Código de resposta: " + responseCode);
        }


        connection.disconnect();
    }

    //Método para atualizar viagem
    public void atualizarViagem(String token) throws IOException {
        // Solicita o ID da viagem a ser atualizada
        String tripId = pedirDados("Digite o ID da viagem para atualizar: ");
        String destination = pedirDados("Digite o novo destino da viagem: ");
        String startsAt = pedirDados("Digite a nova data de início (yyyy-MM-dd): ");
        String endsAt = pedirDados("Digite a nova data de término (yyyy-MM-dd): ");

        // Constrói a URL com o ID da viagem
        String urlString = "http://localhost:8080/trips/" + tripId;
        URL url = new URL(urlString);

        // Configuração da conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("PUT");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "Bearer " + token); // Passando o token no cabeçalho
        connection.setDoOutput(true);

        // Montando o JSON para a viagem atualizada
        String jsonInputString = String.format(
                "{\"destination\": \"%s\", \"startsAt\": \"%s\", \"endsAt\": \"%s\"}",
                destination, startsAt, endsAt
        );

        // Enviando a requisição
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input, 0, input.length);
        }

        // Processando a resposta da API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_CREATED) { // Código 201
            System.out.println("Viagem atualizada com sucesso.");
        } else {
            System.out.println("Erro ao atualizar viagem: " + responseCode);
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                System.out.println("Detalhes do erro: " + errorMessage.toString());
            }
        }

        connection.disconnect();
    }

    // Método para deletar uma viagem
    public void deletarViagem(String token) throws IOException {
        // Solicita o ID da viagem a ser deletada
        String tripId = pedirDados("Digite o ID da viagem para deletar: ");

        // Constrói a URL com o ID da viagem
        String urlString = "http://localhost:8080/trips/" + tripId;
        URL url = new URL(urlString);

        // Configuração da conexão
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("DELETE");
        connection.setRequestProperty("Authorization", "Bearer " + token); // Passando o token no cabeçalho

        // Processando a resposta da API
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_NO_CONTENT) { // Código 204
            System.out.println("Viagem deletada com sucesso.");
        } else {
            System.out.println("Erro ao deletar viagem: " + responseCode);
            try (BufferedReader errorReader = new BufferedReader(new InputStreamReader(connection.getErrorStream()))) {
                StringBuilder errorMessage = new StringBuilder();
                String line;
                while ((line = errorReader.readLine()) != null) {
                    errorMessage.append(line);
                }
                System.out.println("Detalhes do erro: " + errorMessage.toString());
            }
        }

        connection.disconnect();
    }



    // Método para pedir dados ao usuário
    private static String pedirDados(String mensagem) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(mensagem);
        return scanner.nextLine();
    }
}

