package com.myapp.datastructureproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.brunomnsilva.smartgraph.containers.SmartGraphDemoContainer;
import com.brunomnsilva.smartgraph.graph.Graph;
import com.brunomnsilva.smartgraph.graphview.*;

import com.myapp.datastructureproject.networkAnalysis.MapF;
import com.myapp.datastructureproject.networkAnalysis.NetworkHelper;
import com.myapp.datastructureproject.networkAnalysis.User;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;

import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLcontroller {

    @FXML
            TextField path;
    @FXML
            TextArea fixed_text_area;
    @FXML
            TextArea view_operation;
    @FXML
            TextField u1;
    @FXML
            TextField u2;
    @FXML
            Button find_mutual_btn;
    @FXML
            Button search_btn;
    @FXML
            Button visualize_btn;
    @FXML
            TextField topic;
    @FXML
            Button correct_btn;
    @FXML
            Button pretify_btn;
    @FXML
            Button json_btn;
    @FXML
            Button minify_btn;
    @FXML
            Button compress_btn;
    @FXML
            Button decompress_btn;
    @FXML
            Button analyse_btn;
    @FXML
            Button undo_btn;
    @FXML
            Button redo_btn;
    @FXML
            Button check_btn;
    @FXML
            Button find_most_followd_btn;
    @FXML
            Button find_most_active_btn;
    @FXML
            TextField user_s;
    @FXML
            Button suggest_btn;
    @FXML
            RadioButton compressed_mode;
    @FXML
            Button save_btn;
//    GUI
    Alert Alarm;
    Network_nodes network_nodes;
    Operation_nodes operation_nodes;
//    network analysis
    NetworkHelper networkHelper;
    com.myapp.datastructureproject.networkAnalysis.Graph graph;
    User [] users_list;
    MapF users_map;
    User [][] users_suggest;
//    main files object
    MainFiles content;

    @FXML
//    first called function in scene
    public void initialize() {
        Alarm = new Alert(AlertType.ERROR);
        view_operation.setEditable(false);
//        operation and network nodes are disabled except check
        network_nodes = new Network_nodes(u1,u2,topic,find_mutual_btn,search_btn,visualize_btn,find_most_followd_btn,find_most_active_btn,suggest_btn,user_s);
        operation_nodes = new Operation_nodes(pretify_btn,json_btn,minify_btn,compress_btn,decompress_btn,analyse_btn,undo_btn,redo_btn);
        FXMLhelper.disable(correct_btn);
        FXMLhelper.disable(save_btn);

//    listener on action when text in text area changes
        fixed_text_area.textProperty().addListener((obs, oldText, newText) -> {
//          operation and network nodes are disabled except check
            operation_nodes.enable(false);
            network_nodes.enable(false);
            FXMLhelper.disable(correct_btn);
            FXMLhelper.disable(save_btn);
            FXMLhelper.enable(check_btn);
//          reset compressed mode button
            compressed_mode.setSelected(false);
            FXMLhelper.enable(compressed_mode);
//          reset view operation text area
            view_operation.setText("");
        });
//        listener to compressed mode radio button
        compressed_mode.selectedProperty().addListener((observable, oldValue, newValue) -> {
            //if RadioButton is selected
            if (newValue) {
                //check if RadioButton is selected
                FXMLhelper.enable(decompress_btn);
                FXMLhelper.disable(check_btn);
            }
            else {
                FXMLhelper.disable(decompress_btn);
                FXMLhelper.enable(check_btn);
            }
        });

    }
    //------------------------------------------------------------------------------
    //on action of open button
    public void open(ActionEvent ev) {
        fixed_text_area.setText("");
        view_operation.setText("");
        String p = path.getText();
        if (p != null && p.length() != 0 && p.contains(".xml")) {
            String fileContent = null;
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(p));
                    StringBuilder stringBuilder = new StringBuilder();
                    while(true) {
                        String line;
                        if ((line = reader.readLine()) == null) {
                            fileContent = stringBuilder.toString();
//                            System.out.println("File content:");
//                            System.out.println(fileContent);
                            break;
                        }
                        stringBuilder.append(line).append("\n");
                    }
                } catch (IOException e) {
                    Alarm.setContentText("xml file path not correct");
                    Alarm.show();
                }
                fixed_text_area.setText(fileContent);
//                fixed_text_area.setEditable(false);
            }
        else if(p.length() == 0);
        else
        {
            try {
                new FileReader(p);
            } catch (Exception e) {
                Alarm.setContentText("xml file path not correct");
                Alarm.show();
            }
        }
    }
    //------------------------------------------------------------------------------
    //on action of browse button
    public void Browse(ActionEvent ev) {
        try {
            FileChooser fc = new FileChooser();

            fc.setTitle("choose file");
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*xml"));
            File file = fc.showOpenDialog(null);
            path.setText(file.getAbsolutePath());
        }catch (Exception e){

        }
    }
    //------------------------------------------------------------------------------
    //on action of check button
    public void check(ActionEvent ev) {
        ArrayList<String> data = FXMLhelper.splitMultilineString(fixed_text_area.getText());
        if (fixed_text_area.getText().length() == 0)
            return;
        content = new MainFiles(data);
        String valid = FXMLhelper.isValid(content);
        if(valid.length() == 0) {
            view_operation.setText("The xml file has no missing tags");
            operation_nodes.enable(true);
            FXMLhelper.disable(check_btn);
        }
        else{
            view_operation.setText(valid);
            FXMLhelper.enable(correct_btn);
        }
        FXMLhelper.disable(compressed_mode);
    }
    //------------------------------------------------------------------------------
    //on action of correct button
    public void correct(ActionEvent ev) {
        view_operation.setText(content.correction());
        FXMLhelper.enable(save_btn);
    }
    //------------------------------------------------------------------------------
    //on action of Save button
    public void save(ActionEvent ev){
        fixed_text_area.setText(view_operation.getText());
    }
    //------------------------------------------------------------------------------
    //on action of prettify button
    public void prettify(ActionEvent ev) {
        view_operation.setText(content.Prettify());
    }
    //------------------------------------------------------------------------------
    //on action of to json button
    public void tojson(ActionEvent ev) {
        view_operation.setText(content.xmlToJson());
    }
    //------------------------------------------------------------------------------
    //on action of minify button
    public void minify(ActionEvent ev) {
        view_operation.setText(content.Minify()); }
    //------------------------------------------------------------------------------
    //on action of compress button
    public void compress(ActionEvent ev) {
        view_operation.setText(content.Compress());
    }
    //------------------------------------------------------------------------------
    //on action of decompress button
    public void decompress(ActionEvent ev) {
        view_operation.setText(content.Decompress());
    }
    //on action of undo button
    public void undo(ActionEvent ev) {
        view_operation.setText(content.Undo());
    }
    //------------------------------------------------------------------------------
    //on action of redo button
    public void redo(ActionEvent ev) {
        view_operation.setText(content.Redo());
    }
    //------------------------------------------------------------------------------
    //on action of save as button
    public void saveas(ActionEvent ev) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files(*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            FXMLhelper.saveTextToFile(view_operation.getText(), file);
        }
    }
    //------------------------------------------------------------------------------
    //    on action analyse button
    public void analyse(ActionEvent ev) {
        try {
            networkHelper = new NetworkHelper(fixed_text_area.getText());
            graph = networkHelper.CreateNetwork();
            users_list = networkHelper.getUsers();
            users_suggest = networkHelper.suggest();
            users_map = networkHelper.createUserIndexMap();
            network_nodes.enable(true);
        }catch (Exception e){
        }
    }
    //------------------------------------------------------------------------------
    //on action of visualize button
    public void visualize(ActionEvent ev){

        Graph<String, String> g = FXMLhelper.build_sample_digraph(users_list,graph);
        System.out.println(g);
        SmartPlacementStrategy strategy = new SmartCircularSortedPlacementStrategy();
//        SmartPlacementStrategy strategy = new SmartRandomPlacementStrategy();
        SmartGraphPanel<String, String> graphView = new SmartGraphPanel<>(g, strategy);

        /*
        After creating, you can change the styling of some element.
        This can be done at any time afterward.
        */
//        if (g.numVertices() > 0) {
//            graphView.getStylableVertex(users[1].getID()).setStyle("-fx-fill: gold; -fx-stroke: brown;");
//        }
        /*
        Basic usage:
        Use SmartGraphDemoContainer if you want zoom capabilities and automatic layout toggling
        */
        Scene scene = new Scene(new SmartGraphDemoContainer(graphView), 1024, 768);

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("JavaFX SmartGraph Visualization");
        stage.setMinHeight(500);
        stage.setMinWidth(800);
        stage.setScene(scene);
        stage.show();
        /*
        IMPORTANT: Must call init() after scene is displayed, so we can have width and height values
        to initially place the vertices according to the placement strategy.
        */
        graphView.init();

    }
    //------------------------------------------------------------------------------
    //on action of find mutual button
    public void Find_mutual(ActionEvent ev){
        String user1 = u1.getText().replaceAll("\\s", "");
        String user2 = u2.getText().replaceAll("\\s", "");
        if(user1.length() == 0 || user2.length() == 0)
            return;
        else if(!FXMLhelper.isValidID(user1, users_list) ||!FXMLhelper.isValidID(user2, users_list)) {
            Alarm.setContentText("Please enter a valid ID!");
            Alarm.show();
        }
        else{
            view_operation.setText(Arrays.toString(networkHelper.mutualFollowers(user1,user2)));
        }
    }
    //------------------------------------------------------------------------------
    //on action of most followed button
    public void most_followed(ActionEvent ev){
        view_operation.setText(networkHelper.mostFollowed().toString());
    }
    //------------------------------------------------------------------------------
    //on action of most active button
    public void most_active(ActionEvent ev){
        view_operation.setText(networkHelper.mostActive().toString());
    }
    //------------------------------------------------------------------------------
    //on action of search button
    public void search(ActionEvent ev){
        if(topic.getText().length() == 0 || topic.getText().trim().length() == 0)
            return;
        view_operation.setText(networkHelper.search(topic.getText().trim()));
    }
    //------------------------------------------------------------------------------
    //on action of suggest button
    public void suggest(ActionEvent ev){
        String u = user_s.getText().replaceAll("\\s", "");
        if(FXMLhelper.isValidID(u, users_list)) {
            int user_index = users_map.get(u);
            view_operation.setText("for" + users_list[user_index].toString() + "\nsuggested: " + Arrays.toString(users_suggest[user_index]));
        }
        else {
            Alarm.setContentText("Please enter a valid ID!");
            Alarm.show();
        }
    }
}


