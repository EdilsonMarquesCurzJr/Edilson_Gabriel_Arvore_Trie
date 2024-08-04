package Util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Leitor<TIPO> {
    public String[] carregarDados(String caminho) {
        List<String> linhas = new ArrayList<>();
        File arquivo = new File(caminho);

        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                String linha = scanner.nextLine();
                // Adiciona a linha na lista de linhas
                linhas.add(linha);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }

        // Converte a lista de linhas para um array de strings
        return linhas.toArray(new String[0]);
    }
}