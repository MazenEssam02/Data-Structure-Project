import java.util.ArrayList;
import java.util.Stack;
public class MainFiles {
    private ArrayList<String>data= new ArrayList<String>();
    private String StringData;

    private Stack<String> undo=new Stack<>();
    private Stack<String> redo=new Stack<>();
    private static final int DATA_RANGE = 256;
    public MainFiles(ArrayList<String> data){
        this.data=data;
        this.StringData=ListToString(data);
    }
    public String getStringData(){
        return this.StringData;
    }
    private String ListToString(ArrayList<String> s){
        return String.join("\n",s);
    }


    //----------------------------------------------------------

    //Detect Error in XML file and return Array of Objects of size 4 where:
    //r[0] is the Missed Open tag name
    //r[1] is the line number of the close tag of the missed open tag
    //r[2] is the missed close tag name
    //r[3] is the line number of the open tag of the missed close tag
    public  Object[] validation() {
        Object [] result=new Object[4];
        result[0]=-1;
        result[1]=-1;
        result[2]=-1;
        result[3]=-1;

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

        for(String da : this.data) {
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

    //Correct Errors in XML and return the corrected one in a String
    //----------------------------------------------------------
    public String correction(){
        undo.push(this.StringData);
        ArrayList<String> corrected= new ArrayList<String>();
        int OpenLine = 0;
        int CloseLine=0;
        String OTag="";
        String CTag="";
        while (OpenLine !=-1 || CloseLine!=-1){
            Object[] result = validation();
            OpenLine = (Integer) result[1];

            CloseLine = (Integer) result[3];
            if (OpenLine == -1 && CloseLine==-1) {
//                corrected=this.data;
                this.StringData=ListToString(this.data);
                return this.StringData ;

            }
            else{
                corrected.clear();
                int line=0;
                if(OpenLine!=-1 || CloseLine!=-1){


                    for (String dd:this.data) {

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
                    this.data=new ArrayList<>(corrected);

//                    System.out.println(corrected);
                }
            }
        }
        this.StringData=ListToString(corrected);

        return this.StringData;
    }
    //----------------------------------------------------------
    public String Minify()
    {
        undo.push(this.StringData);
        String s=this.StringData;
        String x = "";
        s=s.replaceAll(">\n",">");
        s=s.replaceAll("\n<","<");
        s=s.replaceAll("\n","");
        for(int i=0;i<s.length();i++){
            if(s.charAt(i)!=' '){
                x += s.charAt(i);
            }
        }
        this.StringData=x;

        return x;
    }


    //----------------------------------------------------------
    public  String Prettify(){
        undo.push(this.StringData);
        String file=this.StringData;
        StringBuilder file_after_format= new StringBuilder();

        int n=0;
//file is empty
//        if (file.trim().length()==0)
//        {
//            return "file is Empty";
//        }
        String[] lines = split_the_XML_file(file);

        for (int i =0 ; i<lines.length ; i++) {

            // check the line is empty

            if (lines[i].trim().length() == 0) {
                continue;
            }
            lines[i] = lines[i].trim();
            if (lines[i].startsWith("</")) {
                n--;
                String indent = generateIndentation(n);
                file_after_format.append(indent).append(lines[i]).append("\n");
            } else if (lines[i].startsWith("<")) {
                String indent = generateIndentation(n);
                file_after_format.append(indent).append(lines[i]).append("\n");
                n++;
            } else if (lines[i].startsWith("<?")) {
                file_after_format.append(lines[i]).append("\n");
            }
            // if the line is data
            else {
                String indent = generateIndentation(n);
                file_after_format.append(indent).append(lines[i]).append("\n");
            }
        }
        this.StringData=file_after_format.toString();

        return  file_after_format.toString();
    }
    private  String[] split_the_XML_file(String File){
        String[] lines;
        File =File.trim().replaceAll("<", "\n<").replaceAll(">", ">\n");
        lines = File.split("\n");
        return lines;
    }

    // function to generate Indentation;
    private String generateIndentation(int level)
    {
        StringBuilder indention = new StringBuilder();
        for (int i = 0; i < level; i++)
        {
            indention.append("  ");
        }
        return indention.toString();
    }

    public String Undo(){

        if (undo.empty()){
            return null;
        }
        else{
            redo.push(this.StringData);
            this.StringData=undo.peek();
            undo.pop();
            return this.StringData;
        }

    }
    public String Redo(){

        if (redo.empty()){

            return  null;
        }
        else{

            this.StringData=redo.peek();
            undo.push(redo.peek());
            redo.pop();
            return this.StringData;
        }

    }


}
