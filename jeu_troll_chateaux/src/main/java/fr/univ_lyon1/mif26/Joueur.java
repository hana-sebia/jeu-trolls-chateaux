package fr.univ_lyon1.mif26;

public class Joueur {

    private int nb_pierre;
    private final Strategie strategie;

    public Joueur(final int nb_pierre) {
        this.nb_pierre = nb_pierre;
        strategie = new StrategieAleatoire();
    }

    public Joueur(final int nb_pierre, ChoixStrategie choixStrategie, final int m) {
        this.nb_pierre = nb_pierre;
        if (choixStrategie == ChoixStrategie.PRUDENTE) {
            strategie = new StrategiePrudente(m);
        }
        else {
            strategie = new StrategieAleatoire();
        }
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
        int choix = strategie.choix(this.nb_pierre, adversaire, pos_troll);
        //maj de nb_pierre
        nb_pierre = nb_pierre - choix;
        return choix;
    }
}
