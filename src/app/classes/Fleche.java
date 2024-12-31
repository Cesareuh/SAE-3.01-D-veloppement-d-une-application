package app.classes;


public class Fleche {
    public static int ASSOCIATION = 0;
    public static int HERITAGE = 1;
    public static int IMPLEMENTATION = 2;
    private int blocDepart;
    private int blocArrivee;

    private int type;

    public Fleche(int blocDepart, int blocArrivee, int type) {
        this.blocDepart = blocDepart;
        this.blocArrivee = blocArrivee;
        this.type = type;
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
}

