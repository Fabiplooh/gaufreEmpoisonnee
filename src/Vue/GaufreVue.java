package Vue;

import Modele.GaufreModele;

import javax.swing.*;
import java.awt.*;

public class GaufreVue extends JComponent {
    GaufreModele modele;

    public GaufreVue(GaufreModele modele) {
        this.modele = modele;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        DessineGaufre(g);
    }

    private void DessineGaufre(Graphics g){
        int largeur = getWidth()/ modele.GetColonne();
        int hauteur = getHeight()/ modele.GetLine();

        for (int i=0; i<modele.GetLine(); i++){
            for (int j=0; j<modele.GetColonne(); j++){
                int x = j * largeur;
                int y = i * hauteur;

                switch(modele.GetCase(i,j)){
                    case 0:
                        /*A METTRE DANS FONCTION*/
                        g.setColor(new Color(119, 119, 119));
                        g.fillRect(x,y,largeur,hauteur);
                        break;
                    case 1:
                        g.setColor(new Color(196, 104, 25));
                        g.fillRect(x,y,largeur,hauteur);
                        break;
                    case 2:
                        g.setColor(new Color(196, 104, 25));
                        g.fillRect(x,y,largeur,hauteur);
                        g.setColor(new Color(62, 104, 8));
                        g.fillOval(x+largeur/2,y+hauteur/2,largeur/2,hauteur/2);
                        break;
                }

                g.setColor(Color.black);
                g.drawRect(x,y,largeur,hauteur);
            }
        }
    }

}
