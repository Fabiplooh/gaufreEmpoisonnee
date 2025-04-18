package Vue;

import Modele.GaufreModele;

import java.io.IOException;
import java.util.Scanner;

public class AffichageTerminal {


    private void displayCommands(){
        System.out.println("Voici les commandes disponibles \n" +
                "-print : affiche la tablette ( 2 == poisson 1 == mangée 0 == plein) \n" +
                "-joue <i> <j> : mange la case de ligne i et de colonne j \n" +
                "-annule : annule le dernier coup joué \n" +
                "-refais: refais le dernier coup annulé \n"+
                "-save : enregistre la partie dans un fichier\n"+
                "-reset : permet de lancer une nouvelle partie \n" +
                "-q : permet de qutter le jeu"
                );
    }

    public AffichageTerminal() throws Exception {
        Scanner s = new Scanner(System.in);
        GaufreModele modele = new GaufreModele();
        displayCommands();
        do {
            System.out.print(">>> ");
        } while (s.hasNextLine() && execute(s.nextLine(), modele));
    }


    public boolean execute(String s, GaufreModele t){
        String [] parts = s.split("\\s+");
        int [] args = new int[parts.length - 1];
        try {
            for (int i = 0; i < parts.length - 1; i++)
                args[i] = Integer.parseInt(parts[i + 1]);

            switch (parts[0]) {
                case "joue":
                    t.play(args[0], args[1]);
                    break;
                case "annule":
                    t.undo();
                    break;
                case "save":
                    t.save();
                    break;
                case "refais":
                    t.redo();
                    break;
                case "print":
                    t.Affiche();
                    break;
                case "reset":
                    t.reset();
                    break;
                case "q":
                    return false;
                default:
                    throw new UnsupportedOperationException(parts[0]);
            }
        } catch (Exception e) {
            System.err.println("Commande invalide : " + s + " (" + e.getClass().getCanonicalName() + ")");
        }
        return true;
    }
}
