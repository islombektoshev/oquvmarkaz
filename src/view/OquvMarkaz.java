package view;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 *
 * @author Islom
 */
public class OquvMarkaz extends Application {

    public Stage primaryStage;

    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("CreatationFrame.fxml"));
        Scene scene = new Scene(root);
        stage.resizableProperty().set(true);
        stage.setScene(scene);
        SceneSaver.setOquvMarkaz(this);

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                ChiqishSaqlashDialogi.showD();
            }
        });

        SceneController.setScene(getClass().getResource("ScondScreen.fxml"));
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
