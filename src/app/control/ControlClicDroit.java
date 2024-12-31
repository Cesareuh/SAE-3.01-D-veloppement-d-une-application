package app.control;

import app.*;
import app.vue.VueBloc;
import app.vue.VueContext;
import app.vue.VueMenuContextBloc;
import app.vue.VueMenuContextVide;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControlClicDroit implements EventHandler<MouseEvent> {
    private Modele m;

    public ControlClicDroit(Modele modele) {
        this.m = modele;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.SECONDARY) {
            // Créer le menu contextuel
            VueContext vc;
            if (!(event.getTarget() instanceof Pane)) {
                if (event.getSource() instanceof VueBloc vb) {
                    vc = new VueMenuContextBloc(m);
                    m.setBlocCourant(vb.getBlocId());
                    vc.show(m.getStage(), event.getScreenX(), event.getScreenY());
                }
            } else {
                vc = new VueMenuContextVide(m);
                m.setBlocCourant(0);
                vc.show(m.getStage(), event.getScreenX(), event.getScreenY());
            }

        }
    }
}
