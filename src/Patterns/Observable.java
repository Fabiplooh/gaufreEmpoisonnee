package Patterns;

import java.util.ArrayList;




public class Observable {
    ArrayList<Observateur> viewers;
    ArrayList<Observateur> players;


    public void clearPlayers(){
        players = new ArrayList<>();
    }
    public Observable() {
        viewers = new ArrayList<>();
        players = new ArrayList<>();
    }

    public void addViewer(Observateur e){
        viewers.add(e);
    }

    public void addPlayer(Observateur e){
        players.add(e);
    }

    public void metAJour(){
        for (Observateur e: viewers){
            e.miseAJour();
        }
        for (Observateur e: players){
            e.miseAJour();
        }
    }
}
