package app.vue.fleche;

import app.Modele;
import app.Sujet;
import app.classes.Attribut;
import app.classes.Fleche;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class VueNomFleche extends Text implements VueFleche {
    private int id;

    public VueNomFleche(int id) {
        this.id = id;
    }

    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele) s;
        Fleche f = m.getFlecheById(id);
        if (f == null) {
            if (this.getParent() instanceof Pane pane) {
                pane.getChildren().remove(this);
            }
        } else {

            Attribut a = f.getAttribut();
            this.setText(a.getNom() + " : " + a.getType());

            double x;
            double y;

            x = f.getCentre().getX();
            y = f.getCentre().getY();

            this.setX(x);
            this.setY(y);

        }
    }

    public int getFlecheId() {
        return id;
    }
}
