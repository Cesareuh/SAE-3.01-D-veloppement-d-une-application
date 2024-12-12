package app;

import java.util.List;

public class Bloc {
    private boolean affichageSimple;
    private String nom;
    private String image;
    private String implementation;
    private String heritage;
    private Position pos;
    private List<Attribut> attributs;
    private List<Attribut> methodes;

    public void setPosition(int x, int y) {
        if (pos == null) {
            pos = new Position();
        }
        pos.setX(x);
        pos.setY(y);
    }

    public Position getPosition() {
        return pos;
    }

    public String getImage() {
        return image;
    }

    public String getImplementation() {
        return implementation;
    }

    public String getHeritage() {
        return heritage;
    }

    public List<Attribut> getListAttributs() {
        return attributs;
    }

    public List<Attribut> getListMethodes() {
        return methodes;
    }

    public String getNom(String className) {
        return this.nom;
    }

    public void setNom(String className) {
    }
}
