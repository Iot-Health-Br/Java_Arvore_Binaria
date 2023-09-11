/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.*;
import java.util.*;
/**
 *
 * @author igl_m
 */
public class Leitura_Do_Arquivo_06 {

    public static void main(String[] args) {
        String arquivoTexto = "./src/AB/Txt/Texto.txt";
        String arquivoStop = "./src/AB/Txt/Stop_Words.txt";
        String arquivoResultado = "./src/AB/Txt/Resultado.txt"; // Nome do arquivo de resultado

        try {
            // Leitura do arquivo Stop.txt e criação de um conjunto de palavras para comparação
            Set<String> palavrasStop = lerArquivo(arquivoStop);

            // Inicialização das estruturas de busca e inserção
            List<String> palavrasOrdenadasVetor = new ArrayList<>();
            Set<String> palavrasOrdenadasBST = new TreeSet<>();
            Set<String> palavrasOrdenadasAVL = new TreeSet<>();

            // Leitura do arquivo texto.txt
            BufferedReader reader = new BufferedReader(new FileReader(arquivoTexto));
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = prepararPalavra(palavra); // Remover espaços, pontuações, números e converter para minúsculas
                    if (!palavra.isEmpty() && !palavrasStop.contains(palavra)) {
                        // Inserção nas estruturas de busca e inserção apenas se a palavra não estiver no arquivo Stop.txt
                        inserirComBuscaBinaria(palavra, palavrasOrdenadasVetor);
                        inserirComArvoreBST(palavra, palavrasOrdenadasBST);
                        inserirComArvoreAVL(palavra, palavrasOrdenadasAVL);
                    }
                }
            }

            reader.close();

            // Gravação das palavras em ordem alfabética e suas frequências no arquivo de resultado
            FileWriter writer = new FileWriter(arquivoResultado);

            writer.write("Usando Busca Binária (Vetor Dinâmico):\n");
            escreverPalavrasOrdenadas(palavrasOrdenadasVetor, writer);
            writer.write("\n");

            writer.write("Usando Árvore Binária de Pesquisa (Sem Balanceamento):\n");
            escreverPalavrasOrdenadas(palavrasOrdenadasBST, writer);
            writer.write("\n");

            writer.write("Usando Árvore Binária de Pesquisa com Balanceamento (Árvore AVL):\n");
            escreverPalavrasOrdenadas(palavrasOrdenadasAVL, writer);
            writer.write("\n");

            writer.close();

            System.out.println("Resultado salvo em " + arquivoResultado);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> lerArquivo(String arquivo) throws IOException {
        Set<String> palavras = new TreeSet<>();
        BufferedReader reader = new BufferedReader(new FileReader(arquivo));
        String linha;

        while ((linha = reader.readLine()) != null) {
            String[] partes = linha.split("\\s+");
            for (String parte : partes) {
                parte = prepararPalavra(parte); // Remover espaços, pontuações, números e converter para minúsculas
                if (!parte.isEmpty()) {
                    palavras.add(parte);
                }
            }
        }

        reader.close();
        return palavras;
    }

    private static String prepararPalavra(String palavra) {
        // Remove espaços em branco, sinais de pontuação, números e converte para minúsculas
        return palavra.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }

    private static void inserirComBuscaBinaria(String palavra, List<String> palavrasOrdenadas) {

    int low = 0;
    int high = palavrasOrdenadas.size() - 1;

    while (low <= high) {
        int mid = (low + high) / 2;
        String midWord = palavrasOrdenadas.get(mid);

        int comparison = palavra.compareTo(midWord);

        if (comparison < 0) {
            high = mid - 1;
        } else if (comparison > 0) {
            low = mid + 1;
        } else {
            // Word already exists in the list, no need to insert it again.
            return;
        }
    }
    // Insert the word at the correct position.
    palavrasOrdenadas.add(low, palavra);
    }

    private static void inserirComArvoreBST(String palavra, Set<String> palavrasOrdenadas) {
        // Implemente a inserção em uma árvore binária de pesquisa sem balanceamento aqui
    }

    private static void inserirComArvoreAVL(String palavra, Set<String> palavrasOrdenadas) {
        // Implemente a inserção em uma árvore binária de pesquisa com balanceamento (Árvore AVL) aqui
    }

    private static void escreverPalavrasOrdenadas(Collection<String> palavrasOrdenadas, FileWriter writer) throws IOException {
        for (String palavra : palavrasOrdenadas) {
            writer.write(palavra + "\n");
        }
    }
}
