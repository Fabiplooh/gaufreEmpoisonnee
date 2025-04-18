package Vue;

import Modele.GaufreModele;
import Patterns.*;

import javax.swing.*;
import java.awt.*;

public class GaufreVue extends JComponent implements Observateur {
    GaufreModele modele;
    public static final Color BROWN = new Color(196, 104, 25);
    public static final Color GREEN = new Color(62, 104, 8);
    public static final Color GRAY = new Color(119, 119, 119);

    public GaufreVue(GaufreModele modele) {
        this.modele = modele;
    }

    public void setAdaptateurSouris(AdaptateurSouris souris) {
        this.addMouseListener(souris);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if(modele.isFin()){
            DessineFin(g);
        }else {
            DessineGaufre(g);
        }
    }

    public void miseAJour() {
        repaint();
    }

    private void DessineGaufre(Graphics g){
        int largeur = getWidth()/ modele.getColonne();
        int hauteur = getHeight()/ modele.getLine();

        for (int i=0; i<modele.getLine(); i++){
            for (int j=0; j<modele.getColonne(); j++){
                int x = j * largeur;
                int y = i * hauteur;

                if(modele.estPoison(i, j)){
                    remplisCase(g, x, y, largeur, hauteur, BROWN );
                    g.setColor(GREEN);
                    g.fillOval(x+largeur/4,y+hauteur/4,largeur/2,hauteur/2);
                } else if (modele.estRemplie(i, j)){
                    remplisCase(g, x, y, largeur, hauteur, BROWN );
                } else { 
                    remplisCase(g, x, y, largeur, hauteur, GRAY );
                }
                
                g.setColor(Color.black);
                g.drawRect(x,y,largeur,hauteur);
            }
        }
    }
  
    private void remplisCase(Graphics g, int x, int y, int largeur, int hauteur, Color couleur){
        g.setColor( couleur );
        g.fillRect(x,y,largeur,hauteur);
    }

    private void DessineFin(Graphics g) {
        String joueur_victoire = "Victoire " + modele.getCurrentPlayer() + " !";


        // Calcule dimensions du texte pour le centrer
        FontMetrics fontMetrics = g.getFontMetrics();
        int messageLargeur = fontMetrics.stringWidth(joueur_victoire);
        int messageHauteur = fontMetrics.getHeight();

        // Positionner le message au centre de la fenêtre
        int x = (getWidth() / 2) - messageLargeur ;
        int y = getHeight() / 2;

        // Afficher le message
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 26));
        g.drawString(joueur_victoire, x, y);
    }
}
