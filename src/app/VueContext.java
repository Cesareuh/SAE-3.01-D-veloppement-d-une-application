package app;

import javafx.scene.control.ContextMenu;

public abstract class VueContext extends ContextMenu implements Observateur {
    @Override
    public void actualiser(Sujet s) {

    }
}
