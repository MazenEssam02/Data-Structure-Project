
package com.myapp.datastructureproject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

public class FXMLcontroller {
    private Stage stage;
    Alert A;
    @FXML
    private TextField path;
    @FXML
    final FileChooser fc;
    static String p;
    @FXML
    private TextArea fixed_text_area;
    @FXML
    private TextArea view_operation;

    public FXMLcontroller() {
        this.A = new Alert(AlertType.ERROR);
        this.fc = new FileChooser();
    }

    public void open(ActionEvent ev) throws IOException {
        p = this.path.getText();
        p = p.replaceAll("\\s", "");
        if (p != null && p.length() != 0 && p.contains(".xml")) {
            Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("main_scene.fxml"));
            this.stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            this.stage.setScene(scene);
            this.stage.show();
            this.stage.setResizable(true);
        } else {
            try {
                new FileReader(p);
            } catch (Exception var4) {
                this.A.setContentText("such path doesn't exist");
                this.A.show();
            }
        }

    }

    public void viewFileChooser(ActionEvent event) {
        try {
            this.fc.setTitle("choose file");
            this.fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", new String[]{"*xml"}));
            File file = this.fc.showOpenDialog((Window)null);
            this.path.setText(file.getAbsolutePath());
        } catch (Exception var3) {
        }

    }

    public void viewxml(ActionEvent e) {
        String fileContent = null;
        if (p == null) {
            System.out.println("path is null");
        } else {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(p));

                try {
                    StringBuilder stringBuilder = new StringBuilder();

                    while(true) {
                        String line;
                        if ((line = reader.readLine()) == null) {
                            fileContent = stringBuilder.toString();
                            System.out.println("File content:");
                            System.out.println(fileContent);
                            break;
                        }

                        stringBuilder.append(line).append("\n");
                    }
                } catch (Throwable var7) {
                    try {
                        reader.close();
                    } catch (Throwable var6) {
                        var7.addSuppressed(var6);
                    }

                    throw var7;
                }

                reader.close();
            } catch (IOException var8) {
                this.A.setContentText("An error occurred while reading the file:" + var8.getMessage());
                this.A.show();
                System.out.println("An error occurred while reading the file: " + var8.getMessage());
            }

            this.fixed_text_area.setText(fileContent);
            this.fixed_text_area.setEditable(false);
        }

    }

    public void check(ActionEvent ev) {
        this.view_operation.setText("checked");
    }

    public void prettify(ActionEvent ev) {
        this.view_operation.setText("pretty");
    }

    public void tojson(ActionEvent ev) {
        this.view_operation.setText("json");
    }

    public void minify(ActionEvent ev) {
        this.view_operation.setText("minified");
    }

    public void compress(ActionEvent ev) {
        this.view_operation.setText("compressed");
    }

    public void decompress(ActionEvent ev) {
        this.view_operation.setText("decompressed");
    }

    public void undo(ActionEvent ev) {
    }

    public void redo(ActionEvent ev) {
    }

    private void saveTextToFile(String content, File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println(content);
            writer.close();
        } catch (IOException var4) {
        }

    }

    public void saveas(ActionEvent ev) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("XML Files(*.xml)", new String[]{"*.xml"});
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showSaveDialog(this.stage);
        if (file != null) {
            this.saveTextToFile(this.view_operation.getText(), file);
        }

    }

    public void Exit(ActionEvent ev) throws IOException {
        Parent root = (Parent)FXMLLoader.load(this.getClass().getResource("opening_scene.fxml"));
        this.stage = (Stage)((Node)ev.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        this.stage.setScene(scene);
        this.stage.show();
        this.stage.setResizable(false);
    }
}


