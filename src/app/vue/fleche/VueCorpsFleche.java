package app.vue.fleche;

import app.classes.Modele;
import app.classes.Sujet;
import app.classes.Fleche;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;

public class VueCorpsFleche extends Path implements VueFleche{
    protected int id;

    public VueCorpsFleche (int id){
        this.id = id;
    }

    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele)s;
        this.getElements().removeAll(this.getElements());
        Fleche f = m.getFlecheById(id);
        if(f == null){
            if (this.getParent() instanceof Pane pane) {
                pane.getChildren().remove(this);
            }
        }else {
            MoveTo depart = new MoveTo();
            depart.setX(f.getDepart().getX());
            depart.setY(f.getDepart().getY());
            QuadCurveTo arrivee = new QuadCurveTo();
            arrivee.setX(f.getArrivee2().getX());
            arrivee.setY(f.getArrivee2().getY());

            if(f.getCentre().getX() == -1){
                arrivee.setControlX((f.getDepart().getX() + f.getArrivee().getX()) / 2);
                arrivee.setControlY((f.getDepart().getY() + f.getArrivee().getY()) / 2);
            }else {
                arrivee.setControlX(f.getCentre().getX());
                arrivee.setControlY(f.getCentre().getY());
            }

            if(f.getType() == Fleche.IMPLEMENTATION){
                this.getStrokeDashArray().add(5d);
            }

            this.getElements().addAll(depart, arrivee);
        }
    }

    @Override
    public int getFlecheId() {
        return this.id;
    }
}
