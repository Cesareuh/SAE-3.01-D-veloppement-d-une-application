package app.control;

import app.Modele;
import app.classes.Position;
import app.vue.VueBloc;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.event.EventHandler;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class ControlDeplacerBloc implements EventHandler<MouseEvent>{

    private TreeView<String> fileExplorer;
    private Pane viewport;
    private Modele m;

    /*
    public ControlDragAndDrop(Modele m, TreeView<String> fileExplorer, Pane viewport) {
        this.fileExplorer = fileExplorer;
        this.viewport = viewport;
        this.m = m;
    }

     */

    public ControlDeplacerBloc(Modele m) {
        this.m = m;
        this.fileExplorer = m.getFileExplorerTree();
        this.viewport = m.getViewport();
    }

    @Override
    public void handle(MouseEvent event) {

        // Drag and Drop d'un bloc
        if(event.getButton() == MouseButton.PRIMARY) {
            if (event.getSource() instanceof VueBloc vb) {
                int id = vb.getBlocId();
                Pane viewport = m.getViewport();
                // Convertit la position du curseur en fonction du viewport au lieu de la scène complète
                Position viewport_pos = m.screenPosToViewportPos(new Position(event.getScreenX(), event.getScreenY()));

                // Centre le curseur sur la boite
                int x = (int) (viewport_pos.getX() - vb.getWidth() / 2);
                int y = (int) (viewport_pos.getY() - vb.getHeight() / 2);

                // Ne pas dépasser les limites (avec un ecart de 5 ce qui empêche les bugs)
                int ecart = 5;
                if (x <= ecart) {
                    x = ecart;
                }
                if (x >= viewport.getWidth() - vb.getWidth() - ecart) {
                    x = (int) (viewport.getWidth() - vb.getWidth() - ecart);
                }
                if (y < ecart) {
                    y = ecart;
                }
                if (y >= viewport.getHeight() - vb.getHeight() - ecart) {
                    y = (int) (viewport.getHeight() - vb.getHeight() - ecart);
                }
                m.translaterBloc(id, x, y);
            }
        }


       /*
        // Gérer le drag and drop pour les items du TreeView
        fileExplorer.setCellFactory(param -> new TreeCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(item);
                    // Ajouter un gestionnaire de drag détecté pour chaque élément de TreeView
                    setOnDragDetected(event -> {
                        if (!isEmpty()) {
                            // Démarrer le drag sur l'élément sélectionné
                            Dragboard db = startDragAndDrop(TransferMode.COPY);
                            ClipboardContent content = new ClipboardContent();
                            // Envelopper le fichier dans une liste
                            content.putFiles(Collections.singletonList(new File(getItem()))); // Passer une liste de fichiers
                            db.setContent(content);
                            event.consume();
                        }
                    });
                }
            }
        });

        // Gérer le drag over pour le fileExplorer (gauche) : autoriser le glisser
        fileExplorer.setOnDragOver(event -> {
            if (event.getGestureSource() != fileExplorer && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // Gérer le drag dropped pour le fileExplorer (gauche) : rien ne se passe quand on dépose dans fileExplorer
        fileExplorer.setOnDragDropped(event -> {
            event.consume();
        });

        // Gérer le drag over pour le viewport (droite) : autoriser le glisser
        viewport.setOnDragOver(event -> {
            if (event.getGestureSource() != viewport && event.getDragboard().hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            }
            event.consume();
        });

        // Gérer le drag dropped pour le viewport (droite) : afficher le nom du fichier dans le viewport
        viewport.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                success = true;
                for (File file : db.getFiles()) {
                    // Afficher le nom du fichier ou répertoire dans le viewport
                    Text text = new Text(file.getName());
                    text.setLayoutX(10);
                    text.setLayoutY(10 + (viewport.getChildren().size() * 30)); // Espacement vertical pour chaque fichier
                    viewport.getChildren().add(text);
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
        */
    }
}