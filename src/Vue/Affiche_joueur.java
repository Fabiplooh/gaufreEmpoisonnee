package Vue;

import javax.swing.JLabel;

import Modele.GaufreModele;
import Patterns.*;

public class Affiche_joueur extends JLabel implements Observateur {
    GaufreModele modele;

    public Affiche_joueur( GaufreModele m){
        modele = m;
        miseAJour();
    }

    public void miseAJour(){
        setText(modele.getCurrentPlayer());
    }
    

}
