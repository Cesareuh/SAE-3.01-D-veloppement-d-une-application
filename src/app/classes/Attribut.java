package app.classes;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Attribut attribut)) return false;
        return Objects.equals(autorisation, attribut.autorisation) && Objects.equals(type, attribut.type) && Objects.equals(nom, attribut.nom);
    }

    @Override
    public int hashCode() {
        return Objects.hash(autorisation, type, nom);
    }
}