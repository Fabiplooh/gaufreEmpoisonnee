package Vue;

import Modele.Jeu;
import javax.swing.*;

public class BoutonCharge extends JButton {
    Jeu modele;


    public BoutonCharge(Jeu m) {
        modele = m;
        setText("Load");
        setEnabled(true);
    }
}