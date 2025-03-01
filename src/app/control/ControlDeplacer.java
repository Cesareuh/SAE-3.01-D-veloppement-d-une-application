package app.control;

import app.classes.Modele;
import app.classes.Fleche;
import app.classes.Position;
import app.vue.VueBloc;
import app.vue.fleche.VueFleche;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControlDeplacer implements EventHandler<MouseEvent>{

    private Modele m;

    public ControlDeplacer(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(MouseEvent event) {

        // Drag and Drop d'un bloc
        if(event.getButton() == MouseButton.PRIMARY) {
            if (event.getSource() instanceof VueBloc vb) {
                int id = vb.getBlocId();
                Pane viewport = m.getViewport();
                // Convertit la position du curseur en fonction du viewport au lieu de la scène complète
                Position viewport_pos = m.screenPosToViewportPos(new Position(event.getScreenX(), event.getScreenY()));

                // Centre le curseur sur la boite
                int x = (int) (viewport_pos.getX() - vb.getWidth() / 2);
                int y = (int) (viewport_pos.getY() - vb.getHeight() / 2);

                // Ne pas dépasser les limites (avec un ecart de 5 ce qui empêche les bugs)
                int ecart = 5;
                if (x <= ecart) {
                    x = ecart;
                }
                if (x >= viewport.getWidth() - vb.getWidth() - ecart) {
                    x = (int) (viewport.getWidth() - vb.getWidth() - ecart);
                }
                if (y < ecart) {
                    y = ecart;
                }
                if (y >= viewport.getHeight() - vb.getHeight() - ecart) {
                    y = (int) (viewport.getHeight() - vb.getHeight() - ecart);
                }
                m.translaterBloc(id, x, y);
            }

            if(event.getSource() instanceof VueFleche vf){
                Fleche f = m.getFlecheById(vf.getFlecheId());
                f.setCentre(new Position(event.getX(), event.getY()));
                m.refresh();
            }
        }
    }
}