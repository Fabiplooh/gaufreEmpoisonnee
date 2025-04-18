package Vue;

import java.awt.event.*;

import Modele.GaufreModele;

public class AdaptateurSouris extends MouseAdapter {
    GaufreVue vue;
    GaufreModele modele;

    public AdaptateurSouris(GaufreVue vue, GaufreModele modele) {
        this.modele = modele;
        this.vue = vue;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int colonne = e.getX() / ((vue.getWidth() / modele.getColonne()));
        int ligne = e.getY() / ((vue.getHeight() / modele.getLine()));
        //System.out.println("Mouse clicked: (" + e.getX() + "," + e.getY() + ")");
        //System.out.println("Cell clicked: (" + ligne + "," + colonne + ")");
        modele.play(ligne, colonne);
        vue.repaint();
    }
}