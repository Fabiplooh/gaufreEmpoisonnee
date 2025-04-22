package Controleur;

import java.awt.event.*;

import Vue.Menu;

public class AdaptateurClavier extends KeyAdapter {
    Menu menu;

    public AdaptateurClavier(Menu m) {
        menu = m;
    }
    
    @Override
    public void keyPressed(KeyEvent e){
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE){
            menu.setVisible(true);
        }
    }
}
