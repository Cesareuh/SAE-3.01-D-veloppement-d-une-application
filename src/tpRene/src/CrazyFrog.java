package tpRene.src;

public class CrazyFrog implements Logo{
    private String nomImg;

    public double prix;

    public CrazyFrog() {
        nomImg = "img/CrazyFrog.jpg";
        prix = 4.23;
    }

    public MyImage getLogo() {
        return new MyImage(nomImg);
    }


    public double combienCaCoute() {
        return prix;
    }
}
