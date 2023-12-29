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
                    