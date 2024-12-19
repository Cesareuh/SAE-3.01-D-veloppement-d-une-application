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

            // Gestion du Drag and Drop pour l'explorateur de fichiers
            if (event.getSource() instanceof TreeView<?> treeView) {
                // Gérer le début du drag and drop du fichier dans le TreeView
                TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    File selectedFile = new File(selectedItem.getValue());
                    if (selectedFile.exists() && selectedFile.isFile()) {
                        // Démarrer l'opération de glissement du fichier
                        Dragboard db = treeView.startDragAndDrop(TransferMode.MOVE);
                        ClipboardContent content = new ClipboardContent();
                        content.putFiles(Collections.singletonList(selectedFile));  // Met le fichier dans le dragboard
                        db.setContent(content);
                    }
                }
            }
        }
    }

    // Gérer l'événement DragOver pour accepter les fichiers dans le TreeView
    public void handleDragOver(DragEvent event) {
        // Accepter le drag-and-drop seulement si des fichiers sont présents
        if (event.getGestureSource() != event.getTarget() && event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.MOVE);
        }
        event.consume();
    }

    // Gérer l'événement DragDropped pour gérer le dépôt du fichier
    public void handleDragDropped(DragEvent event) {
        // Vérifier si des fichiers ont été déposés
        Dragboard db = event.getDragboard();
        boolean success = false;

        if (db.hasFiles()) {
            File file = db.getFiles().get(0);  // Récupérer le fichier
            // Vous pouvez effectuer ici une action sur le fichier, par exemple, le déplacer
            success = moveFile(file);
        }

        // Indiquer si le dépôt a réussi ou échoué
        event.setDropCompleted(success);
        event.consume();
    }

    // Exemple de méthode pour déplacer un fichier (à personnaliser selon vos besoins)
    private boolean moveFile(File file) {
        // Implémenter ici la logique de déplacement ou de gestion du fichier
        System.out.println("Fichier déplacé : " + file.getAbsolutePath());

        // Par exemple, on peut déplacer le fichier vers un répertoire spécifique (s'il s'agit d'un répertoire cible)
        File destination = new File("nouveau_chemin_ou_repertoire");
        if (file.renameTo(destination)) {
            System.out.println("Le fichier a été déplacé avec succès !");
            return true;
        } else {
            System.out.println("Échec du déplacement.");
            return false;
        }
    }
}