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

    private int getLine(MouseEvent e){
        return e.getY() / ((vue.getHeight() / modele.getLine()));
    }

    private int getColumn(MouseEvent e){
        return e.getX() / ((vue.getWidth() / modele.getColonne()));
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        //System.out.println("Mouse clicked: (" + e.getX() + "," + e.getY() + ")");
        //System.out.println("Cell clicked: (" + getLine(e) + "," + getColumn(e) + ")");
        modele.play(getLine(e), getColumn(e));
    }

    @Override
    public void mouseMoved(MouseEvent e){
        modele.setPosition(getLine(e), getColumn(e));
    }
}