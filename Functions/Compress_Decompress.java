import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Compress_Decompress {

    private static final int DATA_RANGE = 256;
    private Node result;
    private String str;
    public Compress_Decompress(String str){
        this.str=str;
    }
//compress
public String Compress()
{

    char[] inChars = this.str.toCharArray();
   final int[] freq = new int[DATA_RANGE];
        for (char inChar : inChars)
            freq[inChar]++;

// build Huffman Tree
final Node root = build_Huffman_Tree(freq);

final Map<Character,String> lookUpTable = buildTable(root);
result =root;
String encodedData= (generateEncodedData(str,lookUpTable));

String Data=writeToFile(encodedData);
this.str=Data;
return Data;

}
private  String generateEncodedData(final String data,final Map<Character, String> lookUpTable) {
    final StringBuilder sb = new StringBuilder();
    for(final char character:data.toCharArray()){
        sb.append(lookUpTable.get(character));
    }
    return sb.toString();

    }
 public Node build_Huffman_Tree(int[] freq) {
        // using priority queue
        PriorityQueue<Node> pqNodes = new PriorityQueue<>(256);

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

    private void buildTablehelper(Node node, String s, Map<Character, String> lookUpTable) {
        if(!node.is_Leaf()){
            buildTablehelper(node.getLeftNode(),s+'0',lookUpTable);
            buildTablehelper(node.getRightNode(),s+'1',lookUpTable);
        }else{
            lookUpTable.put(node.getCharacter(),s);
        }
    }
    //decompress
    public  String decompress(){
        String file_compressed=decode(this.str);
        final StringBuilder resultBuilder = new StringBuilder();
        Node current = result;
        int i=0;
        while(i<file_compressed.length()){
            while(!current.is_Leaf()){
                char bit = file_compressed.charAt(i);
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
            current =result;
        }

        return resultBuilder.toString();
}

    private  String writeToFile(String binary)  {

        StringBuilder dataOS=new StringBuilder();
        byte flag = 0;
        byte toByte = 0;
        byte count = 7;
        byte remainder = (byte) (binary.length() % 8);
        if (remainder != 0) {
            flag = (byte) (8 - remainder);
        }
        dataOS.append((char)(flag));
        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '1') {
                toByte |= (1 << count);
            }
            if (count == 0) {
//                System.out.println(toByte);
                dataOS.append((char) (toByte));
                toByte = 0;
                count = 8;
            }
            count--;
        }
        dataOS.append((char)(toByte));
        return dataOS.toString();
    }
    private String decode(String encode)  {


        StringBuilder decoded = new StringBuilder();

        char[] read = encode.toCharArray();

        char flag = read[0];
        for (int i = 1; i < read.length; i++) {
            decoded.append(charToBinaryString(read[i]));
        }
        if (flag != 0) {

            decoded.delete(decoded.length() - flag, decoded.length());
        }
        return decoded.toString();
    }
    private  String charToBinaryString(char c) {
        StringBuilder result = new StringBuilder();
        String binary = Integer.toBinaryString(c & 0xFF);
        for (int i = 0; i < (8 - binary.length()); i++) {
            result.append('0');
        }
        result.append(binary);
        return result.toString();
    }
   

}