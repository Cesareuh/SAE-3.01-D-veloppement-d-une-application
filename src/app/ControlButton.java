package app;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;

import java.io.File;

public class ControlButton implements EventHandler {

	private Modele m;

	public ControlButton(Modele m) {
		this.m = m;
	}

	@Override
	public void handle(Event event) {
		MenuItem source = (MenuItem) event.getTarget();

		// Si l'utilisateur a choisi un répertoire
		if (source.getText().equals("Select directory")) {
			DirectoryChooser dirChooser = new DirectoryChooser();
			dirChooser.setTitle("Open Directory");
			File selectedDirectory = dirChooser.showDialog(m.getStage());

			// Si un répertoire est sélectionné
			if (selectedDirectory != null) {
				System.out.println("Répertoire sélectionné: " + selectedDirectory.getAbsolutePath());

				// Mettre à jour le modèle avec le répertoire sélectionné
				m.setRep(selectedDirectory);
				m.initialiserBlocs(selectedDirectory);

				// Récupérer le VBox pour l'explorateur de fichiers
				VBox fileExplorer = m.getFileExplorer();
				fileExplorer.getChildren().clear(); // Vider le contenu précédent

				// Vérification des fichiers dans le répertoire
				File[] files = selectedDirectory.listFiles();
				if (files != null) {
					for (File file : files) {
						System.out.println("Vérification du fichier: " + file.getName()); // Log du fichier en cours

						// Vérifier si c'est un répertoire
						if (file.isDirectory()) {
							Label dirLabel = new Label("Répertoire: " + file.getName());
							dirLabel.setStyle("-fx-font-weight: bold;");
							fileExplorer.getChildren().add(dirLabel);
						} else if (file.getName().endsWith(".java")) {
							// Si c'est un fichier .java, l'ajouter à l'explorateur
							FileComposite fileComposite = new Fichier(file);
							Label fileLabel = new Label(fileComposite.afficher(""));
							fileLabel.setStyle("-fx-font-style: italic;");
							fileExplorer.getChildren().add(fileLabel);
						}
					}
				} else {
					System.out.println("Le répertoire est vide ou inaccessible.");
				}
			}
		}
	}
}