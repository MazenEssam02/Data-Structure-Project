package com.myapp.datastructureproject;

import com.brunomnsilva.smartgraph.graph.Digraph;
import com.brunomnsilva.smartgraph.graph.DigraphEdgeList;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.myapp.datastructureproject.networkAnalysis.User;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FXMLhelper {
    static Graph<String, String> build_sample_digraph(User []users,com.myapp.datastructureproject.networkAnalysis.Graph graph) {

        Digraph<String, String> g = new DigraphEdgeList<>();


        for (User u : users){
            g.insertVertex(u.getID());
        }
        for (int i = 0; i < users.length; i++)
        {
            for (int j = 0; j < users.length; j++)
            {
                if(graph.isConnected(i,j)){
                    g.insertEdge(users[j].getID(),users[i].getID(),users[j].getID()+users[i].getID());
                }
            }
        }

        return g;
    }
    //helper method store string to specified file
    static void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException e) {
        }
    }
    static ArrayList<String> splitMultilineString(String multilineString) {
        String[] lines = multilineString.split("\r?\n");

        // Convert the array to an ArrayList:
        return new ArrayList<>(Arrays.asList(lines));
    }
    static String isValid(MainFiles obj){
        Object [] resulttest;
        resulttest = obj.validation();
        StringBuilder s = new StringBuilder("");
        //Check before correction
        if((Integer)resulttest[1]!=-1 ){
            s.append("The Tag \"" + resulttest[0]+"\" in line ("+resulttest[1] + ") isn't opened\n");
        }
        if((Integer)resulttest[3]!=-1){
            s.append("The Tag \"" +resulttest[2]+"\" in line ("+resulttest[3] +") isn't closed");
        }
        return s.toString();
    }
    static void enable(Button btn1){
        btn1.setDisable(false);
    }
    static void disable(Button btn1){
        btn1.setDisable(true);
    }
        static boolean isValidID(String str, int n) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        // Check if all characters are digits
        for (char c : str.toCharArray()) {
            if (!Character.isDigit(c)) {
                return false;
            }
        }
        // Convert the string to an integer
        int number = Integer.parseInt(str);
        // Check if the number is within the specified range
        return number > 0 && number <= n;
    }

}
class Network_nodes{
    TextField u1;
    TextField u2;
    TextField topic;
    Button find_mutual_btn;
    Button search_btn;
    Button visualize_btn;
    Button find_most_followd_btn;
    Button find_most_active_btn;
    Button suggest_btn;
    TextField user_s;

    Network_nodes(TextField u1,
                  TextField u2,
                  TextField topic,
                  Button find_mutual,
                  Button search,
                  Button visualize,
                  Button find_most_followd_btn,
                  Button find_most_active_btn,
                  Button suggest_btn,
                  TextField user_s){
        this.u1 = u1;
        this.u2 = u2;
        this.find_mutual_btn = find_mutual;
        this.search_btn = search;
        this.visualize_btn = visualize;
        this.topic = topic;
        this.find_most_followd_btn = find_most_followd_btn;
        this.find_most_active_btn = find_most_active_btn;
        this.suggest_btn = suggest_btn;
        this.user_s =user_s;
        enable(false);
    }
    void enable(boolean s){
        u1.setDisable(!s);
        u2.setDisable(!s);
        topic.setDisable(!s);
        find_mutual_btn .setDisable(!s);
        search_btn.setDisable(!s);
        visualize_btn.setDisable(!s);
        find_most_followd_btn.setDisable(!s);
        find_most_active_btn.setDisable(!s);
        suggest_btn.setDisable(!s);
        user_s.setDisable(!s);
    }

}
class Operation_nodes{
    Button pretify_btn;
    Button json_btn;
    Button minify_btn;
    Button compress_btn;
    Button decompress_btn;
    Button analyse_btn;
    Button undo_btn;
    Button redo_btn;
    public Operation_nodes(Button pretify_btn,
                            Button json_btn,
                            Button minify_btn,
                            Button compress_btn,
                            Button decompress_btn,
                            Button analyse_btn,
                            Button undo_btn,
                            Button redo_btn) {
        this.pretify_btn = pretify_btn;
        this.json_btn = json_btn;
        this.minify_btn = minify_btn;
        this.compress_btn = compress_btn;
        this.decompress_btn = decompress_btn;
        this.analyse_btn = analyse_btn;
        this.undo_btn = undo_btn;
        this.redo_btn = redo_btn;
        enable(false);
    }
    void enable(boolean s){
        pretify_btn.setDisable(!s);
        json_btn.setDisable(!s);
        minify_btn.setDisable(!s);
        compress_btn .setDisable(!s);
        decompress_btn.setDisable(!s);
        analyse_btn.setDisable(!s);
        undo_btn.setDisable(!s);
        redo_btn.setDisable(!s);
    }
}
