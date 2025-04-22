package Vue;

import Modele.GaufreModele;
import Modele.Jeu;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonIA extends JButton {

    Jeu modele;


    public BoutonIA(Jeu m) {
        modele = m;

        setText("AI");
        setEnabled(true);
    }

}
