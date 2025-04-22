package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonNouvelle extends JButton implements Observateur {
    Jeu modele;

    public void miseAJour(){
        setEnabled(true);
    }

    public BoutonNouvelle(Jeu m) {
        modele = m;
        setText("New Game");
        setEnabled(true);
    }
}