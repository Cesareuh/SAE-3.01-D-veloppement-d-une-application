package app;

import app.classes.Bloc;
import app.classes.Methode;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.List;

public class FabriqueInterface extends FabriqueBloc {

    // Méthode modifiée pour accepter des paramètres
    @Override
    public Bloc creerBloc(Fichier f) {
        // Création du Bloc en passant les paramètres au constructeur
        Class<?> c = f.getClasse();
        String nom = c.getSimpleName();
        List<String> implementation = new ArrayList<>();
        for(Class<?> i : c.getInterfaces()){
            implementation.add(i.getSimpleName());
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
        return new Bloc(nom, "interface.png", implementation, null, null, methodes);

        //return new Bloc(nom, image, implementation, heritage);
    }
}
