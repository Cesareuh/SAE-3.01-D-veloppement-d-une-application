package app;

public interface Sujet {
    //methodes pour gerer les observateurs du model
    public void ajouterObs(Observateur o);
    public void supprimerObs(Observateur o);
    public void notifierObs();
}
