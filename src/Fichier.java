import java.io.File;

public class Fichier extends FileComposite{
    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        return prec+">"+f.getName()+"("+f.length()+")\n";
    }
}
