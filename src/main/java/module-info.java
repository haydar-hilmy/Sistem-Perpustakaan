module com.fxml.pbo.pbofxml {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.fxml.pbo.pbofxml to javafx.fxml;
    exports com.fxml.pbo.pbofxml;
}