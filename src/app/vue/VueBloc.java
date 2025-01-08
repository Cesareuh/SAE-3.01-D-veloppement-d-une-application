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

public class VueBloc extends VBox implements Observateur {

    private int id;
    private boolean vueSimple;
    private Modele modele;


    public VueBloc(int id, Modele modele) {
        this.id = id;
        this.modele = modele;
        this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        this.vueSimple = false;  // Initialisation à 'false' pour avoir la vue complexe par défaut

        // Ajouter un gestionnaire de clic sur le bloc
        this.setOnMouseClicked(this::onBlocClick);
    }
    private void onBlocClick(MouseEvent event) {
        if (event.getClickCount() == 1) {  // Un clic simple
            vueSimple = !vueSimple;
            actualiser(modele);
            System.out.println("Changement de vue, nouvelle vue : " + (vueSimple ? "simple" : "complexe"));
        }
    }



    public void actualiser(Sujet s) {
        Platform.runLater(() -> {
            // Le code de mise à jour de l'interface graphique ici
            if (modele.getBlocById(id) == null) {
                if (this.getParent() instanceof Pane pane) {
                    pane.getChildren().remove(this);
                }
            } else {
                BorderStroke bs = new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(1));
                Border border = new Border(bs);

                // Supprimer tous les enfants
                this.getChildren().clear();

                Bloc b = modele.getBlocById(id);
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

                VBox attrs = new VBox();
                if (b.getListAttributs() != null) {
                    for (Attribut a : b.getListAttributs()) {
                        String attribut = a.getAutorisation() + " " + a.getNom() + " : " + a.getType();
                        attrs.getChildren().add(new Label(attribut));
                    }
                } else {
                    attrs.getChildren().add(new Label(" "));
                }
                attrs.setBorder(border);

                VBox meths = new VBox();
                for (Methode meth : b.getListMethodes()) {
                    String methode = meth.getAutorisation() + " " + meth.getNom() + " : " + meth.getType();
                    meths.getChildren().add(new Label(methode));
                }
                meths.setBorder(border);

                this.setBorder(border);

                // Mise à jour de l'affichage selon l'état de la vue
                if (modele.isVueSimple()) {

                    // Affiche la vue simple
                    this.getChildren().addAll(titre);
                    System.out.println("Affichage de la vue simple dans VueBloc.");
                } else {
                    // Affiche la vue complexe
                    this.getChildren().addAll(titre, attrs, meths);
                    System.out.println("Affichage de la vue complexe dans VueBloc.");
                }
            }

        });
    }



    public int getBlocId() {
        return id;
    }
}
