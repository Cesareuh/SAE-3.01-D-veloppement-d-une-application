package app.vue;

import app.*;
import app.classes.Bloc;
import app.classes.Fleche;
import app.classes.Position;
import javafx.scene.layout.*;
import javafx.scene.shape.Line;

public class VueFleche extends Pane implements Observateur {
    private int id;

    public VueFleche(int id){
        this.id = id;
    }

    @Override
    public void actualiser(Sujet s) {
        //TODO factoriser, commenter, traiter le cas où les flèches sont verticales, bug à la création
        Modele m = (Modele)s;

        if(m.getFlecheById(id) == null) {
            if (this.getParent() instanceof Pane pane) {
                pane.getChildren().remove(this);
            }
        }else {

            this.getChildren().removeAll(this.getChildren());

            Line l = new Line();
            Fleche f = m.getFlecheById(id);
            Bloc bDep = m.getBlocById(f.getBlocDepart());
            Bloc bArrivee = m.getBlocById(f.getBlocArrivee());
            VueBloc vDep = m.getVueBlocById(f.getBlocDepart());
            VueBloc vArrivee = m.getVueBlocById(f.getBlocArrivee());

            Position departCentre = new Position();
            departCentre.setX((bDep.getPosition().getX() + bDep.getPosition().getX() + vDep.getWidth())/2);
            departCentre.setY((bDep.getPosition().getY() + bDep.getPosition().getY() + vDep.getHeight())/2);
            Position arriveeCentre = new Position();
            arriveeCentre.setX((bArrivee.getPosition().getX() + bArrivee.getPosition().getX() + vArrivee.getWidth())/2);
            arriveeCentre.setY((bArrivee.getPosition().getY() + bArrivee.getPosition().getY() + vArrivee.getHeight())/2);

            Position depart = new Position();
            Position arrivee = new Position();

            // Dessiner la ligne

            if(departCentre.getX() - arriveeCentre.getX() < 0) {
                setStartEnd(depart, arrivee, bDep, bArrivee, vDep, vArrivee);
            }else{
                setStartEnd(arrivee, depart, bArrivee, bDep, vArrivee, vDep);
            }

            double inclinaison = 5 * Math.PI / 6;
            int taille = 15;
            double angle = Math.atan2(arrivee.getY() - depart.getY(), arrivee.getX() - depart.getX()) - inclinaison;
            double x = Math.cos(angle);
            double y = Math.sin(angle);
            Line brancheHaut = new Line();
            brancheHaut.setStartX(arrivee.getX());
            brancheHaut.setStartY(arrivee.getY());
            brancheHaut.setEndX(arrivee.getX() + x * taille);
            brancheHaut.setEndY(arrivee.getY() + y * taille);

            angle = Math.atan2(arrivee.getY() - depart.getY(), arrivee.getX() - depart.getX()) + inclinaison;
            x = Math.cos(-angle);
            y = Math.sin(-angle);
            Line brancheBas = new Line();
            brancheBas.setStartX(arrivee.getX());
            brancheBas.setStartY(arrivee.getY());
            brancheBas.setEndX(arrivee.getX() + x * taille);
            brancheBas.setEndY(arrivee.getY() - y * taille);
            this.getChildren().addAll(brancheHaut, brancheBas);

            if(f.getType() != Fleche.ASSOCIATION){
                Line brancheMilieu = new Line();
                brancheMilieu.setStartX(brancheHaut.getEndX());
                brancheMilieu.setStartY(brancheHaut.getEndY());
                brancheMilieu.setEndX(brancheBas.getEndX());
                brancheMilieu.setEndY(brancheBas.getEndY());
                this.getChildren().add(brancheMilieu);
                arrivee.setX((brancheMilieu.getStartX()+brancheMilieu.getEndX())/2);
                arrivee.setY((brancheMilieu.getStartY()+brancheMilieu.getEndY())/2);
            }

            if(f.getType() == Fleche.IMPLEMENTATION){
                l.getStrokeDashArray().add(15d);
            }

            l.setStartX(depart.getX());
            l.setStartY(depart.getY());
            l.setEndX(arrivee.getX());
            l.setEndY(arrivee.getY());
            this.getChildren().add(l);

        }
    }

    private void setStartEnd(Position posA, Position posB, Bloc bPosA, Bloc bPosB, VueBloc vPosA, VueBloc vPosB){
        Position aCentre = new Position();
        aCentre.setX((bPosA.getPosition().getX() + bPosA.getPosition().getX() + vPosA.getWidth())/2);
        aCentre.setY((bPosA.getPosition().getY() + bPosA.getPosition().getY() + vPosA.getHeight())/2);
        Position bCentre = new Position();
        bCentre.setX((bPosB.getPosition().getX() + bPosB.getPosition().getX() + vPosB.getWidth())/2);
        bCentre.setY((bPosB.getPosition().getY() + bPosB.getPosition().getY() + vPosB.getHeight())/2);
        if(aCentre.getX() == bCentre.getX()){
            if(aCentre.getY() < bCentre.getY()) {
                posA.setX(aCentre.getX());
                posA.setY(aCentre.getY() + vPosA.getHeight()/2);
                posB.setX(bCentre.getX());
                posB.setY(bCentre.getY() - vPosB.getHeight()/2);
            }else{
                posB.setX(bCentre.getX());
                posB.setY(bCentre.getY() + vPosA.getHeight()/2);
                posA.setX(aCentre.getX());
                posA.setY(aCentre.getY() - vPosB.getHeight()/2);
            }
        }else {
            // Pour calculer y du départ et de l'arrivée, je vais utiliser ax + b qui part du milieu du premier bloc et arrive au milieu du deuxième
            // a -> coefficient directeur (yb - ya)/(xb - xa)
            double a = (bCentre.getY() - aCentre.getY()) / (bCentre.getX() - aCentre.getX());
            // b = y - ax
            double b = aCentre.getY() - aCentre.getX() * a;

            // On peut maintenant trouver la position de la flèche au départ et à l'arrivée
            posA.setX(aCentre.getX());
            posA.setY(aCentre.getY());
            double x = aCentre.getX();
            while (posA.getX() - bPosA.getPosition().getX() < vPosA.getWidth() && posA.getX() > bPosA.getPosition().getX() && posA.getY() - bPosA.getPosition().getY() < vPosA.getHeight() && posA.getY() > bPosA.getPosition().getY()) {
                posA.setX(x);
                posA.setY(a * x + b);
                x += .1;
            }

            posB.setX(bCentre.getX());
            posB.setY(bCentre.getY());
            x = bCentre.getX();
            while (posB.getX() - bPosB.getPosition().getX() < vPosB.getWidth() && posB.getX() > bPosB.getPosition().getX() && posB.getY() - bPosB.getPosition().getY() < vPosB.getHeight() && posB.getY() > bPosB.getPosition().getY()) {
                posB.setX(x);
                posB.setY(a * x + b);
                x -= .1;
            }
        }
    }

    public int getFlecheId(){
        return id;
    }
}
