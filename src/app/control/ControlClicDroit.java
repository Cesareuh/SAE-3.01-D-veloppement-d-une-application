package app.control;

import app.*;
import app.vue.*;
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
            VueContext vc = null;
            if (event.getTarget() instanceof VueBloc vb) {
                afficherContextBloc(event, vc, vb);
            }else if (event.getSource() instanceof VueBloc vb) {
                afficherContextBloc(event, vc, vb);
            }
            if ((event.getTarget() instanceof VueViewport)) {
                vc = new VueMenuContextVide(m);
                m.setBlocCourant(0);
                vc.show(m.getStage(), event.getScreenX(), event.getScreenY());

                System.out.println(event.getTarget());
            }

        }
    }

    private void afficherContextBloc(MouseEvent event, VueContext vc, VueBloc vb){
        vc = new VueMenuContextBloc(m);
        m.setBlocCourant(vb.getBlocId());
        vc.show(m.getStage(), event.getScreenX(), event.getScreenY());
    }
}
