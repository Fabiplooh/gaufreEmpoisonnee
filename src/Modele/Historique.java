package Modele;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Stack;
public class Historique {
    Stack<ArrayList<Cell>> previous;
    ArrayList<Cell> current;
    Stack<ArrayList<Cell>> next;

    Historique(){
        previous = new Stack<>();
        next = new Stack<>();
        current = null;
    }

    public void add(int line, int column){
        if ( current == null ){
            current = new ArrayList<>();
        }
        current.add(new Cell(line, column));
    }

    public void insert(){
        if (current==null){
            return;
        }
        previous.add(current);
        next = new Stack<>();
        current = null;
    }

    public ArrayList<Cell> getPrev(){
        ArrayList<Cell> currentCoup = previous.pop();
        next.add(currentCoup);
        return currentCoup;
    }

    public ArrayList<Cell> getNext(){
        ArrayList<Cell> currentCoup = next.pop();
        previous.add(currentCoup);
        return currentCoup;
    }

    public boolean canUndo(){
        return  ! previous.isEmpty();
    }

    public boolean canRedo(){
        return  ! next.isEmpty();
    }

    public void save_historique(PrintStream my_writter, boolean is_past) {
        Stack<ArrayList<Cell>> moment;
        if (is_past){
            moment = previous;
        } else{
            moment = next;
        }
        if (moment.isEmpty()) {
            return;
        }
        int current = 0;
        while (true){
            try {
                ArrayList<Cell> cur_list = moment.get(current);
                for (Cell cur_coup : cur_list) {
                    my_writter.print( "," + cur_coup.line + "," + cur_coup.column);
                }
                my_writter.println(",-7");
                current++;
            } catch (Exception e) {
                return;
            }
        }
    }

    private int read_historique(Scanner my_scanner, Stack<ArrayList<Cell>> moment, boolean passe) throws Exception{
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

    public void get_historique(Scanner my_scanner) throws Exception{
        if ( ! my_scanner.hasNextInt() ||  my_scanner.nextInt() != -3 ) {
            System.err.println("Il manque le passé");
            my_scanner.close();
            throw new Exception();
        }
        int current_is_explore = -8;
        try{
            current_is_explore = read_historique(my_scanner, previous, true);
        } catch (Exception e){
            throw e;
        }
        if ( current_is_explore != -4){
            System.err.println("Il manque le futur");
            my_scanner.close();
            throw new Exception();
        }
        try{
            read_historique(my_scanner, next, false);
        } catch (Exception e){
            throw e;
        }

    }

}
