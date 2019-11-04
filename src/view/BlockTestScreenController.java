package view;

import java.io.File;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;
import javax.swing.JOptionPane;
import model.model.Abiturient;
import model.model.BlockTest;
import model.model.Data;
import model.model.Student;
import model.modelController.ModelController;

/**
 * FXML Controller class
 *
 * @author Islom
 */
public class BlockTestScreenController implements Initializable {

    @FXML
    private TextField tfSearch;
    @FXML
    private TextField tfIsm;
    @FXML
    private TextField tfFam;
    @FXML
    private TextField tfTel;
    @FXML
    private TableView<Abiturient> tbview;
    @FXML
    private TableColumn<Abiturient, String> tcIsm;
    @FXML
    private TableColumn<Abiturient, String> tcFam;
    @FXML
    private TableColumn<Abiturient, String> tcTel;
    @FXML
    private TableColumn<Abiturient, Integer> tcF1;
    @FXML
    private TableColumn<Abiturient, Integer> tcF2;
    @FXML
    private TableColumn<Abiturient, Integer> tcF3;
    @FXML
    private TableColumn<Abiturient, String> tcIzoh;
    @FXML
    private TextField tfname;
    @FXML
    private DatePicker dpdate;
    @FXML
    private ComboBox<BlockTest> cbBlist;

