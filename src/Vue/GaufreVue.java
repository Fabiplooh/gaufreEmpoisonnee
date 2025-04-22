package Vue;

import Modele.GaufreModele;
import Patterns.*;

import javax.swing.*;
import java.awt.*;
import Global.OurConfiguration;

public class GaufreVue extends JComponent implements Observateur {
    GaufreModele modele;
    public static final Color BROWN = new Color(196, 104, 25);
    public static final Color GREEN = new Color(62, 104, 8);
    public static final Color GRAY = new Color(119, 119, 119);
    public static final Color YELLOW= new Color(140, 45, 45);
    private Timer timer;
    private int curLinePix;
    private int curColPix;
    private float decalagePointille =0.0f;

    public GaufreVue(GaufreModele modele) {
        this.modele = modele;
        timer = new Timer(50, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                decalagePointille +=1.0f;
                if (decalagePointille >10.0f){
                    decalagePointille = 0.0f;
                }
                repaint();
            }
        });
        timer.start();

    }

    public void setAdaptateurSouris(AdaptateurSouris souris) {
        this.addMouseListener(souris);
        this.addMouseMotionListener(souris);
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

    private void DessineGaufre(Graphics g) {
        boolean flag =false;
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
                if (OurConfiguration.instance().getProperty("preview").equals("true")) {
                    if (modele.canBeEat(i, j)) {
                        remplisCase(g, x, y, largeur, hauteur, YELLOW);
                    }
                }

                g.setColor(Color.black);
                g.drawRect(x, y, largeur, hauteur);

                if (modele.isSelectedCase(i, j)) {
                    flag = true;
                    curLinePix = x;
                    curColPix = y;
                }
            }
        }
        if (flag){ //si le joueur a la souris au dessus d'une case
            selectedCase(g, curLinePix, curColPix, largeur, hauteur);
        }
    }

    private void selectedCase(Graphics g, int x, int y, int largeur, int hauteur) {
        Graphics2D g2d = (Graphics2D) g;
        //Tableau avec taille trait, taille espace
        float[] traitsEspace = {5.0f, 5.0f};
        //Permet de definir une nouvelle brosse pour dessiner.
        BasicStroke brossePointillé = new BasicStroke(
                1.0f,                      // Épaisseur de ligne
                BasicStroke.CAP_BUTT,      // Style des extrémités
                BasicStroke.JOIN_MITER,    // Style des jonctions
                10.0f,                     // Limite de mitre
                traitsEspace,                      // Motif de pointillé
                decalagePointille                      // Phase de début
        );
        g2d.setStroke(brossePointillé);
        g2d.setColor(Color.MAGENTA);
        g2d.drawRect(x,y,largeur,hauteur);
        BasicStroke brosseClassique = new BasicStroke();
        g2d.setStroke(brosseClassique);
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

        // Positionner le message au centre de la fenêtre
        int x = (getWidth() / 2) - messageLargeur ;
        int y = getHeight() / 2;

        // Afficher le message
        g.setColor(Color.BLACK);
        g.setFont(new Font("Times New Roman", Font.BOLD, 26));
        g.drawString(joueur_victoire, x, y);
    }
}
