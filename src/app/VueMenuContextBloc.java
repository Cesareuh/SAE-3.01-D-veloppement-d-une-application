package app;

import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends VueContext{

    public VueMenuContextBloc(Modele m){
        super(m);
        Menu affichage = new Menu("View");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");
        affichage.getItems().addAll(simple, complexe);
        MenuItem remove = new MenuItem("Remove");
        remove.setOnAction(new ControlButton(m));
        this.getItems().addAll(affichage, remove);
    }
    @Override
    public void actualiser(Sujet s) {
        //TODO
    }
}
