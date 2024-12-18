package app;

import java.util.List;

public class Methode {
    private String autorisation;
    private String type;
    private String nom;
    private List<String> param;

    public Methode(String aut, String t, String n, List<String> p) {
        this.autorisation = aut;
        this.type = t;
        this.nom = n;
        this.param = p;
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

    public List<String> getParam() {return param;}
}
