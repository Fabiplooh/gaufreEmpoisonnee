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

        this.setLayout(new GridLayout(1, 6));

        BoutonAnnule annuler = new BoutonAnnule(modele);
        annuler.addActionListener(e->modele.undo());
        modele.addViewer(annuler);

        BoutonRefais refait = new BoutonRefais(modele);
        refait.addActionListener(e->modele.redo());
        modele.addViewer(refait);

        BoutonSauve sauve = new BoutonSauve(modele);
        sauve.addActionListener(e-> {
            try {
                modele.save();
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        });
        modele.addViewer(sauve);

        JFrame frame = new JFrame("Sélecteur de fichier");
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null);

        BoutonCharge charge = new BoutonCharge(modele);
        charge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY); // Uniquement des fichiers
                int result = fileChooser.showOpenDialog(frame);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    try {
                        modele.initGaufreWithFile(selectedFile.getAbsolutePath());
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        modele.addViewer(charge);

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