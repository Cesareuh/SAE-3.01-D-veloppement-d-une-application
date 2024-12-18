package tpRene.src;

public class Main
{
   
    public static void main(String args[])
    {
        Logo l = new Text(new Smiley(new Candy(new Lunettes(new Chapeau(new ReneLaTaupe())))),"oui");
        MyImage i = l.getLogo();
        System.out.println(l.combienCaCoute());
        
        //i.paintOver("img/Chapeau.png", 280,42);
        //i.paintOver("img/Sunglasses.png", 255,76);
        
        i.display();  // Permet l'affichage dans une fenetre de l'image associee
        
        
    }
        
}
