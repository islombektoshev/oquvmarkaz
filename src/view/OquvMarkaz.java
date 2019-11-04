

package view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
        stage.resizableProperty().set(false);
        stage.setScene(scene);
        SceneSaver.setOquvMarkaz(this);
        
        SceneController.setScene(getClass().getResource("ScondScreen.fxml"));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }   
}
