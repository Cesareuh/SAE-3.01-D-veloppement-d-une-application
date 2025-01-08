package app.vue;

import app.*;
import app.classes.Attribut;
import app.classes.Bloc;
import app.classes.Methode;
import app.classes.Position;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

public class VueMenuContextVide extends VueContext{

    public VueMenuContextVide(Modele m){
        super(m);
        Menu affichage = new Menu("Global view");
        MenuItem simple = new MenuItem("Simple");
        MenuItem complexe = new MenuItem("Complex");
        affichage.getItems().addAll(simple, complexe);

        // DEBUG
        // A SUPPRIMER
        Menu debug = new Menu("Debug");
        // tester affichage classes
        MenuItem add = new MenuItem("add");
        add.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "int", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            Bloc b = new Bloc("Test", "Image", null, null, a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        MenuItem add2 = new MenuItem("add2");
        add2.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "Test", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            Bloc b = new Bloc("OUII", "Image", null, null, a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        MenuItem add3 = new MenuItem("add3");
        add3.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "Int", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            Bloc b = new Bloc("Heritez-Moi", "Image", null, "OUII", a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        MenuItem add4 = new MenuItem("add4");
        add4.setOnAction((e) -> {
            ArrayList<Attribut> a = new ArrayList<>();
            Attribut a1 = new Attribut("-", "Int", "attribut");
            Attribut a2 = new Attribut("-", "String", "attribut2");
            a.add(a1);
            a.add(a2);
            ArrayList<Methode> meth = new ArrayList<>();
            Methode m1 = new Methode("+", "void", "foo()", null);
            Methode m2 = new Methode("+", "String", "foo2(x : int)", null);
            meth.add(m1);
            meth.add(m2);
            ArrayList<String> imp = new ArrayList<>();
            imp.add("Test");
            Bloc b = new Bloc("Yay", "Image", null, null, a, meth);
            Position new_pos = m.screenPosToViewportPos(new Position(this.getX(), this.getY()));
            b.setPosition(new Position((int) new_pos.getX(), (int) new_pos.getY()));
            m.afficherBloc(b);
        });
        debug.getItems().addAll(add, add2, add3, add4);

        MenuItem New = new MenuItem("New");

        New.setOnAction((e) -> {
            // Créer une instance de FileChooser pour choisir un fichier
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Fichiers Java", "*.java"));

            // Ouvrir la boîte de dialogue pour choisir où enregistrer le fichier
            fileChooser.setTitle("Choisir l'emplacement pour enregistrer le fichier");

            // Ouvrir la boîte de dialogue de sauvegarde
            File selectedFile = fileChooser.showSaveDialog(new Stage());

            if (selectedFile != null) {
                // Vérifier si le fichier sélectionné a bien l'extension .java
                String fileName = selectedFile.getName();
                if (!fileName.endsWith(".java")) {
                    fileName += ".java"; // Ajouter l'extension .java si elle n'est pas présente
                }

                // Créer le fichier à l'emplacement choisi
                File newFile = new File(selectedFile.getParent(), fileName);

                if (!newFile.exists()) {
                    try {
                        // Créer le fichier dans le système
                        if (newFile.createNewFile()) {
                            System.out.println("Fichier créé: " + newFile.getAbsolutePath());

                            // Ajouter ce fichier à la liste dans le modèle (moyennant une méthode ajouterFichier)
                            m.ajouterFichier(newFile); // Met à jour le modèle en ajoutant ce fichier

                            // Déterminer le package basé sur le répertoire sélectionné
                            String packageName = getPackageName(selectedFile.getParent());

                            // Demander à l'utilisateur d'entrer les attributs
                            TextInputDialog attributeDialog = new TextInputDialog();
                            attributeDialog.setTitle("Ajouter des attributs");
                            attributeDialog.setHeaderText("Entrez les attributs, séparés par des virgules");
                            attributeDialog.setContentText("Attributs :");
                            Optional<String> attributesInput = attributeDialog.showAndWait();
                            String[] attributes = attributesInput.isPresent() ? attributesInput.get().split(",") : new String[0];

                            // Demander à l'utilisateur d'entrer les méthodes
                            TextInputDialog methodDialog = new TextInputDialog();
                            methodDialog.setTitle("Ajouter des méthodes");
                            methodDialog.setHeaderText("Entrez les méthodes séparées par des virgules ");
                            methodDialog.setContentText("Méthodes :");
                            Optional<String> methodsInput = methodDialog.showAndWait();
                            String[] methods = methodsInput.isPresent() ? methodsInput.get().split(",") : new String[0];

                            // Construire le contenu du fichier avec le package, attributs et méthodes
                            StringBuilder content = new StringBuilder();
                            content.append("package ").append(packageName).append(";\n\n");
                            content.append("public class ").append(newFile.getName().replace(".java", "")).append(" {\n\n");

                            // Ajouter les attributs à la classe
                            for (String attribute : attributes) {
                                content.append(attribute.trim()).append(";\n");
                            }
                            content.append("\n");

                            // Ajouter les méthodes à la classe
                            for (String method : methods) {
                                content.append("    public ").append(method.trim()).append(" {\n");
                                content.append("        // TODO: Implémenter la méthode\n");
                                content.append("    }\n\n");
                            }

                            // Fermer la classe
                            content.append("}\n");

                            // Écrire dans le fichier
                            try (BufferedWriter writer = new BufferedWriter(new FileWriter(newFile))) {
                                writer.write(content.toString());
                            }
                            System.out.println("Le fichier a été écrit avec succès !");
                        } else {
                            System.out.println("Le fichier existe déjà.");
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                } else {
                    System.out.println("Le fichier existe déjà.");
                }
            }
        });
        this.getItems().addAll(affichage, debug, New);
    }

    private String getPackageName(String path) {
        File dir = new File(path);
        String packageName = "";

        // Vérifier si le chemin est un répertoire valide
        if (dir.exists() && dir.isDirectory()) {
            // Prendre directement le nom du répertoire principal (X)
            packageName = dir.getName();
        }

        // Si le nom du package est vide, retourner "default"
        return packageName.isEmpty() ? "default" : packageName;
    }



    @Override
    public void actualiser(Sujet s) {
        //TODO
    }
}
