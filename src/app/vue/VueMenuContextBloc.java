package app.vue;

import app.Modele;
import app.Sujet;
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

        // Associer les actions aux items "Simple" et "Complex"
        simple.setOnAction(e -> {
            m.setVueSimple(true);
            m.setVueComplexe(false);  // Désactive la vue complexe
            controlButton.handleSimpleViewAction();
            actualiser(m);  // Met à jour le menu contextuel après le changement de vue
        });
        complexe.setOnAction(e -> {
            m.setVueSimple(false);  // Désactive la vue simple
            m.setVueComplexe(true);
            controlButton.handleComplexViewAction();
            actualiser(m);  // Met à jour le menu contextuel après le changement de vue
        });

        affichage.getItems().addAll(simple, complexe);

        // Création des MenuItems "Remove" et "Modify"
        MenuItem remove = new MenuItem("Remove");
        MenuItem modifyItem = new MenuItem("Modify");

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
            // Vérification de l'état de la vue
            if (modele.isVueSimple()) {
                // Si la vue est simple, s'assurer que le menu est réactualisé pour afficher la vue simple
                System.out.println("Affichage en vue simple.");
            } else if (modele.isVueComplexe()) {
                // Si la vue est complexe, s'assurer que le menu est réactualisé pour afficher la vue complexe
                System.out.println("Affichage en vue complexe.");
            }
        }
    }
}


