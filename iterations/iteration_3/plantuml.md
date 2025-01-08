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

class Bloc {
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
    + <color:green>ajouterAttribut(autorisation : String, nom : String, type : String) : void
    + <color:green>ajouterMethode(autorisation : String, nom : String, typeRetour : String, parametres : List<String>) : void
}
Bloc "*" <-- Modele : - blocsMap

class ControlButton {
    + ControlButton(m : Modele)
    + <color:orange>handle(event : ActionEvent) : void</color>
    - createNode(dir : File) : TreeItem<String>
    - exportAsImage() : void
    - getFileExtension(f : File) : String
    + <color:green>ouvrirDialogueModification()</color>
    + <color:green>handleRemoveAction()</color>
    + <color:green>handleModifyAction()</color>
}



class ControlClicDroit {
    + ControlClicDroit(m : Modele)
    + handle(event : MouseEvent) : void
}

class ControlDragNDrop #green {
    - fileExplorer : TreeView<String>
    - viewport : Pane

    + ControlDragAndDrop(m : Modele)
    + <color:yellow>ControlDragNDrop(Modele modele)</color>
    + <color:yellow>setUpTreeViewDragAndDrop() : void</color>
    + <color:yellow>setUpViewportDragAndDrop() : void</color>
    + <color:yellow>handle(event : MouseEvent) : void</color>
    + <color:yellow>handle(DragEvent dragEvent) : void</color>
    + <color:yellow>handleDragOver(DragEvent dragEvent) : void</color>
    + <color:yellow>handleDragDropped(DragEvent dragEvent) : void</color>
    + <color:yellow>setUpMouseDrag(StackPane filePane) : void</color>
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
    <abstract> <color: green> genererPlantUML(s : String) : String 
}

FileComposite <|-- Fichier
FileComposite <|-- Repertoire
FileComposite "*" <-- Repertoire : - fileCompositeArrayList

class Fichier {
    + Fichier(f : File)
    + afficher(prec : String) : String
    + getNomCompletClasse(f : File) : String
    + getClasse() : Class
    + <color:green>genererPlantUML(s : String) : String  <color>
}
Fichier "*" <-- Modele : - fichiers

class Repertoire {
    + Repertoire(f : File)
    + afficher(prec : String) : String
    + getFileCompositeArrayList() : ArrayList<FileComposite>
    + getFichiers() : ArrayList<Fichier>
    + <color : green>genererPlantUML(s : String) : String
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

    + <color:green>ajouterAttributDansBloc(nomBloc : String, autorisation : String, nomAttribut : String, typeAttribut : String)</color>
    + <color:green>modifierNomBloc(newName : String)</color>
    + <color:green>ajouterMethodeDansBloc(nomBloc : String, autorisation : String, nomMethode : String, typeRetour : String, parametres : List<String>)</color>
    + <color:green>ajouterAttributDansBloc(nomBloc : String, nomAttribut : String, typeAttribut : String)</color>
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

class VueFleche #green {
    - id : int
    + VueFleche(i : int)
    + actualiser(s : Sujet) : void
    + getFlecheId() : int
}

class VueTextuel {
    + actualiser(s : Sujet) : void
}

@enduml
