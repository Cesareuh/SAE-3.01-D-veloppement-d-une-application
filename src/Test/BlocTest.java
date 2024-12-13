package Test;

import app.Bloc;
import app.Position;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BlocTest {

    private Bloc bloc;

    @BeforeEach
    public void setUp() {
        bloc = new Bloc("TestBloc", "image.png", "ImplClass", "HeritageClass");
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
        // Vérifie que la liste des attributs n'est pas nulle
        assertNotNull(bloc.getListAttributs(), "La liste des attributs ne doit pas être nulle.");
    }

    @Test
    public void testGetListMethodes() {
        // Vérifie que la liste des méthodes n'est pas nulle
        assertNotNull(bloc.getListMethodes(), "La liste des méthodes ne doit pas être nulle.");
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
