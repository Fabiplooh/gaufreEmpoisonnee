package Modele;

import java.util.Stack;

public class GaufreModele {
    public int [][] gaufre; //0 vide; 1 remplis; 2 empoisonnée
    private int nbLigne;
    private int nbColonne;
    private boolean fin = false;
    private Stack<Coup> historyCoups;


    /*
    Par défaut notre gaufre est remplis et fait une taille de 4 ligne et 6 colonne
     */

    public GaufreModele(){
        nbLigne = 4;
        nbColonne = 6;
        gaufre = new int[nbLigne][nbColonne];
        historyCoups = new Stack<Coup>();

        for(int i=0; i<nbLigne; i++){
            for (int j=0; j<nbColonne; j++){
                gaufre[i][j] = 1;
            }
        }
        gaufre[0][0] = 2;
    }

    public GaufreModele(int x, int y){
        nbLigne = x;
        nbColonne = y;
        historyCoups = new Stack<Coup>();
        gaufre = new int[nbLigne][nbColonne];

        for(int i=0; i<nbLigne; i++){
            for (int j=0; j<nbColonne; j++){
                gaufre[i][j] = 1;
            }
        }
        gaufre[0][0] = 2;
    }

    public void Joue(int x, int y){
        if (x>0 && x <= nbLigne-1 && y>0 && y <= nbColonne-1){
            if (gaufre[x][y] == 2){
                fin = true;
                return;
            }
            int previousX = nbLigne -1;
            int previousY = nbColonne -1;
            boolean flag = true;
            for (int i=x; i<nbLigne; i++){
                for (int j=y; j<nbColonne; j++){
                    if (gaufre[i][j] == 1){
                        gaufre[i][j] = 0;
                    }
                    else if (flag){
                        previousX = i;
                        previousY = j;
                        flag = false;
                    }
                }
            }
            Coup push = new Coup(x,y, previousX, previousY);
            historyCoups.push(push);
        }
    }

    public int GetCase(int x, int y){
        return gaufre[x][y];
    }

    private boolean PeutAnnuler(){
        return !historyCoups.isEmpty();
    }

    public void Annule(){
        if (PeutAnnuler()){
            Coup pop = historyCoups.pop();
            for (int i=pop.x; i< pop.previousX; i++){
                for (int j=pop.y; j< pop.previousY; j++){
                    gaufre[i][j] = 1;
                }
            }
        }
    }

    class Coup{
        int x,y;
        int previousX,previousY;
        public Coup(int x, int y, int previousX, int previousY){
            this.x = x;
            this.y = y;
            this.previousX = previousX;
            this.previousY = previousY;
        }
    }

    public boolean isFin(){
        return fin;
    }

    public void Reset(){
        for (int i=0; i<nbLigne; i++){
            for (int j=0; j<nbColonne; j++){
                gaufre[i][j] = 1;
            }
        }
        gaufre[0][0] = 2;
        historyCoups = new Stack<Coup>();
    }

    public int GetLine(){
        return nbLigne;
    }
    public int GetColonne(){
        return nbColonne;
    }

    public void Affiche(){
        for (int i=0; i<nbLigne; i++){
            for (int j=0; j<nbColonne; j++){
                System.out.print(gaufre[i][j] + " ");
            }
            System.out.println();
        }
    }
}
