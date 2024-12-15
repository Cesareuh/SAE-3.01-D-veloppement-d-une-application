package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ControlButton implements EventHandler<ActionEvent> {

	private Modele modele;

	public ControlButton(Modele modele) {
		this.modele = modele;
	}

	@Override
	public void handle(ActionEvent event) {
		MenuItem source = (MenuItem) event.getSource();  // Récupère l'élément qui a déclenché l'événement

		if (source.getText().equals("Select directory")) {
			// Ouvre un dialogue pour sélectionner un répertoire
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Select Directory");
			File selectedDirectory = dirChooser.showDialog(modele.getStage());

			if (selectedDirectory != null) {
				System.out.println("Répertoire sélectionné : " + selectedDirectory.getAbsolutePath());

				// Mettez à jour le modèle avec le répertoire sélectionné
				modele.setRep(selectedDirectory);
				modele.initialiserBlocs(selectedDirectory);

				// Mettre à jour l'arborescence du TreeView
				TreeView<String> fileExplorer = modele.getFileExplorerTree();
				fileExplorer.setRoot(null);  // Réinitialiser l'arborescence

				// Créer un nouvel élément racine pour le répertoire sélectionné
				TreeItem<String> root = createNode(selectedDirectory);
				root.setExpanded(true);  // Développer le répertoire racine
				fileExplorer.setRoot(root);
			}
		} else if (source.getText().equals("Export as PNG")) {
			// Action pour exporter le contenu en PNG
			exportAsPNG();
		}
	}

	private void exportAsPNG() {
		// Logique d'exportation en PNG, tu peux adapter cela selon tes besoins
		System.out.println("Export en PNG...");
		// Par exemple, tu peux utiliser un `Canvas` et une `WritableImage` pour exporter le contenu
	}

	private TreeItem<String> createNode(File directory) {
		// Crée un nœud dans le TreeView à partir du répertoire sélectionné
		TreeItem<String> node = new TreeItem<>(directory.getName());
		node.setExpanded(true);

		// Ajoute les fichiers et sous-répertoires comme des enfants
		File[] files = directory.listFiles();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {
					node.getChildren().add(createNode(file));  // Appel récursif pour les sous-répertoires
				} else {
					node.getChildren().add(new TreeItem<>(file.getName()));
				}
			}
		}
		return node;
	}
}
