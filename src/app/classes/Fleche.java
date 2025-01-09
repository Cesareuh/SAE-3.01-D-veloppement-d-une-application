package app.classes;


public class Fleche {
    public static int ASSOCIATION = 0;
    public static int HERITAGE = 1;
    public static int IMPLEMENTATION = 2;
    private int blocDepart;
    private int blocArrivee;
    private int type;
    private Position centre;
    private Attribut attribut;

    public Fleche(int blocDepart, int blocArrivee, int type) {
        this.blocDepart = blocDepart;
        this.blocArrivee = blocArrivee;
        this.type = type;
        this.centre = new Position(-1,-1);
        this.attribut = null;
    }

    public Fleche(int blocDepart, int blocArrivee, int type, Attribut a) {
        this(blocDepart, blocArrivee, type);
        this.attribut = a;
    }

    public Attribut getAttribut(){
        return this.attribut;
    }

    public int getBlocDepart(){
        return blocDepart;
    }

    public int getBlocArrivee(){
        return blocArrivee;
    }

    public int getType() {
        return type;
    }

    public Position getCentre(){
        return this.centre;
    }

    public void setCentre(Position p){
        this.centre = p;
    }
}

