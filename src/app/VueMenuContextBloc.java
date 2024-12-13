package app;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends ContextMenu implements Observateur{

    public VueMenuContextBloc(){
        super();
        Menu affichage = new Menu("Affichage");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complexe");
        affichage.getItems().addAll(simple, complexe);
        this.getItems().addAll(affichage);
    }
    @Override
    public void actualiser() {
        //TODO
    }
}
