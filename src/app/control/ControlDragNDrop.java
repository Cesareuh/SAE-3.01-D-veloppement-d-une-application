package app.control;

import app.Modele;
import javafx.event.EventHandler;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.Label;

import java.io.File;
import java.util.List;
import java.util.Map;

public class ControlDragNDrop implements EventHandler<DragEvent> {
    private TreeView<String> fileExplorer;
    private Pane viewport;
    private Modele modele;
    private double offsetX, offsetY;

    public ControlDragNDrop(Modele modele) {
        this.modele = modele;
        this.fileExplorer = modele.getFileExplorerTree();
        this.viewport = modele.getViewport();
        setUpTreeViewDragAndDrop();
        setUpViewportDragAndDrop();
    }

    private void setUpTreeViewDragAndDrop() {
        fileExplorer.setOnDragDetected(event -> {
            TreeItem<String> selectedItem = fileExplorer.getSelectionModel().getSelectedItem();
            if (selectedItem != null) {
                Dragboard dragboard = fileExplorer.startDragAndDrop(TransferMode.COPY_OR_MOVE);
                List<File> files = List.of(new File(selectedItem.getValue()));
                dragboard.setContent(Map.of(javafx.scene.input.DataFormat.FILES, files));
                event.consume();
            }
        });
    }

    private void setUpViewportDragAndDrop() {
        viewport.setOnDragOver(event -> handleDragOver(event));
        viewport.setOnDragDropped(event -> handleDragDropped(event));
    }

    @Override
    public void handle(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();

        switch (dragEvent.getEventType().getName()) {
            case "DRAG_OVER":
                handleDragOver(dragEvent);
                break;
            case "DRAG_DROPPED":
                handleDragDropped(dragEvent);
                break;
            default:
                dragEvent.consume();
                break;
        }
    }

    private void handleDragOver(DragEvent dragEvent) {
        Dragboard dragboard = dragEvent.getDragboard();
        if (dragboard.hasFiles() && dragEvent.getGestureSource() != viewport) {
            dragEvent.acceptTransferModes(TransferMode.COPY_OR_MOVE);
        }
        dragEvent.consume();
    }

    private void handleDragDropped(DragEvent dragEvent) {
        boolean success = false;
        Dragboard dragboard = dragEvent.getDragboard();

        if (dragboard.hasFiles()) {
            List<File> files = dragboard.getFiles();
            success = true;

            for (File file : files) {
                System.out.println("File dropped: " + file.getName());

                // Crée un StackPane pour afficher l'élément dans le Viewport
                StackPane filePane = new StackPane();
                Label fileLabel = new Label(file.getName());
                filePane.getChildren().add(fileLabel);

                // Calculer l'offset du curseur par rapport au Viewport
                double cursorX = dragEvent.getSceneX() - viewport.getLayoutX();
                double cursorY = dragEvent.getSceneY() - viewport.getLayoutY();

                // Ajuster la position du texte (prendre en compte la taille du label)
                double labelHeight = fileLabel.getHeight();
                double labelWidth = fileLabel.getWidth();
                filePane.setLayoutX(cursorX - labelWidth / 2);  // Centrer horizontalement
                filePane.setLayoutY(cursorY - labelHeight / 2);  // Centrer verticalement

                // Ajouter un gestionnaire d'événements pour déplacer l'élément dans le viewport
                setUpMouseDrag(filePane);

                // Ajouter l'élément dans le viewport
                viewport.getChildren().add(filePane);
            }
        }

        dragEvent.setDropCompleted(success);
        dragEvent.consume();
    }

    // Gérer le déplacement d'un élément dans le viewport
    private void setUpMouseDrag(StackPane filePane) {
        filePane.setOnMousePressed(event -> {
            // Enregistrer l'offset initial entre le curseur et le bloc
            offsetX = event.getSceneX() - filePane.getLayoutX();
            offsetY = event.getSceneY() - filePane.getLayoutY();
        });

        filePane.setOnMouseDragged(event -> {
            // Mettre à jour la position du bloc avec l'offset calculé
            double newX = event.getSceneX() - offsetX;
            double newY = event.getSceneY() - offsetY;

            // S'assurer que le bloc reste dans les limites du viewport
            double maxX = viewport.getWidth() - filePane.getWidth();
            double maxY = viewport.getHeight() - filePane.getHeight();

            newX = Math.min(Math.max(newX, 0), maxX);
            newY = Math.min(Math.max(newY, 0), maxY);

            // Mettre à jour la position du bloc
            filePane.setLayoutX(newX);
            filePane.setLayoutY(newY);
        });

        filePane.setOnMouseReleased(event -> {
            // Mettre à jour la position finale lors du relâchement de la souris
        });
    }
}
