package Vue;

import java.awt.event.*;
import Modele.GaufreModele;
import Vue.Menu;

public class AdaptateurClavier extends KeyAdapter {
    Menu menu;

    public AdaptateurClavier(Menu m) {
        menu = m;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        menu.setVisible(true);
    }
}
