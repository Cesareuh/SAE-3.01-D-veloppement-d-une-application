package app.control;

import app.classes.Modele;
import app.vue.*;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControlClic implements EventHandler<MouseEvent> {
    private Modele m;

    public ControlClic(Modele modele) {
        this.m = modele;
    }

    @Override
    public void handle(MouseEvent event) {
        if (event.getButton() == MouseButton.PRIMARY) {
            if (event.getSource() instanceof VueBloc vb) {
                m.setBlocCourant(vb.getBlocId());
            }
            if (event.getTarget() instanceof VueBloc vb) {
                m.setBlocCourant(vb.getBlocId());
            }
        }
        if (event.getButton() == MouseButton.SECONDARY) {
            // Créer le menu contextuel
            VueContext vc = null;
            if (event.getTarget() instanceof VueBloc vb) {
                afficherContextBloc(event, vc, vb);
            }else if (event.getSource() instanceof VueBloc vb) {
                afficherContextBloc(event, vc, vb);
            }
            if (event.getTarget() instanceof VueViewport) {
                vc = new VueMenuContextVide(m);
                m.setBlocCourant(0);
                vc.show(m.getStage(), event.getScreenX(), event.getScreenY());
            }
        }

        m.notifierObs();
    }

    private void afficherContextBloc(MouseEvent event, VueContext vc, VueBloc vb){
        vc = new VueMenuContextBloc(m);
        m.setBlocCourant(vb.getBlocId());
        vc.show(m.getStage(), event.getScreenX(), event.getScreenY());
    }
}
