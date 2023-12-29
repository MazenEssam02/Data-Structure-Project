public class MapF {
    private Node head;
     private int Size ;

     public MapF(){this.Size=0;}
    public void put(String key, int value) {
        Node newNode = new Node(key, value);
        newNode.next = head;
        head = newNode;
        Size++;
    }
    public int get(String key) {
        Node current = head;
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return -1; // Indicate key not found
    }
    public int getSize() {

        return Size;
    }
}
