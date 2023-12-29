package com.myapp.datastructureproject.Compression_Decompression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    private static String path=" ";
    private static final int DATA_RANGE = 256;
public Huffman(){}

public Huffman(String path) {
    this.path = path;
} 
//compress

public Node Compress(final String str) throws IOException 
{
    char[] inChars = str.toCharArray();
   final int[] freq = new int[DATA_RANGE];
        for (char inChar : inChars)
            freq[inChar]++;

// build Huffman Tree
final Node root = build_Huffman_Tree(freq);

final Map<Character,String> lookUpTable = buildTable(root);
File.writeToFile(generateEncodedData(str,lookUpTable),path);
return root;

}
private static String generateEncodedData(final String data,final Map<Character, String> lookUpTable) {
    final StringBuilder sb = new StringBuilder();
    for(final char character:data.toCharArray()){
        sb.append(lookUpTable.get(character));
    }
    return sb.toString();

    }
 public static Node build_Huffman_Tree(int[] freq) {
        // using priority queue
        PriorityQueue<Node> pqNodes = new PriorityQueue<>(256,new COMPARE());

        for (char c = 0; c < DATA_RANGE; c++){
            if (freq[c] > 0)
                pqNodes.add(new Node(c, freq[c], null, null));
        }
        while (pqNodes.size() > 1) {
            final Node left = pqNodes.poll();
            final Node right = pqNodes.poll();
            final Node parent = new Node('\0', (left.getFrequency() + right.getFrequency()), left, right);
            pqNodes.add(parent);
        }
        return pqNodes.poll();
    }
    
     public  Map<Character,String> buildTable(final Node root){
        final Map<Character,String> lookUpTable = new HashMap<>();
        buildTablehelper(root,"",lookUpTable);
        return lookUpTable;
    }

    private static void buildTablehelper(Node node, String s, Map<Character, String> lookUpTable) {
        if(!node.is_Leaf()){
            buildTablehelper(node.getLeftNode(),s+'0',lookUpTable);
            buildTablehelper(node.getRightNode(),s+'1',lookUpTable);
        }else{
            lookUpTable.put(node.getCharacter(),s);
        }
    }
    //decompress
    public  String decompress(final Node result,String path_file_compressed) throws IOException {



        String read = File.readFromFile(path_file_compressed);


        final StringBuilder resultBuilder = new StringBuilder();
        Node current = result;
        int i=0;
        while(i<read.length()){
            while(!current.is_Leaf()){
                char bit = read.charAt(i);
                if(bit=='1'){
                    current=current.getRightNode();
                }else if(bit=='0'){
                    current=current.getLeftNode();
                }
                else{
                    throw new IllegalArgumentException("invalid bit"+bit);
                }
                i++;
            }
            resultBuilder.append(current.getCharacter());
            current = result;
        }

        return resultBuilder.toString();
}

   

}