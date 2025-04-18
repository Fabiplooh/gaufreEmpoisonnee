package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonRefais extends JButton implements Observateur {
    GaufreModele modele;

    public void miseAJour(){
        setEnabled(modele.canRedo());
    }

    public BoutonRefais(GaufreModele m) {
        modele = m;
        setText("->");
        setEnabled(modele.canRedo());
    }
}