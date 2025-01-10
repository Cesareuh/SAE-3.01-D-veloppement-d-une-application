package app.vue;

import app.classes.Modele;
import app.classes.Sujet;
import app.introspection.Fichier;
import app.introspection.Repertoire;

import java.io.File;

public class VueTextuel  implements Observateur {
    private File fRep = null;
    //methodes
    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele) s;
        if(fRep != m.getRep()) {
            String res = "";
            if (m.getRep() != null) {
                File f = m.getRep();
                fRep = f;
                if (f.isFile() && f.getName().endsWith(".java") && !f.getName().equals("module-info.java")) {
                    Fichier fichier = new Fichier(f);
                    res += fichier.afficher("");
                } else if (f.isDirectory()) {
                    Repertoire repertoire = new Repertoire(f);
                    res += repertoire.afficher("");
                } else res = "erreur";
            }
            System.out.println("res : "+res);
        }

    }

}
