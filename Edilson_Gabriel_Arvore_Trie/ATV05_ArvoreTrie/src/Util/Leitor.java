package Util;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Leitor<TIPO> {
    public String[] carregarDados(String caminho) {
        String elementosConcatenados = "";
        File arquivo = new File(caminho);
        try (Scanner scanner = new Scanner(arquivo)) {
            while (scanner.hasNextLine()) {
                 elementosConcatenados += (scanner.nextLine()+"\n");
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo: " + e.getMessage());
        }
        return elementosConcatenados.split("\n");
    }
}
