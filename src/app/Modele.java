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
    private HashMap<Integer, Fleche> flechesMap;


    private HashMap<Integer, Bloc> blocsMap;
    private int derniereID;
    private int derniereFlecheID;
    private List<Observateur> observateurs = new ArrayList<>();
    private TreeView<String> fileExplorerTree;
    private List<Fichier> fichiers;

    public Modele(VBox root, Pane viewport, TreeView<String> fileExplorerTree, Stage stage) {
        this.root = root;
        this.viewport = viewport;
        this.fileExplorerTree = fileExplorerTree;
        this.stage = stage;
        this.derniereID = 0;
        this.derniereFlecheID = 0;
        this.blocsMap = new HashMap<>();
        this.flechesMap = new HashMap<>();
    }

    // Cherche les dépendances d'un bloc donné par son id
    private List<Bloc> chercherDependance(int id) {
        List<Bloc> dependances = new ArrayList<>();
        Bloc bloc = getBlocById(id);
        return dependances;
    }

    /*
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
     */

    // Affiche un bloc dans le diagramme
    public void afficherBloc(Bloc b){
        derniereID++;
        blocsMap.put(derniereID, b);
        VueBloc vb = new VueBloc(derniereID);
        vb.setOnMouseDragged(new ControlDragAndDrop(this));
        vb.setOnMouseClicked(new ControlClicDroit(this));
        viewport.getChildren().add(vb);
        // TODO fleches
        for(Attribut a : b.getListAttributs()){
            for(Bloc b2 : blocsMap.values()){
                if(b2.getNom().equals(a.getType())){
                    System.out.println("dépendance");
                }
            }
        }
        ajouterObs(vb);
        notifierObs();
    }

    public void afficherFleche(Fleche f){
        derniereFlecheID++;
        VueFleche vf = new VueFleche(derniereFlecheID);
        ajouterObs(vf);
        notifierObs();
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

    // Supprime le bloc sélectionné
    public void supprimerBlocSelect() {
        Observateur aSupprimer = null;
        blocsMap.remove(blocCourant);
        for(Observateur obs : observateurs){
            if(obs instanceof VueBloc vb){
                if(vb.getBlocId() == blocCourant){
                    aSupprimer = obs;
                }
            }
        }
        notifierObs();
        supprimerObs(aSupprimer);
    }

    // Déplace un bloc vers une nouvelle position
    public void translaterBloc(int id, int x, int y) {
        Bloc bloc = getBlocById(id);
        if (bloc != null) {
            bloc.setPosition(new Position(x,y));
        }
        notifierObs();
    }

    // Transforme une position sur l'écran en position par rapport au viewport
    public Position screenPosToViewportPos(Position p){
        Position res = new Position(0,0);
        res.setX((int) (p.getX() - this.stage.getX() - this.viewport.getLayoutX()));
        res.setY((int) (p.getY() - this.stage.getY() - this.viewport.getParent().getLayoutY()));
        return res;
    }


    // Initialisation des blocs à partir d'un répertoire
    public void initialiserFichiers(File repertoire) {
        this.fichiers = new ArrayList<>();
        if(repertoire.isDirectory()){
            Repertoire rep = new Repertoire(repertoire);
            fichiers.addAll(rep.getFichiers());
        }else fichiers.add(new Fichier(repertoire));
        notifierObs();
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
        return stage;
    }

    public Pane getViewport(){
        return viewport;
    }

    public VBox getFileExplorer() {
        return this.explorateur;
    }

    public TreeView<String> getFileExplorerTree() {
        return fileExplorerTree; // TreeView<String> initialisé dans le Modele
    }

    public HashMap<Integer, Fleche> getFleches() {
        return flechesMap;
    }

    public int getDerniereID() {
        return derniereID;
    }


    public void resetDerniereID() {
        this.derniereID = 0;
        this.blocsMap.clear();
    }

    public int getBlocCourant() {
        return blocCourant;
    }


}
