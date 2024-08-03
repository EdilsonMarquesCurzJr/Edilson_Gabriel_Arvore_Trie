package Main;

import ArvoreTrie.ArvoreTrie;
import Util.Leitor;
import java.text.Normalizer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArvoreTrie arvoreNormal = new ArvoreTrie(false);
        ArvoreTrie arvoreInvertida = new ArvoreTrie(true);
        Scanner scan = new Scanner(System.in);
        int escolha = 0;
        String palavra;

        Leitor<String> ler = new Leitor<>();

        // Verifique o caminho absoluto ou relativo correto do arquivo
        String[] dados = ler.carregarDados("Edilson_Gabriel_Arvore_Trie/ATV05_ArvoreTrie/src/DadosEntrada/dados.txt");

        if (dados == null || dados.length == 0) {
            System.out.println("Nenhum dado foi carregado. Verifique o arquivo de entrada.");
            return;
        }

        for (String dado : dados) {
            String limpa = limparTexto(dado);
            if (!limpa.isEmpty()) {
                arvoreNormal.inserirElemento(limpa);
                arvoreInvertida.inserirElemento(new StringBuilder(limpa).reverse().toString());
            }
        }

        System.out.println("\n\nÁrvore ordem normal: \n\n");
        arvoreNormal.exibirPalavras();
        System.out.println("\n\nÁrvore ordem inversa: \n\n");
        arvoreInvertida.exibirPalavras();

        do {
            System.out.println("============Menu============");
            System.out.println("\n\n [1]Buscar por prefixo");
            System.out.println(" [2]Buscar por sufixo");
            System.out.println(" [0]Sair");
            System.out.println("\n -->");
            escolha = scan.nextInt();
            scan.nextLine();  // Limpar o buffer do scanner
            switch (escolha) {
                case 1:
                    System.out.println("Informe o prefixo:");
                    palavra = scan.nextLine();
                    arvoreNormal.exibirPalavrasPrefixo(palavra);
                    break;
                case 2:
                    System.out.println("Informe o sufixo:");
                    palavra = scan.nextLine();
                    String sufixoInvertido = new StringBuilder(palavra).reverse().toString();
                    arvoreInvertida.exibirPalavrasPrefixo(sufixoInvertido);
                    break;
                case 0:
                    System.out.println("Saindo...");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);

        scan.close();
    }

    public static String limparTexto(String texto) {
        // Normalizar a string para a forma NFD (Normalization Form D)
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        // Remover os caracteres diacríticos (acentos)
        String textoSemAcentos = textoNormalizado.replaceAll("\\p{M}", "");
        // Remover caracteres especiais, mantendo apenas alfanuméricos e espaços
        return textoSemAcentos.replaceAll("[^a-zA-Z0-9 ]", "");
    }
}
