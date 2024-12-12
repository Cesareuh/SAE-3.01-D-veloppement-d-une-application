import java.io.File;
import java.util.ArrayList;

public class Repertoire extends FileComposite{
    protected ArrayList<FileComposite> fileCompositeArrayList = new ArrayList<>();
    public Repertoire(File file){
        super(file);
        File[] files = f.listFiles();
        if(files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    fileCompositeArrayList.add(new Fichier(f));
                } else {
                    fileCompositeArrayList.add(new Repertoire(f));
                }
            }
        }
    }


    @Override
    public String afficher(String prec) {
        String res="";
        res+=prec+"-"+this.f.getName()+"\n";
        for (FileComposite fileComposite : fileCompositeArrayList) {
            res+=fileComposite.afficher(prec+"| ");
        }
        return res;
    }
}
