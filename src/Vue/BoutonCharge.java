package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonCharge extends JButton implements Observateur {
    GaufreModele modele;

    public void miseAJour(){
        setEnabled(modele.canUndo());
    }

    public BoutonCharge(GaufreModele m) {
        modele = m;
        setText("Load");
        setEnabled(modele.canUndo());
    }
}