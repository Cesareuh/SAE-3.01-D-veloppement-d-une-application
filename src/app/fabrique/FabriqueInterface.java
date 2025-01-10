package app.fabrique;

import app.introspection.Fichier;
import app.classes.Bloc;

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
