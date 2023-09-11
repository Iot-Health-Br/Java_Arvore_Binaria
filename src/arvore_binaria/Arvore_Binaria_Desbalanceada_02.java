/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Pattern;

/**
 *
 * @author igl_m
 */
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

class BinarySearchTree {
    private TreeNode root;

    public void insert(String word) {
        root = insertRec(root, word);
    }

    private TreeNode insertRec(TreeNode root, String word) {
        if (root == null) {
            root = new TreeNode(word);
            return root;
        }

        int comparison = word.compareTo(root.word);
        if (comparison < 0) {
            root.left = insertRec(root.left, word);
        } else if (comparison > 0) {
            root.right = insertRec(root.right, word);
        } else {
            root.frequency++;
        }

        return root;
    }

    public void inorder() {
        inorderRec(root);
    }

    private void inorderRec(TreeNode root) {
        if (root != null) {
            inorderRec(root.left);
            System.out.println(root.word + ": " + root.frequency);
            inorderRec(root.right);
        }
    }
}

public class Arvore_Binaria_Desbalanceada_02 {
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        long startTime = System.currentTimeMillis();

        try (BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+"); // Divide a linha em palavras
                for (String word : words) {
                    // Remover sinais de pontuação e números
                    word = word.replaceAll("[,.!?()\\d\"]", "").toLowerCase();
                    if (!word.isEmpty() && !isInStopList(word)) {
                        bst.insert(word);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Palavras e suas frequências no texto:");
        bst.inorder();
        System.out.println("Tempo de comparação das palavras: " + (endTime - startTime) + " ms");
    }

    private static boolean isInStopList(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().equalsIgnoreCase(word)) {
                    return true;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
