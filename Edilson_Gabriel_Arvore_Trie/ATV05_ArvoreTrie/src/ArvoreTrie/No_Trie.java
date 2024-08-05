
package ArvoreTrie;


public class No_Trie {
    //Atributos da classe:
    private char letra; // A informação que será armazenada nessa árvore é apenas uma letra.
    public No_Trie filhosDoNo[] = new No_Trie[26]; //Cada nó terá no MÁXIMO 26 filhos que representam o alfabeto de A-Z;
    //Aqui modificamos o vetor de nós filhos deste nó para o tamanho 26(tamanho do alfabeto tradicional)
    private boolean fimDePalavra; //Um atributo booleano para indicar se a letra em questão representa o fim de uma palavra;
    //Ex: A->M->O->R Nesse caso, a letra R representa o fim da palavra amor.

    //Método construtor:

    public No_Trie(char letra) {
        this.letra = letra; //Atribuímos a cada nó APENAS uma letra
        this.fimDePalavra = false; //Por padrão, todos os nós serão instanciados com seu verificador "false"
    }

    //Métodos especiais:
    public char getLetra() {
        return letra;
    }

    public void setLetra(char letra) {
        this.letra = letra;
    }

    public No_Trie[] getFilhosDoNo() {
        return filhosDoNo;
    }

    public void setFilhosDoNo(No_Trie[] filhosDoNo) {
        this.filhosDoNo = filhosDoNo;
    }

    public boolean isFimDePalavra() {
        return fimDePalavra;
    }

    public void setFimDePalavra(boolean fimDePalavra) {
        this.fimDePalavra = fimDePalavra;
    }



}
