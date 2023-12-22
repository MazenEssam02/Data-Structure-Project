package Compression;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Huffman {
    private static String path=" ";
    private static final int DATA_RANGE = 256;

public Huffman(String path) {
    this.path = path;
} 

public static void Compress(String str) throws IOException
{
    char[] inChars = str.toCharArray();
    int[] freq = new int[DATA_RANGE];
        for (char inChar : inChars)
            freq[inChar]++;

// build Huffman Tree
Node root = build_Huffman_Tree(freq);
// build_code_table_from_Huffman_Tree
final Map<Character,String> lookUpTable = buildLookUpTable(root);
file.writeBinaryToFile(generateEncodedData(str,lookUpTable),path);

}
private static String generateEncodedData(final String data,final Map<Character, String> lookUpTable) {
    final StringBuilder sb = new StringBuilder();
    for(final char character:data.toCharArray()){
        sb.append(lookUpTable.get(character));
    }
    return sb.toString();

    }
 private static Node build_Huffman_Tree(int[] freq) {
        // using priority queue
        PriorityQueue<Node> pqNodes = new PriorityQueue<>();

        for (char c = 0; c < DATA_RANGE; c++){
            if (freq[c] > 0)
                pqNodes.add(new Node(c, freq[c], null, null));
        }
        while (pqNodes.size() > 1) {
            Node left = pqNodes.poll();
            Node right = pqNodes.poll();
            Node parent = new Node('\0', left.freq + right.freq, left, right);
            pqNodes.add(parent);
        }
        return pqNodes.poll();
    }
    private static class Node  {

        private  char ch;
        private  int freq;
        private  Node left, right;

        Node(char ch, int freq, Node left, Node right) {
            this.ch = ch;
            this.freq = freq;
            this.left = left;
            this.right = right;
        }

         private boolean is_Leaf() {
        return ((this.left == null) && (this.right == null));
    }
    }
     private static Map<Character,String> buildLookUpTable(final Node root){
        final Map<Character,String> lookUpTable = new HashMap<>();
        buildLookUpTablehelper(root,"",lookUpTable);//helper method
        return lookUpTable;
    }

    private static void buildLookUpTablehelper(Node node, String s, Map<Character, String> lookUpTable) {
        if(!node.is_Leaf()){
            buildLookUpTablehelper(node.left,s+'0',lookUpTable);
            buildLookUpTablehelper(node.right,s+'1',lookUpTable);
        }else{
            lookUpTable.put(node.ch,s);
        }
    }

    }
