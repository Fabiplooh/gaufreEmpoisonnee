package IA;
import java.lang.ProcessBuilder.Redirect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import Modele.GaufreModele;

enum TypeNoeud {
    ET,
    OU
}

class Coup {
    int i;
    int j;

    public Coup(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getLigne(){
        return i;
    }

    public int getColonne(){
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        Coup c = (Coup) obj;
        return this.getLigne() == c.getLigne() && this.getColonne() == c.getColonne();
    }

    @Override
    public int hashCode() {
        return String.format("%d,%d", i, j).hashCode();
    }
}

class Noeud{
    TypeNoeud type;
    ArrayList<Coup> coups;

    public Noeud(TypeNoeud type, ArrayList<Coup> coups){
        this.type = type;
        this.coups = new ArrayList<>(coups);
    }

    public ArrayList<Coup> getCoups(){
        return coups;
    }

    public TypeNoeud getType(){
        return type;
    }

    @Override
    public int hashCode() {
        int h = 0;
        for(Coup c : coups){
            h += c.hashCode();
        }
        return h;
    }

    private Set<Coup> bornes(){
        Set<Coup> b = new HashSet<>();
        boolean est_borne;
        for(Coup c : coups){
            est_borne = true;
            for(Coup ci : coups){
                if(ci.getLigne() < c.getLigne() || ci.getColonne() < c.getColonne()){
                    est_borne = false;
                }
            }
            if(est_borne){
                b.add(c);
            }
        }

        return b;
    }

    @Override
    public boolean equals(Object obj) {
        Noeud n = (Noeud) obj;
        if(type != n.type){
            return false;
        }
        Set<Coup> b = bornes();
        Set<Coup> bn = n.bornes();

        return b.equals(bn);
    }

}

class ArbreEtOu implements IA {
    HashMap<Noeud, Boolean> mem; // memoisation
    ArrayList<Coup> etatAct;

    Noeud racine;
    GaufreModele modele;

    @Override
    public void coupAdversaire(int i, int j){
        2.0....;
    }

    public Coup coupquejejoue

    public ArbreEtOu(GaufreModele modele){
        this.modele = modele;
    }


    ArrayList<Coup> joue(Coup c, ArrayList<Coup> config){
        ArrayList<Coup> res = new ArrayList<>();
        res = (ArrayList<Coup>) config.clone();
        res.add(c);
        return res;
    }

    // revoir, c'est peut-être un peu moche
    boolean estGaufreVide(ArrayList<Coup> config){
        return config.contains(new Coup(0, 0));
    }


    ArrayList<Coup> coupsJouables(ArrayList<Coup> coups){
        ArrayList<Coup> cj = new ArrayList<>();
        boolean jouable;
        for(int i=0; i < modele.GetLine(); i++){
            for(int j=0; j < modele.GetColonne(); j++){
                jouable = true;
                for(Coup c : coups){
                    if(i <= c.getLigne() && j >= c.getColonne()){
                        jouable = false;
                    }
                }
                if(jouable){
                    cj.add(new Coup(i, j));
                }
            }
        }
        return cj;
    }


    // Résultat: vrai si la configuration est gagnante, faux sinon (elle est perdante)
    boolean calculJoueurA(ArrayList<Coup> config){
        //Données: Configuration de jeu, c’est au joueur A de jouer
        if(estGaufreVide(config)){
            return true;
        }

        Noeud noeudConfig = new Noeud(TypeNoeud.OU, config);
        if(mem.containsKey(noeudConfig)){
            return mem.get(noeudConfig);
        }

        // Sinon Le joueur A doit jouer
        ArrayList<Coup> coups = coupsJouables(config); // Ensemble des coups jouables par A
            for(Coup c : coups){
                if(calculJoueurB(joue(c,config))){
                    mem.put(noeudConfig, true);
                    return true;
                }
            }
        mem.put(noeudConfig, false);
        return false;
    }


    // Résultat: vrai si la configuration est gagnante pour A, faux sinon (elle est perdante)
    boolean calculJoueurB(ArrayList<Coup> config){
        //Données: Configuration de jeu, c’est au joueur B de jouer
        if (estGaufreVide(config)){
            return false;
        }

        Noeud noeudConfig = new Noeud(TypeNoeud.ET, config);
        if(mem.containsKey(noeudConfig)){
            return mem.get(noeudConfig);
        }

        // Sinon Le joueur A doit jouer
        ArrayList<Coup> coups = coupsJouables(config); // Ensemble des coups jouables par A
            for(Coup c : coups){
                if(calculJoueurA(joue(c,config))){
                    mem.put(noeudConfig, false);
                    return false;
                }
            }
        mem.put(noeudConfig, true);
        return true;
    }
}