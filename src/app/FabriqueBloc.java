package app;

public abstract class FabriqueBloc {
    // Modifier la méthode pour accepter les arguments nécessaires
    public abstract Bloc creerBloc(String nom, String image, String implementation, String heritage);
}
