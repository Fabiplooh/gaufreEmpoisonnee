package Vue;

import javax.swing.*;

import java.awt.Dialog;
import java.awt.GridLayout;
import java.awt.event.*;

public class ChoixJoueur extends JDialog {
    String j1, j2;

    public ChoixJoueur(JDialog menu) {
        super(menu);
        String[] listeJoueurs = {"Humain", "IA facile", "IA moyenne", "IA difficile", "IA random"};
        setTitle("Choix des joueurs");
        this.setSize(900, 200);
        this.setVisible(true);
        this.setLayout(new GridLayout(3, 2));
        this.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
        JLabel label1 = new JLabel("J1");
        JLabel label2 = new JLabel("J2");
        this.add(label1);
        this.add(label2);

        JComboBox<String> box1 = new JComboBox<>(listeJoueurs);
        this.add(box1);

        JComboBox<String> box2 = new JComboBox<>(listeJoueurs);
        this.add(box2);

        JButton bouton = new JButton("Done");
        this.add(bouton);
        bouton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                j1 = box1.getSelectedItem().toString();
                j2 = box2.getSelectedItem().toString();
                dispose();
                menu.dispose();
            }
        });
    }

    public String getJoueur1() {
        return j1;
    }
    public String getJoueur2() {
        return j2;
    }
}
