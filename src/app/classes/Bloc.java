package app.classes;

import javafx.scene.paint.Color;
import java.util.List;

public class Bloc {
    private boolean affichageSimple;
    private String nom;
    private String image;
    private List<String> implementation;
    private String heritage;
    private Position pos;
    private List<Attribut> attributs;
    private List<Methode> methodes;
    private Color backgroundColor;
    // Constructeur
    public Bloc(String nom, String image, List<String> implementation, String heritage, List attrs, List meths) {
        this.nom = nom;
        this.image = image;
        this.implementation = implementation;
        this.heritage = heritage;
        this.pos = new Position();
        this.attributs = attrs;
        this.methodes = meths;
        this.affichageSimple = false; // Par défaut, la vue est complexe
        this.backgroundColor = Color.YELLOW;  // Couleur de fond par défaut
    }

    // Getter et setter pour la couleur de fond
    public Color getBackgroundColor() {
        return backgroundColor;
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    // Nouvelle méthode pour valider la couleur
    public void appliquerCouleurValidee(Color couleur) {
        this.backgroundColor = couleur;
    }

    // Autres getters et setters
    public boolean isAffichageSimple() {
        return affichageSimple;
    }

    public void setAffichageSimple(boolean affichageSimple) {
        this.affichageSimple = affichageSimple;
    }

    public void setPosition(Position pos) {
        if (pos == null) {
            pos = new Position();
        }
        this.pos = pos;
    }

    public void setAttributs(List attrs){
        this.attributs = attrs;
    }

    public void setHeritage(String h){
        this.heritage = h;
    }

    public void setImage(String i){
        this.image = i;
    }

    public Position getPosition() {
        return pos;
    }

    public String getImage() {
        return image;
    }

    public List<String> getImplementation() {
        return implementation;
    }

    public String getHeritage() {
        return heritage;
    }

    public List<Attribut> getListAttributs() {
        return attributs;
    }

    public List<Methode> getListMethodes() {
        return methodes;
    }

    public String getNom() {
        return this.nom;
    }

    public void setNom(String className) {
        this.nom = className;
    }

    public void ajouterAttribut(String autorisation, String nom, String type) {
        Attribut nouvelAttribut = new Attribut(autorisation, type, nom);  // Création de l'attribut avec l'autorisation
        attributs.add(nouvelAttribut);
    }

    public void ajouterMethode(String autorisation, String nom, String typeRetour, List<String> parametres) {
        Methode nouvelleMethode = new Methode(autorisation, typeRetour, nom, parametres);  // Ajout de l'autorisation
        methodes.add(nouvelleMethode);
    }

}
