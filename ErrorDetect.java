import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

public class ErrorDetect {

    public static String ReadXML(String path) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            StringBuilder line=new StringBuilder();
            ArrayList<String> LinesArray = new ArrayList<String>();
            String sline;
            while ((sline = reader.readLine()) != null) {
                line.append((sline));
            }
            reader.close();
            return line.toString();

        } catch (IOException c) {
            c.printStackTrace();
        }
        return null;
    }

    public static void validation(String data) {

        Stack<String> u = new Stack<String>();
        Stack<String> c = new Stack<String>();
        Stack<String> d = new Stack<String>();
        boolean closing = false;
        String close = "\0";
        char[] dataChars={};
        int m=0;
        dataChars=data.toCharArray();



        for (int f = 0; f < dataChars.length; f++) {
            String open = "";
            close = "";
            if (dataChars[f] == ' ') {

            } else if (dataChars[f] == '<') {

//                    LineNumber.add(line);



                int i = f + 1;
                if (dataChars[i] != '/') {
                    while (dataChars[i] != '>') {

                        open += dataChars[i];
                        i++;
                    }
                    closing = false;
                    d.push(open);
//
                } else if (dataChars[i] == '/') {
                    while (dataChars[i + 1] != '>') {

                        close += dataChars[i + 1];
                        i++;
                    }
                    closing = true;
                    c.push(close);
                }


            }

            if (closing && !(d.empty())) {
                if (!(c.peek().equals(d.peek()))) {
                    //if stack c top(closing tag) is not equal stack d top(opening tag)
                    while (!d.empty()) {
                        u.push(d.peek()); //push the top of stack d in stack u
                        d.pop();
                        if (!d.empty()) {
                            if (c.peek().equals(d.peek())) {
                                c.pop();
                                d.pop();
                                while (!u.empty()) {
                                    System.out.println(u.peek() + " tag isn't closed \n");
                                    u.pop();
                                }
                                break;
                            }
                        }
                    }
                    while (!u.empty()) {
                        d.push(u.peek());
                        u.pop();
                    }
                    if (!c.empty()) {
                        System.out.println(c.peek() + " tag has no open");
                        c.pop();
                    }
                } else {
                    d.pop();
                    c.pop();
                }
                closing = false;

            }
        }


        while (!d.empty()) {
            System.out.println(d.peek() + " tag isn't closed \n");
            d.pop();
        }
        while (!c.empty()) {
            System.out.println(c.peek() + " tag has no open \n");
            c.pop();
        }

    }
    // Test Code
    public static void main(String[] args) {
        ErrorDetect obj=new ErrorDetect();
        String data = obj.ReadXML("sample.xml");
//
        obj.validation(data);


    }
}

