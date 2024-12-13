package app;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends VueContext{

    public VueMenuContextBloc(){
        super();
        Menu affichage = new Menu("View");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");
        affichage.getItems().addAll(simple, complexe);
        this.getItems().addAll(affichage);
    }
    @Override
    public void actualiser(Sujet s) {
        //TODO
    }
}
