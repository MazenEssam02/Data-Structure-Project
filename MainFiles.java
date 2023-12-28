import java.util.ArrayList;
import java.util.Stack;
public class MainFiles {
    private ArrayList<String>data;
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

        Stack<String> u = new Stack<>();
        Stack<String> c = new Stack<>();
        Stack<String> d = new Stack<>();
        Stack<Integer> OpenLineNumber = new Stack<>();
        Stack<Integer> tempLineNumber = new Stack<>();
        Stack<Integer> CloseLineNumber = new Stack<>();
        boolean closing = false;
        int line = 0;
        String close = "\0";
        char[] dataChars={};

        for(String da : this.data) {
            dataChars = da.toCharArray();

            for (int f = 0; f < dataChars.length; f++) {
                String open = "";
                close = "";
                if (dataChars[f] == ' ') {}

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


    //----------------------------------------------------------
    //Correct Errors in XML and return the corrected one in a String
    public String correction(){
        undo.push(this.StringData);
        ArrayList<String> corrected= new ArrayList<>();
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

    // XML to JSON Converter
    //----------------------------------------------------------
    public String xmlToJson() {

        String s =Minify();

        String json = "{";
        String w, word = "";
        //i for xml length, j for json length, x for repeated tags
        int i = 0, j = 1, x = 0;
        //tag for opening tags, but they are popped when they are closed,   brackets for({,])
        Stack<String> stack_open_tags = new Stack<>(), tag = new Stack<>(), brackets = new Stack<>();
        Stack<Boolean> opening_tag = new Stack<>(); // true if opening tag,and false if closing
        //bracket holds last printed bracket
        String last_closed_tag = "", bracket = "";
        //saves value of x (repeated tags)
        Stack<Integer> ind = new Stack<>();
        while (i < s.length()) {

            //iterate over the whole xml file
            w = s.substring(i, i + 1);

            if (w.equals("\r") || w.equals("\n")) {
            }//checking if it's tag or data to be printed
            if (w.equals("<")) {        //if it's a tag
                i++;
                w = s.substring(i, i + 1);
                if (!w.equals("/")) {           //if it is opening tag
                    while (!w.equals(">")) {    //store this tag in word
                        word += w;
                        i++;
                        w = s.substring(i, i + 1);
                    }
                    // Handle repeated tags and adjust JSON structure accordingly
                    //if more than one <topic> repeated in scope of <topics>, delete topic from json and add [
                    if (word.equals(last_closed_tag)) {
                        json = json.substring(0, x - ((brackets.size() + 1)) - 2) + json.substring(x - ((brackets.size() + 1)) - 2 + word.length() + 6 + ((brackets.size() + 1)));   //6 bec ({ \n " " : ), x->"    //remove {\n__"user":__
                        j -= (word.length() + 6 + ((brackets.size() + 1)));
                        json = json.substring(0, x - ((brackets.size() + 1)) - 2) + "[\n" + json.substring(x - ((brackets.size() + 1)) - 2);
                        j += 2;
                        brackets.pop();
                        brackets.push("]");
                        //return old value of x
                        if (!ind.isEmpty()) {
                            ind.pop();
                            if (!ind.isEmpty()) {
                                x = ind.peek();
                            }
                        }
                        //skip new <topic>
                        i++;
                        stack_open_tags.push(word);
                        tag.push(word);
                        opening_tag.push(true);
                        json += "\n";
                        j += 1;
                        word = "";
                        continue;
                    }//condition for the first opening tag bec stk is still empty
                    if (stack_open_tags.isEmpty()) {        //
                        json += "\n";
                    }//if last thing in json was closing tag, no need to open "{"
                    else if (opening_tag.peek() == false) {   //if it is opening tag or we have closed the tag before it

                        json += "\n";
                        j += 1;
                    } else { //for formating                                   //if it is closing tag or the last opened tag is not closed yet
                        for (int t = brackets.size() + 1; t > 0; t--) {
                            json += " ";
                            j += 1;
                        }
                        json += "{\n";
                        j += 2;
                        //assuming if there is array in xml file, it must be writtin as (users,user - topics,topic)
                        //save index of user in json to be deleted if it's repeated in the same scope of users
                        if ((word + "s").equals(stack_open_tags.peek())) {
                            x = j + brackets.size() + 3; //x->"
                            ind.push(x);
                        }
                        brackets.push("}");     //add closing curly bracket for the one we added
                    }//for formating
                    for (int t = brackets.size() + 1; t > 0; t--) {
                        json += " ";
                        j += 1;
                    }
                    json += "\"";
                    j++;
                    json += word;
                    j += word.length();     //write tag name in json file
                    json += "\": ";
                    j += 3;
                    stack_open_tags.push(word);     //store the opened tag in the stack
                    tag.push(word);
                    i++;
                    opening_tag.push(true);     //we have opened new tag
                } else {                        //if it is closing tag
                    while (!w.equals(">")) {    //store the closing tag in word
                        i++;
                        w = s.substring(i, i + 1);
                        word += w;
                    }
                    word = word.substring(0, word.length() - 1);    //to remove the > from the end of the word
                    //opening tags are pushed into tag*stack* and poped when they are closed
                    //before poping they are stored in tags*string*
                    //if opening tag is repeated after the closing tag, it will be deleted from json*string*
                    if (word.equals(tag.peek())) {  //check the last tag
                        last_closed_tag = word;     //store the last closed tag
                        tag.pop();                  //pop the tag that is closed

                        // //"topic", x=90->", topics length = count = 6,
                        int count = last_closed_tag.length();
                        String currentWord;
                        currentWord = json.substring(x + 1, x + 2);
                        //currentWord = t

                        //if scope of topics ended without repeating topic, remove index of topic.
                        for (int L = x + 2; L < x + count; L++) {
                            currentWord += json.substring(L, L + 1); //topic
                        }

                        //topic + s == topics
                        if ((currentWord + "s").equals(last_closed_tag)) {
                            //pop 90
                            ind.pop();
                            //x = index of post
                            if (!ind.isEmpty()) {
                                x = ind.peek();
                            }
                        }
                    }
                    //print closing brackets
                    if (opening_tag.peek() == false) {
                        String back;
                        back = json.substring(json.length() - 1);

                        //removing unwanted commas
                        if ((brackets.peek().equals("]") && bracket.equals("}")) ||
                                (brackets.peek().equals("}") && back.equals(",")) ||
                                (brackets.peek().equals("]") && back.equals(","))) { //brackets.top()-> barcket to be printed, bracket->last printed bracket
                            json = json.substring(0, json.length() - 1);
                            j--;
                        }
                        json += "\n";
                        j += 1;
                        for (int t = brackets.size(); t > 0; t--) {
                            json += " ";
                            j += 1;
                        }
                        json += brackets.peek();
                        bracket = brackets.peek();
                        j++;
                        brackets.pop();
                        if (!brackets.isEmpty()) {
                            json += ",";
                            j++;
                        }
                    }
                    i++;
                    opening_tag.push(false);    //last opened tag is closed
                }
            }
            //data to be printed char by char so no need for word here
            else {
                json += "\"";
                j++;
                while (!w.equals("<")) {    //add the data to the json until find an opening tag
                    json += w;
                    j++;
                    i++;
                    w = s.substring(i, i + 1);
                }
                json += "\"";   //close the data field
                json += ",";    // add another
                j += 2;
            }
            word = "";
        }

        json += "\n}";  //closing json string
        j += 2;
        this.StringData=json;
        return json;
    }
    //----------------------------------------------------------
    // Minify XML file by removing spaces an new lines
    public String Minify() {
        undo.push(this.StringData);
        String file=this.StringData;
        StringBuilder file_after_minify= new StringBuilder();
        String[] lines = split_the_XML_file(file);
        for (int i =0 ; i<lines.length ; i++){

            if (lines[i].trim().length()==0)
            {
                continue;
            }
            lines[i] = lines[i].trim();
            file_after_minify.append(lines[i].trim());
        }
        this.StringData=file_after_minify.toString();
        return file_after_minify.toString();

    }


    //----------------------------------------------------------
    // Format XML File
    public  String Prettify(){
        undo.push(this.StringData);
        String file=this.StringData;
        StringBuilder file_after_format= new StringBuilder();

        int n=0;

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
    //----------------------------------------------------------
    // Split XML file to be used in Minify() and Prettify()
    private  String[] split_the_XML_file(String File){
        String[] lines;
        File =File.trim().replaceAll("<", "\n<").replaceAll(">", ">\n");
        lines = File.split("\n");
        return lines;
    }
    //----------------------------------------------------------

    // function to generate Indentation to be used in Prettify()
    private String generateIndentation(int level)
    {
        StringBuilder indention = new StringBuilder();
        for (int i = 0; i < level; i++)
        {
            indention.append("  ");
        }
        return indention.toString();
    }

    //----------------------------------------------------------
    //Undo
    public String Undo(){
        //Check if undo stack is empty return null
        if (undo.empty()){
            return null;
        }
        else{
            redo.push(this.StringData); // push the top of undo stack in the redo stack
            this.StringData=undo.peek(); // put the top of undo stack in StringData then pop
            undo.pop();
            return this.StringData;
        }

    }
    //----------------------------------------------------------
    //Redo
    public String Redo(){
        //Check if redo stack is empty return null
        if (redo.empty()){

            return  null;
        }
        else{

            this.StringData=redo.peek();// push the top of redo stack in the undo stack
            undo.push(redo.peek());  // put the top of redo stack in StringData then pop
            redo.pop();
            return this.StringData;
        }

    }


}
