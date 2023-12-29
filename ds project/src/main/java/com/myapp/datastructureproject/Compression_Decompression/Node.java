package com.myapp.datastructureproject.Compression_Decompression;

public class Node implements Comparable<Node> {

    private final char character;
    private final int frequency;
    private Node leftNode;
    private Node rightNode;

    public Node(final char character,final int frequency,final  Node leftNode,final  Node rightNode) {
        this.character = character;
        this.frequency = frequency;
        this.leftNode = leftNode;
        this.rightNode = rightNode;
    }

    public char getCharacter() {
        return character;
    }

    @Override
    public int compareTo(Node node) {

        final int freqComparison =Integer.compare(frequency,node.getFrequency());
        if(freqComparison!=0){
            return  freqComparison;
        }
            return Integer.compare(this.character,node.character);
    }

    public int getFrequency() {
        return frequency;
    }

    public Node getLeftNode() {
        return leftNode;
    }

    public Node getRightNode() {
        return rightNode;
    }

    public boolean is_Leaf(){
     return this.leftNode ==null && this.rightNode==null;
    }
}