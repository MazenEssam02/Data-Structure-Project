import java.util.ArrayList;

class Post {
    String body;
    ArrayList<String> topics = new ArrayList<String>();
    public void PrintPost() {
        System.out.println("Body:" + body);
        System.out.println("Topics: " + topics);
    }
}
