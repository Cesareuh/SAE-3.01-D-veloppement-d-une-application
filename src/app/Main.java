package app;

import app.classes.Modele;
import app.classes.WindowListener;
import app.control.ControlButton;
import app.control.ControlClic;
import app.control.ControlDragNDrop;
import app.vue.VueFichiers;
import app.vue.VueTextuel;
import app.vue.VueViewport;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        // Titre de la fenêtre principale
        primaryStage.setTitle("DiagraMaker");

        // Layouts principaux
        VBox root = new VBox();
        HBox base = new HBox();
        MenuBar menuBar = new MenuBar();
        menuBar.setUseSystemMenuBar(true);

        // File Explorer (TreeView pour afficher l'arborescence)
        VueFichiers fileExplorer = new VueFichiers();
        VueViewport viewport = new VueViewport();

        // Définir une bordure autour du fileExplorer et du viewport
        BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        Border border = new Border(bs);

        fileExplorer.setBorder(border);
        viewport.setBorder(border);

        // Création du menu
        Menu fileMenu = new Menu("File");
        MenuItem selectRep = new MenuItem("Select directory");
        MenuItem export = new MenuItem("Export as image");
        MenuItem plantuml = new MenuItem("Generate plantuml");
        fileMenu.getItems().addAll(selectRep, export, plantuml);

        menuBar.getMenus().addAll(fileMenu);

        // Création du modèle (Modele gère les interactions)
        Modele m = new Modele(root, viewport, fileExplorer, menuBar, primaryStage);

        selectRep.setOnAction(new ControlButton(m));  // Action pour sélectionner un répertoire
        export.setOnAction(new ControlButton(m));  // Action pour exporter en PNG
        plantuml.setOnAction(new ControlButton(m));

        //Création de la vue textuelle
        VueTextuel vt = new VueTextuel();
        m.ajouterObs(vt);
        m.ajouterObs(fileExplorer);


        // Ajouter les composants dans le layout principal
        base.getChildren().addAll(fileExplorer, viewport);
        root.getChildren().addAll(menuBar, base);

        ControlDragNDrop cdb = new ControlDragNDrop(m);

        // Scene et affichage de la fenêtre principale
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();

        // Gestion des clics droit
        viewport.setOnMouseClicked(new ControlClic(m));

        // Ajuster la taille du viewport et du fileExplorer
        viewport.setMinSize(primaryStage.getWidth() * ((double) 3 / 4), primaryStage.getHeight() - menuBar.getHeight());
        fileExplorer.setMinSize(primaryStage.getWidth() * ((double) 1 / 4), primaryStage.getHeight() - menuBar.getHeight());

        // Ajuster taille éléments en fonction de la taille de la fenêtre
        WindowListener wl = new WindowListener(m);
        primaryStage.widthProperty().addListener(wl);
        primaryStage.heightProperty().addListener(wl);

        // Raccourcis clavier
        // D pour supprimer un bloc sélectionné
        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if(keyEvent.getCode() == KeyCode.D) {
                    m.supprimerBlocSelect();
                }
                m.notifierObs();
            }
        });
    }
}
