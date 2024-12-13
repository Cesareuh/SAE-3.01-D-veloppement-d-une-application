package app;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.File;

public class VueTextuel extends Label implements Observateur {

    //constructeur
    public VueTextuel() {
        this.setText("aaaaaaaaaaaaaaa");
    }

    //methodes
    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele)s;
        File f = m.getRep();
        String res="";
        if(f.isFile()) {
            Fichier fichier = new Fichier(f);
            res+= fichier.afficher("");
            System.out.println("hey");
        }else if(f.isDirectory()) {
            Repertoire repertoire = new Repertoire(f);
            res+= repertoire.afficher("");
        }else res="erreur";
        this.setText(res);
        System.out.println("res : "+res);

    }

}
