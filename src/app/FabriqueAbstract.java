package app;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FabriqueAbstract extends FabriqueBloc {
    @Override
    public Bloc creerBloc(Fichier f) {
        Class<?> c = f.getClasse();
        String nom = c.getSimpleName();
        List<String> implementation = new ArrayList<>();
        for(Class<?> i : c.getInterfaces()){
            implementation.add(i.getSimpleName());
        }
        String heritage = null;
        if(!c.getSuperclass().getSimpleName().equals("Object")){
            heritage = c.getSuperclass().getSimpleName();
        }
        List<Attribut> attributs = new ArrayList<>();
        for (Field field : c.getDeclaredFields()) {
            // Déterminer la visibilité
            String aut = null;
            if (Modifier.isPublic(field.getModifiers())) {
                aut = "+";
            } else if (Modifier.isPrivate(field.getModifiers())) {
                aut = "-";
            } else if (Modifier.isProtected(field.getModifiers())) {
                aut = "#";
            }
            attributs.add(new Attribut(aut, field.getType().getSimpleName(),field.getName()));
        }

        List<Methode> methodes = new ArrayList<>();
        for (Method method : c.getDeclaredMethods()) {
            String aut = null;
            if (Modifier.isPublic(method.getModifiers())) {
                aut = "+";
            } else if (Modifier.isPrivate(method.getModifiers())) {
                aut = "-";
            } else if (Modifier.isProtected(method.getModifiers())) {
                aut = "#";
            }
            List<String> params = new ArrayList<>();
            for (Parameter p : method.getParameters()) {
                String param = p.getType().getSimpleName() + " : " + p.getName();
                params.add(param);
            }
            methodes.add(new Methode(aut, method.getReturnType().getSimpleName(), method.getName(), params));
        }
        return new Bloc(nom, null, implementation, heritage, attributs, methodes);
        //return new Bloc(nom, image, implementation, heritage);
    }
}
