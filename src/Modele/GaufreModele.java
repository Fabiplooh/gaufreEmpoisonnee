package Modele;

import Patterns.Observable;

import java.util.Stack;

public class GaufreModele extends Observable {
    private static final int DEFAULT_LINE_POISON = 0;
    private static final int DEFAULT_COLUMN_POISON = 0;
    public int [][] gaufre; //0 vide; 1 remplis; 2 empoisonnée
    private int nbLigne;
    private int nbColonne;
    private boolean fin = false;
    private Historique my_history;
    private final int POISON = 2;
    private final int REMPLIE = 0;
    private final int VIDE = 1;
    private final int INVALID = -1;

    /*
    Par défaut notre gaufre est remplis et fait une taille de 4 ligne et 6 colonne
     */

    public GaufreModele(){
        nbLigne = 4;
        nbColonne = 6;
        gaufre = new int[nbLigne][nbColonne];
        my_history = new Historique();
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
    }

    public GaufreModele(int x, int y){
        nbLigne = x;
        nbColonne = y;
        my_history = new Historique();
        gaufre = new int[nbLigne][nbColonne];
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
    }

    public boolean canPlay(int line, int column){
        return isValidCell(line, column) && gaufre[line][column] == REMPLIE || gaufre[line][column] == POISON;
    }

    public boolean estPoison(int line, int column){
        return gaufre[line][column] == POISON;
    }

    public boolean estVide(int line, int column){
        return gaufre[line][column] == VIDE;
    }

    public boolean estRemplie(int line, int column){
        return gaufre[line][column] == REMPLIE;
    }

    public void play(int line, int column){
        if ( ! canPlay(line, column)) {
            return;
        }
        boolean game_has_change = false;
        for (int l=line; l<nbLigne; l++){
            boolean line_end = false;    
            for (int c=column; c<nbColonne; c++){
                switch (getCase(l,c)){
                    case REMPLIE:
                        my_history.add(l,c);
                        gaufre[l][c] = VIDE;
                        game_has_change = true;
                        break;

                    case VIDE:
                        line_end = true;
                        break;

                    case POISON:
                        game_has_change = true;
                        fin = true;
                        return;

                    default: 
                        System.exit(1);
                }
                if (line_end){
                    break;
                }
            }
        }
        my_history.insert();
        if (game_has_change){
            metAJour();
        }
    }

    private boolean isValidCell(int line, int column){
        return line >= 0 && line < nbLigne && column >= 0 && column < nbColonne;
    }

    public int getCase(int line, int column){
        if (isValidCell(line, column)) {
            return gaufre[line][column];
        }
        return INVALID;
    }

    private boolean canUndo(){
        return my_history.canUndo();
    }

    public void undo(){
        if (! canUndo()) {
            return;
        }
        for (Coup to_remove : my_history.get()){
            gaufre[to_remove.line][to_remove.column] = REMPLIE;

        };
    }

    public boolean isFin(){
        return fin;
    }

    public void reset(){
        gaufre = new int[nbLigne][nbColonne];
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
        my_history = new Historique();
    }

    public int getLine(){
        return nbLigne;
    }
    public int getColonne(){
        return nbColonne;
    }

    public void Affiche() {
        for (int i = 0; i < nbLigne; i++) {
            for (int j = 0; j < nbColonne; j++) {
                System.out.print(gaufre[i][j] + " ");
            }
            System.out.println();
        }
    }

}
