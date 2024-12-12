package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ControlButton implements EventHandler<ActionEvent> {

    private final Modele modele;
    private final TextField cheminField;
    private final Button button;

    public ControlButton(Modele modele, TextField cheminField, Button button) {
        this.modele = modele;
        this.cheminField = cheminField;
        this.button = button;
        this.button.setOnAction(this);
    }

    public void handle(ActionEvent event) {
        String buttonId = button.getId();

        if ("btnGenerer".equals(buttonId)) {
            String chemin = cheminField.getText();

            if (chemin == null || chemin.isEmpty()) {
                System.out.println("veuillez renseigner un chemin");
            } else {
                try {
                    modele.initialiserBlocs(chemin);
                    System.out.println("Diagramme généré avec : " + chemin);
                } catch (Exception e) {
                    System.out.println("Erreur lors de la génération du diagramme : " + e.getMessage());
                }
            }
        } else {
            System.out.println("l'action n existe pas : " + buttonId);
        }
    }
}
