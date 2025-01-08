package app;

import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Methode;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
            Type t= field.getGenericType();
            String type = field.getType().getSimpleName();
            if(Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())){
                if(t instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) t;
                    Type[] types = pt.getActualTypeArguments();
                    type+="<";
                    if(types.length > 0) {
                        int i=0;
                        for (Type typeArg : types) {
                            if(i>0){
                                type += ", ";
                            }else i++;
                            type +=  ((Class<?>) typeArg).getSimpleName();
                        }
                        System.out.println(type);
                    }
                    type+=">";
                }
            }
            attributs.add(new Attribut(aut, type,field.getName()));
        }

        //return null;
        b.setAttributs(attributs);
        b.setHeritage(heritage);
        b.setImage("class.png");
        return b;
        //return new Bloc(nom, image, implementation, heritage);
    }
}
