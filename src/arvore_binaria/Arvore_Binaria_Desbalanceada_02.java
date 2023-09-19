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

//Classe no da Arore
class TreeNode {
    //Variavel que recebe a palavra a ser inserida
    String word;
    // Variavel que recebe a Frequencia da Palavra na Arvore
    int frequency;
    // Nó filho a esquerdo e a direita
    TreeNode left;
    TreeNode right;

    // Construtor da Classe
    public TreeNode(String word) {
        //Atribuição das variaveis do construtor
        this.word = word;
        this.frequency = 1;
        this.left = null;
        this.right = null;
    }
}

// Classe de busca binaria
class BinarySearchTree {
    // Membro da classe inicia pela busca pela raiz
    private TreeNode root;

    // Metodo de inserção
    public void insert(String word) {
        //Chama o metodo auxiliar
        root = insertRec(root, word);
    }
    // 
    private TreeNode insertRec(TreeNode root, String word) {
        if (root == null) {
            // Se não existir o nó ele retorna com um novo dado para a posição
            // Armazena as informações no construtor
            root = new TreeNode(word);
            // Retorna o Nó
            return root;}
        
        // Compara os dados a serem inseridos com os dados do nó atual
        int comparison = word.compareTo(root.word);
        
            // lexicograficamente anterior retorna um valor negativo e insere a esquerda
            if (comparison < 0) {
                root.left = insertRec(root.left, word);} 
        
            // lexicograficamente posterior retorna um valor positivo a direita
            else if (comparison > 0) {
                root.right = insertRec(root.right, word);} 
        
            // Se for Igual icrementa o contador de palavra repetida
        else {
            root.frequency++;}

        return root;
    }

    public void inorder() {
        // chama o metodo recurssivo
        inorderRec(root);
    }

    /* O metodo percorre toda a subarvore a esquerda e confere se tem sub arvore a direita,
    Caracteristica do metodo travessia in-order
    */
    private void inorderRec(TreeNode root) {
        // Função inicia se não for vazio
        if (root != null) {
            // Verifica a subárvore esquerda do nó atual
            inorderRec(root.left);
            
            // Uma vez que toda a subárvore esquerda do nó atual foi visitada imprime a palavra e sua frequência.
            System.out.println(root.word + ": " + root.frequency);
            
            // Verifica a subárvore direita do nó atual
            inorderRec(root.right);
        }
    }
    /*
       A travessia "in-order" em uma árvore binária de busca é que ela visita os nós 
    do menor para o maior valor(assumindo que a árvore mantenha a propriedade de uma 
    árvore binária de busca).
    */
}

public class Arvore_Binaria_Desbalanceada_02 {
    // Metodo de execução automatico
    public static void main(String[] args) {
        
        // Inicia um novo objeto de busca binaria
        BinarySearchTree bst = new BinarySearchTree();
        
        // Inicia o tempo
        long startTime = System.currentTimeMillis();

        // Lê o arquivo TXT do texto.
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"))) {
            String line;
            // Equanto a linha for diferente de vazio
            while ((line = reader.readLine()) != null) {
                // Divide a linha em palavras usando espaços como delimitadores (split("\\s+")).
                String[] words = line.split("\\s+"); 

                /*  cada palavra dentro do array words, 
                a palavra atual será armazenada na variável word, 
                e o bloco de código dentro do loop será executado para essa palavra.
                */                  
                for (String word : words) {
                    // Remover sinais de pontuação, números e espaço em branco e deixar em minusculo
                    word = word.replaceAll("[,.!?()\\d\"]", "").toLowerCase().trim();
                    
                    //Verifica se não está vazia ou se é uma stop words.
                    if (!word.isEmpty() && !isInStopList(word)) {
                        //Insere a palavra na arvore.
                        bst.insert(word);}
                }
            }
        } 
        catch (IOException e) {
            e.printStackTrace();}

        long endTime = System.currentTimeMillis();
        System.out.println("Palavras e suas frequências no texto:");
        bst.inorder();
        System.out.println("Tempo de comparação das palavras: " + (endTime - startTime) + " ms");
    }

    // Metodo de leitura de Stopwords
    private static boolean isInStopList(String word) {
        try (BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"))) {
            String line;
            //Lê uma palavra por linha se não estiver vazio
            while ((line = reader.readLine()) != null) {
                //Ignora espaço em branco e verifica se a palavra e igual
                if (line.trim().equalsIgnoreCase(word)) {
                    return true;}
            }
        } 
        catch (IOException e) {
            e.printStackTrace();}
        return false;
    }       
}
