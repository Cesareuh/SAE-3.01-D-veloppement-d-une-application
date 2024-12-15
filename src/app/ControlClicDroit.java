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
        VueContext vc;
        ContextMenuEvent contextEvent = (ContextMenuEvent)event;
        if(event.getTarget() instanceof VueBloc){
            vc = new VueMenuContextBloc();
        }else{
            vc = new VueMenuContextVide();
        }

        // Afficher le menu contextuel, en spécifiant la scène principale comme propriétaire
        vc.show(modele.getStage(), event.getScreenX(), event.getScreenY());
    }
}
