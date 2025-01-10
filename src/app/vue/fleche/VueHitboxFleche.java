package app.vue.fleche;

import app.classes.Sujet;
import javafx.scene.paint.Color;

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
