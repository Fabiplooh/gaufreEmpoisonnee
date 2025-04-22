package Vue;

import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonSauve extends JButton implements Observateur {
    Jeu modele;

    public void miseAJour(){
        setEnabled(modele.canUndo());
    }

    public BoutonSauve(Jeu m) {
        modele = m;
        setText("Save");
        setEnabled(modele.canUndo());
    }
}