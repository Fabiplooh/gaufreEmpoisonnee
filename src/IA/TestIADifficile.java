package IA;

import org.junit.Test;
import static org.junit.Assert.assertFalse;


public class TestIADifficile {

    @Test
    public void test(){
        IA ia;
        IA iar;
        Coup c;
        for(int largeur=2; largeur<=6; largeur++){
            for(int hauteur=2; hauteur<=6; hauteur++){
                for(int i=0 ; i<100; i++){
                    System.out.println(String.format("====== Nouvelle partie %dx%d ======", hauteur, largeur));
                    ia = new IADifficile(hauteur, largeur);
                    iar = new IARandom(hauteur, largeur);
                    c = new Coup(-1, -1);

                    while(!(c.getLigne() == 0 && c.getColonne() == 0)){
                        c = ia.coupAJouer();
                        System.out.println("IA: "+c);
                        assertFalse(c.getLigne() == 0 && c.getColonne() == 0);
                        iar.coupAdversaire(c);

                        c = iar.coupAJouer();
                        System.out.println("IA Rand: "+c);
                        ia.coupAdversaire(c);
                    }
                }
            }
        }
    }
}
