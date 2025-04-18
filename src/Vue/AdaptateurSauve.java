package Vue;

import java.awt.event.*;

import Modele.GaufreModele;

public class AdaptateurSauve implements ActionListener {

    private GaufreModele modele;

    public AdaptateurSauve(GaufreModele modele) {
        this.modele = modele;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            modele.save();
        } catch (Exception ex){
            return;
        }
    }
}