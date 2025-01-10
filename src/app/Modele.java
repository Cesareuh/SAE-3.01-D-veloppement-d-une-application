package app;

import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Fleche;
import app.classes.Position;
import app.control.ControlClic;
import app.control.ControlDeplacer;
import app.vue.VueBloc;
import app.vue.fleche.*;
import app.vue.VueViewport;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modele implements Sujet{

    private List<Bloc> blocs;
    private final Stage stage;
    private File rep;
    private int blocCourant; // Bloc sélectionné
    private VueViewport viewport;
    private VBox root;
    private int derniereID; // Id du dernier bloc
    private HashMap<Integer, Bloc> blocsMap; // Classes affichées sur le diagramme
    private int derniereFlecheID; // Id de la dernière flèche
    private HashMap<Integer, Fleche> flechesMap; // Flèches affichées sur le diagramme
    private List<Observateur> observateurs = new ArrayList<>();
    private TreeView<String> fileExplorerTree;
    private List<Fichier> fichiers;
    private MenuBar menuBar;


    public Modele(VBox root, VueViewport viewport, TreeView<String> fileExplorerTree, MenuBar menuBar, Stage stage) {
        this.root = root;
        this.viewport = viewport;
        this.fileExplorerTree = fileExplorerTree;
        this.stage = stage;
        this.derniereFlecheID = 0;
        this.blocsMap = new HashMap<>();
        this.flechesMap = new HashMap<>();
        this.derniereID = 0;
        this.menuBar = menuBar;
    }

    // Cherche les dépendances d'un bloc donné par son id
    private List<Bloc> chercherDependance(int id) {
        List<Bloc> dependances = new ArrayList<>();
        Bloc bloc = getBlocById(id);
        return dependances;
    }

    // Affiche un bloc dans le diagramme
    public void afficherBloc(Bloc b) {
        derniereID++;
        blocsMap.put(derniereID, b);
        VueBloc vb = new VueBloc(derniereID, this);
        vb.setOnMouseDragged(new ControlDeplacer(this));
        vb.setOnMouseClicked(new ControlClic(this));
        viewport.getChildren().add(vb);
        ajouterObs(vb);
        refresh();
    }

    public void afficherFleche(Fleche f){
        for(Fleche f2 : flechesMap.values()){
            if(f2.equals(f))return;
        }
        derniereFlecheID++;
        flechesMap.put(derniereFlecheID, f);

        VueCorpsFleche vcf = new VueCorpsFleche(derniereFlecheID);
        VueHitboxFleche vhf = new VueHitboxFleche(derniereFlecheID);
        vhf.setOnMouseDragged(new ControlDeplacer(this));
        VuePointeFleche vpf = new VuePointeFleche(derniereFlecheID);
        viewport.getChildren().addFirst(vhf);
        viewport.getChildren().addFirst(vcf);
        viewport.getChildren().addFirst(vpf);
        ajouterObs(vcf);
        ajouterObs(vpf);
        ajouterObs(vhf);
        if(f.getType() == Fleche.ASSOCIATION) {
            VueNomFleche vnf = new VueNomFleche(derniereFlecheID);
            VueCardFleche vCardf = new VueCardFleche(derniereFlecheID);
            viewport.getChildren().addFirst(vnf);
            viewport.getChildren().addFirst(vCardf);
            ajouterObs(vnf);
            ajouterObs(vCardf);
        }
    }

    public void actualiserFleches(){
        //supprimerFleches();
        for(int idB : blocsMap.keySet()) {

            // Ajoute les flèches du bloc en fonction des attributs
            if(getBlocById(idB).getListAttributs() != null) {
                for (Attribut a : getBlocById(idB).getListAttributs()) {
                    if(getAssociation(a) != -1){
                        afficherFleche(new Fleche(idB, getAssociation(a), Fleche.ASSOCIATION, a));
                    }
                }
            }

            // Ajoute les flèches en fonction des héritages
            if(getBlocById(idB).getHeritage() != null){
                for (int indexB2 : blocsMap.keySet()) {
                    if(getBlocById(indexB2).getNom().contains(getBlocById(idB).getHeritage())){
                        afficherFleche(new Fleche(idB, indexB2, Fleche.HERITAGE));
                    }
                }
            }

            // Ajoute les flèches d'implémentation
            if(getBlocById(idB).getImplementation() != null){
                for (String imp : getBlocById(idB).getImplementation()) {
                    for (int indexB2 : blocsMap.keySet()) {
                        if (getBlocById(indexB2).getNom().contains(imp)){
                            afficherFleche(new Fleche(idB, indexB2, Fleche.IMPLEMENTATION));
                        }
                    }
                }
            }
        }

        for(Fleche f : flechesMap.values()){
            Bloc bDep = getBlocById(f.getBlocDepart());
            Bloc bArrivee = getBlocById(f.getBlocArrivee());
            VueBloc vDep = getVueBlocById(f.getBlocDepart());
            VueBloc vArrivee = getVueBlocById(f.getBlocArrivee());
            Position[] positions;
            Position[] positionsCentre;

            Position departPos;
            Position departTaille;
            if(f.getCentre().getX() != -1){
                departPos = new Position(f.getCentre().getX(), f.getCentre().getY());
                departTaille = new Position(0, 0);
            }else{
                departPos = bDep.getPosition();
                departTaille = new Position(vDep.getWidth(), vDep.getHeight());
            }

            if (departPos.getX() + departTaille.getWidth()/2 < bArrivee.getPosition().getX() + vArrivee.getWidth()/2) {
                positions = f.getStartEnd(bDep.getPosition(), bArrivee.getPosition(), new Position(vDep.getWidth(), vDep.getHeight()), new Position(vArrivee.getWidth(), vArrivee.getHeight()));
                positionsCentre = f.getStartEnd(departPos, bArrivee.getPosition(), new Position(departTaille.getWidth(), departTaille.getHeight()), new Position(vArrivee.getWidth(), vArrivee.getHeight()));
                f.setDepart(positions[0]);
                f.setArrivee(positionsCentre[1]);
            } else {
                positions = f.getStartEnd(bArrivee.getPosition(), bDep.getPosition(), new Position(vArrivee.getWidth(), vArrivee.getHeight()), new Position(vDep.getWidth(), vDep.getHeight()));
                positionsCentre = f.getStartEnd(bArrivee.getPosition(), departPos, new Position(vArrivee.getWidth(), vArrivee.getHeight()), new Position(departTaille.getWidth(), departTaille.getHeight()));
                f.setDepart(positions[1]);
                f.setArrivee(positionsCentre[0]);
            }

            if(f.getType() != Fleche.ASSOCIATION){
                Position arr = new Position();
                arr.setX((f.getBranches()[0].getX() + f.getBranches()[1].getX())/2);
                arr.setY((f.getBranches()[0].getY() + f.getBranches()[1].getY())/2);
                f.setArrivee2(arr);
            }else {
                f.setArrivee2(f.getArrivee());
            }

            if(f.getCentre().getX() == -1){
                double x = (f.getDepart().getX() + f.getArrivee().getX()) / 2;
                double y = (f.getDepart().getY() + f.getArrivee().getY()) / 2;
                double rand = Math.random()*200-100;
                double rand2 = Math.random()*200-100;
                f.setCentre(new Position(x + rand, y+rand2));
            }
        }
        notifierObs();
    }

    public void supprimerFleches(){
        ArrayList<Integer> aSupprimerFleche = new ArrayList<>(flechesMap.keySet());

        for (int id : aSupprimerFleche){
            flechesMap.remove(id);
        }

        notifierObs();

    }

    /**
     * Retourne le bloc correspondant à l'attribut sur le diagramme
     * @param a
     * @return
     */
    public int getAssociation(Attribut a){
        for (int indexB2 : blocsMap.keySet()) {
            if (a.getType().contains(getBlocById(indexB2).getNom()+">") || a.getType().contains("<"+getBlocById(indexB2).getNom()) || a.getType().equals(getBlocById(indexB2).getNom())) {
                return indexB2;
            }
        }
        return -1;
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
        ArrayList<Observateur> aSupprimer = new ArrayList<>();
        ArrayList<Integer> flechesASupprimer = new ArrayList<>();

        // Supprime le bloc de la liste
        blocsMap.remove(blocCourant);

        // Trouver les flèches liées au bloc à supprimer
        for(int idF : flechesMap.keySet()){
            if(flechesMap.get(idF).getBlocDepart() == blocCourant || flechesMap.get(idF).getBlocArrivee() == blocCourant){
                flechesASupprimer.add(idF);
            }
        }

        // Supprimer les flèches de la liste
        for(int idF : flechesASupprimer){
            flechesMap.remove(idF);
        }

        // Trouver la vue du bloc et des flèches à supprimer
        for(Observateur obs : observateurs){
            if(obs instanceof VueBloc vb){
                if(vb.getBlocId() == blocCourant){
                    aSupprimer.add(obs);
                }
            }
        }
        // Notifier les vues de leur suppression
        notifierObs();

        // Supprimer les vues de la liste de observateurs
        for(Observateur obs : aSupprimer) {
            supprimerObs(obs);
        }
    }

    // Déplace un bloc vers une nouvelle position
    public void translaterBloc(int id, int x, int y) {
        Bloc bloc = getBlocById(id);
        if (bloc != null) {
            bloc.setPosition(new Position(x,y));
        }
        refresh();
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
        if(repertoire.isDirectory()) {
            Repertoire rep = new Repertoire(repertoire);
            fichiers.addAll(rep.getFichiers());
        } else {
            fichiers.add(new Fichier(repertoire));
        }
        compiler();
        notifierObs();
    }

    public void ajouterFichier(File fichier) {
        // Ajout du fichier à la liste
        fichiers.add(new Fichier(fichier));
        notifierObs(); // Mise à jour de l'affichage
    }

    // Définit le bloc courant
    public void setBlocCourant(int id) {
        this.blocCourant = id;
    }

    // Récupère un bloc par son id
    public Bloc getBlocById(int id) {
        return blocsMap.get(id);
    }

    public VueBloc getVueBlocById(int id){
        for(Observateur obs : observateurs){
            if(obs instanceof VueBloc vb && vb.getBlocId() == id){
                return vb;
            }
        }
        return null;
    }

    public void ajouterObs(Observateur observateur) {observateurs.add(observateur);}

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

    /**
     * Actualise l'affichage
     */
    public void refresh(){
        // Quand un bloc est créé, sa largeur est initialisée à 0 car javafx n'a pas eu le temps de calculer sa taille
        // Il faut donc attendre un peu avant de pouvoir l'utiliser
        Timeline t = new Timeline(new KeyFrame(Duration.millis(30), event -> {
            actualiserFleches();
        }));
        t.setCycleCount(1);
        t.play();
        notifierObs();
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

    public MenuBar getMenuBar(){
        return this.menuBar;
    }

    public TreeView<String> getFileExplorerTree() {
        return fileExplorerTree; // TreeView<String> initialisé dans le Modele
    }

    public HashMap<Integer, Fleche> getFleches() {
        return flechesMap;
    }

    public Fleche getFlecheById(int id){
        return flechesMap.get(id);
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

    public List<Fichier> getFichiers(){
        return fichiers;
    }


    public void ajouterAttributDansBloc(String nomBloc, String autorisation, String nomAttribut, String typeAttribut) {
        for (Bloc bloc : blocs) {
            if (bloc.getNom().equals(nomBloc)) {
                bloc.ajouterAttribut(autorisation, nomAttribut, typeAttribut);  // Ajout avec l'autorisation
                break;
            }
        }
    }


    public void modifierNomBloc(String newName) {
        Bloc bloc = getBlocById(blocCourant);
        if (bloc != null) {
            bloc.setNom(newName); // Modifie le nom du bloc
            notifierObs();  }
    }



    public void ajouterMethodeDansBloc(String nomBloc, String autorisation, String nomMethode, String typeRetour, List<String> parametres) {
        for (Bloc bloc : blocs) {
            if (bloc.getNom().equals(nomBloc)) {
                bloc.ajouterMethode(autorisation, nomMethode, typeRetour, parametres);  // Ajouter l'autorisation ici
                break;
            }
        }
    }

    // Exemple de méthode pour ajouter un attribut dans un bloc
    public void ajouterAttributDansBloc(String nomBloc, String nomAttribut, String typeAttribut) {
        // Logique pour ajouter un attribut à un bloc
        System.out.println("Attribut ajouté : " + nomAttribut + " de type " + typeAttribut);
    }
    public void ajouterBlocMap(Bloc b){
        blocsMap.put(getDerniereID()+1, b);
    }


    public Bloc getBlocSelectionne() {
        return blocsMap.get(blocCourant);
    }


    public HashMap<Integer, Bloc> getBlocsMap() {
        return blocsMap;
    }

    public HashMap<Integer, Fleche> getFlechesMap() {
        return flechesMap;
    }

    public void compiler(){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        List<String> arguments = new ArrayList<>();
        List<String> sourceFichier = new ArrayList<>();
        String[] compileArguments;
        for(Fichier f : fichiers){
            sourceFichier.add(f.getF().getAbsolutePath());
        }
        arguments.add("-d");
        arguments.add("projClass");
        arguments.addAll(sourceFichier);
        compileArguments = arguments.toArray(new String[0]);
        int res = compiler.run(null, null, null, compileArguments);
        if(res == 0){
            System.out.println("Compilation reussie");
        }else{
            System.out.println("Erreur lors de la compilation");
        }
    }

    public void ajouterClass(File f){
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        int res = compiler.run(null, null, null, "-d", "projClass", f.getAbsolutePath());
        if(res == 0){
            System.out.println("Compilation reussie");
            ajouterFichier(f);
        }else{
            System.out.println("Erreur lors de la compilation");
        }
    }

    public void suppRepClass(){
        File file = new File("./projClass");
        Repertoire repertoire = new Repertoire(file);
        repertoire.supp();
    }
}


