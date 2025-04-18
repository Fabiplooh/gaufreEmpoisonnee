import Modele.GaufreModele;
import Vue.*;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class Main implements Runnable {
    public static String[] argsGlobal;

    public static void main(String[] args) {
        if (args.length == 2){
            argsGlobal = args;
        }
        SwingUtilities.invokeLater(new Main());
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Gaufre empoisonnée");
        frame.setLayout(new BorderLayout());
        start(frame);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void start(JFrame frame) {
        GaufreModele modele;
        if (argsGlobal != null){
            try{
                int x = Integer.parseInt(argsGlobal[0]);
                int y = Integer.parseInt(argsGlobal[1]);
                modele = new GaufreModele(x, y);
            } catch (NumberFormatException e){
                System.err.println("Argument invalide");
                modele = new GaufreModele();
            }
        }else {
            modele = new GaufreModele();
        }
        
        GaufreVue vue = new GaufreVue(modele);
        AdaptateurSouris souris = new AdaptateurSouris(vue, modele);

        vue.setAdaptateurSouris(souris);

        frame.add(vue, BorderLayout.CENTER);

        // panneau du bas
        JPanel panneau = new JPanel();
        panneau.setLayout(new GridLayout(1, 5));
        JButton annuler = new BoutonAnnule(modele);
        AdaptateurAnnule adaptateurAnnule = new AdaptateurAnnule(modele);
        annuler.addActionListener(adaptateurAnnule);
        
        JButton refait = new BoutonRefais(modele);
        JButton sauve = new BoutonSauve(modele);
        JButton charge = new BoutonCharge(modele);
        JButton nouvelle = new BoutonNouvelle(modele);
        JLabel label = new JLabel(modele.getCurrentPlayer());
        panneau.add(annuler);
        panneau.add(refait);
        panneau.add(sauve);
        panneau.add(charge);
        panneau.add(label);
        frame.add(panneau, BorderLayout.SOUTH);
        

        Scanner s = new Scanner(System.in);
        while (s.hasNextLine()) {
            execute(s.nextLine(), modele);
        }
    }



    public void execute(String s, GaufreModele t){
        String [] parts = s.split("\\s+");
        int [] args = new int[parts.length - 1];
        try {
            for (int i = 0; i < parts.length - 1; i++)
                args[i] = Integer.parseInt(parts[i + 1]);

            switch (parts[0]) {
                case "joue":
                    t.play(args[0], args[1]);
                    break;
                case "print":
                    t.Affiche();
                    break;
                case "annule":
                    t.undo();
                    break;
                default:
                    throw new UnsupportedOperationException(parts[0]);
            }
        } catch (Exception e) {
            System.err.println("Commande invalide : " + s + " (" + e.getClass().getCanonicalName() + ")");
        }
    }
}