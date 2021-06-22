package fr.univ_lyon1.mif26;

import java.util.Random;

public class StrategieAleatoireImpair implements Strategie{

    @Override
    public int choix(int j0, int j1, int pos_troll) {
        if (j0 == 0) {
            return 0;
        }
        if (j0 < 3) {
            return 1;
        }
        return (new Random().nextInt(j0/2)*2 + 1);
    }
}
