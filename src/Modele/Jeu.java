package Modele;

import Global.OurConfiguration;
import Patterns.Observable;

import java.lang.module.Configuration;

public class Jeu extends Observable {
    GaufreModele modele;
    public int typePlayers;
    final private int HUMAN = 0;
    final public int IA = 1;


    public Jeu() throws Exception {
        modele = new GaufreModele();
    }

    public boolean canPlay(int line, int column) {
        return modele.canPlay(line, column);
    }

    public boolean estPoison(int line, int column) {
        return modele.estPoison(line, column);
    }

    public boolean estVide(int line, int column) {
        return modele.estVide(line, column);
    }

    public boolean estRemplie(int line, int column) {
        return modele.estRemplie(line, column);
    }

    public void setPosition(int line, int column){
        modele.setPosition(line, column);
        metAJour();
    }

    public void play(int line, int column) {
        if ( modele.play(line, column)) {
            if (OurConfiguration.instance().getProperty("mode_vue").equals("terminal")) {
                modele.Affiche();
            }
            metAJour();
        }
    }

    public boolean canUndo() {
        return modele.canUndo();
    }

    public boolean canRedo() {
        return modele.canRedo();
    }

    public void undo() {
        if (! canUndo()) {
            return;
        }
        if (typePlayers == IA ) {
            modele.undo(2);
        } else {
            modele.undo(1);
        }
        metAJour();
    }

    public void redo() {
        if (! canRedo()) {
            return;
        }
        if (typePlayers == IA ) {
            modele.redo(2);
        } else {
            modele.redo(1);
        }
        metAJour();
    }

    public boolean isFin() {
        return modele.isFin();
    }

    public void reset() {
        reset(0);
        metAJour();
    }

    public void reset(int typePlayers) {
        clearPlayers();
        this.typePlayers = typePlayers;
        modele.reset();
        metAJour();
    }

    public boolean isSelectedCase(int ligne, int colonne){
        return modele.isSelectedCase(ligne, colonne);
    }

    public int getLine() {
        return modele.getLine();
    }

    public int getColonne() {
        return modele.getColonne();
    }

    public void Affiche() {
        modele.Affiche();
    }

    public Cell lastMove(){
        return modele.lastMove();
    }

    public int getCurrentPlayerInt() {
        return modele.getCurrentPlayerInt();
    }

    public String getCurrentPlayer() {
        return modele.getCurrentPlayer();
    }

    public void save() throws Exception {
        modele.save();
    }

    public void save(String fichier) throws Exception {
        modele.save(fichier);
    }

    public boolean canBeEat(int lineCellVisited, int columnCellVisited){
        return modele.canBeEat(lineCellVisited, columnCellVisited);
    }

    public void initGaufreWithFile(String absolutePath) throws Exception {
        modele.initGaufreWithFile(absolutePath);
        metAJour();
    }
}
