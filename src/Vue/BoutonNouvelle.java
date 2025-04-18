package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonNouvelle extends JButton implements Observateur {
    GaufreModele modele;

    public void miseAJour(){
        setEnabled(true);
    }

    public BoutonNouvelle(GaufreModele m) {
        modele = m;
        setText("New Game");
        setEnabled(true);
    }
}