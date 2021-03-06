package br.pro.hashi.ensino.desagil.morse;

import java.util.HashMap;

public class MorseTree {

    private Node[] tree;

    private HashMap<String,Node> path = new HashMap<>();

    public Node[] generateTree(String[] cores){
        Node[] nodes = new Node[cores.length];
        for (int i = 0; i < cores.length;i++ ){
            nodes[i] = new Node(cores[i],null,null);
            path.put(nodes[i].getCore(),nodes[i]);
        }

        int maxLayer = 7;
        Node[][] temp = new Node[maxLayer][];
        int layer = 0;
        int count = 0;
        while(true){
            if(layer  == maxLayer){
                break;
            }
            temp[layer] = new Node[(int) Math.pow(2,layer)];
            for (int i = 0 ; i < (int) Math.pow(2,layer) ; i ++){
                temp[layer][i] = nodes[count];
                count++;
            }
            layer ++;
        }
        count = 0;
        for (int i = 0; i < maxLayer - 1; i++){
            for(int j = 0; j < temp[i].length;j ++){
                nodes[count].setLeft(temp[i + 1][j * 2]);
                nodes[count].setRight(temp[i + 1][j * 2 + 1]);
                count ++;
            }
        }
        this.tree = nodes;

        //coll way for setting how many layers a regular binary tree have, making the code generic
        int size = nodes.length + 1;
        size = Integer.highestOneBit(size);
        int powTwo = Integer.bitCount(~size); //could I use size -1? yes... I do.
        nodes[0].setLayer(powTwo);
        //E sim Ale, eu sei que eu podia resumir tudo em uma linha...

        return (nodes);
        
    }

    public Node[] getTree(){
        return this.tree;
    }

    public boolean[] getCode(String letter){
        return path.get(letter).getFullPath();
    }

    public Node getNode(String letter){
        return path.get(letter);
    }


    public String translate(boolean[] code) {

        Node node = tree[0];
        for (int i = 0; i < code.length; i++) {
            if(node != null) {
                node = code[i] ? node.getRight() : node.getLeft();
            }
        }
        if (node != null) {
            return node.getCore() != null ? node.getCore() : "blank";
        } else{
            return "null";
        }
    }

    public boolean[] getAdress(int index){
        return tree[index].getFullPath();
    }

}
