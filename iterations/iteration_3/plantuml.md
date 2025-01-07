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

class Bloc{
    - affichageSimple : boolean
    - nom : String
    - image : String
    - implementation : List<String>
    - heritage : String
    + Bloc(n : String, i : String, impl : List<String>, h : String, attr : List<Attribut>, meth : List<Methode>)
    + getPosition() : Position
    + setPosition (p : Position) : void
    + getImage() : String
    + getNom() : String
    + setNom(n : String) : void
    + getHeritage() : String
    + getImplementation() : List<String>
    + getListAttributs() : List<Attribut>
    + getListMethodes() : List<Methode>
}
Bloc "*" <-- Modele : - blocsMap

class ControlButton {
    + ControlButton(m : Modele)
    + handle(event : ActionEvent) : void
    - createNode(dir : File) : TreeItem<String>
    - exportAsImage(): void
    - getFileExtension(f : File) : String
}

class ControlClicDroit {
    + ControlClicDroit(m : Modele)
    + handle(event : MouseEvent) : void
}

class ControlDragAndDrop{
    - fileExplorer : TreeView<String>
    - viewport : Pane
    + ControlDragAndDrop(m : Modele)
    + handle(event : MouseEvent) : void
}

class FabriqueBloc {
    + creerBloc(f : Fichier) : Bloc
}
FabriqueBloc <|-- FabriqueAbstract
FabriqueBloc <|-- FabriqueClass
FabriqueBloc <|-- FabriqueInterface

class FabriqueAbstract {
    + creerBloc(f : Fichier) : Bloc
}

class FabriqueClass {
    + creerBloc(f : Fichier) : Bloc
}

class FabriqueInterface {
    + creerBloc(f : Fichier) : Bloc
}

abstract class FileComposite {
    # f : File
    + FileComposite(f : File)
    + afficher(s : String) : String
}

FileComposite <|-- Fichier
FileComposite <|-- Repertoire
FileComposite "*" <-- Repertoire : - fileCompositeArrayList

class Fichier {
    + Fichier(f : File)
    + afficher(prec : String) : String
    + getNomCompletClasse(f : File) : String
    + getClasse() : Class
}
Fichier "*" <-- Modele : - fichiers

class Repertoire {
    + Repertoire(f : File)
    + afficher(prec : String) : String
    + getFileCompositeArrayList() : ArrayList<FileComposite>
    + getFichiers() : ArrayList<Fichier>
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
Sujet  <|.. Bloc

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
}

Modele "1" <-- ControlButton : - modele
Modele "1" <-- ControlClicDroit : - modele
Modele "1" <-- ControlDragAndDrop : - modele

interface Observateur {
    + actualiser(s : Sujet) : void
}
Observateur "*" <-- Modele : - observateurs
Observateur <|.. VueBloc
Observateur <|.. VueContext
Observateur <|.. VueFleche
Observateur <|.. VueTextuel

class VueBloc{
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

class VueMenuContextVide{
    + VueMenuContextBloc(m : Modele)
    + actualiser(s : Sujet) : void
}

class VueFleche {
    - id : int
    + VueFleche(i : int)
    + actualiser(s : Sujet) : void
    + getFlecheId() : int
}

class VueTextuel {
    + actualiser(s : Sujet) : void
}

@enduml
