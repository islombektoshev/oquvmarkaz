package view;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.model.Data;
import model.model.Group;
import model.model.Subject;
import model.modelController.ModelController;

/**
 * FXML Controller class
 *
 * @author Islom
 */
public class CreatationBlockTestController implements Initializable {

    @FXML
    private RadioButton rbEvry;
    @FXML
    private RadioButton rbOne;
    @FXML
    private VBox vboxFanlar;
    @FXML
    private VBox vboxGrular;

    ModelController mc = new ModelController();

    CheckBox[] subjects = new CheckBox[Data.subjects.size()];
    CheckBox[] groups = new CheckBox[Data.groups.size()];
    List<Group> groupslist = new ArrayList<>();
    List<Subject> subjectslist = new ArrayList<>();

    /**
     * Initializes the controller class.
     */
    ToggleGroup tg = new ToggleGroup();
    @FXML
    private DatePicker datepicer;
    @FXML
    private TextField tfname;
    @FXML
    private CheckBox cbnomalum;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
    }

    public void init() {
        setTitle();
        rbEvry.setToggleGroup(tg);
        rbOne.setToggleGroup(tg);

        for (int i = 0; i < subjects.length; i++) {
            final Subject subject = Data.subjects.get(i);
            final CheckBox checkBox = subjects[i] = new CheckBox(subject.getSubjectName());
            checkBox.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (checkBox.isSelected()) {
                        subjectslist.add(subject);
                    } else if (subjectslist.contains(subject)) {
                        subjectslist.remove(subject);
                    }
                }
            });
            vboxFanlar.getChildren().add(checkBox);
        }

        for (int i = 0; i < groups.length; i++) {
            final Group group = Data.groups.get(i);
            final CheckBox checkBox = groups[i] = new CheckBox(group.getGroupName());
            checkBox.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {
                    if (checkBox.isSelected()) {
                        groupslist.add(group);
                    } else if (groupslist.contains(group)) {
                        groupslist.remove(group);
                    }
                }
            });
            vboxGrular.getChildren().add(checkBox);
        }
        
        ModelController.blocktestCreationTypeNOMALUM = ModelController.BlocktestCreationType.NOMALUMNI_OLMA;
        cbnomalum.setOnAction((ActionEvent event) -> {
            if(cbnomalum.isSelected())
                ModelController.blocktestCreationTypeNOMALUM = ModelController.BlocktestCreationType.NOMALUMNI_OL;
            else
                ModelController.blocktestCreationTypeNOMALUM = ModelController.BlocktestCreationType.NOMALUMNI_OLMA;
        });
        setFormatToDatePicer();
        
        

    }

    private void setFormatToDatePicer() {
        datepicer.setValue(LocalDate.now());
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
        datepicer.setConverter(stringConverter);
    }

    @FXML
    private void keyingi(ActionEvent event) {
        mc.addNewBlockTest(tfname.getText(), new Date(datepicer.getValue().getYear() - 1900, datepicer.getValue().getMonthValue(), datepicer.getValue().getDayOfMonth()), groupslist, subjectslist);
        mc.selectedBocktestId = Data.bockTests.size() - 1;
        new SceneSaver().setBlockTestScreenFXML();
        SceneSaver.SECOND_STAGE.hide();
    }

    @FXML
    private void bekorqilish(ActionEvent event) {
        SceneSaver.SECOND_STAGE.close();
    }

    @FXML
    private void setBlocktestCreation(ActionEvent event) {
        if (rbEvry.isSelected()) {
            ModelController.blocktestCreationType = ModelController.BlocktestCreationType.EVRY;
        }
        if (rbOne.isSelected()) {
            ModelController.blocktestCreationType = ModelController.BlocktestCreationType.ONE;
        }
    }

    private void setTitle() {
        SceneSaver.SECOND_STAGE.setTitle("Bloktest yaratish");
    }
}
