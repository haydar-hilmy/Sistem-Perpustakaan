package machinelearning.guimachinelearning;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AppMain extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AppMain.class.getResource("firis.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Sistem Machine Learning");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}