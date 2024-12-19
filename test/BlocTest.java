import app.Bloc;
import app.Position;
import app.Attribut;
import app.Methode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlocTest {

    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        List<Attribut> attributs = new ArrayList<>();
        List<Methode> methodes = new ArrayList<>();
        bloc = new Bloc("TestBloc", "image.png", "ImplClass", "HeritageClass", attributs, methodes);
    }

    @Test
    public void testSetPosition() {
        bloc.setPosition(10, 20);
        assertNotNull(bloc.getPosition(), "La position ne doit pas être nulle.");
        assertEquals(10, bloc.getPositionX(), "La position X doit être 10.");
        assertEquals(20, bloc.getPositionY(), "La position Y doit être 20.");
    }

    @Test
    public void testSetNom() {
        bloc.setNom("TestBlocModifie");
        assertEquals("TestBlocModifie", bloc.getNom(), "Le nom du bloc doit être 'TestBlocModifie'.");
    }

    @Test
    public void testGetListAttributs() {
        List<Attribut> attributs = bloc.getListAttributs();
        assertNotNull(attributs, "La liste des attributs ne doit pas être nulle.");
        assertTrue(attributs.isEmpty(), "La liste des attributs doit être vide au départ.");
    }

    @Test
    public void testGetListMethodes() {
        List<Methode> methodes = bloc.getListMethodes();
        assertNotNull(methodes, "La liste des méthodes ne doit pas être nulle.");
        assertTrue(methodes.isEmpty(), "La liste des méthodes doit être vide au départ.");
    }

    @Test
    public void testInitialPosition() {
        Position positionInitiale = bloc.getPosition();
        assertNotNull(positionInitiale, "La position initiale ne doit pas être nulle.");
        assertEquals(0, positionInitiale.getX(), "La position X initiale doit être 0.");
        assertEquals(0, positionInitiale.getY(), "La position Y initiale doit être 0.");
    }

    @Test
    public void testHeritageAndImplementation() {
        assertEquals("HeritageClass", bloc.getHeritage(), "L'héritage doit être 'HeritageClass'.");
        assertEquals("ImplClass", bloc.getImplementation(), "L'implémentation doit être 'ImplClass'.");
    }
}
