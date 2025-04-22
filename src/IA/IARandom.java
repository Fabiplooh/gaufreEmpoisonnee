package IA;

import java.util.ArrayList;
import java.util.Random;

public class IARandom implements IA {
    Random rand;
    int hauteur,largeur;
    ArrayList<Coup> coups;

    public IARandom(int hauteur, int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        coups =  new ArrayList<>();
        rand = new Random();
    }

    ArrayList<Coup> coupsJouables(ArrayList<Coup> coups){
        ArrayList<Coup> cj = new ArrayList<>();
        boolean jouable;
        for(int i=0; i < hauteur; i++){
            for(int j=0; j < largeur; j++){
                jouable = true;
                if(coups != null){
                    for(Coup c : coups){
                        if(i >= c.getLigne() && j >= c.getColonne()){
                            jouable = false;
                        }
                    }
                }
                if(jouable){
                    cj.add(new Coup(i, j));
                }
            }
        }
        return cj;
    }

    @Override
    public void coupAdversaire(Coup c) {
        coups.add(c);
    }

    @Override
    public Coup coupAJouer() {
        ArrayList<Coup> cj = coupsJouables(coups);
        int r = rand.nextInt(cj.size());
        coups.add(cj.get(r));
        return cj.get(r);
    }

    public void annulerDernierCoup(){
        coups.remove(coups.size()-1);
    }
}