package Vue;

import javax.swing.JLabel;

import Modele.Jeu;
import Patterns.*;

public class Affiche_joueur extends JLabel implements Observateur {
    Jeu modele;

    public Affiche_joueur( Jeu m){
        modele = m;
        miseAJour();
    }

    public void miseAJour(){
        setText(modele.getCurrentPlayer());
    }
    

}
