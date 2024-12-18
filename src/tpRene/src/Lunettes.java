package tpRene.src;

public class Lunettes extends Accesoire{

    public Lunettes(Logo composant) {
        super(composant);
        prix=0.8;
    }

    public MyImage getLogo() {
        MyImage l = composant.getLogo();
        l.paintOver("img/Sunglasses.png", 255,76);
        return l;
    }
}
