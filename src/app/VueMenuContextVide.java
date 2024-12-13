package app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextVide extends VueContext{
    public VueMenuContextVide(){
        super();
        Menu affichage = new Menu("Global view");
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
