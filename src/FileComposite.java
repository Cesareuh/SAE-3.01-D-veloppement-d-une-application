import java.io.File;

public abstract class FileComposite {
    //attributs
    protected File f;

    //constructeur
    public FileComposite(File file) {
        this.f = file;
    }
    //methodes
    public abstract String afficher(String s);
}
