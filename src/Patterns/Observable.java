package Patterns;

import java.util.ArrayList;




public class Observable {
    ArrayList<Observateur> viewers;

    public Observable() {
        viewers = new ArrayList<>();
    }

    public void addViewer(Observateur e){
        viewers.add(e);
    }

    public void metAJour(){
        for (Observateur e: viewers){
            e.miseAJour();
        }
    }
}
