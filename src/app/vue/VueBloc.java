package app.vue;

import app.*;
import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Methode;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class VueBloc extends VBox implements Observateur {

    private int id;
    private Modele modele;

    public VueBloc(int id, Modele modele) {
        this.id = id;
        this.modele = modele;
        this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));

        // Ajouter un gestionnaire de clic sur le bloc
        this.setOnMouseClicked(this::onBlocClick);
    }

    private void onBlocClick(MouseEvent event) {
        if (event.getClickCount() == 1) {  // Un clic simple
            Bloc bloc = modele.getBlocById(id);
            if (bloc != null) {
                bloc.setAffichageSimple(!bloc.isAffichageSimple());  // Bascule l'affichage
                actualiser(modele);  // Rafraîchir l'affichage du bloc
                System.out.println("Changement de vue, nouvelle vue : " + (bloc.isAffichageSimple() ? "simple" : "complexe"));
            }
        }
    }


    public void actualiser(Sujet s) {
            this.getChildren().clear();  // Supprime les anciens enfants

            Bloc b = modele.getBlocById(id);
            if (b == null) {
                if (this.getParent() instanceof Pane pane) {
                    pane.getChildren().remove(this);
                }
            } else {
                BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
                Border border = new Border(bs);

                this.relocate(b.getPosition().getX(), b.getPosition().getY());

                HBox titre = new HBox();
                titre.setSpacing(10);
                Image image;
                try {
                    image = new Image(b.getImage(), 30, 30, true, false);
                } catch (NullPointerException e) {
                    image = null;
                }
                titre.getChildren().addAll(new ImageView(image), new Label(b.getNom()));
                titre.setAlignment(Pos.CENTER);
                titre.setBorder(border);

                VBox details = new VBox();
                if (!b.isAffichageSimple()) {
                    // Vue complexe : Afficher les attributs et méthodes
                    VBox attrs = new VBox();
                    if (b.getListAttributs() != null) {
                        for (Attribut a : b.getListAttributs()) {
                            String attribut = a.getAutorisation() + " " + a.getNom() + " : " + a.getType();
                            attrs.getChildren().add(new Label(attribut));
                        }
                    } else {
                        attrs.getChildren().add(new Label("Aucun attribut"));
                    }

                    VBox meths = new VBox();
                    for (Methode meth : b.getListMethodes()) {
                        String methode = meth.getAutorisation() + " " + meth.getNom() + " : " + meth.getType();
                        meths.getChildren().add(new Label(methode));
                    }

                    attrs.setBorder(border);
                    meths.setBorder(border);
                    details.getChildren().addAll(attrs, meths);
                } else {
                    // Vue simple : Afficher uniquement le titre
                    details.getChildren().add(new Label("Vue simple"));
                }

                titre.setBorder(border);
                this.setBorder(border);

                // Ajouter le titre et les détails à la vue
                this.getChildren().addAll(titre, details);

            }

    }



    public int getBlocId() {
        return id;
    }
}
