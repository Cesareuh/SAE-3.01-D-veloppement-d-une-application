package app.introspection;

import java.io.File;
import java.io.IOException;

public abstract class FileComposite {
    //attributs
    protected File f;

    //constructeur
    public FileComposite(File file) {

        this.f = file;
    }
    //methodes
    public abstract String afficher(String s);

    public abstract String genererPlantUML(String s) throws IOException;

    public File getF() {
        return f;
    }

    public abstract void supp();
}
