package app.vue;

import app.Modele;
import app.Observateur;
import app.Sujet;
import javafx.scene.control.ContextMenu;

public abstract class VueContext extends ContextMenu implements Observateur {
    public VueContext(Modele m) {
    }

    @Override
    public void actualiser(Sujet s) {

    }
}
