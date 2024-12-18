package tpRene.src;

public class Text extends Accesoire{
    private String text;
    public Text(Logo composant, String text) {
        super(composant);
        prix=text.length()*15;
        this.text=text;
    }

    public MyImage getLogo() {
        MyImage l = composant.getLogo();
        l.textOver(text, 300,200);
        return l;
    }
}
