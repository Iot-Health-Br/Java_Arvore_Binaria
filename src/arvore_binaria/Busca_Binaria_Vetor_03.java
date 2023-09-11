/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Busca_Binaria_Vetor_03 {
    public static void main(String[] args) {
        List<String> stopwords = new ArrayList<>();
        List<String> textoWords = new ArrayList<>();

        // Passo 1: Ler o arquivo "Texto.txt" e converter para letras minúsculas
        try (BufferedReader textoReader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"))) {
            String line;
            while ((line = textoReader.readLine()) != null) {
                String[] words = line.toLowerCase().split("\\s+");
                Collections.addAll(textoWords, words);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Passo 2: Ler o arquivo "Stopwords.txt"
        try (BufferedReader stopwordsReader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"))) {
            String word;
            while ((word = stopwordsReader.readLine()) != null) {
                stopwords.add(word.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<String> resultado = new ArrayList<>();

        // Passo 3: Comparar e adicionar palavras não stopwords em um vetor dinâmico
        for (String palavra : textoWords) {
            if (!stopwords.contains(palavra)) {
                if (resultado.isEmpty()) {
                    resultado.add(palavra);
                } else {
                    int index = buscaBinaria(resultado, palavra);
                    resultado.add(index, palavra);
                }
            }
        }

        // Passo 4: Exibir as palavras não stopwords
        System.out.println("Palavras não stopwords:");
        for (String palavra : resultado) {
            System.out.println(palavra);
        }
    }

    // Função de busca binária
    private static int buscaBinaria(List<String> lista, String palavra) {
        int inicio = 0;
        int fim = lista.size() - 1;

        while (inicio <= fim) {
            int meio = (inicio + fim) / 2;
            int comparacao = palavra.compareTo(lista.get(meio));
            if (comparacao == 0) {
                return meio;
            } else if (comparacao < 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        }

        return inicio;
    }
}
