package view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import model.model.Data;
import model.model.Group;
import model.model.Subject;
import model.modelController.ModelController;

public class CreatationFrameController implements Initializable {

    ModelController mc = new ModelController();

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
        groups.clear();
        subjects.clear();
        groups.addAll(mc.getGroups());
        subjects.addAll(mc.getSubjects());
        grouplist.setItems(groups);
        subjectlist.setItems(subjects);

        subjectlist.setEditable(true);
        subjectlist.setCellFactory(TextFieldListCell.forListView(new StringConverter<Subject>() {

            @Override
            public String toString(Subject object) {
                return object.getSubjectName();
            }

            @Override
            public Subject fromString(String string) {
                subjectlist.getSelectionModel().getSelectedItem().setSubjectName(string);
                return subjectlist.getSelectionModel().getSelectedItem();
            }
        }));

        grouplist.setEditable(true);
        grouplist.setCellFactory(TextFieldListCell.forListView(new StringConverter<Group>() {
            @Override
            public String toString(Group object) {
                return object.getGroupName();
            }

            @Override
            public Group fromString(String string) {
                grouplist.getSelectionModel().getSelectedItem().setGroupName(string);
                return grouplist.getSelectionModel().getSelectedItem();
            }
        }));

        subjectlist.setOnEditCommit(new EventHandler<ListView.EditEvent<Subject>>() {

            @Override
            public void handle(ListView.EditEvent<Subject> event) {
                ChiqishSaqlashDialogi.changed();
            }
        });

        grouplist.setOnEditCommit(new EventHandler<ListView.EditEvent<Group>>() {

            @Override
            public void handle(ListView.EditEvent<Group> event) {
                ChiqishSaqlashDialogi.changed();
            }
        });
    }

    @FXML
    private void delSbj(ActionEvent event) {
        if (subjectlist.getSelectionModel().getSelectedIndex() > -1) {
            mc.deletSubject(subjectlist.getSelectionModel().getSelectedItem());
            subjects.clear();
            subjects.addAll(Data.subjects);
            ChiqishSaqlashDialogi.changed();

        }
    }

    @FXML
    private void addsbj(ActionEvent event) {

        if (mc.addSubject(new Subject(tfsubject.getText()))) {
            subjects.clear();
            subjects.addAll(Data.subjects);
            tfsubject.clear();
            ChiqishSaqlashDialogi.changed();
        }

    }

    @FXML
    private void delGroup(ActionEvent event) {
        if (grouplist.getSelectionModel().getSelectedIndex() > -1) {
            mc.deletGroup(grouplist.getSelectionModel().getSelectedItem());
            groups.clear();
            groups.addAll(Data.groups);
            ChiqishSaqlashDialogi.changed();

        }
    }

    @FXML
    private void addGroup(ActionEvent event) {

        if (mc.addGroup(new Group(tfgroup.getText()))) {
            groups.clear();
            groups.addAll(Data.groups);
            tfgroup.clear();
            ChiqishSaqlashDialogi.changed();
        }
    }

    @FXML
    private void nextScreen(ActionEvent event) {
        SceneController.setScene(getClass().getResource("ScondScreen.fxml"));
    }
}
