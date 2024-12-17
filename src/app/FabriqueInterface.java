package app;

public class FabriqueInterface extends FabriqueBloc {

    // Méthode modifiée pour accepter des paramètres
    @Override
    public Bloc creerBloc(String nom, String image, String implementation, String heritage) {
        // Création du Bloc en passant les paramètres au constructeur
        return null;
        //return new Bloc(nom, image, implementation, heritage);
    }
}
