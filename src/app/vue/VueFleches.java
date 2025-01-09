package app.vue;

import app.*;
import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Fleche;
import app.classes.Position;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;

public class VueFleches extends Pane implements Observateur {

    public VueFleches() {

    }

    @Override
    public void actualiser(Sujet s) {
        //TODO cardinalités
        Modele m = (Modele) s;

        // Utilisation de Platform.runLater pour exécuter du code après que la scène a été affichée
        this.getChildren().removeAll(this.getChildren());
        for (int idF : m.getFleches().keySet()) {
            Path pCorps = new Path();
            Path pPointe = new Path();

            Fleche f = m.getFlecheById(idF);
            Bloc bDep = m.getBlocById(f.getBlocDepart());
            Bloc bArrivee = m.getBlocById(f.getBlocArrivee());
            VueBloc vDep = m.getVueBlocById(f.getBlocDepart());
            VueBloc vArrivee = m.getVueBlocById(f.getBlocArrivee());

            Position departCentre = new Position();
            departCentre.setX((bDep.getPosition().getX() + bDep.getPosition().getX() + vDep.getWidth()) / 2);
            departCentre.setY((bDep.getPosition().getY() + bDep.getPosition().getY() + vDep.getHeight()) / 2);
            Position arriveeCentre = new Position();
            arriveeCentre.setX((bArrivee.getPosition().getX() + bArrivee.getPosition().getX() + vArrivee.getWidth()) / 2);
            arriveeCentre.setY((bArrivee.getPosition().getY() + bArrivee.getPosition().getY() + vArrivee.getHeight()) / 2);

            MoveTo depart = new MoveTo();
            MoveTo arrivee = new MoveTo();

            // Dessiner la ligne
            if (departCentre.getX() - arriveeCentre.getX() < 0) {
                setStartEnd(depart, arrivee, bDep, bArrivee, vDep, vArrivee);
            } else {
                setStartEnd(arrivee, depart, bArrivee, bDep, vArrivee, vDep);
            }

            // 2 branches du bout de la flèche
            double inclinaison = 5 * Math.PI / 6;
            int taille = 15;
            double angle = Math.atan2(arrivee.getY() - depart.getY(), arrivee.getX() - depart.getX());
            double x = Math.cos((angle - inclinaison));
            double y = Math.sin((angle - inclinaison));
            LineTo brancheHaut = new LineTo();
            brancheHaut.setX(arrivee.getX() + x * taille);
            brancheHaut.setY(arrivee.getY() + y * taille);

            x = Math.cos((angle + inclinaison));
            y = Math.sin((angle + inclinaison));
            LineTo brancheBas = new LineTo();
            brancheBas.setX(arrivee.getX() + x * taille);
            brancheBas.setY(arrivee.getY() + y * taille);

            MoveTo arrivee2 = new MoveTo(arrivee.getX(), arrivee.getY());
            pPointe.getElements().addAll(arrivee, brancheHaut, arrivee, brancheBas);

            // Actions en fonction du type de la flèche
            if (f.getType() != Fleche.ASSOCIATION) {
                // Ferme la flèche
                pPointe.getElements().add(brancheHaut);
                arrivee2.setX((brancheHaut.getX() + brancheBas.getX()) / 2);
                arrivee2.setY((brancheHaut.getY() + brancheBas.getY()) / 2);
                if (f.getType() == Fleche.IMPLEMENTATION) {
                    pCorps.getStrokeDashArray().add(5d);
                }
            }else{
                // Affiche les cardinalités de la flèche
                Attribut a = f.getAttribut();
                Text nomAttribut = new Text(a.getNom());
                Text card = new Text("1");
                if(a.getType().contains("List") || a.getType().contains("Map")){
                    card.setText("*");
                }

                double dist = 25;
                double textAngle = angle + Math.PI/2;
                double cardAngle = angle + 5*Math.PI/6;

                double arrX;
                double arrY;

                arrX = (depart.getX() + arrivee.getX()) / 2 + Math.cos(textAngle) * dist;
                arrY = (depart.getY() + arrivee.getY()) / 2 + Math.sin(textAngle) * dist;

                if(arrivee.getY() > depart.getY()) {
                    arrX = (depart.getX() + arrivee.getX()) / 2 - Math.cos(textAngle) * dist;
                    arrY = (depart.getY() + arrivee.getY()) / 2 - Math.sin(textAngle) * dist;
                }

                nomAttribut.setX(arrX);
                nomAttribut.setY(arrY);

                card.setX(arrivee.getX() + Math.cos(cardAngle) * dist);
                card.setY(arrivee.getY() + Math.sin(cardAngle) * dist);

                this.getChildren().addAll(nomAttribut, card);

            }
            pCorps.getElements().add(arrivee2);
            pCorps.getElements().add(new LineTo(depart.getX(), depart.getY()));

            this.getChildren().addAll(pPointe, pCorps);
        }
    }

    private void setStartEnd(MoveTo posA, MoveTo posB, Bloc bPosA, Bloc bPosB, VueBloc vPosA, VueBloc vPosB) {
        // Calcul du centre de chaque point
        Position aCentre = new Position();
        aCentre.setX((bPosA.getPosition().getX() + bPosA.getPosition().getX() + vPosA.getWidth()) / 2);
        aCentre.setY((bPosA.getPosition().getY() + bPosA.getPosition().getY() + vPosA.getHeight()) / 2);
        Position bCentre = new Position();
        bCentre.setX((bPosB.getPosition().getX() + bPosB.getPosition().getX() + vPosB.getWidth()) / 2);
        bCentre.setY((bPosB.getPosition().getY() + bPosB.getPosition().getY() + vPosB.getHeight()) / 2);

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
}
