package fr.univ_lyon1.mif26;

public class StrategiePrudenteDouble implements Strategie {

    public GainDouble gainDouble;

    /**
     * Constructeur.
     * @param idJoueur identifiant du joueur (1 ou 2)
     * @param m paramètre m du plateau
     */
    public StrategiePrudenteDouble(final int idJoueur, final int m) {
        //repartir de 0
        gainDouble = new GainDouble(idJoueur, m);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return gainDouble.choix(j0, j1, pos_troll);
    }
}
