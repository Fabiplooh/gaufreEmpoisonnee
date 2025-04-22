package IA;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

enum TypeNoeud {
    ET,
    OU
}


class Noeud{
    TypeNoeud type;
    ArrayList<Coup> coups;
    Set<Coup> bornes;

    public Noeud(TypeNoeud type, ArrayList<Coup> coups){
        this.type = type;
        this.coups = new ArrayList<>(coups);
        bornes = calcul_bornes();
    }

    public ArrayList<Coup> getCoups(){
        return coups;
    }

    public TypeNoeud getType(){
        return type;
    }

    @Override
    public int hashCode() {
        return bornes.hashCode();
    }

    private Set<Coup> calcul_bornes(){
        Set<Coup> b = new HashSet<>();
        boolean est_borne;
        for(Coup c : coups){
            est_borne = true;
            for(Coup ci : coups){
                if(!c.equals(ci) && ci.getLigne() <= c.getLigne() && ci.getColonne() <= c.getColonne()){
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

        return bornes.equals(n.bornes);
    }

}

public abstract class ArbreEtOu implements IA {
    private HashMap<Noeud, Boolean> mem; // memoisation
    private ArrayList<Coup> configAct;
    private int hauteur, largeur;

    protected abstract Coup choisir_coup(ArrayList<Coup> coupsGagnants,ArrayList<Coup> coupsPerdants);

    public ArbreEtOu(int hauteur, int largeur){
        this.hauteur = hauteur;
        this.largeur = largeur;
        mem = new HashMap<>();
        configAct = new ArrayList<>();

    }

    public void coupAdversaire(Coup c){
        configAct.add(c);
    }

    public Coup coupAJouer(){
        ArrayList<Coup> coupsPossibles = coupsJouables(configAct);
        ArrayList<Coup> coupsPerdants = new ArrayList<>();
        ArrayList<Coup> coupsGagnants = new ArrayList<>();

        for(Coup c : coupsPossibles){
            if(calculJoueurB(joue(c, configAct))){
                coupsGagnants.add(c);
            } else {
                coupsPerdants.add(c);
            }
        }

        Coup coup = choisir_coup(coupsGagnants, coupsPerdants);

        configAct.add(coup);
        return coup;
    }

    public void annulerDernierCoup(){
        configAct.remove(configAct.size()-1);
    }


    private ArrayList<Coup> joue(Coup c, ArrayList<Coup> config){
        ArrayList<Coup> res;
        if(config != null){
            res = new ArrayList<>(config);
        }else{
            res = new ArrayList<>();
        }

        res.add(c);
        return res;
    }

    // revoir, c'est peut-être un peu moche
    boolean estGaufreVide(ArrayList<Coup> config){
        return config.contains(new Coup(0, 0));
    }


    private ArrayList<Coup> coupsJouables(ArrayList<Coup> coups){
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


    // Résultat: vrai si la configuration est gagnante, faux sinon (elle est perdante)
    private boolean calculJoueurA(ArrayList<Coup> config){
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
    private boolean calculJoueurB(ArrayList<Coup> config){
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
                if(!calculJoueurA(joue(c,config))){
                    mem.put(noeudConfig, false);
                    return false;
                }
            }
        mem.put(noeudConfig, true);
        return true;
    }
}