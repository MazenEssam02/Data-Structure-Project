import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;

//Class to detect and correct XML File
public class ErrorHandler {
    //Read XML and return it in Array of String
    public static ArrayList<String> ReadXML(String path){
        try {
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String line;
            ArrayList<String> LinesArray = new ArrayList<String>();
            while((line=reader.readLine()) != null){
                LinesArray.add(line);
            }
            reader.close();
            return LinesArray;

        }
        catch(IOException c){
            c.printStackTrace();
        }
        return null;
    }

    //Detect Error in XML file and return Array of Objects of size 4 where:
    //r[0] is the Missed Open tag name
    //r[1] is the line number of the close tag of the missed open tag
    //r[2] is the missed close tag name
    //r[3] is the line number of the open tag of the missed close tag
    public static Object[] validation(ArrayList<String> data) {
        Object [] result=new Object[4];
        result[0]=-1;
        result[1]=-1;
        result[2]=-1;
        result[3]=-1;
        int object=-1;
        Stack<String> u = new Stack<String>();
        Stack<String> c = new Stack<String>();
        Stack<String> d = new Stack<String>();
        Stack<Integer> OpenLineNumber = new Stack<Integer>();
        Stack<Integer> tempLineNumber = new Stack<Integer>();
        Stack<Integer> CloseLineNumber = new Stack<Integer>();
        boolean closing = false;
        int line = 0;
        String close = "\0";
        char[] dataChars={};
        int m=0;
        for(String da : data) {
            dataChars = da.toCharArray();

        for (int f = 0; f < dataChars.length; f++) {
            String open = "";
            close = "";
            if (dataChars[f] == ' ') {

            }

            else if (dataChars[f] == '<') {





                int i = f + 1;
                if (dataChars[i] != '/') {
                    while (dataChars[i] != '>') {

                        open += dataChars[i];
                        i++;
                    }
                    closing = false;
                    OpenLineNumber.push(line);
                    d.push(open);

//
                }
                else if (dataChars[i] == '/') {
                    while (dataChars[i + 1] != '>') {

                        close += dataChars[i + 1];
                        i++;
                    }
                    closing = true;
                    c.push(close);
                    CloseLineNumber.push(line);
                }


            }

            if (closing && !(d.empty())) {
                if (!(c.peek().equals(d.peek()))) {
                    //if stack c top(closing tag) is not equal stack d top(opening tag)
                    while (!d.empty()) {
                        u.push(d.peek()); //push the top of stack d in stack u
                        tempLineNumber.push(OpenLineNumber.peek());
                        d.pop();
                        OpenLineNumber.pop();
                        if (!d.empty()) {
                            if (c.peek().equals(d.peek())) {
                                c.pop();
                                CloseLineNumber.pop();
                                d.pop();
                                OpenLineNumber.pop();
                                while (!u.empty()) {

//                                    System.out.println(u.peek() + " tag isn't closed at line "+ tempLineNumber.peek()+"\n");
                                    result[2]=u.peek();
                                    result[3]=tempLineNumber.peek();
                                    u.pop();
                                    tempLineNumber.pop();
                                }
                                break;
                            }
                        }
                    }
                    while (!u.empty()) {
                        d.push(u.peek());
                        OpenLineNumber.push(tempLineNumber.peek());
                        u.pop();
                        tempLineNumber.pop();
                    }
                    if (!c.empty()) {
//                        System.out.println(c.peek() + " tag has no open at line "+CloseLineNumber.peek()+"\n");
                        result[0]=c.peek();
                        result[1]=CloseLineNumber.peek();
                        c.pop();
                        CloseLineNumber.pop();
                    }
                } else {
                    d.pop();
                    OpenLineNumber.pop();
                    c.pop();
                    CloseLineNumber.pop();
                }
                closing = false;

            }

        }
            line++;
        }


        while (!d.empty()) {
//            System.out.println(d.peek() + " tag isn't closed at line "+ OpenLineNumber.peek()+"\n");
            result[2]=d.peek();
            result[3]=OpenLineNumber.peek();
            d.pop();
            OpenLineNumber.pop();
        }
        while (!c.empty()) {
//            System.out.println(c.peek() + " tag has no open at line "+CloseLineNumber.peek()+"\n");
            result[0]=c.peek();
            result[1]=CloseLineNumber.peek();
            c.pop();
            CloseLineNumber.pop();
        }
        return result;

    }

    //Correct Errors in XML and return the corrected one in Array of String

    public ArrayList<String> correcrtion(ArrayList<String> data){
        ArrayList<String> corrected= new ArrayList<String>();
        int OpenLine = 0;
        int CloseLine=0;
        String OTag="";
        String CTag="";
        while (OpenLine !=-1 || CloseLine!=-1){
            Object[] result = validation(data);
            OpenLine = (Integer) result[1];

            CloseLine = (Integer) result[3];
            if (OpenLine == -1 && CloseLine==-1) {
                corrected=data;
                return corrected;

            }
            else{
                corrected.clear();
                int line=0;
                if(OpenLine!=-1 || CloseLine!=-1){


                    for (String dd:data) {

//                        if ((DataChar[f] == '\n') ){line++;}
                        if(OpenLine==line) {
                            OTag=(String) result[0];
                            corrected.add(("<"+OTag+">"));
                            corrected.add(dd) ;
                        } else if (CloseLine==line) {
                            CTag=(String) result[2];
                            corrected.add(dd) ;
                            corrected.add(("</"+CTag+">"));
//                            System.out.println(corrected);
                        } else {
                            corrected.add(dd) ;
                        }
                            line++;
                    }
                    data=new ArrayList<>(corrected);
//                    System.out.println(corrected);
                }
            }
        }

        return corrected;
    }

    // Test Code
    public static void main(String[] args) {
        ErrorHandler obj=new ErrorHandler();
        ArrayList<String> data = obj.ReadXML("sample.xml");
        Object [] resulttest=new Object[4];
        Object [] resulttest2=new Object[4];
       resulttest2= obj.validation(data);
        System.out.println(resulttest2[0]+" "+resulttest2[1] + " "+ resulttest2[2]+" "+resulttest2[3]);
        ArrayList<String>s=obj.correcrtion(data);
        resulttest= obj.validation(s);
        System.out.println(resulttest[0]+" "+resulttest[1]+" "+resulttest[2]+" "+resulttest[3]);
        System.out.println(s);


    }
}