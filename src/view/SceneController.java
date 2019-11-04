package view;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 *
 * @author User
 */
public class SceneController {
    public static void setScene(URL url){
        try {
            Parent root = FXMLLoader.load(url);
            Scene scene = new Scene(root);
            SceneSaver.getOquvMarkaz().primaryStage.setScene(scene);
        } catch (IOException ex) {
            Logger.getLogger(CreatationFrameController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
