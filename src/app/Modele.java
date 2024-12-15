package app;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;

//import java.lang.classfile.Attribute;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modele implements Sujet{

    private final Stage stage;
    private File rep;
    private int blocCourant;
    private Pane viewport;
    private VBox explorateur;
    private VBox root;
    private Stage primaryStage;
    private List<Fleche> fleches;
    private HashMap<Integer, Bloc> blocsMap;
    private int derniereID = 1;
    private List<Observateur> observateurs = new ArrayList<>();
    private TreeView<String> fileExplorerTree;

    public Modele(VBox root, Pane viewport, TreeView<String> fileExplorerTree, Stage stage) {
        this.root = root;
        this.viewport = viewport;
        this.fileExplorerTree = fileExplorerTree;
        this.stage = stage;
    }

    // Cherche les dépendances d'un bloc donné par son id
    private List<Bloc> chercherDependance(int id) {
        List<Bloc> dependances = new ArrayList<>();
        Bloc bloc = getBlocById(id);
        return dependances;
    }

    // Crée une flèche entre deux blocs avec un type donné
    public void creerFleche(int blocDepart, int blocArrivee, String type) {
        Fleche nouvelleFleche = new Fleche(blocDepart, blocArrivee, type);
        fleches.add(nouvelleFleche);
    }

    // Crée un nouveau bloc à partir d'une classe et d'une position
    public void creerBloc(Class className, Position position) {
        // Génère un ID unique pour le nouveau bloc
        int id = derniereID++;

        // Crée une nouvelle instance de Bloc
        Bloc nouveauBloc = new Bloc("NomBloc", "ImageBloc", "ImplementationBloc", "HeritageBloc");

        // Initialise les attributs du bloc
        nouveauBloc.setPosition(position.getX(), position.getY());

        // Ajoute le bloc dans la HashMap avec l'ID unique
        blocsMap.put(id, nouveauBloc);
    }

    public void updateFileExplorer(File directory) {
        TreeItem<String> rootItem = new TreeItem<>(directory.getName());
        rootItem.setExpanded(true);

        // Parcours du répertoire pour ajouter les fichiers et sous-répertoires
        if (directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                TreeItem<String> item = new TreeItem<>(file.getName());
                rootItem.getChildren().add(item);
            }
        }

        // Mettre à jour le TreeView
        fileExplorerTree.setRoot(rootItem);
    }

    // Supprime un bloc donné
    public void supprimerBloc(int id) {
        blocsMap.remove(id);
    }

    // Déplace un bloc vers une nouvelle position
    public void translaterBloc(int id, int x, int y) {
        Bloc bloc = getBlocById(id);
        if (bloc != null) {
            bloc.setPosition(x, y);
        }
    }


    // Initialisation des blocs à partir d'un répertoire
    public void initialiserBlocs(File repertoire) {
        // Logique d'initialisation des blocs depuis un répertoire donné
    }

    // Affiche un menu contextuel lorsque l'utilisateur clique avec le bouton droit
    public void afficherClicDroit(Position p) {
        // Logique pour afficher un menu contextuel en fonction de la position
    }

    // Définit le bloc courant
    public void setBlocCourant(int id) {
        this.blocCourant = id;
    }

    // Récupère un bloc par son id
    public Bloc getBlocById(int id) {
        return blocsMap.get(id);
    }

    public void ajouterObs(Observateur observateur) {
        observateurs.add(observateur);
    }

    // Retirer un observateur
    public void supprimerObs(Observateur observateur) {
        observateurs.remove(observateur);
    }

    // Notifie tous les observateurs des changements
    public void notifierObs() {
        for (Observateur observateur : observateurs) {
            observateur.actualiser(this);
        }
    }

    public File getRep() {
        return rep;
    }

    public void setRep(File rep) {
        this.rep = rep;
    }

    public Stage getStage(){
        return primaryStage;
    }

    public VBox getFileExplorer() {
        return this.explorateur;
    }

    public TreeView<String> getFileExplorerTree() {
        return fileExplorerTree; // TreeView<String> initialisé dans le Modele
    }

}
