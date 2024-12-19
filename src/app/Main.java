package app;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

        // File Explorer (TreeView pour afficher l'arborescence)
        TreeView<String> fileExplorer = new TreeView<>();
        Pane viewport = new Pane();

        // Définir une bordure autour du fileExplorer et du viewport
        BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2));
        Border border = new Border(bs);

        fileExplorer.setBorder(border);
        viewport.setBorder(border);

        // Création du modèle (Modele gère les interactions)
        Modele m = new Modele(root, viewport, fileExplorer, primaryStage);

        // Création du menu
        Menu fileMenu = new Menu("File");
        MenuItem selectRep = new MenuItem("Select directory");
        selectRep.setOnAction(new ControlButton(m));  // Action pour sélectionner un répertoire
        MenuItem export = new MenuItem("Export as PNG");
        export.setOnAction(new ControlButton(m));  // Action pour exporter en PNG
        fileMenu.getItems().addAll(selectRep, export);

        Menu editMenu = new Menu("Edit");
        menuBar.getMenus().addAll(fileMenu, editMenu);


        // Ajouter les composants dans le layout principal
        base.getChildren().addAll(fileExplorer, viewport);
        root.getChildren().addAll(menuBar, base);


        // Scene et affichage de la fenêtre principale
        primaryStage.setScene(new Scene(root, 1200, 600));
        primaryStage.show();

        // Gestion des clics droit
        root.setOnMouseClicked(new ControlClicDroit(m));

        // Ajuster la taille du viewport et du fileExplorer
        viewport.setMinSize(primaryStage.getWidth() * ((double) 3 / 4), primaryStage.getHeight() - menuBar.getHeight());
        fileExplorer.setMinSize(primaryStage.getWidth() * ((double) 1 / 4), primaryStage.getHeight() - menuBar.getHeight());



    }
}
