package tpRene.src;

public abstract class Accesoire implements Logo {
    protected double prix;
    protected Logo composant;

    public Accesoire(Logo composant) {
        this.composant = composant;
    }

    public double combienCaCoute(){
        return composant.combienCaCoute()+this.prix;
    }
}
