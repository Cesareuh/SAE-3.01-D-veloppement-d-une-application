package app;

import app.vue.VueTextuel;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.File;

public class MainTest extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        // Créer un TreeView pour l'affichage de l'explorateur de fichiers
        TreeView<String> fileExplorerTree = new TreeView<>();

        // Créer un VBox pour gérer la disposition verticale
        VBox vBox = new VBox();

        // Créer un objet Modele pour gérer l'état de l'application, en passant 4 arguments
        Modele modele = new Modele(vBox, new Pane(), fileExplorerTree, primaryStage);

        // Créer l'affichage textuel (si nécessaire)
        VueTextuel vt = new VueTextuel();

        // Charger le répertoire à afficher (ici, c'est un répertoire statique pour l'exemple)
        File f = new File("C:/iut/S3/SAE/dev_app_java/SAE-3.01-D-veloppement-d-une-application/src/app");
        System.out.println(f.getName());

        // Mettre à jour le modèle avec le répertoire sélectionné
        modele.setRep(f);
        System.out.println(modele.getRep());

        // Actualiser la vue avec les nouveaux fichiers et répertoires
        vt.actualiser(modele);

        // Créer le panneau de la scène avec l'affichage textuel (ou avec le TreeView si c'est nécessaire)
        Pane pane = new Pane();
        pane.getChildren().add(fileExplorerTree); // Remplacer VueTextuel par TreeView

        // Créer un HBox ou BorderPane pour aligner la TreeView et le contenu
        HBox hBox = new HBox();

        // Ajouter le TreeView à gauche et le contenu à droite (comme un explorateur de fichiers)
        hBox.getChildren().add(fileExplorerTree);
        hBox.getChildren().add(vBox); // Ajouter vBox comme un espace de contenu (vue de détail)

        // Définir le comportement de la TreeView pour qu'elle prenne toute la hauteur disponible
        VBox.setVgrow(fileExplorerTree, javafx.scene.layout.Priority.ALWAYS);

        // Créer la scène avec le panneau et l'ajouter à la fenêtre principale
        Scene scene = new Scene(hBox, 1200, 600);
        primaryStage.setScene(scene);

        // Afficher la fenêtre
        primaryStage.setTitle("DiagraMaker");
        primaryStage.show();
    }
}
