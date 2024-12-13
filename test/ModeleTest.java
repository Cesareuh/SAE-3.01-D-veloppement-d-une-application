import static org.junit.jupiter.api.Assertions.*;

import app.Bloc;
import app.Fleche;
import app.Position;
import app.Modele;
import org.junit.jupiter.api.Test;

import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

class ModeleTest {

    // Test de la méthode creerBloc
    @Test
    void testCreerBloc() {

        VBox root = new VBox();
        Canvas canvas = new Canvas();
        Pane viewport = new Pane();
        VBox explorateur = new VBox();
        Stage stage = new Stage();
        Modele modele = new Modele(root, viewport, explorateur, stage);

        Position position = new Position(10, 20);

        modele.creerBloc(Bloc.class, position);

        Bloc bloc = modele.getBlocById(1);
        assertNotNull(bloc, "Le bloc doit exister.");
        assertEquals(10, bloc.getPositionX(), "La position X du bloc doit être 10.");
        assertEquals(20, bloc.getPositionY(), "La position Y du bloc doit être 20.");
    }


    @Test
    void testCreerFleche() {
        // Arrange
        VBox root = new VBox();
        Canvas canvas = new Canvas();
        Pane viewport = new Pane();
        VBox explorateur = new VBox();
        Stage stage = new Stage();
        Modele modele = new Modele(root, canvas, viewport, explorateur, stage);


        modele.creerFleche(1, 2, "type");

        assertEquals(1, modele.getfleches().size(), "Il devrait y avoir une flèche.");
        Fleche fleche = modele.getfleches().get(0);
        assertEquals(1, fleche.getBlocDepart(), "Le bloc de départ de la flèche doit être 1.");
        assertEquals(2, fleche.getBlocArrivee(), "Le bloc d'arrivée de la flèche doit être 2.");
        assertEquals("type", fleche.getType(), "Le type de la flèche doit être 'type'.");
    }

    // Test de la méthode setBlocCourant
    @Test
    void testSetBlocCourant() {
        VBox root = new VBox();
        Canvas canvas = new Canvas();
        Pane viewport = new Pane();
        VBox explorateur = new VBox();
        Stage stage = new Stage();
        Modele modele = new Modele(root, canvas, viewport, explorateur, stage);

        modele.setBlocCourant(3);

        assertEquals(3, modele.getblocCourant(), "Le bloc courant doit être 3.");
    }




}
