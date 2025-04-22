package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonAnnule extends JButton implements Observateur {
    Jeu modele;

    public void miseAJour(){
        setEnabled(modele.canUndo());
    }

    public BoutonAnnule(Jeu m) {
        modele = m;
        setText("<-");
        setEnabled(modele.canUndo());
    }
}