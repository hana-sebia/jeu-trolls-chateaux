package fr.univ_lyon1.mif26;

public class Joueur {

    private int nb_pierre;
    private final Strategie strategie;
    private final boolean inverse;

    public Joueur(final int nb_pierre, final boolean inverse) {
        this.nb_pierre = nb_pierre;
        this.strategie = new StrategieAleatoire();
        this.inverse = inverse;
    }

    public Joueur(final int nb_pierre, ChoixStrategie choixStrategie, final boolean inverse, final int m) {
        this.nb_pierre = nb_pierre;
        if (choixStrategie == ChoixStrategie.PRUDENTE) {
            strategie = new StrategiePrudente(inverse ? 2 : 1, m);
        }
        else {
            strategie = new StrategieAleatoire();
        }
        this.inverse = inverse;
    }

    public int getNbPierre() {
        return nb_pierre;
    }

    public void setNbPierre(int nb_pierre) {
        this.nb_pierre = nb_pierre;
    }

    public void retireNbPierre(int nb_pierre) {
        this.nb_pierre = this.nb_pierre - nb_pierre;
    }

    public int joue(final int adversaire, final int pos_troll) {
        //choisit le nombre pierre à lancer en fct de la stratégie
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
