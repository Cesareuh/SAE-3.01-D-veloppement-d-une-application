package tpRene.src;

public class Chapeau extends Accesoire{

    public Chapeau(Logo composant) {
        super(composant);
        prix=0.5;
    }

    public MyImage getLogo() {
        MyImage l = composant.getLogo();
        l.paintOver("img/Chapeau.png", 280,42);
        return l;
    }
}
