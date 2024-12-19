package app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Fichier extends FileComposite {

    public Fichier(File f) {
        super(f);
    }

    @Override
    public String afficher(String prec) {
        Class<?> c = getClasse();

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
        if(c.getSuperclass() != null) {
            prec += "superClass : " + c.getSuperclass().getSimpleName() + "\n";
        }

        return prec + "}\n";
    }

    public static String getNomCompletClasse(File file) throws IOException {
        String packageName = null;
        String className = file.getName().replace(".java", ""); // Nom du fichier sans extension

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            Pattern packagePattern = Pattern.compile("^\\s*package\\s+([a-zA-Z0-9_.]+);");
            while ((line = reader.readLine()) != null) {
                Matcher matcher = packagePattern.matcher(line);
                if (matcher.find()) {
                    packageName = matcher.group(1); // Nom du package
                    break;
                }
            }
        }

        // Construire le nom complet
        if (packageName != null) {
            return packageName + "." + className;
        } else {
            return className; // Pas de package trouvé
        }
    }

    public Bloc creerBloc(){
        Class<?> c;
        Bloc bloc = null;
        try {
            c = Class.forName(getNomCompletClasse(f));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (c.isInterface()) {
            FabriqueInterface fab = new FabriqueInterface();
            bloc = fab.creerBloc(this);
        } else if (Modifier.isAbstract(c.getModifiers())) {
            FabriqueAbstract fab = new FabriqueAbstract();
            bloc = fab.creerBloc(this);
        } else {
            FabriqueClass fab = new FabriqueClass();
            bloc = fab.creerBloc(this);
        }
        return bloc;
    }

    public Class<?> getClasse(){
        Class<?> c = null;
        try {
            c = Class.forName(getNomCompletClasse(f));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public String getName(){
        return f.getName();
    }

}
