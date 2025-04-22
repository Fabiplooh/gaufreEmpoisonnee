package Vue;

import java.awt.event.*;

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
