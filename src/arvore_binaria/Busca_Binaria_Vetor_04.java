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
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class Busca_Binaria_Vetor_04 {

    public static void main(String[] args) {
        try {
            // Lê as palavras do arquivo Stopwords.txt e as armazena em um conjunto (para verificação eficiente)
            Set<String> stopwords = lerStopwords("./src/AB/Txt/Stop_Words.txt");

            // Lê o texto do arquivo Texto.txt e processa as palavras
            Map<String, Integer> palavrasFiltradas = processarTexto("./src/AB/Txt/Texto.txt", stopwords);

            // Ordena as palavras em ordem alfabética
            List<String> palavrasOrdenadas = new ArrayList<>(palavrasFiltradas.keySet());
            Collections.sort(palavrasOrdenadas);

            // Exibe as palavras em ordem alfabética com suas contagens
            System.out.println("Palavras filtradas em ordem alfabética com contagens:");
            for (String palavra : palavrasOrdenadas) {
                int contagem = palavrasFiltradas.get(palavra);
                System.out.println(palavra + ": " + contagem);
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler os arquivos: " + e.getMessage());
        }
    }

    public static Set<String> lerStopwords(String arquivoStopwords) throws IOException {
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoStopwords))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                stopwords.add(linha.toLowerCase()); // Converte para minúsculas e adiciona ao conjunto
            }
        }
        return stopwords;
    }

    public static Map<String, Integer> processarTexto(String arquivoTexto, Set<String> stopwords) throws IOException {
        Map<String, Integer> palavrasFiltradas = new TreeMap<>(); // Usamos TreeMap para manter a ordem alfabética
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivoTexto))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.split("\\s+"); // Divide a linha em palavras

                for (String palavra : palavras) {
                    // Converte para minúsculas e remove vírgulas, pontos e números
                    palavra = palavra.toLowerCase().replaceAll("[,.()0-9]", "");

                    // Verifica se a palavra não é uma stopword
                    if (!stopwords.contains(palavra) && !palavra.isEmpty()) {
                        // Atualiza a contagem da palavra no mapa
                        palavrasFiltradas.put(palavra, palavrasFiltradas.getOrDefault(palavra, 0) + 1);
                    }
                }
            }
        }
        return palavrasFiltradas;
    }
}
