package app;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class Fichier extends FileComposite {

    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        String path = f.getAbsolutePath();

        // Obtenir le nom qualifié de la classe
        String basePath = System.getProperty("user.dir") + File.separator + "bin"; // Répertoire des classes compilées
        if (path.startsWith(basePath)) {
            path = path.substring(basePath.length() + 1); // Supprimer la partie "bin/"
        }
        path = path.replace(File.separator, ".").replace(".class", ""); // Convertir le chemin en nom qualifié

        Class<?> c;
        try {
            c = Class.forName(path); // Charger la classe dynamiquement
        } catch (ClassNotFoundException e) {
            System.out.println("Erreur : Classe introuvable pour " + f.getName());
            return "";
        }

        // Vérifier si la classe est une interface ou une classe abstraite ou normale
        if (c.isInterface()) {
            prec += "Interface " + c.getSimpleName() + " {\n";
        } else {
            if (Modifier.isAbstract(c.getModifiers())) {
                prec += "Abstract Class " + c.getSimpleName() + " {\n";
            } else {
                prec += "Class " + c.getSimpleName() + " {\n";
            }
        }

        // Ajouter les attributs
        prec += "  Attributs :\n";
        for (Field field : c.getDeclaredFields()) {
            // Déterminer la visibilité
            String modifierSymbol;
            if (Modifier.isPublic(field.getModifiers())) {
                modifierSymbol = "  +";
            } else if (Modifier.isPrivate(field.getModifiers())) {
                modifierSymbol = "  -";
            } else if (Modifier.isProtected(field.getModifiers())) {
                modifierSymbol = "  #";
            } else {
                modifierSymbol = "   ";
            }
            prec += modifierSymbol + field.getName() + " : " + field.getType().getName() + "\n";
        }

        // Ajouter les méthodes
        prec += "  Méthodes :\n";
        for (Method method : c.getDeclaredMethods()) {
            // Déterminer la visibilité
            String modifierSymbol;
            if (Modifier.isPublic(method.getModifiers())) {
                modifierSymbol = "  +";
            } else if (Modifier.isPrivate(method.getModifiers())) {
                modifierSymbol = "  -";
            } else if (Modifier.isProtected(method.getModifiers())) {
                modifierSymbol = "  #";
            } else {
                modifierSymbol = "   ";
            }

            // Construire la signature de la méthode
            prec += modifierSymbol + method.getName() + "(";
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    prec += ", ";
                }
                prec += paramTypes[i].getTypeName();
            }
            prec += ") : " + method.getReturnType().getName() + "\n";
        }

        return prec + "}\n";
    }
}
