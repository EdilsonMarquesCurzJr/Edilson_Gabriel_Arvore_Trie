package Main;

import ArvoreTrie.ArvoreTrie;
import Util.Leitor;
import java.text.Normalizer;
import java.util.Locale;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        //Declaração das árvores Trie do programa
        ArvoreTrie arvoreNormal = new ArvoreTrie(false);
        ArvoreTrie arvoreInvertida = new ArvoreTrie(true);

        //Instância do scanner
        Scanner scan = new Scanner(System.in);

        // Variável representativa da escolha do usuário
        int escolha = 0;

        // Palavra a ser inserida
        String palavra;

        // Instância do leitor de dados txt
        Leitor<String> ler = new Leitor<>();

        // Verificaçãoo caminho absoluto ou relativo correto do arquivo
        String[] dados = ler.carregarDados("Edilson_Gabriel_Arvore_Trie/ATV05_ArvoreTrie/src/DadosEntrada/dados.txt");

        // Verificação se o arquivo é nulo
        if (dados == null || dados.length == 0) {
            System.out.println("Nenhum dado foi carregado. Verifique o arquivo de entrada.");
            return;
        }

        // Inserindo dados de arquivo no programa com leitor
        for (String dado : dados) {
            String limpa = limparTexto(dado);
            if (!limpa.isEmpty()) {
                arvoreNormal.inserirElemento(limpa);
                arvoreInvertida.inserirElemento(limpa);
            }
        }


        // MENU E LÓGICA
        do {
            System.out.println("============ Menu ============");
            System.out.println(" [1] LISTAR COMPOSTOS QUÍMICOS");
            System.out.println(" [2] BUSCAR COMPOSTOS POR PREFIXO");
            System.out.println(" [3] BUSCAR COMPOSTOS POR SUFIXO");
            System.out.println(" [4] INSERIR NOVO COMPOSTO");
            System.out.println(" [5] REMOVER COMPOSTO");
            System.out.println(" [0] ENCERRAR ");
            System.out.println("\n -->");
            escolha = scan.nextInt();
            scan.nextLine();  // Limpar o buffer do scanner

            switch (escolha) {
                case 1: //LISTAGEM
                    System.out.println("Quantidade de compostos cadastrados: " + arvoreNormal.getQuantidadeDePalavras());
                    System.out.println("Listagem de compostos cadastrados:");
                    arvoreNormal.exibirPalavras();
                    System.out.println("\n\n\n");

                    break;

                case 2:// BUSCA POR PREFIXO
                    System.out.println("Informe o prefixo:");
                    palavra = scan.nextLine();
                    arvoreNormal.exibirPalavrasPrefixo(palavra);
                    break;

                case 3:// BUSCA POR SUFIXO
                    System.out.println("Informe o sufixo: ");
                    palavra = scan.nextLine();
                    arvoreInvertida.exibirPalavrasSufixo(palavra);
                    break;

                case 4: //INSERIR
                    System.out.println("Informe o novo composto que deseja inserir:");
                    palavra = scan.nextLine();
                    palavra = palavra.toLowerCase();
                    arvoreNormal.inserirElemento(palavra);
                    arvoreInvertida.inserirElemento(palavra);
                    break;

                case 5: // DELETAR
                    System.out.println("Informe o composto que deseja remover:");
                    palavra = scan.nextLine();
                    arvoreNormal.removerElemento(palavra);
                    arvoreInvertida.removerElemento(palavra);

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

    public static void BuscarArvoreReversa(ArvoreTrie arvoreTrie, String input){
        //Tratar o input revertendo a ordem e jogando na árvore inversa, em lower case
        input = input.toLowerCase();
        StringBuilder sb = new StringBuilder(input);
        input = sb.reverse().toString();


    }
}
