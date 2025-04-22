package IA;

import java.util.ArrayList;
import java.util.Random;

public class IADifficile extends ArbreEtOu{

    Random rand;

    public IADifficile(int hauteur, int largeur) {
        super(hauteur, largeur);
        rand = new Random();
    }

    @Override
    protected Coup choisir_coup(ArrayList<Coup> coupsGagnants, ArrayList<Coup> coupsPerdants) {
        int indice;
        if(!coupsGagnants.isEmpty()){
            indice = rand.nextInt(coupsGagnants.size());
            return coupsGagnants.get(indice);
        } else {
            indice = rand.nextInt(coupsPerdants.size());
            return coupsPerdants.get(indice);
        }
    }

}
