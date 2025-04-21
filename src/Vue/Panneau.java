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

        JFrame saveFrame = new JFrame("Sauvegarde de fichier");
        saveFrame.setSize(400, 200);
        saveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        saveFrame.setLayout(null);

        BoutonSauve sauve = new BoutonSauve(modele);
        sauve.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Donner un nom au fichier");
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);

                int userSelection = fileChooser.showSaveDialog(saveFrame);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                try {
                    modele.save(fileToSave.getAbsolutePath());
                } catch (Exception ex) {
                    throw new RuntimeException(ex);
                }   
                }   
            }   
        });
        modele.addViewer(sauve);

        JFrame explorateurFrame = new JFrame("Sélecteur de fichier");
        explorateurFrame.setSize(400, 200);
        explorateurFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        explorateurFrame.setLayout(null);

        BoutonCharge charge = new BoutonCharge(modele);
        charge.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                int result = fileChooser.showOpenDialog(explorateurFrame);

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