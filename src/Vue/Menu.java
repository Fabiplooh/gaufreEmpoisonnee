package Vue;

import javax.swing.*;
import Modele.GaufreModele;
import Modele.Jeu;

import java.awt.event.*;
import java.io.File;

public class Menu extends JDialog {
    Jeu modele;

    public Menu(Jeu m) {
        modele = m;

        this.setModal(true);
        this.setTitle("Menu");
        this.setSize(400, 200);
        this.setLayout(new BoxLayout(getContentPane(), BoxLayout.PAGE_AXIS));

        JFrame saveFrame = new JFrame("Sauvegarde de fichier");
        saveFrame.setSize(400, 200);
        saveFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        saveFrame.setLayout(null);

        /* SAVE BUTTON */
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
                    dispose();
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


        /* LOAD BUTTON */
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
                        dispose();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                }
            }
        });
        Menu menud = this;
        BoutonNouvelle nouvelle = new BoutonNouvelle(modele);
        nouvelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new ChoixJoueur(menud);
                //dispose();
            }
        });

        this.add(nouvelle);
        this.add(sauve);
        this.add(charge);
    }
}
