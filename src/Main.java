import Modele.GaufreModele;

import javax.swing.*;
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
        start();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void start() {
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
                    t.Joue(args[0], args[1]);
                    break;
                case "print":
                    t.Affiche();
                    break;
                case "annule":
                    t.Annule();
                    break;
                default:
                    throw new UnsupportedOperationException(parts[0]);
            }
        } catch (Exception e) {
            System.err.println("Commande invalide : " + s + " (" + e.getClass().getCanonicalName() + ")");
        }
    }
}