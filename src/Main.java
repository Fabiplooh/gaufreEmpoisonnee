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
        modele.addViewer(vue);
        // panneau du bas
        Panneau panneau = new Panneau(modele);
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