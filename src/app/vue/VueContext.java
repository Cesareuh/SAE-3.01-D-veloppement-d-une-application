package app.vue;

import app.classes.Modele;
import app.classes.Sujet;
import javafx.scene.control.ContextMenu;

public abstract class VueContext extends ContextMenu implements Observateur {
    public VueContext(Modele m) {
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
