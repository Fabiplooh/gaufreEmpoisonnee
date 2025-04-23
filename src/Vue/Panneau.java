package Vue;

import javax.swing.*;
import java.awt.*;

import Modele.Jeu;

public class Panneau extends JPanel{
    Jeu modele;

    public Panneau(Jeu modelea){
        modele = modelea;

        this.setLayout(new GridLayout(1, 3));

        BoutonAnnule annuler = new BoutonAnnule(modele);
        annuler.addActionListener(e->modele.undo());
        annuler.setFocusable(false);
        modele.addViewer(annuler);

        BoutonRefais refait = new BoutonRefais(modele);
        refait.addActionListener(e->modele.redo());
        refait.setFocusable(false);
        modele.addViewer(refait);

        Affiche_joueur label = new Affiche_joueur(modele);
        modele.addViewer(label);

        this.add(annuler);
        this.add(refait);
        this.add(label);
    }


}