package app;

import java.util.ArrayList;
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

    public Bloc(String nom, String image, String implementation, String heritage) {
        this.nom = nom;
        this.image = image;
        this.implementation = implementation;
        this.heritage = heritage;
        this.pos = new Position();
        this.attributs = new ArrayList<>();
        this.methodes = new ArrayList<>();
    }

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

    public String getNom() {
        return this.nom;
    }

    public void setNom(String className) {
    }

    public int getPositionX() {
        return pos.getX();
    }

    public int getPositionY(){
        return pos.getY();
    }
}
