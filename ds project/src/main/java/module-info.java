module com.myapp.dsproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires smartgraph;
    requires java.logging;


    opens com.myapp.datastructureproject to javafx.fxml;
    exports com.myapp.datastructureproject;
}