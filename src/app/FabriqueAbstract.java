package app;

import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Methode;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FabriqueAbstract extends FabriqueBloc {
    @Override
    public Bloc creerBloc(Fichier f) {
        Bloc b = super.creerBloc(f);
        Class<?> c = f.getClasse();
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

        b.setAttributs(attributs);
        b.setHeritage(heritage);
        b.setImage("abstract.png");
        return b;
        //return new Bloc(nom, image, implementation, heritage);
    }
}