    ModelController mc = new ModelController();
    List<Abiturient> abiturients = new ArrayList<>();
    BlockTest thisBlocktest = Data.bockTests.get(ModelController.selectedBocktestId);

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    private void init() {
        setTitle();
        tcIsm.setCellValueFactory(new PropertyValueFactory("name"));
        tcFam.setCellValueFactory(new PropertyValueFactory("surename"));
        tcTel.setCellValueFactory(new PropertyValueFactory("tel"));
        tcF1.setCellValueFactory(new PropertyValueFactory("f1"));
        tcF2.setCellValueFactory(new PropertyValueFactory("f2"));
        tcF3.setCellValueFactory(new PropertyValueFactory("f3"));
        tcIzoh.setCellValueFactory(new PropertyValueFactory("individualIzoh"));

        if (mc.selectedBocktestId > -1 && mc.selectedBocktestId < Data.bockTests.size()) {
            abiturients.addAll(Data.bockTests.get(mc.selectedBocktestId).getAbiturients());
        }
        
        tbview.setItems(FXCollections.observableList(abiturients));
        tbview.setEditable(true);

        tfname.setText(thisBlocktest.getName());
        tfname.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if (!tfname.getText().isEmpty()) {
                    thisBlocktest.setName(tfname.getText());
                } else {
                    thisBlocktest.setName(ModelController.NONE_NAME);
                }
                ChiqishSaqlashDialogi.changed();
            }
        });
        
        dpdate.setValue(LocalDate.of(thisBlocktest.getDate().getYear() + 1900, thisBlocktest.getDate().getMonth(), thisBlocktest.getDate().getDate()));
        dpdate.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                if (dpdate.getValue() != null) {
                    Date d = new Date(dpdate.getValue().getYear() - 1900, dpdate.getValue().getMonthValue(), dpdate.getValue().getDayOfMonth());
                    thisBlocktest.setDate(d);
                    ChiqishSaqlashDialogi.changed();
                }
            }
        });
        setFormatToDatePicer();

        cbBlist.setItems(FXCollections.observableArrayList(Data.bockTests));
        cbBlist.getSelectionModel().select(thisBlocktest);
        cbBlist.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("event cbBlist");
                if (cbBlist.getSelectionModel().getSelectedIndex() < Data.bockTests.size()) {
                    System.out.println("event cbBlist in if");
                    ModelController.selectedBocktestId = cbBlist.getSelectionModel().getSelectedIndex();
                    updateTbview();
                    ChiqishSaqlashDialogi.changed();
                }
            }
        });
        
        tfSearch.textProperty().addListener(new InvalidationListener() {

            @Override
            public void invalidated(Observable observable) {
                if (!tfSearch.getText().isEmpty()) {
                    ObservableList<Abiturient> templist1 = FXCollections.observableArrayList();
                    System.out.println("mc.getStudents().size = " + mc.getStudents().size());
                    for (Abiturient x : abiturients) {
                        if (x.getName().toLowerCase().contains(tfSearch.getText().toLowerCase())
                                | x.getSurename().toLowerCase().contains(tfSearch.getText().toLowerCase())
                                | x.getTel().toLowerCase().contains(tfSearch.getText().toLowerCase())) {
                            templist1.add(x);
                            System.out.println("find: " + x);
                        }
                    }
                    tbview.setItems(templist1);
                } else {
                    tbview.setItems(FXCollections.observableList((List<Abiturient>) abiturients));
                }
            }
        });

        tcIsm.setCellFactory(TextFieldTableCell.forTableColumn());
        tcIsm.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, String> event) {
                if (!event.getRowValue().getName().equals(event.getNewValue())) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setName(event.getNewValue());
            }
        });

        tcFam.setCellFactory(TextFieldTableCell.forTableColumn());
        tcFam.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, String> event) {
                if (!event.getRowValue().getName().equals(event.getNewValue())) {
                    ChiqishSaqlashDialogi.changed();
                }
                event.getRowValue().setSurename(event.getNewValue());
            }
        });

        tcTel.setCellFactory(TextFieldTableCell.forTableColumn());
        tcTel.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, String> event) {
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

        tcF1.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }));

        tcF2.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }));

        tcF3.setCellFactory(TextFieldTableCell.forTableColumn(new StringConverter<Integer>() {

            @Override
            public String toString(Integer object) {
                return object.toString();
            }

            @Override
            public Integer fromString(String string) {
                try {
                    return Integer.parseInt(string);
                } catch (NumberFormatException e) {
                    return -1;
                }
            }
        }));

        tcF1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, Integer> event) {
                event.getRowValue().setF1(event.getNewValue());
                ChiqishSaqlashDialogi.changed();
            }
        });

        tcF2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, Integer> event) {
                event.getRowValue().setF2(event.getNewValue());
                ChiqishSaqlashDialogi.changed();
            }
        });

        tcF3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, Integer>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, Integer> event) {
                event.getRowValue().setF3(event.getNewValue());
                ChiqishSaqlashDialogi.changed();
            }
        });

        tcIzoh.setCellFactory(TextFieldTableCell.forTableColumn());
        tcIzoh.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Abiturient, String>>() {

            @Override
            public void handle(TableColumn.CellEditEvent<Abiturient, String> event) {
                event.getRowValue().setIndividualIzoh(event.getNewValue());
                ChiqishSaqlashDialogi.changed();
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
    
    private void setFormatToDatePicer() {
        String format = "dd.MM.yyyy";
        StringConverter stringConverter = new StringConverter<LocalDate>() {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);

            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null) {
                    return LocalDate.parse(string);
                } else {
                    return null;
                }
            }
        };
        dpdate.setConverter(stringConverter);
    }

    @FXML
    private void yangiFayl(ActionEvent event) {
        mc.saveDocument();
        FileChooser fc = new FileChooser();
        fc.setTitle("Yangi faylni saqlash joyini ko'rsating");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dastur fayli", "*.mdf"));
        File f = fc.showSaveDialog(SceneSaver.oquvMarkaz.primaryStage);
        if (f != null) {
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
    private void saqalash(ActionEvent event) {
        mc.saveDocument();
        ChiqishSaqlashDialogi.setDefault();
    }

    @FXML
    private void kabisaqlash(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Faylni saqlash joyini ko'rsating");
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Dastur fayli", "*.mdf"));
        File f = fc.showSaveDialog(SceneSaver.oquvMarkaz.primaryStage);
        if (f != null) {
            ModelController.FILE = f;
            mc.saveDocument();
            SceneSaver.oquvMarkaz.primaryStage.setTitle(f.getName());
        }
        ChiqishSaqlashDialogi.setDefault();
    }

    @FXML
    private void chiqish(ActionEvent event) {
        ChiqishSaqlashDialogi.showWithCancel();
    }

    @FXML
    private void filtrla(ActionEvent event) {
    }

    @FXML
    private void addStudent(ActionEvent event) {
        if (!tfIsm.getText().isEmpty() && !tfFam.getText().isEmpty() && correctTel(tfTel.getText())) {
            Student s = new Student(tfIsm.getText(), tfFam.getText());
            String tel = tfTel.getText().startsWith("+998") ? tfTel.getText() : tfTel.getText().startsWith("998") ? "+" + tfTel.getText() : "+998" + tfTel.getText();
            s.setTel(tel);
            tbview.getItems().add(tbview.getItems().size(), new Abiturient(99999999, s));
            Data.bockTests.get(mc.selectedBocktestId).getAbiturients().clear();
            Data.bockTests.get(mc.selectedBocktestId).getAbiturients().addAll(tbview.getItems());
            tfFam.clear();
            tfIsm.clear();
            tfTel.clear();
            mc.resetID();
            ChiqishSaqlashDialogi.changed();

        }
    }

    @FXML
    private void deletStudent(ActionEvent event) {
        int id = tbview.getSelectionModel().getSelectedIndex();
        if (id > -1) {
            int index = abiturients.indexOf(tbview.getSelectionModel().getSelectedItem());
            System.out.println("idex: " + index);
            Abiturient s = tbview.getItems().remove(id);
            mc.resetID();
            if (!tfSearch.getText().isEmpty()) {
                abiturients.remove(index);
            }
            ChiqishSaqlashDialogi.changed();
        }
    }

    @FXML
    private void deletAll(ActionEvent event) {
        int i = JOptionPane.showConfirmDialog(null, "Barchsi o'chirilsinmi", "Tasdilang", JOptionPane.OK_CANCEL_OPTION);
        if (i == JOptionPane.OK_OPTION) {
            System.out.println("delet");
            ChiqishSaqlashDialogi.changed();
            thisBlocktest.getAbiturients().clear();
            tbview.getItems().clear();
            updateTbview();
        }
    }

    @FXML
    private void updateTbview() {
        new SceneSaver().setBlockTestScreenFXML();
    }

    @FXML
    private void oquvchilarjadvali(ActionEvent event) {
        new SceneSaver().setScondScreenFXML();
    }

    @FXML
    private void blocktestlar(ActionEvent event) {
        new SceneSaver().setBlocktestListFXML();
    }

    public static boolean correctTel(String tel) {
        return Pattern.matches("(((\\+?9987[9|5])|(\\+?9989[0|1|3|4|5|7|9]))\\d{7})||((7[9|5])|(9[0|1|3|4|5|7|9]))\\d{7}", tel);
    }

    private void setTitle() {
        SceneSaver.oquvMarkaz.primaryStage.setTitle(mc.FILE.getName() + "  -  Bloktest: " + thisBlocktest.getName());
    }
    

}
