package Vue;

import java.awt.event.*;

import Modele.GaufreModele;

public class AdaptateurAnnule implements ActionListener {
    private GaufreModele modele;

    public AdaptateurAnnule(GaufreModele modele) {
        this.modele = modele;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        modele.undo();
    }
}