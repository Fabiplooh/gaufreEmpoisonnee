package IA;

import org.junit.Test;
import static org.junit.Assert.assertFalse;

import Modele.GaufreModele;

public class TestIA {
    @Test
    public void test(){
        GaufreModele modele;
        IA ia;
        IA iar;
        Coup c;
        final int hauteur = 6;
        final int largeur = 6;

        for(int i=0 ; i<1; i++){

            ia = new ArbreEtOu(hauteur, largeur);
            iar = new IARandom(hauteur, largeur);
            c = new Coup(-1, -1);

            while(c.getLigne() != 0 && c.getColonne() != 0){
                c = ia.coupAJouer();
                System.out.println("IA: "+c);
                assertFalse(c.getLigne() == 0 && c.getColonne() == 0);
                iar.coupAdversaire(c);
                iar.coupAdversaire(c);

                c = iar.coupAJouer();
                System.out.println("IA Rand: "+c);
                if(c.getLigne() == 0 && c.getColonne() == 0){
                    break;
                }
                ia.coupAdversaire(c);
                ia.coupAdversaire(c);
            }
        }
    }
}
