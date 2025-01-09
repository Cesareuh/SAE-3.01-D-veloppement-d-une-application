package app.vue.fleche;

import app.Modele;
import app.Sujet;
import app.classes.Fleche;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

public class VueHitboxFleche extends VueCorpsFleche{

    public VueHitboxFleche(int id){
        super(id);
    }

    @Override
    public void actualiser(Sujet s) {
        super.actualiser(s);
        this.setStroke(Color.TRANSPARENT);
        this.setStrokeWidth(15);
    }

    @Override
    public int getFlecheId() {
        return this.id;
    }
}
