package fr.univ_lyon1.mif26;

import java.util.Random;

public class StrategieAleatoire implements Strategie {

    @Override
    public int choix(int j0, int j1, int pos_troll) {
        return new Random().nextInt(j0) + 1;
    }
}
