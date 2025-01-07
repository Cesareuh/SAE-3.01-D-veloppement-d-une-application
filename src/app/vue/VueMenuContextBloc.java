package app.vue;

import app.Modele;
import app.Sujet;
import app.control.ControlButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends VueContext {

    public VueMenuContextBloc(Modele m) {
        super(m);
        // Création du menu "View" avec les options "Simple" et "Complex"
        Menu affichage = new Menu("View");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");
        affichage.getItems().addAll(simple, complexe);

        // Création des MenuItems "Remove" et "Modify"
        MenuItem remove = new MenuItem("Remove");
        MenuItem modifyItem = new MenuItem("Modify");

        // Associer les actions pour les deux MenuItems
        remove.setOnAction(e -> new ControlButton(m).handleRemoveAction());
        modifyItem.setOnAction(e -> new ControlButton(m).handleModifyAction());

        // Ajouter les items au menu contextuel
        this.getItems().addAll(affichage, remove, modifyItem);
    }

    @Override
    public void actualiser(Sujet s) {
        // TODO: Implémenter la mise à jour
    }
}
