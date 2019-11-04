package view;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class SceneSaver {
    
    public final static Stage SECOND_STAGE = new Stage();
    public static OquvMarkaz oquvMarkaz;
//    public static final Stage MAIN_STAGE = oquvMarkaz.primaryStage;


    public static OquvMarkaz getOquvMarkaz() {
        return oquvMarkaz;
    }

    public static void setOquvMarkaz(OquvMarkaz oquvMarkaz) {
        SceneSaver.oquvMarkaz = oquvMarkaz;
    }
    
    public void setBlocktestListFXML(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("BlockTestList.fxml"));
            SECOND_STAGE.setScene(new Scene(parent));
            SECOND_STAGE.show();
        } catch (IOException ex) {
            Logger.getLogger(SceneSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setCreatationBlockTestFXML(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("CreatationBlockTest.fxml"));
            SECOND_STAGE.setScene(new Scene(parent));
            SECOND_STAGE.show();
        } catch (IOException ex) {
            Logger.getLogger(SceneSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setScondScreenFXML(){
        try {
            Parent parent = FXMLLoader.load(getClass().getResource("ScondScreen.fxml"));
            oquvMarkaz.primaryStage.setScene(new Scene(parent));
            oquvMarkaz.primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SceneSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setBlockTestScreenFXML(){
        try {
            System.out.println(getClass().getResource("BlockTestScreen.fxml"));
            Parent parent = FXMLLoader.load(getClass().getResource("BlockTestScreen.fxml"));
            oquvMarkaz.primaryStage.setScene(new Scene(parent));
            oquvMarkaz.primaryStage.show();
        } catch (IOException ex) {
            Logger.getLogger(SceneSaver.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
