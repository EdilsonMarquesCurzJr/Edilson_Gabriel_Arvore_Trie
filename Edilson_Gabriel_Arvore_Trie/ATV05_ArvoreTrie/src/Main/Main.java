/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Main;
import ArvoreTrie.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class Main {
    public static void main(String[] args) {
        ArvoreTrie arvoreNormal = new ArvoreTrie(false);
        ArvoreTrie arvoreInvertida = new ArvoreTrie(true);

        String palavra = "exemplo";

        arvoreNormal.inserirElemento(palavra);
        arvoreInvertida.inserirElemento(palavra);

        System.out.println("Exibindo palavras na árvore normal:");
        arvoreNormal.exibirPalavras();

        System.out.println("\nExibindo palavras na árvore invertida:");
        arvoreInvertida.exibirPalavras();
    }
}
