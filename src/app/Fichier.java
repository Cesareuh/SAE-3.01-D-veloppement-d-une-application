package app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Fichier extends FileComposite{
    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        try {
            System.out.println("a"+f.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String path = f.getPath();
        path = path.replace("\\", ".");
        Class<?> c;
        try {
            c = Class.forName(path);
        } catch (ClassNotFoundException e) {
            System.out.println("erreur");
            return "";
        }
        if(c.isInterface()) {
            prec+="Interface "+c.getSimpleName()+"{\n";
            //return prec+">"+f.getName()+"("+f.length()+")\n";
        }else {
            if (Modifier.isAbstract(c.getModifiers())) {
                prec += "Abstract Class " + c.getSimpleName() + "{\n";
            } else {
                prec += "Class " + c.getSimpleName() + "{\n";
            }
            prec+=" attribut : \n";
            for(Field f : c.getDeclaredFields()) {
                if(Modifier.isPublic(f.getModifiers())){
                    prec+="  +";
                }else if(Modifier.isPrivate(f.getModifiers())){
                    prec+="  -";
                }else{
                    prec+="  #";
                }
                prec+=f.getName()+" : "+f.getType().getName()+"\n";
            }
        }
        System.out.println(prec);
        return prec+"}\n";
    }
}
