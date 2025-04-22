package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonRefais extends JButton implements Observateur {
    Jeu modele;

    public void miseAJour(){
        setEnabled(modele.canRedo());
    }

    public BoutonRefais(Jeu m) {
        modele = m;
        setText("->");
        setEnabled(modele.canRedo());
    }
}