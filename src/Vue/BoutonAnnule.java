package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonAnnule extends JButton implements Observateur {
    GaufreModele modele;

    public void miseAJour(){
        setEnabled(modele.canUndo());
    }

    public BoutonAnnule(GaufreModele m) {
        modele = m;
        setText("<-");
        setEnabled(modele.canUndo());
    }
}