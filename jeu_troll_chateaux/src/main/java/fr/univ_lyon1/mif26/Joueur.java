package fr.univ_lyon1.mif26;

public class Joueur {

    private int nb_pierre;
    private final Strategie strategie;
    private final boolean inverse;

    /**
     * Constructeur.
     * @param nb_pierre nombre de pierres au début du jeu
     * @param inverse pour le joueur 2, la configuration du jeu est inversée
     */
    public Joueur(final int nb_pierre, final boolean inverse) {
        this.nb_pierre = nb_pierre;
        this.strategie = new StrategieAleatoire();
        this.inverse = inverse;
    }

    /**
     * Constructeur.
     * @param nb_pierre nombre de pierres au début du jeu
     * @param choixStrategie stratégie du joueur
     * @param inverse pour le joueur 2, la configuration du jeu est inversée
     * @param m valeur m du plateau
     */
    public Joueur(final int nb_pierre, ChoixStrategie choixStrategie, final boolean inverse, final int m) {
        this.nb_pierre = nb_pierre;
        if (choixStrategie == ChoixStrategie.PRUDENTE) {
            strategie = new StrategiePrudente(inverse ? 2 : 1, m);
        }
        else if (choixStrategie == ChoixStrategie.PRUDENTEQ3) {
            strategie = new StrategiePrudenteQ3(inverse ? 2 : 1, m);
        }
        else if (choixStrategie == ChoixStrategie.ALEATOIREQ3) {
            strategie = new StrategieAleatoireQ3();
        }
        else if (choixStrategie == ChoixStrategie.ALEATOIREIMPAIR) {
            strategie = new StrategieAleatoireImpair();
        }
        else if (choixStrategie == ChoixStrategie.PRUDENTEIMPAIR) {
            strategie = new StrategiePrudenteImpair(inverse ? 2 : 1, m);
        }
        else {
            strategie = new StrategieAleatoire();
        }
        this.inverse = inverse;
    }

    /**
     * Getteur.
     * @return nombre de pierres du joueur
     */
    public int getNbPierre() {
        return nb_pierre;
    }

    /**
     * Setteur.
     * @param nb_pierre nombre de pierres du joueur
     */
    public void setNbPierre(int nb_pierre) {
        this.nb_pierre = nb_pierre;
    }

    /**
     * Retire des pierres du joueur.
     * @param nb_pierre nombre de pierres à retirer (correspond au nombre de pierres lancées)
     */
    public void retireNbPierre(int nb_pierre) {
        this.nb_pierre = this.nb_pierre - nb_pierre;
    }

    /**
     * Le joueur détermine le nombre de pierre qu'il veut lancer.
     * @param adversaire
     * @param pos_troll
     * @return
     */
    public int joue(final int adversaire, final int pos_troll) {
        //choisit le nombre pierre à lancer en fonction de la stratégie
        int choix;
        if (inverse) {
            choix = strategie.choix(this.nb_pierre, adversaire, -pos_troll);
        }
        else {
            choix = strategie.choix(this.nb_pierre, adversaire, pos_troll);
        }

        //maj de nb_pierre
        this.retireNbPierre(choix);
        return choix;
    }
}
