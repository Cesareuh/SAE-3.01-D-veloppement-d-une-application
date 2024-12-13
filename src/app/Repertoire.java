package app;

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

    public void addFiles(String path){
        //TODO
    }


    @Override
    public String afficher(String prec) {
        String res = "";
        System.out.println(this.f.getAbsolutePath());
        System.out.println(this.getFileCompositeArrayList());
        for(FileComposite file : this.getFileCompositeArrayList()){
            res+=file.afficher(" "+prec);
            System.out.println(res);
        }
        return res;
    }

    public ArrayList<FileComposite> getFileCompositeArrayList() {
        return fileCompositeArrayList;
    }
}
