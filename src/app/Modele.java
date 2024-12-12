package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Modele {


    private int blocCourant;
    private Pane viewport;
    private VBox explorateur;
    private GridPane scene;
    private Canvas canvas;
    private List<Fleche> fleches;

    public Modele(GridPane s, Canvas c , Pane p, VBox e){
        this.viewport = p;
        this.explorateur = e;
        this.scene = s;
        this.canvas = c;
    }

    private List<Bloc> chercherDependance(int id ){
        HashMap<Bloc, Integer> dependance = new HashMap<>();
        for

    }


    public void creerFleche(int blocDepart, int blocArrivee, String type) {
        Fleche nouvelleFleche = new Fleche(blocDepart, blocArrivee, type);
        fleches.add(nouvelleFleche);
    }

    public void creerBloc(Class classe, Position p){
        Bloc nouveauBloc = new Bloc();

    }

    public void supprimerBloc(VueBloc b){

    }

    public void translaterBloc(int id, int x, int y){

    }
    public void initialiserBlocs(String repertoire){

    }

    public void afficherClicDroit(Position p){

    }
    public void setBlocCourant(int id){}
}
