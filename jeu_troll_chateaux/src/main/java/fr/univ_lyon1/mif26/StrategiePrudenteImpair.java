package fr.univ_lyon1.mif26;

public class StrategiePrudenteImpair implements Strategie{

    public GainImpair gainImpair;

    /**
     * Constructeur.
     * @param idJoueur identifiant du joueur (1 ou 2)
     * @param m paramètre m du plateau
     */
    public StrategiePrudenteImpair(final int idJoueur, final int m) {
        gainImpair = new GainImpair(idJoueur, m);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return gainImpair.choix(j0, j1, pos_troll);
    }

}
