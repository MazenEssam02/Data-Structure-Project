import java.util.ArrayList;

class User {
    String ID;
    String name;
    ArrayList<Post> Posts = new ArrayList<Post>();
    ArrayList<String> Followers = new ArrayList<String>();

    @Override
    public String toString() {
        return this.ID;
    }
    public void print() {
        System.out.println("User ID is: " + ID);
        System.out.println("User Name is " + name);
        System.out.println("Number of followers: " + Followers);

    }
    public void printdata() {
        System.out.println("User ID is: " + ID);
        System.out.println("User Name is " + name);
    }
}
