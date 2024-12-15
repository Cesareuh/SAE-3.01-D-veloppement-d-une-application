package app;

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.Scene;

public class ControlClicDroit implements EventHandler<ContextMenuEvent> {
    private Modele modele;

    public ControlClicDroit(Modele modele) {
        this.modele = modele;
    }

    @Override
    public void handle(ContextMenuEvent event) {
        // Créer le menu contextuel 
        ContextMenu contextMenu = new ContextMenu();

        // Ajouter des éléments au menu contextuel
        MenuItem item1 = new MenuItem("Option 1");
        item1.setOnAction(e -> {
            // Action pour Option 1
        });
        MenuItem item2 = new MenuItem("Option 2");
        item2.setOnAction(e -> {
            // Action pour Option 2
        });
        contextMenu.getItems().addAll(item1, item2);

        // Afficher le menu contextuel, en spécifiant la scène principale comme propriétaire
        contextMenu.show((Node) event.getSource(), event.getScreenX(), event.getScreenY());
    }
}
