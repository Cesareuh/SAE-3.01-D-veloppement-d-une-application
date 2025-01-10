package app.vue;

import app.classes.Modele;
import app.classes.Sujet;
import app.classes.Bloc;
import app.control.ControlButton;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends VueContext {

    public VueMenuContextBloc(Modele m) {
        super(m);
        ControlButton controlButton = new ControlButton(m);

        // Menu "View" existant avec les options "Simple" et "Complex"
        Menu affichage = new Menu("View");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");

        simple.setOnAction(e -> {
            Bloc blocSelectionne = m.getBlocSelectionne();
            if (blocSelectionne != null) {
                blocSelectionne.setAffichageSimple(true);
                m.refresh();
                System.out.println("Vue simple activée pour le bloc : " + blocSelectionne.getNom());
            } else {
                System.out.println("Aucun bloc sélectionné.");
            }
        });

        complexe.setOnAction(e -> {
            Bloc blocSelectionne = m.getBlocSelectionne();
            if (blocSelectionne != null) {
                blocSelectionne.setAffichageSimple(false);
                m.refresh();
                System.out.println("Vue complexe activée pour le bloc : " + blocSelectionne.getNom());
            } else {
                System.out.println("Aucun bloc sélectionné.");
            }
        });

        affichage.getItems().addAll(simple, complexe);

        // Menu item pour changer la couleur de fond
        MenuItem changeColorItem = new MenuItem("Change Background Color");
        changeColorItem.setOnAction(e -> controlButton.handle(new ActionEvent(changeColorItem, null)));

        // Ajouter l'item "Change Background Color" au menu
        affichage.getItems().add(changeColorItem);

        // Menu item pour "Remove"
        MenuItem removeItem = new MenuItem("Remove");
        removeItem.setOnAction(e -> controlButton.handleRemoveAction());  // Appel de la méthode pour gérer l'action "Remove"

        // Menu item pour "Modify"
        MenuItem modifyItem = new MenuItem("Modify");
        modifyItem.setOnAction(e -> controlButton.handleModifyAction());  // Appel de la méthode pour gérer l'action "Modify"

        // Ajouter les items "Remove" et "Modify" au menu
        this.getItems().addAll(affichage, removeItem, modifyItem);
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Modele modele) {
            System.out.println("Menu contextuel mis à jour.");
        }
    }
}
