package Vue;

import javax.swing.*;
import java.awt.*;

import Modele.GaufreModele;

public class Panneau extends JPanel{
    GaufreModele modele;

    public Panneau(GaufreModele modelea){
        modele = modelea;

        this.setLayout(new GridLayout(1, 6));

        BoutonAnnule annuler = new BoutonAnnule(modele);
        annuler.addActionListener(e->modele.undo());
        modele.addViewer(annuler);

        BoutonRefais refait = new BoutonRefais(modele);
        refait.addActionListener(e->modele.redo());
        modele.addViewer(refait);

        BoutonSauve sauve = new BoutonSauve(modele);
        //sauve.addActionListener(e->modele.save());
        //modele.addViewer(sauve);

        BoutonCharge charge = new BoutonCharge(modele);
        //charge.addActionListener(e->modele.load);

        BoutonNouvelle nouvelle = new BoutonNouvelle(modele);
        nouvelle.addActionListener(e->modele.reset());


        Affiche_joueur label = new Affiche_joueur(modele);
        modele.addViewer(label);
        
        this.add(annuler);
        this.add(refait);
        this.add(sauve);
        this.add(charge);
        this.add(nouvelle);
        this.add(label);
    }
}