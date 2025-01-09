package app.vue.fleche;

import app.Modele;
import app.Sujet;
import app.classes.Attribut;
import app.classes.Fleche;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class VueCardFleche extends Text implements VueFleche{
    private int id;

    public VueCardFleche(int id){
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
            this.setText("1");
            if(a.getType().contains("List") || a.getType().contains("Map")){
                this.setText("*");
            }

            double dist = 35;
            double cardAngle = f.getAngle() + 5*Math.PI/6;

            double x;
            double y;

            this.setX(f.getArrivee().getX() + Math.cos(cardAngle) * dist);
            this.setY(f.getArrivee().getY() + Math.sin(cardAngle) * dist);
        }
    }

    @Override
    public int getFlecheId() {
        return id;
    }
}
