package tpRene.src;

public class Candy extends Accesoire{
    public Candy(Logo composant) {
        super(composant);
        prix=0.7;
    }

    @Override
    public MyImage getLogo() {
        MyImage logo = composant.getLogo();
        logo.paintOver("img/Candy.png", 441, 202);
        return logo;
    }
}
