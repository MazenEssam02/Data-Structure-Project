import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Minify {
    
    Minify() throws FileNotFoundException {

    }

    public static String to_Minify(String s)
    {
        String x = "";
        s=s.replaceAll(">\n",">");
        s=s.replaceAll("\n<","<");
        s=s.replaceAll("\n","");
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                x += s.charAt(i);
            }
        }
        return x;
    }
public static void main(String[]args){
        String d="<users>\n" +
        "<user>\n" +
          "<id>\n" +
            "1\n" +
          "</id>\n" +
          "<name>\n" +
            "user1\n" +
          "</name>\n" +
          "<posts>\n" +
            "<post>\n" +
              "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.\n" +
            "</post>\n" +
            "<post>\n" +
              "Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.\n" +
            "</post>\n" +
          "</posts>\n" +
          "<followers>\n" +
            "<follower>\n" +
              "<id>\n" +
                "2\n" +
             "</id>\n" +
            "</follower>\n" +
            "<follower>\n" +
              "<id>\n" +
                "4\n" +
              "</id>\n" +
            "</follower>\n" +
            "</followers>\n" +
        "</user>\n" +
      "</users>\n";
       System.out.println(to_Minify(d));
}

}