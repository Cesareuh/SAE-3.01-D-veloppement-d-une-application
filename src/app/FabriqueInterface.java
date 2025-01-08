package app;

import app.classes.Bloc;
import app.classes.Methode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FabriqueInterface extends FabriqueBloc {

    // Méthode modifiée pour accepter des paramètres
    @Override
    public Bloc creerBloc(Fichier f) {
        Bloc b = super.creerBloc(f);
        b.setImage("interface.png");
        return b;
        //return new Bloc(nom, image, implementation, heritage);
    }
}
