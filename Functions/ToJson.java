import java.util.Stack;

class ToJson {
    public static String xmlToJson(String s) {
        String json = "{";
        String w, word = "";
        //i for xml length, j for json length, x for repeated tags
        int i = 0, j = 1, x = 0;
        //tag for opening tags, but they are popped when they are closed,   brackets for({,])
        Stack<String> stack_open_tags = new Stack<>(), tag = new Stack<>(), brackets = new Stack<>();
        Stack<Boolean> opening_tag = new Stack<>(); // true if opening tag,and false if closing
        //bracket holds last printed bracket
        String last_closed_tag="", bracket="";
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
                if(w.equals("?")||w.equals("!")){    //if it is xml header ignore
                    while (!w.equals(">")) {
                        i++;
                        w = s.substring(i, i + 1);
                    }
                    i++;
                    continue;
                }
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
                        if ((word + "s" ).equals(stack_open_tags.peek())) {
                            x = j + brackets.size() + 3; //x->"
                            ind.push(x);
                        }
                        brackets.push("}");     //add closing curly bracket for the one we added
                    }
                    //for formating
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
                        if ((currentWord + "s") .equals(last_closed_tag)) {
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

        return json;
    }
