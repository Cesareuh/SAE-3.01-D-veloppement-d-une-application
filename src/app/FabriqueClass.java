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

public class FabriqueClass extends FabriqueBloc {

    @Override
    public Bloc creerBloc(Fichier f) {

        Class<?> c = f.getClasse();
        Bloc b = super.creerBloc(f);
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

        //return null;
        b.setAttributs(attributs);
        b.setHeritage(heritage);
        b.setImage("class.png");
        return b;
        //return new Bloc(nom, image, implementation, heritage);
    }
}
