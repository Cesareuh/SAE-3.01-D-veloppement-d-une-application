package app;

public class FabriqueClass extends FabriqueBloc {

    @Override
    public Bloc creerBloc(String nom, String image, String implementation, String heritage) {
        return new Bloc(nom, image, implementation, heritage);
    }
}
