package app.introspection;

import java.io.*;
import java.util.ArrayList;

public class Repertoire extends FileComposite{
    protected ArrayList<FileComposite> fileCompositeArrayList = new ArrayList<>();
    public Repertoire(File file){
        super(file);
        File[] files = f.listFiles();
        if(files != null) {
            for (File f : files) {
                if (f.isFile() && f.getName().endsWith(".java") && !f.getName().equals("module-info.java")) {
                    fileCompositeArrayList.add(new Fichier(f));
                } else if (f.isDirectory()) {
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
        for(FileComposite file : this.getFileCompositeArrayList()){
            res+=file.afficher(" "+prec);
        }
        return res;
    }

    @Override
    public String genererPlantUML(String s) throws IOException {
        String res = "";
        for(FileComposite file : this.getFileCompositeArrayList()){
            res+=file.genererPlantUML("\n"+s);
        }
        return res;
    }

    public ArrayList<FileComposite> getFileCompositeArrayList() {
        return fileCompositeArrayList;
    }

    public ArrayList<Fichier> getFichiers() {
        ArrayList<Fichier> fichiers = new ArrayList<>();
        for(FileComposite file : this.getFileCompositeArrayList()){
            if(file instanceof Fichier){
                fichiers.add((Fichier)file);
            }else {
                fichiers.addAll(((Repertoire) file).getFichiers());
            }
        }
        return fichiers;
    }

    public void supp(){
        File[] files = this.f.listFiles();
        if(files != null) {
            for (File f : files) {
                FileComposite fichier;
                if(f.isFile()) {
                    fichier = new Fichier(f);
                }else{
                    fichier = new Repertoire(f);
                }
                fichier.supp();
            }
        }
        if(f.delete()){
            System.out.println("reussite supp");
        }else System.out.println("erreur supp");
    }
}
