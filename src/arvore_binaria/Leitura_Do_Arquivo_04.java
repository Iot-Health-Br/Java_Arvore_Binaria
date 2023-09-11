/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.*;
import java.util.*;
/**
 *
 Leia um arquivo de texto.txt e compare as palavras com outro arquivo Stop.txt, se as palavras forem iguais não deverá ser impressa na saida!
Imprimir as demais palavras em ordem alfabetica e sua frequencia no texto.
* Remove Numeros do texto também

 */
public class Leitura_Do_Arquivo_04 {

    public static void main(String[] args) {
        String arquivoTexto = "./src/AB/Txt/Texto.txt";
        String arquivoStop = "./src/AB/Txt/Stop_Words.txt";
        String arquivoResultado = "./src/AB/Txt/Resultado.txt"; // Nome do arquivo de resultado

        try {
            // Leitura do arquivo Stop.txt e criação de um conjunto de palavras para comparação
            Set<String> palavrasStop = lerArquivo(arquivoStop);

            // Leitura do arquivo texto.txt e contagem das palavras não presentes no arquivo Stop.txt
            Map<String, Integer> frequenciaPalavras = new HashMap<>();
            Set<String> palavrasOrdenadas = new TreeSet<>();

            BufferedReader reader = new BufferedReader(new FileReader(arquivoTexto));
            String linha;

            while ((linha = reader.readLine()) != null) {
                String[] palavras = linha.split("\\s+");
                for (String palavra : palavras) {
                    palavra = prepararPalavra(palavra); // Remover espaços e pontuações e converter para minúsculas
                    if (!palavrasStop.contains(palavra) && !palavra.isEmpty()) {
                        int frequencia = frequenciaPalavras.getOrDefault(palavra, 0);
                        frequenciaPalavras.put(palavra, frequencia + 1);
                        palavrasOrdenadas.add(palavra);
                    }
                }
            }

            reader.close();

            // Gravação das palavras em ordem alfabética e suas frequências no arquivo de resultado
            FileWriter writer = new FileWriter(arquivoResultado);
            for (String palavra : palavrasOrdenadas) {
                int frequencia = frequenciaPalavras.get(palavra);
                writer.write(palavra + ": " + frequencia + "\n");
            }
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
                parte = prepararPalavra(parte); // Remover espaços e pontuações e converter para minúsculas
                if (!parte.isEmpty()) {
                    palavras.add(parte);
                }
            }
        }

        reader.close();
        return palavras;
    }

    private static String prepararPalavra(String palavra) {
        // Remove espaços em branco e sinais de pontuação, e converte para minúsculas
        //return palavra.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        return palavra.replaceAll("[^a-zA-Z]", "").toLowerCase();
    }
}
