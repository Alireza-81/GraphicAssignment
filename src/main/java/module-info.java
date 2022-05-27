module com.example.graphix {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.graphix to javafx.fxml;
    exports com.example.graphix;
    exports com.example.graphix.Controllers;
    opens com.example.graphix.Controllers to javafx.fxml;
    exports com.example.graphix.VIEW;
    opens com.example.graphix.VIEW to javafx.fxml;
    requires javafx.media;
}