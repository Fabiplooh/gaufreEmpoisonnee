package Modele;

import Global.OurConfiguration;
import Patterns.Observable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Scanner;

public class GaufreModele extends Observable {
    private static final int DEFAULT_LINE_POISON = 0;
    private static final int DEFAULT_COLUMN_POISON = 0;
    public int[][] gaufre; //0 vide; 1 remplis; 2 empoisonnée
    private int nbLigne;
    private int nbColonne;
    int curColumn;
    int curLine;
    private boolean fin = false;
    private Historique my_history;
    private final int POISON = 2;
    private final int REMPLIE = 0;
    private final int VIDE = 1;
    private final int INVALID = -1;
    private int current_player = 0;
    /*
    Par défaut notre gaufre est remplis et fait une taille de 4 ligne et 6 colonne
     */


    public GaufreModele() throws Exception {
        if (OurConfiguration.instance().getProperty("new_game").compareTo("true") == 0){
            int lines =  Integer.parseInt(OurConfiguration.instance().getProperty("line"));
            int columns = Integer.parseInt(OurConfiguration.instance().getProperty("column"));
            initGaufreWithDim(lines, columns);
        } else {
            initGaufreWithFile(OurConfiguration.instance().getProperty("input_file"));
        }
        curColumn = getColonne() + 1;
        curLine = getLine() + 1;
    }

    public void initGaufreWithFile(String fichier) throws Exception {
        FileInputStream my_file;
        Scanner my_scanner;
        my_history = new Historique();
        try{
            my_file = new FileInputStream(fichier);
            my_scanner = new Scanner(my_file);
        } catch( Exception e){
            throw e;
        }
        my_scanner.useDelimiter("[,\\s\\n]+");
        try{
            get_dimension(my_scanner);
            get_map(my_scanner);
            my_history.get_historique(my_scanner);
        } catch (Exception e){
            throw e;
        }
        my_scanner.close();
    }

    private void initGaufreWithDim(int x, int y) {
        nbLigne = x;
        nbColonne = y;
        my_history = new Historique();
        gaufre = new int[nbLigne][nbColonne];
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
    }

    public boolean canPlay(int line, int column) {
        return isValidCell(line, column) && gaufre[line][column] == REMPLIE || gaufre[line][column] == POISON;
    }

    public boolean estPoison(int line, int column) {
        return gaufre[line][column] == POISON;
    }

    public boolean estVide(int line, int column) {
        return gaufre[line][column] == VIDE;
    }

    public boolean estRemplie(int line, int column) {
        return gaufre[line][column] == REMPLIE;
    }

    public void setPosition(int line, int column){
        curLine = line;
        curColumn = column;
        metAJour();
    }

    public void play(int line, int column) {
        if (!canPlay(line, column)) {
            return;
        }
        if (isFin()){
            return;
        }
        boolean game_has_change = false;
        for (int l = line; l < nbLigne; l++) {
            boolean line_end = false;
            for (int c = column; c < nbColonne; c++) {
                switch (getCase(l, c)) {
                    case REMPLIE:
                        my_history.add(l, c);
                        gaufre[l][c] = VIDE;
                        game_has_change = true;
                        break;

                    case VIDE:
                        line_end = true;
                        break;

                    case POISON:
                        my_history.add(l, c);
                        fin = true;
                        game_has_change=true;
                        break;

                    default:
                        System.exit(1);
                }
                if (line_end) {
                    break;
                }
            }
        }
        if (game_has_change) {
            my_history.insert();
            changePlayer();
            metAJour();
        }
    }

    private void changePlayer(){
        current_player = 1 - current_player;
    }

    private boolean isValidCell(int line, int column) {
        return line >= 0 && line < nbLigne && column >= 0 && column < nbColonne;
    }

    public int getCase(int line, int column) {
        if (isValidCell(line, column)) {
            return gaufre[line][column];
        }
        return INVALID;
    }

    public boolean canUndo() {
        return my_history.canUndo();
    }

    public boolean canRedo() {
        return my_history.canRedo();
    }

    public void undo() {
        if (!canUndo()) {
            return;
        }
        fin = false;
        for (Cell to_remove : my_history.getPrev()) {
            gaufre[to_remove.line][to_remove.column] = REMPLIE;
            if (to_remove.line == DEFAULT_LINE_POISON && to_remove.column == DEFAULT_COLUMN_POISON){
                gaufre[to_remove.line][to_remove.column] = POISON;
            }
        }
        changePlayer();
        metAJour();
    }

    public void redo() {
        if (!canRedo()) {
            return;
        }
        for (Cell to_redo : my_history.getNext()) {
            gaufre[to_redo.line][to_redo.column] = VIDE;
            if (to_redo.line == DEFAULT_LINE_POISON && to_redo.column == DEFAULT_COLUMN_POISON){
                fin=true;
            }
        }
        changePlayer();
        metAJour();
    }

    public boolean isFin() {
        return fin;
    }

    public void reset() {
        gaufre = new int[nbLigne][nbColonne];
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
        my_history = new Historique();
        fin = false;
        current_player = 0;
        metAJour();
    }

    public int getLine() {
        return nbLigne;
    }

    public int getColonne() {
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

    public String getCurrentPlayer() {
        if (current_player == 0) {
            return " Joueur 1";
        }
        return "Joueur 2";
    }

    public void save() throws Exception {
        save(OurConfiguration.instance().getProperty("output_file"));
    }

    public void save(String fichier) throws Exception {
        FileOutputStream my_file;
        PrintStream my_writter;
        try {
            my_file = new FileOutputStream(fichier);
            my_writter = new PrintStream(my_file);
        } catch (Exception e) {
            throw e;
        }
        my_writter.println(this.nbLigne + "," + this.nbColonne);
        for (int line = 0; line < this.nbLigne; line++) {
            for (int col = 0; col < this.nbColonne; col++) {
                my_writter.print(getCase(line, col) + " ");
            }
            my_writter.println();
        }
        my_writter.println("-3");
        my_history.save_historique(my_writter, true);
        my_writter.println("-4");
        my_history.save_historique(my_writter, false);
        my_writter.close();
    }

    void get_dimension(Scanner my_scanner) throws Exception{
        for (int i = 0; i < 2; i++){
            if (my_scanner.hasNextInt() ) {
                if (i == 0){
                    this.nbLigne = my_scanner.nextInt();
                } else{
                    this.nbColonne = my_scanner.nextInt();
                }
            } else{
                if (i == 0){
                    System.err.println("Il faut le numéro de ligne");
                } else{
                    System.err.println("Il faut le numéro de colonnes");
                }
                my_scanner.close();
                throw new Exception();
            }
        }
        this.gaufre= new int[this.nbLigne][this.nbColonne];
    }

    void get_map(Scanner my_scanner) throws Exception{
        for (int line = 0; line < this.nbLigne; line++){
            for (int col= 0; col < this.nbColonne; col++){
                if (my_scanner.hasNextInt() ) {
                    this.gaufre[line][col] = my_scanner.nextInt();
                } else{
                    System.err.println("Il manque des cases");
                    my_scanner.close();
                    throw new Exception();
                }
            }
        }
    }

    public boolean canBeEat(int lineCellVisited, int columnCellVisited){
        if ( ! isValidCell(lineCellVisited, columnCellVisited)){
           return false;
        }
        int valueCell = getCase(lineCellVisited, columnCellVisited);
        return lineCellVisited >= curLine && columnCellVisited >= curColumn &&
                ( valueCell == REMPLIE || valueCell == POISON ) ;
    }

}