/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package arvore_binaria;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

class AVLNode {
    // Que armazena o dado a ser inserido
    String data;
    // Variavel que armazena a repetição da palavra
    int count;
    // Variavel que armazena o peso do nó
    int height;
    // Referencia dos Nós filhos a esquerda e direita.
    AVLNode left;
    AVLNode right;

    //Construtor da Classe AVLNode
    AVLNode(String data) {
        //Atribuição das variaveis do construtor
        this.data = data;
        this.count = 1;
        this.height = 1;
        this.left = null;
        this.right = null;
    }
}

// Class Arvore Avl
class AVLTree {
    // Variavel que armazena a raiz da arvore
    private AVLNode root;

    //Metodo da Classe Arvore Avl
    AVLTree() {
        //Atribuição da variavel do construtor
        root = null;
    }
    
    // Metodo do peso da arvore
    private int height(AVLNode node) {
        // se não existir o nó, retorna 0
        if (node == null) {
            return 0;}
        // se existir retorna o peso do nó
        return node.height;
    }
    
    // Metodo de balenciamento da arvore
    private int getBalanceFactor(AVLNode node) {
        if (node == null) {
            return 0;}
        
        // Retorna o peso dos nós da esquerda menos o da direita.
        // Fator de balanceamento
        return height(node.left) - height(node.right);
    }
    
    // Metodo de Balanciamento a direita.
    private AVLNode rightRotate(AVLNode y) {
        AVLNode x = y.left;
        AVLNode T2 = x.right;

        x.right = y;
        y.left = T2;

        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        return x;
    }

    // Metodo de Balanciamento a esquerda.
    private AVLNode leftRotate(AVLNode x) {
        AVLNode y = x.right;
        AVLNode T2 = y.left;

        y.left = x;
        x.right = T2;

        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        return y;
    }
    
    // Metodo de Inserção e balanciamento de dados na arvore
    // Nó atual, dado a ser inserido
    private AVLNode insert(AVLNode node, String data) {
        // Se não existir o nó ele retorna com um novo dado para a posição
        // Armazena as informações no construtor
        if (node == null) {
            return new AVLNode(data);}
        
        // Compara os dados a serem inseridos com os dados do nó atual
        int compareResult = data.compareTo(node.data);

            // lexicograficamente anterior retorna um valor negativo e insere a esquerda
            if (compareResult < 0) {
                node.left = insert(node.left, data);} 
            
            // lexicograficamente posterior retorna um valor positivo a direita
            else if (compareResult > 0) {
                node.right = insert(node.right, data);} 
            
        // Se for Igual icrementa o contador
        else {
            node.count++;
            return node;}

        // Após a inserção, atualiza a altura do nó atual com base nas alturas de seus filhos:
        // Math.max retorna o maior dos dois valores fornecidos, 
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // Retorna o peso dos nós da esquerda menos o da direita.
        int balanceFactor = getBalanceFactor(node);

        // Left Heavy
        if (balanceFactor > 1) {
            if (data.compareTo(node.left.data) < 0) {
                return rightRotate(node);} 
            else {
                node.left = leftRotate(node.left);
                return rightRotate(node);}
        }

        // Right Heavy
        if (balanceFactor < -1) {
            if (data.compareTo(node.right.data) > 0) {
                return leftRotate(node);} 
            else {
                node.right = rightRotate(node.right);
                return leftRotate(node);}
        }
        // O nó atual retorna, se houve rotação na arvore o nó será diferente do nó de entrada
        return node;
    }

    // Metodo de inserção do dado na arvore
    public void insert(String data) {
        root = insert(root, data);}

    public void inOrder() {
        inOrder(root);}

    private void inOrder(AVLNode node) {
        if (node == null) {
            return;}
        inOrder(node.left);
        System.out.println(node.data + " : " + node.count);
        inOrder(node.right);
    }
}

public class Arvore_AVL {
    public static void main(String[] args) {
        
        //Estacia um novo ojeto no construtor AVLTree
        AVLTree avlTree = new AVLTree();
        
        // Inicializa o tempo
        long startTime = System.currentTimeMillis();

        try {
            // Ler o arquivo de texto, uma palavra por linha
            BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Texto.txt"));
            String line;
            while ((line = reader.readLine()) != null) {
                // Dividir a linha em palavras
                String[] words = line.split("\\s+");
                for (String word : words) {
                    // Remover pontuações e converter para minúsculas
                    //word = word.replaceAll("[,.!?()\\d\"]", "").toLowerCase();
                    word = word.replaceAll("[,\\.!?()\\d\\s\"]", "").toLowerCase();

                    // Verificar se a palavra não está na lista de palavras a serem ignoradas STOPWORDS
                    if (!isStopWord(word)) {
                        //Se não for stopwords insere
                        avlTree.insert(word);
                    }
                }
            }
            //Fecha a leitura
            reader.close();
        } 
        catch (IOException e) {
            e.printStackTrace();}
        
        //Finaliza o tempo e ver o tempo de execução
        long endTime = System.currentTimeMillis();
        long totalTime = endTime - startTime;

        System.out.println("Palavras e suas frequências:");
        avlTree.inOrder();
        System.out.println("Tempo de processamento: " + totalTime + " milissegundos");
    }

    // Classe de consulta de StopWord
    private static boolean isStopWord(String word) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("./src/AB/Txt/Stop_Words.txt"));
            String line;
            // Lê a linha quando for diferente de vazio
            while ((line = reader.readLine()) != null) {
                // Lê a linha ingnorando espação em branco e Maiuscula e Minuscula
                // Se a palvavra for igual a encontra no arquivo stopwords retorna verdadeiro
                // e finaliza o caso de leitura, acarrentando na não inserção da palavra na arvore
                if (line.trim().equalsIgnoreCase(word)) {
                    reader.close();
                    return true;}
            }
            reader.close();} 
        catch (IOException e) {
            e.printStackTrace();}
        
        // Caso contrario retorna falso e a palavra e inserida.
        return false;
    }
}
