package app.control;

import app.classes.Modele;
import app.introspection.Repertoire;
import app.classes.Bloc;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

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
					modele.suppRepClass();
					// Ouvre un dialogue pour sélectionner un répertoire
					DirectoryChooser dirChooser = new DirectoryChooser();
					dirChooser.setTitle("Select Directory");
					File selectedDirectory = dirChooser.showDialog(modele.getStage());

					if (selectedDirectory != null) {
						System.out.println("Répertoire sélectionné : " + selectedDirectory.getAbsolutePath());

						// Mettez à jour le modèle avec le répertoire sélectionné
						modele.setRep(selectedDirectory);
						modele.initialiserFichiers(selectedDirectory);
					}
					break;

				case "Export as image":
					// Action pour exporter le contenu en PNG
					exportAsImage();
					break;

				case "Remove":
					modele.supprimerBlocSelect();
					System.out.println("Bloc supprimé.");
					break;

				case "Modifier": // Regrouper les options "Remove" et "Modifier"
					System.out.println("Bloc modifié.");
					// Appel de la méthode pour ouvrir un éditeur de bloc
					ouvrirDialogueModification();
					break;

				case "Change Background Color":
					// Action pour changer la couleur de fond
					openColorPicker();
					break;

				case "Generate plantuml":
					System.out.println("oui");
					if (modele.getRep().isDirectory()) {
						Repertoire r = new Repertoire(modele.getRep());
						File f = new File("src/uml/plantuml.puml");
						if (f.exists()) {
							f.delete();
						}
						try {
							f.createNewFile();
							BufferedWriter bw = new BufferedWriter(new FileWriter(f));
							bw.write("@startuml");
							bw.newLine();
							bw.write(r.genererPlantUML(""));
							bw.newLine();
							bw.write("@enduml");
							bw.flush();
							System.out.println("fini");
						} catch (IOException e) {
							throw new RuntimeException(e);
						}
					}
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






	private void ouvrirDialogueModification() {
		// Création d'une nouvelle fenêtre de dialogue
		Stage stage = new Stage();
		stage.setTitle("Modifier Bloc");

		// Création des éléments de l'interface utilisateur
		TextField nomBlocField = new TextField();
		nomBlocField.setPromptText("Nouveau nom du bloc");

		TextField attributNomField = new TextField();
		attributNomField.setPromptText("Nom de l'attribut");

		TextField attributTypeField = new TextField();
		attributTypeField.setPromptText("Type de l'attribut");

		Button ajouterAttributButton = new Button("Ajouter Attribut");
		Button validerButton = new Button("Valider");

		// Création d'un conteneur pour les attributs ajoutés
		VBox attributsBox = new VBox(5);
		List<String> attributs = new ArrayList<>();

		// Action pour ajouter un attribut
		ajouterAttributButton.setOnAction(e -> {
			String nomAttribut = attributNomField.getText();
			String typeAttribut = attributTypeField.getText();

			if (!nomAttribut.isEmpty() && !typeAttribut.isEmpty()) {
				attributs.add(nomAttribut + ": " + typeAttribut);
				attributsBox.getChildren().add(new Label(nomAttribut + ": " + typeAttribut));
				attributNomField.clear();
				attributTypeField.clear();
			}
		});

		// Action pour valider la modification du bloc
		validerButton.setOnAction(e -> {
			String nouveauNomBloc = nomBlocField.getText();
			if (!nouveauNomBloc.isEmpty()) {
				modele.modifierNomBloc(nouveauNomBloc); // Mise à jour du modèle avec le nouveau nom du bloc
				System.out.println("Nom du bloc modifié : " + nouveauNomBloc);
			}

			// Ajouter les attributs au modèle
			for (String attribut : attributs) {
				String[] parts = attribut.split(": ");
				modele.ajouterAttributDansBloc(nouveauNomBloc, parts[0], parts[1]); // Ajouter les attributs au bloc
			}

			stage.close(); // Fermer la fenêtre après validation
		});

		// Mise en place du layout
		VBox layout = new VBox(10, nomBlocField, attributNomField, attributTypeField, ajouterAttributButton, attributsBox, validerButton);
		layout.setPadding(new Insets(20));

		Scene scene = new Scene(layout);
		stage.setScene(scene);
		stage.show();
	}



	public void handleRemoveAction() {
		modele.supprimerBlocSelect();
		System.out.println("Bloc supprimé.");
	}

	public void handleModifyAction() {
		System.out.println("Bloc modifié.");
		ouvrirDialogueModification();  // Ouvre un dialogue de modification
	}

	private void openColorPicker() {
		// Récupérer le bloc sélectionné
		Bloc selectedBloc = modele.getBlocSelectionne();
		if (selectedBloc != null) {
			// Créer un sélecteur de couleur
			ColorPicker colorPicker = new ColorPicker();
			colorPicker.setValue(selectedBloc.getBackgroundColor());  // Définir la couleur actuelle du bloc

			// Créer une nouvelle fenêtre pour afficher le sélecteur de couleur
			Stage colorStage = new Stage();
			colorStage.setTitle("Choose Background Color");

			// Lorsque l'utilisateur choisit une couleur
			colorPicker.setOnAction(e -> {
				// Mettre à jour la couleur de fond du bloc sélectionné
				selectedBloc.setBackgroundColor(colorPicker.getValue());

				// Rafraîchir l'UI ou mettre à jour l'affichage du bloc
				modele.notifierObs();  // Notifier les observateurs pour que l'interface soit mise à jour
			});

			// Mise en place du layout
			VBox layout = new VBox(10, colorPicker);
			layout.setPadding(new Insets(20));

			Scene scene = new Scene(layout);
			colorStage.setScene(scene);
			colorStage.show();
		}
	}


	public void handleRemoveAAction() {
		// Vérifie si le modèle est nul pour éviter des erreurs
		if (modele == null) {
			System.out.println("Le modèle est nul. Impossible de supprimer.");
			return;
		}

		// Supprime toutes les flèches du modèle
		modele.getFlechesMap().clear();

		// Supprime tous les blocs du modèle
		modele.getBlocsMap().clear();

		// Notifie les observateurs pour mettre à jour l'interface
		modele.notifierObs();

		// Affiche un message de confirmation
		System.out.println("Tous les blocs et les flèches ont été supprimés.");
	}


}

