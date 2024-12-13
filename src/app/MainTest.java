package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import test.TestClass;

import java.io.File;

public class MainTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        Modele modele = new Modele(new VBox(), new Pane(), new VBox(), new Stage());
        VueTextuel vt = new VueTextuel();
        File f = new File("C:/iut/S3/SAE/dev_app_java/SAE-3.01-D-veloppement-d-une-application/src/app");
        System.out.println(f.getName());
        modele.setRep(f);
        System.out.println(modele.getRep());
        vt.actualiser(modele);
        Pane pane = new Pane();
        pane.getChildren().add(vt);
        Scene scene = new Scene(pane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
