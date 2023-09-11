/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package arvore_binaria;
import java.io.*;
import java.util.*;
import java.util.regex.*;
/**
 *
 * @author igl_m
 */

// Classe para representar um nó de uma árvore binária de pesquisa (sem balanceamento)
class TreeNode {
    String word;
    int frequency;
    TreeNode left;
    TreeNode right;

    public TreeNode(String word) {
        this.word = word;
        this.frequency = 1;
        this.left = null;
        this.right = null;
    }
}

// Classe para representar uma árvore AVL
class AVLNode {
    String word;
    int frequency;
    int height;
    AVLNode left;
    AVLNode right;

    public AVLNode(String word) {
        this.word = word;
        this.frequency = 1;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

class AVLTree {
    AVLNode root;

    // Função para obter a altura de um nó
    private int height(AVLNode node) {
        if (node == null) return 0;
        return node.height;
    }

    // Função para calcular o fator de equilíbrio de um nó
    private int getBalance(AVLNode node) {
        if (node == null) return 0;
        return height(node.left) - height(node.right);
    }

    // Função para atualizar a altura de um nó
    private void updateHeight(AVLNode node) {
        if (node != null)
            node.height = 1 + Math.max(height(node.left), height(node.right));
    }

    // Função para realizar uma rotação à direita em um nó
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        updateHeight(y);
        updateHeight(x);

        return x;
    }

    // Função para realizar uma rotação à esquerda em um nó
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        updateHeight(x);
        updateHeight(y);

        return y;
    }

    // Função para inserir uma palavra na árvore AVL
    public AVLNode insert(AVLNode node, String word) {
        if (node == null) return new AVLNode(word);

        int compareResult = word.compareTo(node.word);

        if (compareResult < 0)
            node.left = insert(node.left, word);
        else if (compareResult > 0)
            node.right = insert(node.right, word);
        else
            node.frequency++;

        updateHeight(node);

        int balance = getBalance(node);

        // Casos de desequilíbrio

        // Esquerda-Esquerda
        if (balance > 1 && word.compareTo(node.left.word) < 0)
            return rightRotate(node);

        // Direita-Direita
        if (balance < -1 && word.compareTo(node.right.word) > 0)
            return leftRotate(node);

        // Esquerda-Direita
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Direita-Esquerda
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Função para inserir uma palavra na árvore AVL
    public void insert(String word) {
        root = insert(root, word);
    }

    // Função para fazer uma travessia em ordem da árvore e imprimir as palavras
    public void inorderTraversal() {
        inorderTraversal(root);
    }

    private void inorderTraversal(AVLNode node) {
        if (node != null) {
            inorderTraversal(node.left);
            System.out.println(node.word + ": " + node.frequency);
            inorderTraversal(node.right);
        }
    }
}

public class Arvore_Binaria {
    public static void main(String[] args) {
        // Leitura das stopwords
        Set<String> stopwords = new HashSet<>();
        try (BufferedReader stopWordReader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"))) {
            String line;
            while ((line = stopWordReader.readLine()) != null) {
                stopwords.add(line.toLowerCase());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Leitura do arquivo Texto.txt e processamento das palavras
        AVLTree avlTree = new AVLTree();
        List<String> words = new ArrayList<>();

        try (BufferedReader textReader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"))) {
            String line;
            Pattern pattern = Pattern.compile("\\b\\w+\\b");
            while ((line = textReader.readLine()) != null) {
                Matcher matcher = pattern.matcher(line.toLowerCase());
                while (matcher.find()) {
                    String word = matcher.group();
                    if (!stopwords.contains(word)) {
                        avlTree.insert(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Imprimir palavras e frequências em ordem alfabética
        avlTree.inorderTraversal();
    }
}
