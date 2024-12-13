package Test;

import app.ControlButton;
import app.Modele;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ControlButtonTest {

    private Modele modele; // Instance réelle de Modele
    private TextField cheminField;
    private Button btnGenerer;
    private ControlButton controlButton;

    @BeforeEach
    void setUp() {
        // Création des objets nécessaires pour le constructeur de Modele
        GridPane scene = new GridPane();
        Canvas canvas = new Canvas();
        Pane viewport = new Pane();
        VBox explorateur = new VBox();

        // Initialisation du modèle avec les objets requis
        modele = new Modele(scene, canvas, viewport, explorateur);

        // Configuration des composants pour le contrôleur
        cheminField = new TextField();
        btnGenerer = new Button("Générer Diagramme");
        btnGenerer.setId("btnGenerer");

        // Instanciation de ControlButton
        controlButton = new ControlButton(modele, cheminField, btnGenerer);
    }

    @Test
    void testHandle_genererDiagramme_success() {
        // Définir un chemin valide
        cheminField.setText("src/main/java");

        // Vérifier que l’action ne lève pas d’exception
        assertDoesNotThrow(() -> btnGenerer.fire());
    }

    @Test
    void testHandle_genererDiagramme_emptyPath() {
        // Chemin vide
        cheminField.setText("");

        // Vérifier qu’aucune exception n’est levée (le contrôleur gère l'erreur via des logs ou messages utilisateur)
        assertDoesNotThrow(() -> btnGenerer.fire());
    }

    @Test
    void testHandle_invalidButtonId() {
        // Définir un ID invalide
        btnGenerer.setId("invalidAction");

        // Vérifier qu’aucune exception n’est levée
        assertDoesNotThrow(() -> btnGenerer.fire());
    }
}
