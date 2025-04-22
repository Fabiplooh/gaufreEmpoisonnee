package IA;

import org.junit.Test;
import static org.junit.Assert.assertFalse;


public class TestIAMoyen {

    @Test
    public void test(){
        IA ia;
        IA iar;
        Coup c;
        for(int largeur=2; largeur<=6; largeur++){
            for(int hauteur=2; hauteur<=6; hauteur++){
                for(int i=0 ; i<100; i++){
                    System.out.println(String.format("====== Nouvelle partie contre Difficile%dx%d ======", hauteur, largeur));
                    ia = new IAMoyen(hauteur, largeur);
                    iar = new IADifficile(hauteur, largeur);
                    c = new Coup(-1, -1);

                    while(!(c.getLigne() == 0 && c.getColonne() == 0)){
                        c = ia.coupAJouer();
                        System.out.println("IA Moyen: "+c);
                        if(c.getColonne() == 0 && c.getLigne() == 0){
                            break;
                        }
                        iar.coupAdversaire(c);

                        c = iar.coupAJouer();
                        System.out.println("IA Difficile: "+c);
                        ia.coupAdversaire(c);
                    }
                }
                for(int i=0 ; i<100; i++){
                    System.out.println(String.format("====== Nouvelle partie contre Random%dx%d ======", hauteur, largeur));
                    ia = new IARandom(hauteur, largeur);
                    iar = new IAMoyen(hauteur, largeur);
                    c = new Coup(-1, -1);

                    while(!(c.getLigne() == 0 && c.getColonne() == 0)){
                        c = ia.coupAJouer();
                        System.out.println("IA Rand: "+c);
                        if(c.getColonne() == 0 && c.getLigne() == 0){
                            break;
                        }
                        iar.coupAdversaire(c);

                        c = iar.coupAJouer();
                        System.out.println("IA Moyen: "+c);
                        ia.coupAdversaire(c);
                    }
                }
            }
        }
    }
}
