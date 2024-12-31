package app.vue;

import app.*;
import app.classes.Bloc;
import app.classes.Fleche;
import app.classes.Position;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
            if(f.getType() == Fleche.ASSOCIATION || f.getType() == Fleche.HERITAGE) {

                // Pour calculer y du départ et de l'arrivée, je vais utiliser ax + b qui part du milieu du premier bloc et arrive au milieu du deuxième
                // a -> coefficient directeur (yb - ya)/(xb - xa)
                double a = (arriveeCentre.getY() - departCentre.getY())/(arriveeCentre.getX() - departCentre.getX());
                // b = y - ax
                double b = departCentre.getY() - departCentre.getX()*a;
                // On peut maintenant trouver la position de la flèche au départ et à l'arrivée
                if(departCentre.getX() - arriveeCentre.getX() < 0) {
                    depart.setX(departCentre.getX());
                    depart.setY(departCentre.getY());
                    double x = departCentre.getX();
                    while (depart.getX() - bDep.getPosition().getX() < vDep.getWidth() && depart.getX() > bDep.getPosition().getX() && depart.getY() - bDep.getPosition().getY() < vDep.getHeight() && depart.getY() > bDep.getPosition().getY()) {
                        depart.setX(x);
                        depart.setY(a * x + b);
                        x += .1;
                    }

                    arrivee.setX(arriveeCentre.getX());
                    arrivee.setY(arriveeCentre.getY());
                    x = arriveeCentre.getX();
                    while (arrivee.getX() - bArrivee.getPosition().getX() < vArrivee.getWidth() && arrivee.getX() > bArrivee.getPosition().getX() && arrivee.getY() - bArrivee.getPosition().getY() < vArrivee.getHeight() && arrivee.getY() > bArrivee.getPosition().getY()) {
                        arrivee.setX(x);
                        arrivee.setY(a * x + b);
                        x-=.1;
                    }
                }else{
                    arrivee.setX(arriveeCentre.getX());
                    arrivee.setY(arriveeCentre.getY());
                    double x = arriveeCentre.getX();
                    while (arrivee.getX() - bArrivee.getPosition().getX() < vArrivee.getWidth() && arrivee.getX() > bArrivee.getPosition().getX() && arrivee.getY() - bArrivee.getPosition().getY() < vArrivee.getHeight() && arrivee.getY() > bArrivee.getPosition().getY()) {
                        arrivee.setX(x);
                        arrivee.setY(a * x + b);
                        x+=.1;
                    }

                    depart.setX(departCentre.getX());
                    depart.setY(departCentre.getY());
                    x = departCentre.getX();
                    while (depart.getX() - bDep.getPosition().getX() < vDep.getWidth() && depart.getX() > bDep.getPosition().getX() && depart.getY() - bDep.getPosition().getY() < vDep.getHeight() && depart.getY() > bDep.getPosition().getY()) {
                        depart.setX(x);
                        depart.setY(a * x + b);
                        x-=.1;
                    }

                }

                Line l = new Line();
                l.setStartX(depart.getX());
                l.setStartY(depart.getY());
                l.setEndX(arrivee.getX());
                l.setEndY(arrivee.getY());
                this.getChildren().add(l);

            }

            if(f.getType() == Fleche.ASSOCIATION) {
                double inclinaison = 5 * Math.PI / 6;
                int taille = 10;
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
            }
        }

        /*
         FAIRE UNE FLECHE POINTILLES
        l.getStrokeDashArray().add(15d);

         */
    }

    public int getFlecheId(){
        return id;
    }
}
