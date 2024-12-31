package app.classes;

public class Attribut {
    private String autorisation;
    private String type;
    private String nom;

    public Attribut(String aut, String t, String n){
        this.autorisation = aut;
        this.type = t;
        this.nom = n;
    }

    public String getNom() {
        return nom;
    }

    public String getType() {
        return type;
    }

    public String getAutorisation() {
        return autorisation;
    }
}