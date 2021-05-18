package fr.univ_lyon1.mif26;

import java.io.Serializable;
import java.util.HashMap;

public class StrategiePrudente implements Strategie {

    public Gain gain;

    public StrategiePrudente(final int m) {
        gain = new Gain(m);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        //gain.calculeMatrice(j0, j1, pos_troll);

        return 0;
    }





}
