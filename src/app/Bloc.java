package app;

import java.util.ArrayList;
import java.util.List;

public class Bloc {
    private boolean affichageSimple;
    private String nom;
    private String image;
    private List<String> implementation;
    private String heritage;
    private Position pos;
    private List<Attribut> attributs;
    private List<Methode> methodes;

    public Bloc(String nom, String image, List<String> implementation, String heritage, List attrs, List meths) {
        this.nom = nom;
        this.image = image;
        this.implementation = implementation;
        this.heritage = heritage;
        this.pos = new Position();
        this.attributs = attrs;
        this.methodes = meths;
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

    public List<String>  getImplementation() {
        return implementation;
    }

    public String getHeritage() {
        return heritage;
    }

    public List<Attribut> getListAttributs() {
        return attributs;
    }

    public List<Methode> getListMethodes() {
        return methodes;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String className) {
        this.nom = className;
    }


    public double getPositionX() {
        return pos.getX();
    }

    public double getPositionY(){
        return pos.getY();
    }
}
