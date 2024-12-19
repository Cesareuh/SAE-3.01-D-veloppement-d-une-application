package app;


public class Fleche {
    private int blocDepart;
    private int blocArrivee;

    private String type;

    public Fleche(int blocDepart, int blocArrivee, String type) {
        this.blocDepart = blocDepart;
        this.blocArrivee = blocArrivee;
        this.type = type;
    }

    public Position getPosDep(){
       return null;
    }

    public Position getPosArr(){
        return null;
    }

    public int getBlocDepart(){
        return blocDepart;
    }

    public int getBlocArrivee(){
        return blocArrivee;
    }

    public String getType() {
        return type;
    }
}

