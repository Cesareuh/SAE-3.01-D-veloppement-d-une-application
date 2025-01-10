package app.vue;

import app.classes.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class VueBloc extends VBox implements Observateur {

    private int id;
    private Modele modele;
    private ColorPicker colorPicker;
    private Button validerCouleurButton;

    public VueBloc(int id, Modele modele) {
        this.id = id;
        this.modele = modele;
        this.setBackground(new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));  // Couleur de fond par défaut

        // Ajouter un gestionnaire de clic sur le bloc
        this.setOnMouseClicked(this::onBlocClick);

        // Ajouter le ColorPicker pour choisir la couleur
        colorPicker = new ColorPicker();
        colorPicker.setValue(Color.YELLOW); // Valeur par défaut

        // Ajouter un bouton pour valider la couleur
        validerCouleurButton = new Button("Valider Couleur");
        validerCouleurButton.setOnAction(e -> changeBackgroundColor(colorPicker.getValue())); // Appliquer la couleur sélectionnée

        // Ajouter le ColorPicker et le bouton à la vue
        this.getChildren().addAll(colorPicker, validerCouleurButton);
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

    private void changeBackgroundColor(Color color) {
        // Appliquer la couleur sélectionnée comme fond du bloc
        this.setBackground(new Background(new BackgroundFill(color, CornerRadii.EMPTY, Insets.EMPTY)));

        // Mise à jour du modèle avec la nouvelle couleur
        Bloc bloc = modele.getBlocById(id);
        if (bloc != null) {
            bloc.appliquerCouleurValidee(color);  // Sauvegarder la couleur dans le modèle
        }

        // Rafraîchir l'interface pour refléter le changement
        actualiser(modele);
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
                        if(modele.getAssociation(a) == -1) {
                            String attribut = a.getAutorisation() + " " + a.getNom() + " : " + a.getType();
                            attrs.getChildren().add(new Label(attribut));
                        }
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
            }

            this.setBorder(border);

            // Ajouter le titre et les détails à la vue
            this.getChildren().addAll(titre, details);

            // Appliquer la couleur de fond mise à jour à la vue
            mettreAJourCouleur();
        }
    }

    // Méthode pour mettre à jour la couleur de fond de la vue
    private void mettreAJourCouleur() {
        Bloc bloc = modele.getBlocById(id);
        if (bloc != null) {
            Color couleur = bloc.getBackgroundColor();  // Récupère la couleur du modèle
            this.setBackground(new Background(new BackgroundFill(couleur, CornerRadii.EMPTY, Insets.EMPTY)));  // Applique la couleur à la vue
        }
    }

    public int getBlocId() {
        return id;
    }
}
