package app;

import javafx.scene.text.Text;

public class VueTextuel extends Text implements Observateur {
    //attributs
    protected Modele m;

    //constructeur
    public VueTextuel(Modele modele) {
        this.m = modele;
    }

    //methodes
    @Override
    public void actualiser() {
        FileComposite f = m.getRep();
        this.setText(afficher(f));
    }

    public String afficher(FileComposite f) {
        String res = "";
        if(f instanceof Repertoire){
            for(FileComposite file : ((Repertoire) f).getFileCompositeArrayList()){
                res+=afficher(file);
            }
        }else if(f instanceof Fichier){

        }
        return null;
    }

}
