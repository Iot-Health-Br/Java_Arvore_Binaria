/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

class BinarySearchTree {
    static class Node {
        String word;
        int frequency;
        Node left, right;

        Node(String word) {
            this.word = word;
            this.frequency = 1;
            left = right = null;
        }
    }

    Node root;

    void insert(String word) {
        root = insertRec(root, word);
    }

    Node insertRec(Node root, String word) {
        if (root == null) {
            root = new Node(word);
            return root;
        }

        int compareResult = word.compareTo(root.word);
        if (compareResult < 0) {
            root.left = insertRec(root.left, word);
        } else if (compareResult > 0) {
            root.right = insertRec(root.right, word);
        } else {
            root.frequency++;
        }

        return root;
    }

    boolean search(String word) {
        return searchRec(root, word);
    }

    boolean searchRec(Node root, String word) {
        if (root == null) {
            return false;
        }

        int compareResult = word.compareTo(root.word);
        if (compareResult == 0) {
            return true;
        } else if (compareResult < 0) {
            return searchRec(root.left, word);
        } else {
            return searchRec(root.right, word);
        }
    }

    void inOrderTraversal() {
        inOrderTraversal(root);
    }

    void inOrderTraversal(Node root) {
        if (root != null) {
            inOrderTraversal(root.left);
            System.out.println(root.word + " : " + root.frequency);
            inOrderTraversal(root.right);
        }
    }
}

public class Busca_Binaria_Vetor {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        String fileName = "./src/AB/Txt/Texto.txt";
        String stopFileName = "./src/AB/Txt/Stop_Words.txt";

        BinarySearchTree bst = new BinarySearchTree();

        try {
            Set<String> stopwords = readStopwords(stopFileName);

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;

            while ((line = br.readLine()) != null) {
                line = line.toLowerCase(); // Converte a linha para minúsculas
                line = removePunctuation(line); // Remove a pontuação, mas mantém os acentos
                String[] words = line.split("\\s+"); // Divide a linha em palavras

                for (String word : words) {
                    if (!stopwords.contains(word) && !word.isEmpty()) {
                        bst.insert(word);
                    }
                }
            }
            br.close();

            System.out.println("Palavras e frequência:");
            bst.inOrderTraversal();

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Tempo de comparação das palavras: " + totalTime + " milissegundos");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Set<String> readStopwords(String fileName) throws IOException {
        Set<String> stopwords = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line.toLowerCase()); // Converte stopwords para minúsculas
            }
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de stopwords: " + e.getMessage());
        }

        return stopwords;
    }

    private static String removePunctuation(String text) {
        // Remove a pontuação, mas mantém os acentos
        return text.replaceAll("[^\\p{L}\\s]", "");
    }
}