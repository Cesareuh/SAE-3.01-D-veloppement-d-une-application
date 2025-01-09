package app.vue;

import app.Modele;
import app.Sujet;
import app.classes.Bloc;
import app.control.ControlButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

public class VueMenuContextBloc extends VueContext {


    public VueMenuContextBloc(Modele m) {
        super(m);
        ControlButton controlButton = new ControlButton(m);

        // Création du menu "View" avec les options "Simple" et "Complex"
        Menu affichage = new Menu("View");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");

        simple.setOnAction(e -> {
            Bloc blocSelectionne = m.getBlocSelectionne(); // Récupère le bloc sélectionné dans le modèle
            if (blocSelectionne != null) {
                blocSelectionne.setAffichageSimple(true); // Active la vue simple
                m.refresh();
                System.out.println("Vue simple activée pour le bloc : " + blocSelectionne.getNom());
            } else {
                System.out.println("Aucun bloc sélectionné.");
            }
        });

        complexe.setOnAction(e -> {
            Bloc blocSelectionne = m.getBlocSelectionne(); // Récupère le bloc sélectionné dans le modèle
            if (blocSelectionne != null) {
                blocSelectionne.setAffichageSimple(false); // Active la vue complexe
                m.refresh();
                System.out.println("Vue complexe activée pour le bloc : " + blocSelectionne.getNom());
            } else {
                System.out.println("Aucun bloc sélectionné.");
            }

        });

        affichage.getItems().addAll(simple, complexe);

        // Création des MenuItems "Remove" et "Modify"
        MenuItem remove = new MenuItem("Remove");
        MenuItem modifyItem = new MenuItem("Modify");

        // TODO Ça n'a aucun sens, pourquoi appeler controlButton au lieu de le mettre en param ????
        // Associer les actions pour les deux MenuItems
        remove.setOnAction(e -> controlButton.handleRemoveAction());
        modifyItem.setOnAction(e -> controlButton.handleModifyAction());

        // Ajouter les items au menu contextuel
        this.getItems().addAll(affichage, remove, modifyItem);
    }

    @Override
    public void actualiser(Sujet s) {
        if (s instanceof Modele modele) {
            System.out.println("Menu contextuel mis à jour.");

        }
    }
}


