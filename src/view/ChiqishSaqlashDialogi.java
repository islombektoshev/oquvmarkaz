package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.modelController.ModelController;

/**
 *
 * @author User
 */
public class ChiqishSaqlashDialogi {

    private static int delta = 0;

    public static void showD() {
        if (delta >= 1) {
            show();
        } else {
            System.exit(1);
        }
    }
    
    public static void showDontExit() {
        if (delta >= 1) {
            show();
        }
    }
    

    public static void show() {
        Stage s = new Stage();
        GridPane gp = new GridPane();
        Label lb = new Label("Fayl saqlansinmi?              ");
        lb.setFont(Font.font("Arial Black", 24));
        Button saqlash = new Button("Ha"), chiqish = new Button("Yo'q");
        saqlash.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new ModelController().saveDocument();
                System.exit(1);
            }
        });

        chiqish.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(1);
            }
        });

        gp.add(lb, 0, 0, 2, 1);
        gp.add(new Label(ModelController.FILE.getName()), 0, 1,2,1);
        gp.add(saqlash, 0, 2);
        gp.add(chiqish, 1, 2);

        gp.setPadding(new Insets(5, 15, 5, 15));
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER_LEFT);

        s.setScene(new Scene(gp,400, 150));
        s.setTitle("Chiqish");
        s.show();
    }
    public static void showWithCancel() {
        if(delta<1) System.exit(1);
        Stage s = new Stage();
        GridPane gp = new GridPane();
        Label lb = new Label("Fayl saqlansinmi?              ");
        lb.setFont(Font.font("Arial Black", 24));
        Button saqlash = new Button("Ha"), chiqish = new Button("Yo'q");
        Button qaytarish = new Button("Bekor qilish");
        
        qaytarish.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                s.close();
            }
        });
        
        saqlash.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                new ModelController().saveDocument();
                System.exit(1);
            }
        });

        chiqish.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.exit(1);
            }
        });

        gp.add(lb, 0, 0, 2, 1);
        gp.add(new Label(ModelController.FILE.getName()), 0, 1,2,1);
        gp.add(saqlash, 0, 2);
        gp.add(chiqish, 1, 2);
        gp.add(qaytarish, 2, 2);

        gp.setPadding(new Insets(5, 15, 5, 15));
        gp.setVgap(10);
        gp.setHgap(10);
        gp.setAlignment(Pos.CENTER_LEFT);

        s.setScene(new Scene(gp,400, 150));
        s.setTitle("Chiqish");
        s.show();
    }
//4239
    public static void setDefault() {
        delta = 0;
    }

    public static void changed() {
        delta++;
    }
}
