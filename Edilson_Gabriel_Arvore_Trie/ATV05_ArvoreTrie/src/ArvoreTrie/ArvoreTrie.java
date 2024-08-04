package ArvoreTrie;
import java.text.Normalizer;
import java.text.Normalizer.Form;

public class ArvoreTrie {
    private No_Trie raiz;
    private int quantidadeDePalavras;
    private boolean revers;

    public ArvoreTrie(boolean revers) {
        this.raiz = new No_Trie('\0');
        this.quantidadeDePalavras = 0;
        this.revers = revers;
    }

    public void inserirElemento(String palavra) {
        No_Trie percorrer = this.raiz;

        // Normaliza e remove acentos
        palavra = Normalizer.normalize(palavra, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");

        if (verificaNumeros(palavra)) {
            if (revers) {
                palavra = new StringBuilder(palavra).reverse().toString();
            }
            char[] vetorPalavra = palavra.toLowerCase().toCharArray();

            for (char caractere : vetorPalavra) {
                // Verifica se o caractere é alfabético
                if (caractere < 'a' || caractere > 'z') {
                    System.out.println("Caractere inválido na palavra: " + caractere);
                    return;
                }

                int indice = caractere - 'a';

                // Verifica se o índice está dentro do intervalo
                if (indice < 0 || indice >= 26) {
                    System.out.println("Índice fora dos limites permitido para o caractere: " + caractere);
                    return;
                }

                if (percorrer.filhosDoNo[indice] == null) {
                    No_Trie novoElemento = new No_Trie(caractere);
                    percorrer.filhosDoNo[indice] = novoElemento;
                }
                percorrer = percorrer.filhosDoNo[indice];
            }
            percorrer.setFimDePalavra(true);
            //System.out.println("[" + palavra + "] inserido com sucesso na árvore.");
            this.quantidadeDePalavras++;
        } else {
            System.out.println("Não foi possível inserir [" + palavra + "] na árvore. Verifique se existe algum número ou caractere especial no meio da palavra.");
        }
    }

    public void removerElemento(String palavraRemover) {
        palavraRemover = Normalizer.normalize(palavraRemover, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        if (verificaNumeros(palavraRemover)) {
            if (verificaPalavra(palavraRemover)) {
                char[] charPalavraRemover = palavraRemover.toLowerCase().toCharArray();
                No_Trie percorrer = this.raiz;
                String caminho = "";
                for (int i = 0; i < charPalavraRemover.length; i++) {
                    int indice = charPalavraRemover[i] - 'a';
                    caminho += charPalavraRemover[i];
                    percorrer = percorrer.filhosDoNo[indice];
                }
                percorrer.setFimDePalavra(false);
                while (!caminho.isEmpty()) {
                    int indice = caminho.charAt(caminho.length() - 1) - 'a';
                    caminho = caminho.substring(0, caminho.length() - 1);
                    if (verificaFilhos(percorrer, indice)) {
                        percorrer.filhosDoNo[indice] = null;
                    } else {
                        break;
                    }
                    percorrer = this.raiz;
                    for (char letra : caminho.toCharArray()) {
                        indice = letra - 'a';
                        percorrer = percorrer.filhosDoNo[indice];
                    }
                }
                System.out.println("[" + palavraRemover + "] removido/alterado com sucesso.");
                this.quantidadeDePalavras--;
            } else {
                System.out.println("A palavra [" + palavraRemover + "] não está presente na árvore.");
            }
        } else {
            System.out.println("Número ou caractere especial detectado em [" + palavraRemover + "]. Utilize apenas letras.");
        }
    }

    public String buscarElemento(String palavraBuscada, No_Trie percorrer) {
        String palavraParcial = "";
        if (verificaPalavra(palavraBuscada)) {
            char[] charPalavraBuscada = palavraBuscada.toLowerCase().toCharArray();
            for (int i = 0; i < charPalavraBuscada.length; i++) {
                int indice = charPalavraBuscada[i] - 'a';
                palavraParcial += percorrer.filhosDoNo[indice].getLetra();
                percorrer = percorrer.filhosDoNo[indice];
            }
            if (percorrer.isFimDePalavra()) {
                return palavraParcial;
            }
        }
        return null;
    }

    public String buscarElemento(String palavraBuscada) {
        if (verificaNumeros(palavraBuscada)) {
            palavraBuscada = Normalizer.normalize(palavraBuscada, Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
            palavraBuscada = palavraBuscada.toLowerCase();
            return buscarElemento(palavraBuscada, this.raiz);
        } else {
            System.out.println("Detectado um número ou caractere especial na palavra [" + palavraBuscada + "]. Utilize apenas letras.");
            return null;
        }
    }

    public void alterarElemento(String palavraAlterar, String palavraExistente) {
        removerElemento(palavraExistente);
        inserirElemento(palavraAlterar);
    }

    public void exibirPalavras() {
        System.out.println("Imprimindo palavras da árvore:");
        exibirPalavras(this.raiz, "");
    }

    private void exibirPalavras(No_Trie percorrer, String palavra) {
        if (percorrer != null) {
            if (percorrer.getLetra() != '\0') {
                palavra += percorrer.getLetra();
            }
            if (percorrer.isFimDePalavra()) {
                System.out.println("[" + palavra + "]");
            }
            for (int i = 0; i < 26; i++) {
                exibirPalavras(percorrer.filhosDoNo[i], palavra);
            }
        }
    }


    public void exibirPalavrasComecadasEm(char letra) {
        letra = Character.toLowerCase(letra);
        String semAcento = Normalizer.normalize(String.valueOf(letra), Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        letra = semAcento.charAt(0);
        try {
            if (!Character.isLetter(letra)) {
                throw new IllegalArgumentException("Caractere inválido [" + letra + "]: Utilize apenas letras.");
            }
            System.out.println("Todas as palavras começadas em [" + letra + "]");
            int indiceInicial = letra - 'a';
            exibirPalavrasComecadasEm(this.raiz.filhosDoNo[indiceInicial], "", indiceInicial);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void exibirPalavrasComecadasEm(No_Trie percorrer, String palavra, int indice) {
        if (percorrer != null) {
            palavra += percorrer.getLetra();
            if (percorrer.isFimDePalavra()) {
                System.out.println("[" + palavra + "]");
            }
            for (int i = 0; i < 26; i++) {
                if (percorrer.filhosDoNo[i] != null) {
                    exibirPalavrasComecadasEm(percorrer.filhosDoNo[i], palavra, i);
                }
            }
        }
    }

    public boolean verificaPalavra(String palavra) {
        char[] charPalavra = palavra.toLowerCase().toCharArray();
        No_Trie percorrer = this.raiz;
        for (int i = 0; i < charPalavra.length; i++) {
            int indice = charPalavra[i] - 'a';
            if (percorrer.filhosDoNo[indice] != null && charPalavra[i] == percorrer.filhosDoNo[indice].getLetra()) {
                percorrer = percorrer.filhosDoNo[indice];
            } else {
                return false;
            }
        }
        return percorrer.isFimDePalavra();
    }

    public void exibirPalavrasPrefixo(String prefixo) {
        No_Trie percorrer = this.raiz;
        // BUSCA POR PREFIXO EM ARVORE NAO REVERSA
        if (verificaNumeros(prefixo)) {
            char[] charPrefixo = prefixo.toCharArray();
            for (int i = 0; i < charPrefixo.length; i++) {
                int indice = charPrefixo[i] - 'a';
                if (percorrer.filhosDoNo[indice] == null || percorrer.filhosDoNo[indice].getLetra() != charPrefixo[i]) {
                    System.out.println("O prefixo [" + prefixo + "] não existe na árvore.");
                    return;
                }
                percorrer = percorrer.filhosDoNo[indice];
            }
            System.out.println("Palavras começadas em [" + prefixo + "]");
            exibirPalavrasPrefixo(prefixo, "", percorrer);
        } else {
            System.out.println("O prefixo [" + prefixo + "] possui algum número ou caractere especial. Utilize apenas letras.");
        }
    }

    private void exibirPalavrasPrefixo (String prefixo, String palavraParcial, No_Trie percorrer){
        if (percorrer != null) {
            if (percorrer.isFimDePalavra()) {
                String palavra = prefixo + palavraParcial;
                System.out.println("[" + palavra + "]");
            }
            for (int i = 0; i < 26; i++) {
                if (percorrer.filhosDoNo[i] != null) {

                    exibirPalavrasPrefixo(prefixo, palavraParcial + percorrer.filhosDoNo[i].getLetra(), percorrer.filhosDoNo[i]);
                }
            }
        }
    }


    public void exibirPalavrasSufixo(String sufixo) {
        if (revers) {
            sufixo = new StringBuilder(sufixo).reverse().toString();
            if (verificaNumeros(sufixo)) {
                No_Trie percorrer = this.raiz;
                char[] charSufixo = sufixo.toCharArray();
                for (int i = 0; i < charSufixo.length; i++) {
                    int indice = charSufixo[i] - 'a';
                    if (percorrer.filhosDoNo[indice] == null || percorrer.filhosDoNo[indice].getLetra() != charSufixo[i]) {
                        System.out.println("O sufixo [" + sufixo + "] não existe na árvore.");
                        return;
                    }
                    percorrer = percorrer.filhosDoNo[indice];
                }
                System.out.println("Palavras terminadas em [" + new StringBuilder(sufixo).reverse().toString() + "]");
                exibirPalavrasSufixo("", new StringBuilder(sufixo).toString(), percorrer);
            } else {
                System.out.println("O sufixo [" + sufixo + "] possui algum número ou caractere especial. Utilize apenas letras.");
            }
        } else {
            System.out.println("A árvore não está configurada para reverso. Utilize o método exibirPalavrasPrefixo.");
        }
    }

    private void exibirPalavrasSufixo(String sufixo, String palavraParcial, No_Trie percorrer) {
        if (percorrer != null) {
            if (percorrer.isFimDePalavra()) {
                String palavraOriginal = new StringBuilder(palavraParcial).reverse().toString();
                System.out.println("[" + palavraOriginal + "]");
            }
            for (int i = 0; i < 26; i++) {
                if (percorrer.filhosDoNo[i] != null) {
                    exibirPalavrasSufixo(sufixo, palavraParcial + percorrer.filhosDoNo[i].getLetra(), percorrer.filhosDoNo[i]);
                }
            }
        }
    }


    private boolean verificaFilhos (No_Trie percorrer,int indice){
            if (percorrer != null && percorrer.filhosDoNo[indice] != null ) {
                if (percorrer.filhosDoNo[indice].isFimDePalavra()) {
                    return false;
                }
                for (No_Trie noFilho : percorrer.filhosDoNo[indice].filhosDoNo) {
                    if (noFilho != null) {
                        return false;
                    }
                }
            }
            return true;
        }

        private boolean verificaNumeros (String palavra){
            return !palavra.matches(".*\\d.*");
        }

        public int getQuantidadeDePalavras () {
            return quantidadeDePalavras;
        }
}
