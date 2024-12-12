package app;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.*;
import java.util.ArrayList;
import java.util.List;

public class Modele {


    private int blocCourant;
    private Pane viewport;
    private VBox explorateur;
    private GridPane scene;
    private Canvas canvas;

    public Modele(GridPane s, Canvas c , Pane p, VBox e){

    }

    private List<Bloc> chercherDepenance(int id ){
        return null;
    }

    public ArrayList<Fleche> afficherFleches(){
        return null;
    }

    public void creerBloc(Class classe, Position p){

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
