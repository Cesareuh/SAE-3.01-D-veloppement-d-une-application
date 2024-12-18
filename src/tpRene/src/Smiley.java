package tpRene.src;

public class Smiley extends Accesoire{

    public Smiley(Logo composant) {
        super(composant);
        prix=0.3;
    }

    public MyImage getLogo() {
        MyImage logo = composant.getLogo();
        logo.paintOver("img/Smiley.png", 260, 210);
        return logo;
    }
}
