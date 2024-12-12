import java.io.File;

public abstract class FileComposite {
    //attributs
    protected File file;

    //constructeur
    public FileComposite(File file) {
        this.file = file;
    }
    //methodes
    public abstract String afficher(String s);
}
