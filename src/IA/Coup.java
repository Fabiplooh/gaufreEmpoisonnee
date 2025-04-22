package IA;

public class Coup {
    int i;
    int j;

    public Coup(int i, int j){
        this.i = i;
        this.j = j;
    }

    public int getLigne(){
        return i;
    }

    public int getColonne(){
        return j;
    }

    @Override
    public boolean equals(Object obj) {
        Coup c = (Coup) obj;
        return this.getLigne() == c.getLigne() && this.getColonne() == c.getColonne();
    }

    @Override
    public int hashCode() {
        return String.format("%d,%d", i, j).hashCode();
    }

    @Override
    public String toString() {
        return String.format("(%d, %d)", i, j);
    }
}