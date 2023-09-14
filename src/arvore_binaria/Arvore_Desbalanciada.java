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

class Node {
    String data;
    Node left;
    Node right;

    public Node(String data) {
        this.data = data;
        left = null;
        right = null;
    }
}

class BinarySearchTree {
    Node root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(String data) {
        root = insertRec(root, data);
    }

    private Node insertRec(Node root, String data) {
        if (root == null) {
            root = new Node(data);
            return root;
        }

        if (data.compareTo(root.data) < 0) {
            root.left = insertRec(root.left, data);
        } else if (data.compareTo(root.data) > 0) {
            root.right = insertRec(root.right, data);
        }

        return root;
    }

    public void printToFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            printToFile(root, writer, 0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void printToFile(Node root, PrintWriter writer, int level) {
        if (root != null) {
            printToFile(root.right, writer, level + 1);
            for (int i = 0; i < level; i++) {
                writer.print("  "); // Espaços para representar o nível na árvore
            }
            writer.println(root.data);
            printToFile(root.left, writer, level + 1);
        }
    }
}

public class Arvore_Desbalanciada {
    public static void main(String[] args) {
        try {
            // Leitura do arquivo "Stop.txt" e armazenamento das palavras em um conjunto
            Set<String> stopWords = new HashSet<>();
            BufferedReader stopReader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"));
            String stopLine;
            while ((stopLine = stopReader.readLine()) != null) {
                stopWords.add(stopLine.trim());
            }
            stopReader.close();

            // Leitura do arquivo de texto principal e inserção das palavras na árvore binária
            BinarySearchTree tree = new BinarySearchTree();
            BufferedReader mainReader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"));
            String mainLine;
            while ((mainLine = mainReader.readLine()) != null) {
                String[] words = mainLine.split("\\s+");
                for (String word : words) {
                    if (!stopWords.contains(word)) {
                        tree.insert(word.toLowerCase().replaceAll("[,.()0-9]", ""));
                        // Passa para minuscula, e substitui a pontuaçao que esta na  palavra
                    }
                }
            }
            mainReader.close();

            // Impressão da árvore binária em "Arvore.txt" no formato de árvore
            tree.printToFile("./src/AB/Txt/Arvore.txt");
            System.out.println("Árvore binária foi impressa em Arvore.txt.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
