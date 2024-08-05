package Main;

import ArvoreTrie.ArvoreTrie;
import Util.Leitor;
import java.text.Normalizer;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Declaração das árvores Trie do programa
        ArvoreTrie arvoreNormal = new ArvoreTrie(false);
        ArvoreTrie arvoreInvertida = new ArvoreTrie(true);

        // Instância do scanner
        Scanner scan = new Scanner(System.in);

        // Variável representativa da escolha do usuário
        int escolha;

        // Palavra a ser inserida
        String palavra;

        // Instância do leitor de dados txt
        Leitor<String> ler = new Leitor<>();

        // Verificação do caminho absoluto ou relativo correto do arquivo
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
                case 1: // LISTAGEM
                    System.out.println("Quantidade de compostos cadastrados: " + arvoreNormal.getQuantidadeDePalavras());
                    System.out.println("Listagem de compostos cadastrados:");
                    arvoreNormal.exibirPalavras();
                    System.out.println("\n\n\n");
                    break;

                case 2: // BUSCA POR PREFIXO
                    System.out.println("Informe o prefixo:");
                    palavra = scan.nextLine();
                    if (verificaTexto(palavra)) {
                        arvoreNormal.exibirPalavrasPrefixo(palavra);
                    } else {
                        System.out.println("Prefixo inválido. Utilize apenas letras.");
                    }
                    System.out.println("\n\n\n");
                    break;

                case 3: // BUSCA POR SUFIXO
                    System.out.println("Informe o sufixo: ");
                    palavra = scan.nextLine();
                    if (verificaTexto(palavra)) {
                        arvoreInvertida.exibirPalavrasSufixo(palavra);
                    } else {
                        System.out.println("Sufixo inválido. Utilize apenas letras.");
                    }
                    System.out.println("\n\n\n");
                    break;

                case 4: // INSERIR
                    System.out.println("Informe o novo composto que deseja inserir:");
                    palavra = scan.nextLine().toLowerCase();
                    if (verificaTexto(palavra)) {
                        if (!arvoreNormal.verificaPalavra(palavra)) {
                            arvoreNormal.inserirElemento(palavra);
                            arvoreInvertida.inserirElemento(palavra);
                            System.out.println("Palavra inserida com sucesso.");
                        } else {
                            System.out.println("Palavra já presente na estrutura.");
                        }
                    } else {
                        System.out.println("Palavra inválida. Utilize apenas letras.");
                    }
                    System.out.println("\n\n\n");
                    break;

                case 5: // REMOVER
                    System.out.println("Informe o composto que deseja remover:");
                    palavra = scan.nextLine();
                    if (verificaTexto(palavra)) {
                        arvoreNormal.removerElemento(palavra);
                        palavra = new StringBuilder(palavra).reverse().toString();
                        arvoreInvertida.removerElemento(palavra);
                        System.out.println("Composto removido com sucesso.");
                    } else {
                        System.out.println("Palavra inválida. Utilize apenas letras.");
                    }
                    System.out.println("\n\n\n");
                    break;

                case 0: // ENCERRAR
                    System.out.println("Saindo...");
                    scan.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (escolha != 0);
    }

    // Função para limpar o texto
    public static String limparTexto(String texto) {
        // Normalizar a string para a forma NFD (Normalization Form D)
        String textoNormalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
        // Remover os caracteres diacríticos (acentos)
        String textoSemAcentos = textoNormalizado.replaceAll("\\p{M}", "");
        // Remover caracteres especiais, mantendo apenas alfanuméricos e espaços
        return textoSemAcentos.replaceAll("[^a-zA-Z ]", "");
    }

    // Função para verificar se a string contém apenas letras
    private static boolean verificaTexto(String texto) {
        return texto.matches("[a-zA-Z]+");
    }
}
