package Modele;
import java.util.ArrayList;
import java.util.Stack;
public class Historique {
    Stack<ArrayList<Coup>> previous;
    ArrayList<Coup> current;
    Historique(){
        previous = new Stack<>();
        current = null;
    }

    public void add(int line, int column){
        if ( current == null ){
            current = new ArrayList<>();
        }
        current.add(new Coup(line, column));
    }

    public void insert(){
        if (current==null){
            return;
        }
        previous.add(current);
        current = null;
    }

    public ArrayList<Coup> get(){
        return previous.pop();
    }

    public boolean canUndo(){
        return  ! previous.isEmpty();
    }


}
