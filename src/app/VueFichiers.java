package app;

import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;

import java.util.List;

public class VueFichiers extends TreeView implements Observateur{
    @Override
    public void actualiser(Sujet s) {
        List<Fichier> f = ((Modele) s).getFichiers();
        TreeItem<String> root = new TreeItem<>(((Modele) s).getFichiers().getFirst().getName());
        root.setExpanded(true);
        for(Fichier fichier : f) {
            if(fichier!= f.getFirst()) {
                TreeItem<String> c = new TreeItem<>(fichier.getName());
                root.getChildren().add(c);
            }
        }
        this.setRoot(root);
    }
}
