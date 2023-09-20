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

// Classe do nó da arvore
class BinarySearchTree {
    static class Node {
        //Variavel que recebe a palavra a ser inserida
        String word;
        //Variavel que a frequencia da palavra a ser inserida
        int frequency;
        //Filho do nó a esquerda e a direita
        Node left, right;
        
        // Construtor da Classe
        Node(String word) {
            //Atribuição das variaveis do construtor
            this.word = word;
            this.frequency = 1;
            left = right = null;
        }
    }
    // Membro da classe inicia pela busca pela raiz
    Node root;

    // Metodo de inserção
    void insert(String word) {
        //Chama o metodo auxiliar
        root = insertRec(root, word);
    }

    Node insertRec(Node root, String word) {
        // Se não existir o nó ele retorna com um novo dado para a posição
        if (root == null) {
            // Armazena as informações no construtor
            root = new Node(word);
            // Retorna o Nó
            return root;}
        
        // Compara os dados a serem inseridos com os dados do nó atual
        int compareResult = word.compareTo(root.word);
        
        // lexicograficamente anterior retorna um valor negativo e insere a esquerda
        if (compareResult < 0) {
            root.left = insertRec(root.left, word);} 
        
        // lexicograficamente posterior retorna um valor positivo a direita
        else if (compareResult > 0) {
            root.right = insertRec(root.right, word);} 
        
        // Se for Igual icrementa o contador de palavra repetida
        else {
            root.frequency++;}

        return root;
    }
    //Metodo de pesquisa retorna True and False
    boolean search(String word) {
        // Chama um sub-metodo para pesquisar o nó atual e a palavra.
        return searchRec(root, word);
    }

    boolean searchRec(Node root, String word) {
        // Verifica se o Nó esta vazio
        if (root == null) {
            return false;
        }
        
        // Compara os dados a serem inseridos com os dados do nó atual
        int compareResult = word.compareTo(root.word);
            
            // Se retona 0 encontramos a palavra 
            if (compareResult == 0) {
                return true;} 
            
                // lexicograficamente anterior retorna um valor negativo e avança para esquerda
                else if (compareResult < 0) {
                    return searchRec(root.left, word);} 
            
            // lexicograficamente posterior retorna um valor positivo e avança para direita
            else {
                return searchRec(root.right, word);}
    }
    
    void inOrderTraversal() {
        // chama o metodo recurssivo
        inOrderTraversal(root);
    }

    /* O metodo percorre toda a subarvore a esquerda e confere se tem sub arvore a direita,
    Caracteristica do metodo travessia in-order
    */    
    void inOrderTraversal(Node root) {
        if (root != null) {
            // Verifica a subárvore esquerda do nó atual
            inOrderTraversal(root.left);
            // Uma vez que toda a subárvore esquerda do nó atual foi visitada imprime a palavra e sua frequência.
            System.out.println(root.word + " : " + root.frequency);
            // Verifica a subárvore direita do nó atual
            inOrderTraversal(root.right);}
    }
    /*
       A travessia "in-order" em uma árvore binária de busca é que ela visita os nós 
    do menor para o maior valor(assumindo que a árvore mantenha a propriedade de uma 
    árvore binária de busca).
    */
}

public class Busca_Binaria_Vetor {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        // Armazena o caminho do diretorio dos arquivos
        String fileName = "./src/AB/Txt/Texto.txt";
        String stopFileName = "./src/AB/Txt/Stop_Words.txt";

        // Instancio um novo objeto de pesquisa binaria
        BinarySearchTree bst = new BinarySearchTree();

        try {
            // Cria uma lista das Stop words
            Set<String> stopwords = readStopwords(stopFileName);

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            
            // Equanto a linha for diferente de vazio
            while ((line = br.readLine()) != null) {
                // Converte a linha para minúsculas
                line = line.toLowerCase(); 
                
                // Remove a pontuação, mas mantém os acentos
                line = removePunctuation(line); 
                
                // Divide a linha em palavras usando espaços como delimitadores (split("\\s+")).
                String[] words = line.split("\\s+");

                /*  cada palavra dentro do array words, 
                a palavra atual será armazenada na variável word, 
                e o bloco de código dentro do loop será executado para essa palavra.
                */     
                for (String word : words) {            
                    // Remova espaços em branco extras
                    word = word.trim(); 
                    if (!word.isEmpty() && !stopwords.contains(word)) {
                        // Verifica se a palavra não é uma stopword
                        bst.insert(word);}
                }            
            }
            br.close();

            System.out.println("Palavras e frequência:");
            bst.inOrderTraversal();

            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Tempo de comparação das palavras: " + totalTime + " milissegundos");
        } 
        catch (IOException e) {
            e.printStackTrace();}
    }

    private static Set<String> readStopwords(String fileName) throws IOException {
        Set<String> stopwords = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                stopwords.add(line.toLowerCase().trim()); // Converte stopwords para minúsculas
            }
        } 
        catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de stopwords: " + e.getMessage());}

        return stopwords;
    }

    private static String removePunctuation(String text) {
        // Remove a pontuação, mas mantém os acentos
        return text.replaceAll("[,.()0-9]", "");
    }
}