package app;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import javax.swing.text.Document;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

public class ControlButton implements EventHandler<ActionEvent> {

	private Modele modele;

	public ControlButton(Modele modele) {
		this.modele = modele;
	}

	@Override
	public void handle(ActionEvent event) {
		if (event.getSource() instanceof MenuItem source) { // Récupère l'élément qui a déclenché l'événement
			switch (source.getText()) {
				case "Select directory":
					// Ouvre un dialogue pour sélectionner un répertoire
					DirectoryChooser dirChooser = new DirectoryChooser();
					dirChooser.setTitle("Select Directory");
					File selectedDirectory = dirChooser.showDialog(modele.getStage());

					if (selectedDirectory != null) {
						System.out.println("Répertoire sélectionné : " + selectedDirectory.getAbsolutePath());

						// Mettez à jour le modèle avec le répertoire sélectionné
						modele.setRep(selectedDirectory);
						modele.initialiserFichiers(selectedDirectory);

						// Mettre à jour l'arborescence du TreeView
						TreeView<String> fileExplorer = modele.getFileExplorerTree();
						fileExplorer.setRoot(null);  // Réinitialiser l'arborescence

						// Créer un nouvel élément racine pour le répertoire sélectionné
						TreeItem<String> root = createNode(selectedDirectory);
						root.setExpanded(true);  // Développer le répertoire racine
						fileExplorer.setRoot(root);
					}
					break;
				case "Export as image":
					// Action pour exporter le contenu en PNG
					exportAsImage();
					break;
				case "Remove":
					modele.supprimerBlocSelect();
					break;
			}
		}
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


	private void exportAsImage() {
		System.out.println("Export en PNG ou JPEG...");

		// Capture du contenu du viewport ou de ton Pane
		Pane viewport = modele.getViewport();
		WritableImage writableImage = viewport.snapshot(new SnapshotParameters(), null);

		// Convertir WritableImage en BufferedImage
		BufferedImage bufferedImage = new BufferedImage(
				(int) writableImage.getWidth(),
				(int) writableImage.getHeight(),
				BufferedImage.TYPE_INT_ARGB
		);

		IntBuffer buffer = IntBuffer.allocate((int) (writableImage.getWidth() * writableImage.getHeight()));
		writableImage.getPixelReader().getPixels(
				0, 0,
				(int) writableImage.getWidth(),
				(int) writableImage.getHeight(),
				javafx.scene.image.PixelFormat.getIntArgbInstance(),
				buffer,
				(int) writableImage.getWidth()
		);
		bufferedImage.setRGB(0, 0, (int) writableImage.getWidth(), (int) writableImage.getHeight(), buffer.array(), 0, (int) writableImage.getWidth());

		// Ouvrir un dialogue pour sauvegarder le fichier
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Exporter en image");
		fileChooser.getExtensionFilters().addAll(
				new FileChooser.ExtensionFilter("Fichiers PNG", "*.png"),
				new FileChooser.ExtensionFilter("Fichiers JPEG", "*.jpeg")
		);
		File file = fileChooser.showSaveDialog(modele.getStage());

		if (file != null) {
			// Détermine l'extension du fichier choisi
			String extension = getFileExtension(file).toLowerCase();

			// Vérifie si le format est supporté et exporte l'image
			try {
				switch (extension) {
					case "png":
						ImageIO.write(bufferedImage, "png", file);
						System.out.println("Image exportée en PNG : " + file.getAbsolutePath());
						break;
					case "jpeg":
					case "jpg":
						// Conversion en BufferedImage TYPE_INT_RGB pour éviter des erreurs de format JPEG
						BufferedImage rgbImage = new BufferedImage(
								bufferedImage.getWidth(),
								bufferedImage.getHeight(),
								BufferedImage.TYPE_INT_RGB
						);
						rgbImage.getGraphics().drawImage(bufferedImage, 0, 0, null);
						ImageIO.write(rgbImage, "jpeg", file);
						System.out.println("Image exportée en JPEG : " + file.getAbsolutePath());
						break;
					default:
						System.err.println("Format non pris en charge : " + extension);
				}
			} catch (IOException e) {
				System.err.println("Erreur lors de l'exportation : " + e.getMessage());
			}
		}
	}

	// Méthode pour récupérer l'extension d'un fichier
	private String getFileExtension(File file) {
		String fileName = file.getName(); // Récupère le nom complet du fichier
		int dotIndex = fileName.lastIndexOf('.'); // Cherche la dernière occurrence du point
		return (dotIndex > 0) ? fileName.substring(dotIndex + 1) : ""; // Retourne l'extension
	}

}