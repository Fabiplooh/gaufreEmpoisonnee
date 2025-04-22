package IA;

import java.util.ArrayList;
import java.util.Random;

public class IAMoyen extends ArbreEtOu{

    Random rand;
    float proba = 2/3;

    public IAMoyen(int hauteur, int largeur) {
        super(hauteur, largeur);
        rand = new Random();
    }

    @Override
    protected Coup choisir_coup(ArrayList<Coup> coupsGagnants, ArrayList<Coup> coupsPerdants) {
        int indice;
        ArrayList<Coup> nonnonnon = new ArrayList<>();
        coupsPerdants.remove(new Coup(0, 0));
        nonnonnon.add(new Coup(0, 0));

        if(coupsPerdants.contains(new Coup(0, 1))){
            coupsPerdants.remove(new Coup(0, 1));
            nonnonnon.add(new Coup(0, 1));
        }
        if(coupsPerdants.contains(new Coup(1, 0))){
            coupsPerdants.remove(new Coup(1, 0));
            nonnonnon.add(new Coup(1, 0));
        }

        if(coupsGagnants.isEmpty() && coupsPerdants.isEmpty()){
            indice = rand.nextInt(nonnonnon.size());
            return nonnonnon.get(indice);
        } else if(!coupsGagnants.isEmpty() && (coupsPerdants.isEmpty() || rand.nextInt(100) < (proba*100))){
            indice = rand.nextInt(coupsGagnants.size());
            return coupsGagnants.get(indice);
        } else {
            indice = rand.nextInt(coupsPerdants.size());
            return coupsPerdants.get(indice);
        }
    }

}
