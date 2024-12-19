package app;

import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;

import java.util.ArrayList;

public class VueMenuContextVide extends VueContext{
    public VueMenuContextVide(Modele m){
        super(m);
        Menu affichage = new Menu("Global view");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");
        affichage.getItems().addAll(simple, complexe);

        // DEBUG
        // A SUPPRIMER
        Menu debug = new Menu("Debug");
        // tester affichage classes
        MenuItem add = new MenuItem("add");
        add.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "int", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            Bloc b = new Bloc("Test", "Image", null, null, a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        MenuItem add2 = new MenuItem("add2");
        add2.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "Test", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            Bloc b = new Bloc("OUII", "Image", null, null, a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        debug.getItems().addAll(add, add2);
        this.getItems().addAll(affichage, debug);
    }
    @Override
    public void actualiser(Sujet s) {
        //TODO
    }
}
