package app;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VueBloc extends VBox implements Observateur{

    private int id;

    public VueBloc(int id){
        this.id = id;
        this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    }
    @Override
    public void actualiser(Sujet s) {
        Modele m = (Modele) s;

        // Supprimer la vue si elle la classe est supprimée dans le modèle
        if(m.getBlocById(id) == null){
            if(this.getParent() instanceof Pane pane){
                pane.getChildren().remove(this);
            }
        }else {

            BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
            Border border = new Border(bs);

            // Supprimer tous les enfants
            this.getChildren().removeAll(this.getChildren());

            Bloc b = m.getBlocById(id);
            this.relocate(b.getPositionX(), b.getPositionY());
            HBox titre = new HBox();
            titre.getChildren().addAll(new Label(b.getImage()), new Label(b.getNom()));
            titre.setAlignment(Pos.CENTER);
            titre.setBorder(border);

            VBox attrs = new VBox();
            for (Attribut a : b.getListAttributs()) {
                String attribut = a.getAutorisation() + " " + a.getNom() + " : " + a.getType();
                attrs.getChildren().add(new Label(attribut));
            }
            attrs.setBorder(border);

            VBox meths = new VBox();
            for (Methode meth : b.getListMethodes()) {
                String methode = meth.getAutorisation() + " " + meth.getNom() + " : " + meth.getType();
                meths.getChildren().add(new Label(methode));
            }
            meths.setBorder(border);
            this.setBorder(border);
            this.getChildren().addAll(titre, attrs, meths);
        }

    }

    public int getBlocId(){
        return id;
    }
}
