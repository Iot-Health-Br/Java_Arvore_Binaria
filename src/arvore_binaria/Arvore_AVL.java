/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class AVLNode {
    String data;
    int count;
    int height;
    AVLNode left;
    AVLNode right;

    AVLNode(String data) {
        this.data = data;
        this.count = 1;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

class AVLTree {
    private AVLNode root;

    AVLTree() {
        root = null;
    }

    private int height(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return node.height;
    }

    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;
        }
        return height(node.left) - height(node.right);
    }

    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }

    private AVLNode insert(AVLNode node, String data) {
        if (node == null) {
            return new AVLNode(data);
        }

        int compareResult = data.compareTo(node.data);

        if (compareResult < 0) {
            node.left = insert(node.left, data);
        } else if (compareResult > 0) {
            node.right = insert(node.right, data);
        } else {
            node.count++;
            return node;
        }

        node.height = 1 + Math.max(height(node.left), height(node.right));

        int balanceFactor = getBalanceFactor(node);

        // Left Heavy
        if (balanceFactor > 1) {
            if (data.compareTo(node.left.data) < 0) {
                return rightRotate(node);
            } else {
                node.left = leftRotate(node.left);
                return rightRotate(node);
            }
        }

        // Right Heavy
        if (balanceFactor < -1) {
            if (data.compareTo(node.right.data) > 0) {
                return leftRotate(node);
            } else {
                node.right = rightRotate(node.right);
                return leftRotate(node);
            }
        }

        return node;
    }

    public void insert(String data) {
        root = insert(root, data);
    }

    public void inOrder() {
        inOrder(root);
    }

    private void inOrder(AVLNode node) {
        if (node == null) {
            return;
        }
        inOrder(node.left);
        System.out.println(node.data + " : " + node.count);
        inOrder(node.right);
    }
}

public class Arvore_AVL {
    public static void main(String[] args) {
        AVLTree avlTree = new AVLTree();
        long startTime = System.currentTimeMillis();

        try {
            // Ler o arquivo de texto
            BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir a linha em palavras
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remover pontuações e converter para minúsculas
                    //word = word.replaceAll("[,.!?()\\d\"]", "").toLowerCase();
                    word = word.replaceAll("[,\\.!?()\\d\\s\"]", "").toLowerCase();

                    // Verificar se a palavra não está na lista de palavras a serem ignoradas
                    if (!isStopWord(word)) {
                        avlTree.insert(word);
                    }
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Palavras e suas frequências:");
        avlTree.inOrder();
        System.out.println("Tempo de processamento: " + totalTime + " milissegundos");
    }

    private static boolean isStopWord(String word) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word)) {
                    reader.close();
                    return true;
                }
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
