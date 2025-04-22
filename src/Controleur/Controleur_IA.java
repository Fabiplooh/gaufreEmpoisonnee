package Controleur;

import IA.*;
import Modele.Jeu;
import Patterns.Observateur;

public class Controleur_IA implements Observateur {

    int whoIAm;
    Jeu modele;
    IA IA;


    public Controleur_IA(Jeu modele, int whoIAm, int difficulty){
        this.whoIAm=whoIAm;
        this.modele=modele;
        assert(difficulty>=1 && difficulty<=4);
        switch (difficulty){
            case 0:
                IA = new IAFacile(modele.getLine(), modele.getColonne());
                break;
            case 1:
                IA = new IAMoyen(modele.getLine(), modele.getColonne());
                break;

            case 2:
                IA = new IADifficile(modele.getLine(), modele.getColonne());
                break;
            case 3:
                IA = new IARandom(modele.getLine(), modele.getColonne());
                break;
            default:
                System.exit(1);
                break;
        }
    }


    @Override
    public void miseAJour() {
        Coup coup;
        if (modele.getCurrentPlayerInt() == whoIAm) {
            try {
                coup = new Coup(modele.lastMove().line, modele.lastMove().column);
                IA.coupAdversaire(coup);
            } catch (Exception e) {}
            coup = IA.coupAJouer();
            modele.play(coup.getLigne(), coup.getColonne());
        }
    }
}
