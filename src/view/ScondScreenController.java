package view;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.model.Data;
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
        tbview.setItems(FXCollections.observableList((List<Student>) model.model.Data.students));
        SceneSaver.oquvMarkaz.primaryStage.setTitle(mc.FILE.getName());
        ChiqishSaqlashDialogi.setDefault();

        tfSearch.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if (!tfSearch.getText().isEmpty()) {
                    ObservableList<Student> templist1 = FXCollections.observableArrayList();
                    System.out.println("mc.getStudents().size = " + mc.getStudents().size());
                    for (Student x : mc.getStudents()) {
                        if (x.getName().toLowerCase().contains(tfSearch.getText().toLowerCase())
                                | x.getSurename().toLowerCase().contains(tfSearch.getText().toLowerCase())
                                | x.getTel().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                            templist1.add(x);
                            System.out.println("find: " + x);
                        }
                    }
                    tbview.setItems(templist1);
                } else {
                    tbview.setItems(FXCollections.observableList((List<Student>) model.model.Data.students));
                }
            }
        });

    }

    @FXML
    public void saqalash(ActionEvent event) {
        mc.saveDocument();
    }

    private void setCells() {
        // tbview.setItems(FXCollections.observableList((List<Student>) model.model.Data.students));
        tcIsm.setCellValueFactory(new PropertyValueFactory("name"));
        tcFam.setCellValueFactory(new PropertyValueFactory("surename"));
        tcTel.setCellValueFactory(new PropertyValueFactory("tel"));
        tcSb1.setCellValueFactory(new PropertyValueFactory("sb1"));
        tcSb2.setCellValueFactory(new PropertyValueFactory("sb2"));
        tcSb3.setCellValueFactory(new PropertyValueFactory("sb3"));
        tcGr.setCellValueFactory(new PropertyValueFactory("gr"));

        tbview.setEditable(true);

        tcIsm.setCellFactory(TextFieldTableCell.forTableColumn());
        tcIsm.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                if (!event.getRowValue().getName().equals(event.getNewValue())) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setName(event.getNewValue());
            }
        });

        tcFam.setCellFactory(TextFieldTableCell.forTableColumn());
        tcFam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                if (!event.getRowValue().getName().equals(event.getNewValue())) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setSurename(event.getNewValue());
            }
        });

        tcTel.setCellFactory(TextFieldTableCell.forTableColumn());
        tcTel.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, String> event) {
                if (!event.getRowValue().getName().equals(event.getNewValue())) {
                    ChiqishSaqlashDialogi.changed();
                }
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
                if (event.getRowValue().getSbId1() != event.getNewValue().getSubjectId()) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setSbId1(event.getNewValue().getSubjectId());
            }
        });

        tcSb2.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getSubjects()));
        tcSb2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Subject>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Subject> event) {
                if (event.getRowValue().getSbId2() != event.getNewValue().getSubjectId()) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setSbId2(event.getNewValue().getSubjectId());
            }
        });

        tcSb3.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getSubjects()));
        tcSb3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Subject>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Subject> event) {
                if (event.getRowValue().getSbId3() != event.getNewValue().getSubjectId()) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setSbId3(event.getNewValue().getSubjectId());
            }
        });

        tcGr.setCellFactory(ComboBoxTableCell.forTableColumn(mc.getGroups()));
        tcGr.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Student, Group>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Student, Group> event) {
                if (event.getRowValue().getGrId() != event.getNewValue().getGroupId()) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setGrId(event.getNewValue().getGroupId());
            }
        });

        tbview.itemsProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                ChiqishSaqlashDialogi.changed();
                System.out.println("event ++++++++++++++++++");
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
            ChiqishSaqlashDialogi.changed();
        }
    }

    @FXML
    private void deletStudent(ActionEvent event) {
        int id = tbview.getSelectionModel().getSelectedIndex();
        if (id > -1) {
            Student s = tbview.getItems().remove(id);
            mc.resetID();
            if (!tfSearch.getText().isEmpty()) {
                mc.deletStudent(s.getStudentId());
            }
            ChiqishSaqlashDialogi.changed();
        }

    }

    @FXML
    public void updateTbview() {
        SceneController.setScene(getClass().getResource("ScondScreen.fxml"));
    }


    @FXML
    private void filtrla(ActionEvent event) {

    }

    Stage s = new Stage();

    @FXML
    private void deletAll(ActionEvent event) {
        GridPane gp = new GridPane();
        Label lb = new Label("Barchasi O'chirilsinmi");
        lb.setFont(Font.font("Arial Black", 24));
        Button saqlash = new Button("Ha"), chiqish = new Button("Yo'q");
        saqlash.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                tbview.setItems(null);
                Data.students.clear();
                s.hide();
            }
        });

        chiqish.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                s.hide();
            }
        });

        gp.add(lb, 0, 0, 2, 1);
        gp.add(saqlash, 0, 1);
        gp.add(chiqish, 1, 1);

        gp.setPadding(new Insets(5, 15, 5, 15));
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER_LEFT);

        s.setScene(new Scene(gp));
        s.show();
        SceneSaver.oquvMarkaz.primaryStage.setTitle(mc.FILE.getName());
    }

    @FXML
    private void yangiFayl(ActionEvent event) {
        mc.saveDocument();
        FileChooser fc = new FileChooser();
        fc.setTitle("Yangi faylni saqlash joyini ko'rsating");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dastur fayli", "*.mdf"));
        File f = fc.showSaveDialog(SceneSaver.oquvMarkaz.primaryStage);
        if(f!=null){
            ModelController.FILE = ModelController.EMPTYFILE;
            ModelController.read();
            ModelController.FILE = f;
            mc.saveDocument();
            ChiqishSaqlashDialogi.setDefault();
            updateTbview();
        }
    }

    @FXML
    private void choicefile(ActionEvent event) {
        mc.saveDocument();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Dastur fayli", "*.mdf"),
                new FileChooser.ExtensionFilter("Batcha fayllar", "*.*")
        );
        fileChooser.initialDirectoryProperty().set(mc.FILE.getParentFile());
        File f = fileChooser.showOpenDialog(SceneSaver.oquvMarkaz.primaryStage);
        if (f != null) {
            ModelController.FILE = f;
            ModelController.read();
            ModelController.saveFilePath(f);
            updateTbview();
        }
    }
    
    @FXML
    private void kabisaqlash(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Faylni saqlash joyini ko'rsating");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dastur fayli", "*.mdf"));
        File f = fc.showSaveDialog(SceneSaver.oquvMarkaz.primaryStage);
        if(f!=null){
            ModelController.FILE = f;
            mc.saveDocument();
            SceneSaver.oquvMarkaz.primaryStage.setTitle(f.getName());
        }
    }

    @FXML
    private void chiqish(ActionEvent event) {
        ChiqishSaqlashDialogi.showWithCancel();
    }
}
