

package view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import model.modelController.ModelController;
import model.model.Data;
import model.model.Group;
import model.model.Subject;

/**
 *
 * @author Islom
 */
public class OquvMarkaz extends Application {
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("CreatationFrame.fxml"));
        ModelController data = new ModelController();
        data.read();
        Scene scene = new Scene(root);
        stage.setOnShowing(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                for (Group x : Data.groups) {
                    CreatationFrameController.groups.add(x);
                }
                
                for (Subject x : Data.subjects) {
                    CreatationFrameController.subjects.add(x);
                }
            }
        });
        stage.resizableProperty().set(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
