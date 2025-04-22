package Vue;

import Modele.GaufreModele;
import Patterns.Observateur;
import javax.swing.*;

public class BoutonIA extends JButton {

    GaufreModele modele;


    public BoutonIA(GaufreModele m) {
        modele = m;

        setText("AI");
        setEnabled(true);
    }

}
