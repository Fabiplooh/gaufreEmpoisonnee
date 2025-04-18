package Modele;
import java.util.ArrayList;
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

}
