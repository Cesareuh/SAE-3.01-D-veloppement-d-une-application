package app;

import javafx.scene.input.*;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import java.io.File;
import java.util.Collections;

public class ControlDragAndDrop {

    private TreeView<String> fileExplorer;
    private Pane viewport;

    public ControlDragAndDrop(TreeView<String> fileExplorer, Pane viewport) {
        this.fileExplorer = fileExplorer;
        this.viewport = viewport;
    }

    public void handle() {
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
                    Text text = new Text("Fichier ou répertoire déposé: " + file.getName());
                    text.setLayoutX(10);
                    text.setLayoutY(10 + (viewport.getChildren().size() * 30)); // Espacement vertical pour chaque fichier
                    viewport.getChildren().add(text);
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }
}
