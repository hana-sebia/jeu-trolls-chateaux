package fr.univ_lyon1.mif26;

public class StrategiePrudenteQ3 implements Strategie {

    public GainQ3 gainQ3;

    /**
     * Constructeur.
     * @param idJoueur identifiant du joueur (1 ou 2)
     * @param m paramètre m du plateau
     */
    public StrategiePrudenteQ3(final int idJoueur, final int m) {
        gainQ3 = new GainQ3(idJoueur, m);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return gainQ3.choix(j0, j1, pos_troll);
    }
}
