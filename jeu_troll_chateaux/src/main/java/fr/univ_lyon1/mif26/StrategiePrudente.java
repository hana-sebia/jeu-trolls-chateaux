package fr.univ_lyon1.mif26;

public class StrategiePrudente implements Strategie {

    public Gain gain;

    public StrategiePrudente(final int m) {
        gain = new Gain(m);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        int g = gain.choix(j0, j1, pos_troll);
        //System.out.println("je joue : " + g);
        return g;
    }
}
