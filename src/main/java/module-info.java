module com.minim.library.pbo_perpus {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.minim.library.pbo_perpus to javafx.fxml;
    exports com.minim.library.pbo_perpus;
    exports com.minim.library.models;
    opens com.minim.library.models to javafx.fxml;
}