package app;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public class VueFleche implements Observateur{
    private int id;

    public VueFleche(int id){
        this.id = id;
    }

    @Override
    public void actualiser(Sujet s) {
        //TODO
        Modele m = (Modele)s;
        Line l = new Line(10,10,100,100);
        double points[] = { 10.0d, 140.0d, 30.0d, 110.0d, 40.0d,
                50.0d, 50.0d, 40.0d, 110.0d, 30.0d, 140.0d, 10.0d };
        Polygon p = new Polygon(points);
        m.getViewport().getChildren().addAll(l, p);
    }
}
