@startuml
class Attribut {
    - autorisation : String
    - type : String
    - nom : String
    + Attribut(a : String, t : String, n : String)
    + getAutorisation() : String
    + getType() : String
    + getNom() : String
}
Attribut "*" <-- Bloc : - attributs

class Methode {
    - autorisation : String
    - type : String
    - nom : String
    - parametres : List<String>
    + Methode(a : String, t : String, n : String, p : List<String>)
    + getAutorisation() : String
    + getType() : String
    + getNom() : String
}
Methode "*" <-- Bloc : - methodes

class Position {
    - x : int
    - y : int
    + Position(x : int, y : int)
    + getX() : int
    + getY() : int
    + setX(x : int) : void
    + setY(y : int) : void
}
Position "1" <-- Bloc : - pos

class Bloc #yellow {
    - affichageSimple : boolean
    - nom : String
    - image : String
    - implementation : List<String>
    - heritage : String

    + Bloc(n : String, i : String, impl : List<String>, h : String, attr : List<Attribut>, meth : List<Methode>)
    + getPosition() : Position
    + setPosition(p : Position) : void
    + getImage() : String
    + getNom() : String
    + setNom(n : String) : void
    + getHeritage() : String
    + getImplementation() : List<String>
    + getListAttributs() : List<Attribut>
    + getListMethodes() : List<Methode>
    + ajouterAttribut(autorisation : String, nom : String, type : String) : void
    + ajouterMethode(autorisation : String, nom : String, typeRetour : String, parametres : List<String>) : void
}
Bloc "*" <-- Modele : - blocsMap

class ControlButton {
    + ControlButton(m : Modele)
    + handle(event : ActionEvent) : void
    - createNode(dir : File) : TreeItem<String>
    - exportAsImage() : void
    - getFileExtension(f : File) : String
    + ouvrirDialogueModification()
    + handleRemoveAction()
    + handleModifyAction()
    }



class ControlClicDroit {
    + ControlClicDroit(m : Modele)
    + handle(event : MouseEvent) : void
}

class ControlDragNDrop {
    - fileExplorer : TreeView<String>
    - viewport : Pane

    + ControlDragAndDrop(m : Modele)
    + ControlDragNDrop(Modele modele)
    + setUpTreeViewDragAndDrop() : void
    + setUpViewportDragAndDrop() : void
    + handle(event : MouseEvent) : void
    + handle(DragEvent dragEvent) : void
    + handleDragOver(DragEvent dragEvent) : void
    + handleDragDropped(DragEvent dragEvent) : void
    + setUpMouseDrag(StackPane filePane) : void
}
class FabriqueBloc #yellow {
    + creerBloc(f : Fichier) : Bloc
}
FabriqueBloc <|-- FabriqueAbstract
FabriqueBloc <|-- FabriqueClass
FabriqueBloc <|-- FabriqueInterface

class FabriqueAbstract #yellow {
    + creerBloc(f : Fichier) : Bloc
}

class FabriqueClass #yellow {
    + creerBloc(f : Fichier) : Bloc
}

class FabriqueInterface #yellow{
    + creerBloc(f : Fichier) : Bloc
}

abstract class FileComposite {
    # f : File
    + FileComposite(f : File)
    + afficher(s : String) : String
    <abstract> genererPlantUML(s : String) : String 
}

FileComposite <|-- Fichier
FileComposite <|-- Repertoire
FileComposite "*" <-- Repertoire : - fileCompositeArrayList

class Fichier {
    + Fichier(f : File)
    + afficher(prec : String) : String
    + getNomCompletClasse(f : File) : String
    + getClasse() : Class
    + genererPlantUML(s : String) : String  <color>
}
Fichier "*" <-- Modele : - fichiers

class Repertoire {
    + Repertoire(f : File)
    + afficher(prec : String) : String
    + getFileCompositeArrayList() : ArrayList<FileComposite>
    + getFichiers() : ArrayList<Fichier>
    + genererPlantUML(s : String) : String
}
class Fleche {
    - blocDepart : int
    - blocArrivee : int
    - type : String
    + Fleche(blocD : int, blocA : int, t : String)
    + getPosDep() : Position
    + getPosArr() : Position
    + getBlocDepart() : int
    + getBlocArrivee() : int
    + getType() : String
}
Fleche "*" <-- Modele : - flechesMap


interface Sujet {
    + ajouterObs(o : Observateur) : void
    + supprimerObs(o : Observateur) : void
    + notifierObs() : void
}
Sujet  <|.. Modele

class Modele {
    - stage : Stage
    - rep : File
    - blocCourant : int
    - viewport : Pane
    - explorateur : VBox
    - root : VBox
    - derniereID : int
    - derniereFlecheID : int
    - fileExplorerTree : TreeView<String>

    + ajouterAttributDansBloc(nomBloc : String, autorisation : String, nomAttribut : String, typeAttribut : String)
    + modifierNomBloc(newName : String)
    + ajouterMethodeDansBloc(nomBloc : String, autorisation : String, nomMethode : String, typeRetour : String, parametres : List<String>)
    + ajouterAttributDansBloc(nomBloc : String, nomAttribut : String, typeAttribut : String)
}



Modele "1" <-- ControlButton : - modele
Modele "1" <-- ControlClicDroit : - modele
Modele "1" <-- ControlDragNDrop : - modele

interface Observateur {
    + actualiser(s : Sujet) : void
}
Observateur "*" <-- Modele : - observateurs
Observateur <|.. VueBloc
Observateur <|.. VueContext
Observateur <|.. VueFleche
Observateur <|.. VueTextuel

class VueBloc #yellow{
    - id : int
    + VueBloc(i : int)
    + actualiser(s : Sujet) : void
    + getBlocId() : int
}

class VueContext{
    + VueContext()
    + actualiser(s : Sujet) : void
}
VueContext <|-- VueMenuContextBloc
VueContext <|-- VueMenuContextVide

class VueMenuContextBloc{
    + VueMenuContextBloc(m : Modele)
    + actualiser(s : Sujet) : void
}

class VueMenuContextVide #yellow {
    + VueMenuContextBloc(m : Modele)
    + actualiser(s : Sujet) : void
    - getPackageName(path : String ) : String 
}

class VueFleche #yellow {
    - id : int
    + VueFleche(i : int)
    + actualiser(s : Sujet) : void
    + getFlecheId() : int
    - setStartEnd(posA : MoveTo, posB : MoveTo, bPosA : Bloc, bPosB : Bloc, vPosA : VueBloc, vPosB : VueBloc) : void 
}

class VueTextuel {
    + actualiser(s : Sujet) : void
}

@enduml

