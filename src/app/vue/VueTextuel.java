package app.vue;

import app.*;

import java.io.File;

public class VueTextuel  implements Observateur {
    private boolean appele = false;
    //methodes
    @Override
    public void actualiser(Sujet s) {
        if(!appele) {
            String res = "";
            Modele m = (Modele) s;
            if (m.getRep() != null) {
                File f = m.getRep();
                if (f.isFile() && f.getName().endsWith(".java") && !f.getName().equals("module-info.java")) {
                    Fichier fichier = new Fichier(f);
                    res += fichier.afficher("");
                } else if (f.isDirectory()) {
                    Repertoire repertoire = new Repertoire(f);
                    res += repertoire.afficher("");
                } else res = "erreur";
            }
            appele = true;
            System.out.println("res : "+res);
        }

    }

}
