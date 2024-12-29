package app;

import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.File;
import java.util.Collections;
import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class ControlDragAndDrop implements EventHandler<MouseEvent> {

    private Modele m;

    public ControlDragAndDrop(Modele m) {
        this.m = m;
    }

    @Override
    public void handle(MouseEvent event) {
        // Gestion du Drag and Drop pour un bloc
        if (event.getButton() == MouseButton.PRIMARY) {
            if (event.getSource() instanceof VueBloc vb) {
                int id = vb.getBlocId();
                Pane viewport = m.getViewport();

                // Convertir la position de l'écran en position relative au viewport
                Position viewportPos = m.screenPosToViewportPos(
                        new Position(event.getScreenX(), event.getScreenY())
                );

                // Calculer la position centrée du bloc par rapport au curseur
                int x = (int) (viewportPos.getX() - vb.getWidth() / 2);
                int y = (int) (viewportPos.getY() - vb.getHeight() / 2);

                // Contraindre le bloc à rester dans les limites du viewport
                int margin = 5;
                x = Math.max(margin, Math.min(x, (int) (viewport.getWidth() - vb.getWidth() - margin)));
                y = Math.max(margin, Math.min(y, (int) (viewport.getHeight() - vb.getHeight() - margin)));

                // Mettre à jour la position du bloc dans le modèle
                m.translaterBloc(id, x, y);
            }
        }
        else if (event.getSource() instanceof TreeView<?> treeView) {
            // Vérifie si un élément est sélectionné
            TreeView<String> fileExplorerTree = m.getFileExplorerTree();
            TreeItem<?> selectedItem = fileExplorerTree.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                System.out.println("Élément sélectionné : " + selectedItem.getValue());
                // Crée un Label temporaire pour représenter visuellement le fichier
                Label fileLabel = new Label(selectedItem.getValue().toString());
                fileLabel.setStyle("-fx-background-color: lightgray; -fx-padding: 5; -fx-border-color: black;");

                Pane viewport = m.getViewport();
                viewport.getChildren().add(fileLabel);

                // Suivre le curseur lors du déplacement
                fileLabel.setOnMouseDragged(dragEvent -> {
                    double x = dragEvent.getSceneX() - fileLabel.getWidth() / 2;
                    double y = dragEvent.getSceneY() - fileLabel.getHeight() / 2;

                    // Contraindre aux limites du viewport
                    x = Math.max(0, Math.min(x, viewport.getWidth() - fileLabel.getWidth()));
                    y = Math.max(0, Math.min(y, viewport.getHeight() - fileLabel.getHeight()));

                    fileLabel.setLayoutX(x);
                    fileLabel.setLayoutY(y);
                });

                // Fixe le Label dans le Pane à la position lâchée
                fileLabel.setOnMouseReleased(releaseEvent -> {
                    double x = releaseEvent.getSceneX() - fileLabel.getWidth() / 2;
                    double y = releaseEvent.getSceneY() - fileLabel.getHeight() / 2;

                    // Contraindre la position finale aux limites du viewport
                    x = Math.max(0, Math.min(x, viewport.getWidth() - fileLabel.getWidth()));
                    y = Math.max(0, Math.min(y, viewport.getHeight() - fileLabel.getHeight()));

                    fileLabel.setLayoutX(x);
                    fileLabel.setLayoutY(y);

                    // Finaliser la position dans le Pane
                    fileLabel.setOnMouseDragged(null); // Désactiver le drag après placement
                    fileLabel.setOnMouseReleased(null);
                });
            }
            else {
                System.out.println("Aucun élément sélectionné.");
            }
        }

    }
}