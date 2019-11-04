package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import model.model.BlockTest;
import model.model.Data;
import model.modelController.ModelController;

/**
 * FXML Controller class
 *
 * @author Islom
 */
public class BlockTestListController implements Initializable {
    @FXML
    private ListView<BlockTest> list;

    ModelController mc = new ModelController();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        list.setItems(FXCollections.observableList(Data.bockTests));
        setTitle();
    }    

    @FXML
    private void yangiblocktest(ActionEvent event) {
        new SceneSaver().setCreatationBlockTestFXML();
    }

    @FXML
    private void ochirish(ActionEvent event) {
        if(list.getSelectionModel().getSelectedIndex()>-1){
            mc.deletBlockTest(list.getSelectionModel().getSelectedIndex());
            list.setItems(FXCollections.observableList(Data.bockTests));
            new SceneSaver().setScondScreenFXML();
            new SceneSaver().setBlocktestListFXML();
            ChiqishSaqlashDialogi.changed();
        }
    }

    @FXML
    private void ochish(ActionEvent event) {
        if(list.getSelectionModel().getSelectedIndex()>-1){
            ModelController.selectedBocktestId = list.getSelectionModel().getSelectedIndex();
            new SceneSaver().setBlockTestScreenFXML();
            SceneSaver.SECOND_STAGE.hide();
            ChiqishSaqlashDialogi.changed();
        }
    }

    private void setTitle() {
        SceneSaver.SECOND_STAGE.setTitle("Bloktestlar");
    }
    
    
}
