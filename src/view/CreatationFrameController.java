package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.model.Data;
import model.model.Group;
import model.modelController.IdScanner;
import model.model.Subject;

public class CreatationFrameController implements Initializable {

    public static ObservableList<Group> groups = FXCollections.observableArrayList();
    public static ObservableList<Subject> subjects = FXCollections.observableArrayList();

    @FXML
    public ListView<Subject> subjectlist;
    @FXML
    public ListView<Group> grouplist;
    @FXML
    private TextField tfsubject;
    @FXML
    private TextField tfgroup;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        grouplist.setItems(groups);
        subjectlist.setItems(subjects);
    }

    @FXML
    private void delSbj(ActionEvent event) {
        if (subjectlist.getSelectionModel().getSelectedIndex() > -1) {
            
            int i = subjectlist.getSelectionModel().getSelectedIndex();
            int id = subjectlist.getSelectionModel().getSelectedItem().getSubjectId();
            
            subjects.remove(i);
            Data.subjects.remove(IdScanner.getIndexbyIdSubjet(id));
        }
    }

    @FXML
    private void addsbj(ActionEvent event) {
        // AGAR LISTDA BOR BO'LSA QOSHIMAYDI YANI RUTURN BILAN CHIQIB KETADI
        boolean isAdd = true;
        for (Subject x : Data.subjects) {
            isAdd &= !x.getSubjectName().equalsIgnoreCase(tfsubject.getText());
        }
        if (!isAdd || tfsubject.getText().isEmpty()) {
            return;
        }
        
        
        //BO'SH IDGA QO'SHADI
        int emptyId = IdScanner.getEmptySubjectId();

        Data.subjects.add(emptyId, new Subject(tfsubject.getText(), emptyId));
        subjects.add(Data.subjects.get(emptyId));
        
        tfsubject.clear();

    }

    @FXML
    private void delGroup(ActionEvent event) {
        if (grouplist.getSelectionModel().getSelectedIndex() > -1) {
            
            int i = grouplist.getSelectionModel().getSelectedIndex();
            int id = grouplist.getSelectionModel().getSelectedItem().getGroupId();
            
            groups.remove(i);
            Data.groups.remove(IdScanner.getIndexbyIdGroup(id));
        }
    }

    @FXML
    private void addGroup(ActionEvent event) {
        // AGAR LISTDA BOR BO'LSA QOSHIMAYDI YANI RUTURN BILAN CHIQIB KETADI
        boolean isAdd = true;
        for (Group x : Data.groups) {
            isAdd &= !x.getGroupName().equalsIgnoreCase(tfgroup.getText());
        }
        if (!isAdd || tfgroup.getText().isEmpty()) {
            return;
        }
        
        
        //BO'SH IDGA QO'SHADI
        int emptyId = IdScanner.getEmptyGroupId();

        Data.groups.add(emptyId, new Group(tfgroup.getText(), emptyId));
        groups.add(Data.groups.get(emptyId));
        
        tfgroup.clear();
    }
}
