package view;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ChoiceBoxTableCell;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import model.model.Group;
import model.model.Student;
import model.model.Subject;
import model.modelController.ModelController;

/**
 * FXML Controller class
 *
 * @author Islom
 */
public class ScondScreenController implements Initializable {

    @FXML
    private TableView<Student> tbview;
    @FXML
    private TableColumn<Student, String> tcIsm;
    @FXML
    private TableColumn<Student, String> tcFam;
    @FXML
    private TableColumn<Student, String> tcTel;
    @FXML
    private TableColumn<Student, Subject> tcSb1;
    @FXML
    private TableColumn<Student, Subject> tcSb2;
    @FXML
    private TableColumn<Student, Subject> tcSb3;
    @FXML
    private TableColumn<Student, Group> tcGr;
    @FXML
    private TextField tfIsm;
    @FXML
    private TextField tfFam;

    ModelController mc = new ModelController();
    @FXML
    private TextField tfSearch;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        setCells();
        tbview.selectionModelProperty().addListener(new ChangeListener<TableView.TableViewSelectionModel<Student>>() {
            @Override
            public void changed(ObservableValue<? extends TableView.TableViewSelectionModel<Student>> observable, TableView.TableViewSelectionModel<Student> oldValue, TableView.TableViewSelectionModel<Student> newValue) {
                mc.resetID();
            }
        });
    }

    @FXML
    public void saqalash(ActionEvent event) {
        mc.saveDocument();
    }

    private void setCells() {
        tcIsm.setCellValueFactory(new PropertyValueFactory("name"));
        tcFam.setCellValueFactory(new PropertyValueFactory("surename"));
        tcTel.setCellValueFactory(new PropertyValueFactory("tel"));
        tcSb1.setCellValueFactory(new PropertyValueFactory("sb1"));
        tcSb2.setCellValueFactory(new PropertyValueFactory("sb2"));
        tcSb3.setCellValueFactory(new PropertyValueFactory("sb3"));
        tcGr.setCellValueFactory(new PropertyValueFactory("gr"));

        tbview.setItems(FXCollections.observableList((List<Student>) model.model.Data.students));
        tbview.setEditable(true);

        tcIsm.setCellFactory(TextFieldTableCell.forTableColumn());
        tcIsm.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                event.getRowValue().setName(event.getNewValue());
            }
        });

        tcFam.setCellFactory(TextFieldTableCell.forTableColumn());
        tcFam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                event.getRowValue().setSurename(event.getNewValue());
            }
        });

        tcTel.setCellFactory(TextFieldTableCell.forTableColumn());
        tcTel.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                String regax = "((\\+?9987[9|5])|(\\+?9989[0|1|3|4|5|7|9]))\\d{7}";
                if (Pattern.matches(regax, event.getNewValue())) {
                    if (!event.getNewValue().startsWith("+")) {
                        event.getRowValue().setTel(event.getNewValue());
                    } else {
                        event.getRowValue().setTel(event.getNewValue());
                    }
                } else {
                    if (Pattern.matches("((7[9|5])|(9[0|1|3|4|5|7|9]))\\d{7}", event.getNewValue())) {
                        event.getRowValue().setTel("+998" + event.getNewValue());
                    } else {
                        event.getRowValue().setTel(null);
                    }
                }
            }
        });

        tcSb1.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getSubjects()));
        tcSb1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Subject>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Subject> event) {
                event.getRowValue().setSbId1(event.getNewValue().getSubjectId());
            }
        });

        tcSb2.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getSubjects()));
        tcSb2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Subject>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Subject> event) {
                event.getRowValue().setSbId2(event.getNewValue().getSubjectId());
            }
        });

        tcSb3.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getSubjects()));
        tcSb3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Subject>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Subject> event) {
                event.getRowValue().setSbId3(event.getNewValue().getSubjectId());
            }
        });

        tcGr.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getGroups()));
        tcGr.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Group>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Group> event) {
                event.getRowValue().setGrId(event.getNewValue().getGroupId());
            }
        });
    }

    @FXML
    private void miFanGruh(ActionEvent event) {
        SceneController.setScene(getClass().getResource("CreatationFrame.fxml"));
    }

    @FXML
    private void addStudent(ActionEvent event) {
        if (!tfIsm.getText().isEmpty()) {
            tbview.getItems().add(new Student(tfIsm.getText(), tfFam.getText()));
            tfFam.clear();
            tfIsm.clear();
            mc.resetID();
        }
    }

    @FXML
    private void deletStudent(ActionEvent event) {
        System.out.println("delet fuction is running");
        int id = tbview.getSelectionModel().getSelectedIndex();
        tbview.getItems().remove(id);
        mc.resetID();
        mc.synchronizWithStudentForDeleting__Group(id);
        
    }

    public void updateTbview() {
        try {
            tbview.setVisible(false);
            wait(10);
        } catch (InterruptedException ex) {
            Logger.getLogger(ScondScreenController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            tbview.setVisible(true);
        }
    }

}
