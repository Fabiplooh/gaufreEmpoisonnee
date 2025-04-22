package Vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

import Global.OurConfiguration;
import Modele.GaufreModele;

public class Panneau extends JPanel{
    GaufreModele modele;

    public Panneau(GaufreModele modelea){
        modele = modelea;

        this.setLayout(new GridLayout(1, 3));

        BoutonAnnule annuler = new BoutonAnnule(modele);
        annuler.addActionListener(e->modele.undo());
        modele.addViewer(annuler);

        BoutonRefais refait = new BoutonRefais(modele);
        refait.addActionListener(e->modele.redo());
        modele.addViewer(refait);

        BoutonIA IA = new BoutonIA(modele);
        //nouvelle.addActionListener();


        Affiche_joueur label = new Affiche_joueur(modele);
        modele.addViewer(label);
        
        this.add(annuler);
        this.add(refait);
        this.add(label);
    }


}