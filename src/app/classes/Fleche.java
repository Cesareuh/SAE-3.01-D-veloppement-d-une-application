package app.classes;

import java.util.Objects;

public class Fleche {
    public static int ASSOCIATION = 0;
    public static int HERITAGE = 1;
    public static int IMPLEMENTATION = 2;
    private int blocDepart;
    private int blocArrivee;
    private int type;
    private  Position depart;
    private Position arrivee;
    private Position arrivee2;
    private Position centre;
    private Attribut attribut;

    public Fleche(int blocDepart, int blocArrivee, int type) {
        this.blocDepart = blocDepart;
        this.blocArrivee = blocArrivee;
        this.type = type;
        this.centre = new Position(-1,-1);
        this.attribut = null;
    }

    public Fleche(int blocDepart, int blocArrivee, int type, Attribut a) {
        this(blocDepart, blocArrivee, type);
        this.attribut = a;
    }

    public Attribut getAttribut(){
        return this.attribut;
    }

    public int getBlocDepart(){
        return blocDepart;
    }

    public int getBlocArrivee(){
        return blocArrivee;
    }

    public int getType() {
        return type;
    }

    public Position getCentre(){
        return this.centre;
    }

    public void setCentre(Position p){
        this.centre = p;
    }

    public void setDepart(Position p){
        this.depart = p;
    }
    public void setArrivee(Position p){
        this.arrivee = p;
    }
    public void setArrivee2(Position p){
        this.arrivee2 = p;
    }

    public Position getDepart(){return this.depart;};
    public Position getArrivee(){return this.arrivee;};
    public Position getArrivee2(){return this.arrivee2;};

    public Position[] getStartEnd(Position bPosA, Position bPosB, Position vPosA, Position vPosB) {
        Position posA = new Position();
        Position posB = new Position();
        // Calcul du centre de chaque point
        Position aCentre = new Position();
        if(vPosA.getWidth() == 0) {
            aCentre.setX(bPosA.getX());
            aCentre.setY(bPosA.getY());
        }else{
            aCentre.setX((bPosA.getX() + bPosA.getX() + vPosA.getWidth()) / 2);
            aCentre.setY((bPosA.getY() + bPosA.getY() + vPosA.getHeight()) / 2);
        }

        Position bCentre = new Position();
        if(vPosB.getWidth() == 0) {
            bCentre.setX(bPosB.getX());
            bCentre.setY(bPosB.getY());
        }else{
            bCentre.setX((bPosB.getX() + bPosB.getX() + vPosB.getWidth()) / 2);
            bCentre.setY((bPosB.getY() + bPosB.getY() + vPosB.getHeight()) / 2);
        }

        // Gère le cas où les points sont à la verticale
        if (aCentre.getX() == bCentre.getX()) {
            if (aCentre.getY() < bCentre.getY()) {
                posA.setX(aCentre.getX());
                posA.setY(aCentre.getY() + vPosA.getHeight() / 2);
                posB.setX(bCentre.getX());
                posB.setY(bCentre.getY() - vPosB.getHeight() / 2);
            } else {
                posB.setX(bCentre.getX());
                posB.setY(bCentre.getY() + vPosA.getHeight() / 2);
                posA.setX(aCentre.getX());
                posA.setY(aCentre.getY() - vPosB.getHeight() / 2);
            }
        } else {
            // Pour calculer y du départ et de l'arrivée, je vais utiliser ax + b qui part du milieu du premier bloc et arrive au milieu du deuxième
            // a -> coefficient directeur (yb - ya)/(xb - xa)
            double a = (bCentre.getY() - aCentre.getY()) / (bCentre.getX() - aCentre.getX());
            // b = y - ax
            double b = aCentre.getY() - aCentre.getX() * a;

            // On peut maintenant trouver la position de la flèche au départ et à l'arrivée
            posA.setX(aCentre.getX());
            posA.setY(aCentre.getY());
            double x = aCentre.getX();
            while (posA.getX() - bPosA.getX() < vPosA.getWidth() && posA.getX() > bPosA.getX() && posA.getY() - bPosA.getY() < vPosA.getHeight() && posA.getY() > bPosA.getY()) {
                posA.setX(x);
                posA.setY(a * x + b);
                x += .1;
            }

            posB.setX(bCentre.getX());
            posB.setY(bCentre.getY());
            x = bCentre.getX();
            while (posB.getX() - bPosB.getX() < vPosB.getWidth() && posB.getX() > bPosB.getX() && posB.getY() - bPosB.getY() < vPosB.getHeight() && posB.getY() > bPosB.getY()) {
                posB.setX(x);
                posB.setY(a * x + b);
                x -= .1;
            }
        }
        Position[] positions = {posA, posB};
        return positions;
    }

    public double getAngle(){
        if(getCentre().getX() == -1) {
            return Math.atan2(getArrivee().getY() - getDepart().getY(), getArrivee().getX() - getDepart().getX());
        }else{
            return Math.atan2(getArrivee().getY() - getCentre().getY(), getArrivee().getX() - getCentre().getX());
        }
    }

    public Position[] getBranches(){
        double inclinaison = 5 * Math.PI / 6;
        int taille = 15;
        double x = Math.cos((getAngle() - inclinaison));
        double y = Math.sin((getAngle() - inclinaison));
        Position brancheHaut = new Position();
        brancheHaut.setX(getArrivee().getX() + x * taille);
        brancheHaut.setY(getArrivee().getY() + y * taille);

        x = Math.cos((getAngle() + inclinaison));
        y = Math.sin((getAngle() + inclinaison));
        Position brancheBas = new Position();
        brancheBas.setX(getArrivee().getX() + x * taille);
        brancheBas.setY(getArrivee().getY() + y * taille);

        Position[] positions = {brancheHaut, brancheBas};
        return positions;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Fleche fleche)) return false;
        return blocDepart == fleche.blocDepart && blocArrivee == fleche.blocArrivee && type == fleche.type && Objects.equals(attribut, fleche.attribut);
    }

    @Override
    public int hashCode() {
        return Objects.hash(blocDepart, blocArrivee, type, attribut);
    }
}

