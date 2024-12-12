package app;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Fichier extends FileComposite{
    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        Class<? extends Fichier> c = this.getClass();
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
        return prec+"}\n";
    }
}
