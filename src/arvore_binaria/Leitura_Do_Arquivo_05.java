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
public class Leitura_Do_Arquivo_05 {

    public static void main(String[] args) {
        String arquivoTexto = "./src/AB/Txt/Texto.txt";
        String arquivoStop = "./src/AB/Txt/Stop_Words.txt";
        String arquivoResultado = "./src/AB/Txt/Resultado.txt"; // Nome do arquivo de resultado

        try {
            // Leitura do arquivo Stop.txt e criação de um conjunto de palavras para comparação
            Set<String> palavrasStop = lerArquivo(arquivoStop);

            // Leitura do arquivo texto.txt
            List<String> palavrasTexto = lerArquivoTexto(arquivoTexto);

            // Busca binária com vetor dinâmico
            List<String> palavrasOrdenadasVetor = new ArrayList<>();
            long startTimeVetor = System.nanoTime();
            int comparacoesVetor = inserirComBuscaBinaria(palavrasTexto, palavrasOrdenadasVetor, palavrasStop);
            long endTimeVetor = System.nanoTime();
            long tempoVetor = endTimeVetor - startTimeVetor;

            // Árvore binária de pesquisa sem balanceamento
            Set<String> palavrasOrdenadasBST = new TreeSet<>();
            long startTimeBST = System.nanoTime();
            int comparacoesBST = inserirComArvoreBST(palavrasTexto, palavrasOrdenadasBST, palavrasStop);
            long endTimeBST = System.nanoTime();
            long tempoBST = endTimeBST - startTimeBST;

            // Árvore binária de pesquisa com balanceamento (Árvore AVL)
            Set<String> palavrasOrdenadasAVL = new TreeSet<>();
            long startTimeAVL = System.nanoTime();
            int comparacoesAVL = inserirComArvoreAVL(palavrasTexto, palavrasOrdenadasAVL, palavrasStop);
            long endTimeAVL = System.nanoTime();
            long tempoAVL = endTimeAVL - startTimeAVL;

            // Gravação das palavras em ordem alfabética e suas frequências no arquivo de resultado
            FileWriter writer = new FileWriter(arquivoResultado);

            writer.write("Usando Busca Binária (Vetor Dinâmico):\n");
            writer.write("Tempo: " + tempoVetor + " ns\n");
            writer.write("Comparacoes: " + comparacoesVetor + "\n\n");

            writer.write("Usando Árvore Binária de Pesquisa (Sem Balanceamento):\n");
            writer.write("Tempo: " + tempoBST + " ns\n");
            writer.write("Comparacoes: " + comparacoesBST + "\n\n");

            writer.write("Usando Árvore Binária de Pesquisa com Balanceamento (Árvore AVL):\n");
            writer.write("Tempo: " + tempoAVL + " ns\n");
            writer.write("Comparacoes: " + comparacoesAVL + "\n\n");

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

    private static List<String> lerArquivoTexto(String arquivo) throws IOException {
        List<String> palavras = new ArrayList<>();
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

    private static int inserirComBuscaBinaria(List<String> palavrasTexto, List<String> palavrasOrdenadas, Set<String> palavrasStop) {
        int comparacoes = 0;
        for (String palavra : palavrasTexto) {
            if (!palavrasStop.contains(palavra)) {
                int index = buscaBinaria(palavrasOrdenadas, palavra);
                if (index < 0) {
                    int insertIndex = -index - 1;
                    palavrasOrdenadas.add(insertIndex, palavra);
                }
                comparacoes++;
            }
        }
        return comparacoes;
    }

    private static int buscaBinaria(List<String> lista, String palavra) {
        int esquerda = 0;
        int direita = lista.size() - 1;
        while (esquerda <= direita) {
            int meio = esquerda + (direita - esquerda) / 2;
            int comparacao = palavra.compareTo(lista.get(meio));
            if (comparacao == 0) {
                return meio;
            }
            if (comparacao < 0) {
                direita = meio - 1;
            } else {
                esquerda = meio + 1;
            }
        }
        return -(esquerda + 1);
    }

    private static int inserirComArvoreBST(List<String> palavrasTexto, Set<String> palavrasOrdenadas, Set<String> palavrasStop) {
        int comparacoes = 0;
        for (String palavra : palavrasTexto) {
            if (!palavrasStop.contains(palavra)) {
                if (!palavrasOrdenadas.contains(palavra)) {
                    palavrasOrdenadas.add(palavra);
                }
                comparacoes++;
            }
        }
        return comparacoes;
    }

    private static int inserirComArvoreAVL(List<String> palavrasTexto, Set<String> palavrasOrdenadas, Set<String> palavrasStop) {
        int comparacoes = 0;
        for (String palavra : palavrasTexto) {
            if (!palavrasStop.contains(palavra)) {
                if (!palavrasOrdenadas.contains(palavra)) {
                    palavrasOrdenadas.add(palavra);
                }
                comparacoes++;
            }
        }
        return comparacoes;
    }
}
