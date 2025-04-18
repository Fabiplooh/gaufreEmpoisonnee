package Modele;

import Patterns.Observable;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;

public class GaufreModele extends Observable {
    private static final int DEFAULT_LINE_POISON = 0;
    private static final int DEFAULT_COLUMN_POISON = 0;
    public int[][] gaufre; //0 vide; 1 remplis; 2 empoisonnée
    private int nbLigne;
    private int nbColonne;
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


    public GaufreModele() {
        this(4,6);
    }

    public GaufreModele(int x, int y) {
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

    public void play(int line, int column) {
        if (!canPlay(line, column)) {
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
                        current_player = 1 - current_player;
                        metAJour();
                        fin = true;
                        metAJour();
                        return;

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
            current_player = 1 - current_player;
            metAJour();
        }
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
        for (Cell to_remove : my_history.getPrev()) {
            gaufre[to_remove.line][to_remove.column] = REMPLIE;
        }
        metAJour();
    }


    public void redo() {
        if (!canRedo()) {
            return;
        }
        for (Cell to_redo : my_history.getNext()) {
            gaufre[to_redo.line][to_redo.column] = VIDE;
        }
        metAJour();
    }

    public boolean isFin() {
        return fin;
    }

    public void reset() {
        gaufre = new int[nbLigne][nbColonne];
        gaufre[DEFAULT_LINE_POISON][DEFAULT_COLUMN_POISON] = POISON;
        my_history = new Historique();
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


    private void save_historique(PrintStream my_writter, Stack<ArrayList<Cell>> moment) {
        if (moment.isEmpty()) {
            return;
        }
        Collections.reverse(moment);
        int current = 0;
        while (true){
            try {
                ArrayList<Cell> cur_list = moment.get(current);
                for (Cell cur_coup : cur_list) {
                    my_writter.print( "," + cur_coup.line + "," + cur_coup.column);
                }
                my_writter.println("-7");
                current++;
            } catch (Exception e) {
                Collections.reverse(moment);
                return;
            }
        }
    }

    public void save() throws Exception {
        save("plouby.txt");
    }

    public void save(String fichier) throws Exception {
        FileOutputStream my_file = null;
        PrintStream my_writter = null;
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
        save_historique(my_writter, my_history.previous);
        my_writter.println("-4");
        save_historique(my_writter, my_history.next);
        my_writter.close();
    }



    int read_historique(Scanner my_scanner, Stack<ArrayList<Cell>> moment, boolean passe) throws Exception{
        int current_line = 0;
        int current_col;
        while (my_scanner.hasNextInt() ){
            current_line = my_scanner.nextInt();
            if (current_line == -4 && passe ){
                break;
            }
            if (! my_scanner.hasNextInt() ){
                System.err.println("Il manque la colonne");
                my_scanner.close();
                throw new Exception();
            }
            current_col= my_scanner.nextInt();
            if (current_col == -4 && passe ){
                System.err.println("Il manque la colonne");
                my_scanner.close();
                throw new Exception();
            }
            ArrayList<Cell> temporary = new ArrayList<>();
            temporary.add(new Cell(current_line, current_col));
            while (true){
                current_line = my_scanner.nextInt();
                if (current_line == -7){
                    break;
                }
                current_col= my_scanner.nextInt();
                temporary.add(new Cell(current_line, current_col));
            }
            moment.add(temporary);
        }
        return current_line;
    }

    void get_historique(Scanner my_scanner) throws Exception{
        if ( ! my_scanner.hasNextInt() ||  my_scanner.nextInt() != -3 ) {
            System.err.println("Il manque le passé");
            my_scanner.close();
            throw new Exception();
        }
        int current_is_explore = -8;
        try{
            current_is_explore = read_historique(my_scanner, my_history.previous, true);
        } catch (Exception e){
            throw e;
        }
        if ( current_is_explore != -4){
            System.err.println("Il manque le futur");
            my_scanner.close();
            throw new Exception();
        }
        try{
            read_historique(my_scanner, my_history.next, false);
        } catch (Exception e){
            throw e;
        }

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

    public GaufreModele(String fichier) throws Exception {
        FileInputStream my_file;
        Scanner my_scanner;
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
            get_historique(my_scanner);
        } catch (Exception e){
            throw e;
        }
        my_scanner.close();
    }

}