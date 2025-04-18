package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonSauve extends JButton implements Observateur {
    GaufreModele modele;

    public void miseAJour(){
        setEnabled(modele.canUndo());
    }

    public BoutonSauve(GaufreModele m) {
        modele = m;
        setText("Save");
        setEnabled(modele.canUndo());
    }
}