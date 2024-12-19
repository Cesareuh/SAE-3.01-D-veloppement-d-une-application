package app;

import javafx.scene.control.Label;
import javafx.scene.text.Text;

import java.io.File;

public class VueTextuel  implements Observateur {

    //methodes
    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele)s;
        if(m.getRep()!=null) {
            File f = m.getRep();
            String res = "";
            if (f.isFile()) {
                Fichier fichier = new Fichier(f);
                res += fichier.afficher("");
            } else if (f.isDirectory()) {
                Repertoire repertoire = new Repertoire(f);
                res += repertoire.afficher("");
            } else res = "erreur";
        }
        //System.out.println("res : "+res);

    }

}
