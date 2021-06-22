package fr.univ_lyon1.mif26;

import java.util.Random;

public class StrategieAleatoireQ3 implements Strategie {
    /**
     * Choix du nombre de pierres à lancer. Dépend de la stratégie.
     * Ici, on joue un nombre aléatoire en 1 et n/3.
     * @param j0 nombre de pierres du joueur 1
     * @param j1 nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @return nombre de pierres à lancer.
     */
    @Override
    public int choix(int j0, int j1, int pos_troll) {
        if (j0 < 3) {
            return 1;
        }
        return (new Random().nextInt(j0/3) + 1);
    }
}
