package app.vue.fleche;

import app.classes.Modele;
import app.classes.Sujet;
import app.classes.Fleche;
import javafx.scene.layout.Pane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;

public class VuePointeFleche extends Path implements VueFleche {
    private int id;

    public VuePointeFleche(int id) {
        this.id = id;
    }

    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele) s;
        this.getElements().removeAll(this.getElements());
        Fleche f = m.getFlecheById(id);
        if (f == null) {
            if (this.getParent() instanceof Pane pane) {
                pane.getChildren().remove(this);
            }
        } else {
            this.getElements().removeAll(this.getElements());

            LineTo brancheHaut = new LineTo();
            brancheHaut.setX(f.getBranches()[0].getX());
            brancheHaut.setY(f.getBranches()[0].getY());

            LineTo brancheBas = new LineTo();
            brancheBas.setX(f.getBranches()[1].getX());
            brancheBas.setY(f.getBranches()[1].getY());

            MoveTo arrivee = new MoveTo(f.getArrivee().getX(), f.getArrivee().getY());

            this.getElements().addAll(arrivee, brancheHaut, arrivee, brancheBas);
            if(f.getType() != Fleche.ASSOCIATION){
                this.getElements().add(brancheHaut);
            }
        }
    }

    public int getFlecheId() {
        return id;
    }
}
