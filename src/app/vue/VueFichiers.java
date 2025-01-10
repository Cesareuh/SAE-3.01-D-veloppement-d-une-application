package app.vue;

import app.introspection.Fichier;
import app.classes.Modele;
import app.classes.Sujet;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.Comparator;
import java.util.List;

public class VueFichiers extends TreeView implements Observateur {
    @Override
    public void actualiser(Sujet s) {
        List<Fichier> f = ((Modele) s).getFichiers();

        // Trier la liste des fichiers par ordre alphabétique
        f.sort(Comparator.comparing(Fichier::getName));

        // Créer la racine
        TreeItem<String> root = new TreeItem<>(new Fichier(((Modele) s).getRep()).getName());
        root.setExpanded(true);

        // Ajouter les enfants triés
        for (Fichier fichier : f) {
            TreeItem<String> c = new TreeItem<>(fichier.getName());
            root.getChildren().add(c);
        }

        // Mettre à jour la racine du TreeView
        this.setRoot(root);
    }
}

