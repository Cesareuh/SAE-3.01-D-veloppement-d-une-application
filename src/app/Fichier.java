package app;

import app.classes.Bloc;

import java.io.*;
import java.lang.reflect.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.*;
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
            if(!c.getSuperclass().getSimpleName().equals("Object")) {
                prec += "superClass : " + c.getSuperclass().getSimpleName() + "\n";
            }
        }

        return prec + "}\n";
    }

    @Override
    public String genererPlantUML(String s) throws IOException {
        Class<?> c = getClasse();
        // Vérifier si la classe est une interface ou une classe abstraite ou normale
        if (c.isInterface()) {
            s+="Interface " + c.getSimpleName() + " {\n";
        } else {
            if (Modifier.isAbstract(c.getModifiers())) {
                s+="Abstract Class " + c.getSimpleName() + " {\n";
            } else {
                s+="Class " + c.getSimpleName() + " {\n";
            }
        }
        HashMap<Field, String> lf = new HashMap<>();
        // Ajouter les attributs
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
            if(field.getType().isPrimitive() || field.getType().getSimpleName().equals("String")) {
                s+=modifierSymbol + field.getName() + " : " + field.getType().getName()+"\n";
            }else{
                lf.put(field, modifierSymbol);
            }
        }

        // Ajouter les méthodes
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
            s+=modifierSymbol + method.getName() + "(";
            Class<?>[] paramTypes = method.getParameterTypes();
            for (int i = 0; i < paramTypes.length; i++) {
                if (i > 0) {
                    s+=", ";
                }
                s+=paramTypes[i].getTypeName();
            }
            s+=") : " + method.getReturnType().getName() + "\n";
        }
        s+="}\n";
        if(c.getSuperclass() != null) {
            if(!c.getSuperclass().getSimpleName().equals("Object")) {
                s += c.getSimpleName() + " --|> " + c.getSuperclass().getSimpleName() + "\n";
            }
        }

        for(Class i : c.getInterfaces()){
            s+=c.getSimpleName()+" ..|> " + i.getSimpleName()+"\n";
        }

        for(Field field : lf.keySet()) {
            if(Collection.class.isAssignableFrom(field.getType()) || Map.class.isAssignableFrom(field.getType())) {
                Type t = field.getGenericType();
                if(t instanceof ParameterizedType) {
                    ParameterizedType pt = (ParameterizedType) t;
                    Type[] types = pt.getActualTypeArguments();
                    for(Type typeArg : types) {
                        s+=c.getSimpleName()+ " -->\"*\"" + ((Class<?>)typeArg).getSimpleName() +" : "+ field.getName() + "\n";
                    }
                }
            }else {
                s += c.getSimpleName() + " --> " + field.getType().getSimpleName() + " : " + lf.get(field) + field.getName() + "\n";
            }
        }
        return s;
    }

    public static String getNomCompletClasse(File file) {
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
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Construire le nom complet
        if (packageName != null) {
            return packageName + "." + className;
        } else {
            return className; // Pas de package trouvé
        }
    }

    public Bloc creerBloc(){
        Class<?> c = getClasse();
        Bloc bloc = null;
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
        File file = new File("./projClass");
        System.out.println("Fichier existe : " + file.exists());
        String classPath = null;
        try {
            classPath = file.getCanonicalPath();
            System.out.println("Chemin absolu : " + file.getCanonicalPath());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        URL[] urls;
        try {
            urls = new URL[]{new URL("file:/"+classPath+"/")};
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        URLClassLoader ucl = new URLClassLoader(urls);
        Class<?> c = null;
        try {
            System.out.println(getNomCompletClasse(f));
            System.out.println(Arrays.toString(ucl.getURLs()));
            c = ucl.loadClass(getNomCompletClasse(f));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return c;
    }

    public String getName(){
        return f.getName();
    }

    public void supp(){
        if(f.delete()){
            System.out.println("reussite supp");
        }else System.out.println("erreur supp");
    }

}
