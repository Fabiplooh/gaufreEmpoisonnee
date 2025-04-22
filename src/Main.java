import Vue.*;
import Vue.Menu;
import Modele.GaufreModele;
import Global.OurConfiguration;
import javax.swing.*;
import java.awt.*;

public class Main implements Runnable {

    public static void main(String[] args) throws Exception {
        if (OurConfiguration.instance().getProperty("mode_vue").equals("graphique")){
            SwingUtilities.invokeLater(new Main());
        } else{
            new AffichageTerminal();
        }
    }

    @Override
    public void run() {
        JFrame frame = new JFrame("Gaufre empoisonnée");
        frame.setLayout(new BorderLayout());
        try {
            start(frame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public void start(JFrame frame) throws Exception {
        GaufreModele modele = new GaufreModele();

        GaufreVue vue = new GaufreVue(modele);
        AdaptateurSouris souris = new AdaptateurSouris(vue, modele);
        
        vue.setAdaptateurSouris(souris);

        frame.add(vue, BorderLayout.CENTER);
        modele.addViewer(vue);
        // panneau du bas
        Panneau panneau = new Panneau(modele);
        frame.add(panneau, BorderLayout.SOUTH);

        Menu menu = new Menu(modele);
        AdaptateurClavier clavier = new AdaptateurClavier(menu);
        frame.addKeyListener(clavier);
        menu.addKeyListener(clavier);
        menu.setVisible(false);
    }
}