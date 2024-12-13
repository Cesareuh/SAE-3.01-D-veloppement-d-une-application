package app;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("DiagraMaker");

        VBox root = new VBox();
        HBox base = new HBox();
        MenuBar menuBar = new MenuBar();
        VBox fileExplorer = new VBox();
        Pane viewport = new Pane();
        Canvas canvas = new Canvas();

        BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        Border border = new Border(bs);

        fileExplorer.setBorder(border);
        viewport.setBorder(border);

        Modele m = new Modele(root, canvas, viewport, fileExplorer, primaryStage);

        // Ajout du menu supérieur
        Menu fileMenu = new Menu("File");
        MenuItem selectRep = new MenuItem("Select directory");
        selectRep.setOnAction(new ControlButton(m));
        MenuItem export = new MenuItem("Export as PNG");
        export.setOnAction(new ControlButton(m));

        Menu editMenu = new Menu("Edit");
        fileMenu.getItems().addAll(selectRep, export);

        VueMenuContextBloc cb = new VueMenuContextBloc();
        root.setOnContextMenuRequested(e -> cb.show(root, e.getScreenX(), e.getScreenY()));


        /*
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

         */

        menuBar.getMenus().addAll(fileMenu, editMenu);
        viewport.getChildren().add(canvas);
        base.getChildren().addAll(fileExplorer, viewport);
        root.getChildren().addAll(menuBar, base);
        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}