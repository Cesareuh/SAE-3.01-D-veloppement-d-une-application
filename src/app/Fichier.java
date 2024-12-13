package app;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

public class Fichier extends FileComposite{
    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        Class<?> c;
        try {
            String path ="app."+this.f.getName();
            path = path.replace(".java", "");
            c = Class.forName(path);
        } catch (ClassNotFoundException e) {
            System.out.println("erreurs : " + this.f.getName());
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
            prec+=" methode : \n";
            for(Method m : c.getDeclaredMethods()) {
                if(Modifier.isPublic(m.getModifiers())){
                    prec+="  +";
                }else if(Modifier.isPrivate(m.getModifiers())){
                    prec+="  -";
                }else {
                    prec+="  #";
                }
                prec+=m.getName()+"(";
                Type[] types = m.getParameterTypes();
                if(types.length > 0) {
                    prec+=types[0].getTypeName();
                    for(int i=1;i<types.length;i++) {
                        prec+=", "+types[i].getTypeName();
                    }
                }
                prec+=") : "+m.getReturnType().getName()+"\n";
            }
        }
        System.out.println(prec);
        return prec+"}\n";
    }
}
