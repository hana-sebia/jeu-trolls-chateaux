package fr.univ_lyon1.mif26;

public class Jeu {

    private final int nb_case;
    private int pos_troll;
    private final int nb_pierre[];
    Strategie sI;
    Strategie sA;

    /**
     * Constructeur par défaut.
     */
    public Jeu() {
        this.nb_case = 7;
        this.pos_troll = 0;
        this.nb_pierre = new int[2];
        for (int i = 0; i < 2; i++) {
            this.nb_pierre[i] = 15;
        }
        sI = new StrategiePrudente(2);
        sA = new StrategieAleatoire();
    }

    /**
     * Constructeur.
     * @param nb_case nombre de cases du jeu
     */
    public Jeu(int nb_case) {
        if (nb_case % 2 == 0) {
            System.exit(1);
        }
        this.nb_case = nb_case;
        this.pos_troll = 0;
        this.nb_pierre = new int[2];
        for (int i = 0; i < 2; i++) {
            this.nb_pierre[i] = 15;
        }
    }

    /**
     * Constructeur.
     * @param nb_case nombre de cases du jeu
     * @param nb_pierre nombre de pierre pour chacun des chateaux en début de partie
     */
    public Jeu(int nb_case, int nb_pierre) {
        //pas de configuration du jeu avec un nombre impair de cases
        if (nb_case % 2 == 0) {
            System.exit(1);
        }
        this.nb_case = nb_case;
        this.pos_troll = 0;
        this.nb_pierre = new int[2];
        for (int i = 0; i < 2; i++) {
            this.nb_pierre[i] = nb_pierre;
        }
    }

    /**
     * Choisis le nombre de pierre à tirer pour le joueur en paramètre.
     * @param joueur
     * @return
     */
    public int choixA(final int joueur) {

        return sA.choix(nb_pierre[joueur], nb_pierre[(joueur + 1)%2], pos_troll);
    }

    public int choixI(final int joueur) {

        return sI.choix(nb_pierre[joueur], nb_pierre[(joueur + 1)%2], pos_troll);
    }

    /**
     * Joue un tour du jeu.
     */
    public void joueTour() {
        int j0 = choixI(0);
        int j1 = choixA(1);

        //pas possible de lancer plus de pierres que le joueur n'en a
        if (j0 > nb_pierre[0] || j1 > nb_pierre[1]) {
            System.exit(1);
        }

        if (j0 > j1) {
            pos_troll++;
        }
        else if (j0 < j1) {
            pos_troll--;
        }
        nb_pierre[0] = nb_pierre[0] - j0;
        nb_pierre[1] = nb_pierre[1] - j1;
    }

    /**
     * Détermine le vainqueur
     * @return 0 : fin de partie, match nul
     *          1 : fin de partie, le joueur 1 a gagné
     *          2 : fin de partie, le joueur 2 a gagné
     */
    private int resultPosTroll() {
        if (pos_troll == 0) {
            return 0;
        }
        else if (pos_troll > 0) {
            return 1;
        }
        else
            return 2;
    }

    /**
     * Détermine l'état du jeu
     * @return -1 : partie non terminée
     *         0 : fin de partie, match nul
     *         1 : fin de partie, le joueur 1 a gagné
     *         2 : fin de partie, le joueur 2 a gagné
     */
    public int finJeu() {
        if (nb_pierre[0] == 0) {
            while (nb_pierre[1] > 0 && Math.abs(pos_troll) < nb_case/2) {
                pos_troll = pos_troll - 1;
                nb_pierre[1]--;
            }
            return resultPosTroll();
        }
        else if (nb_pierre[1] == 0) {
            while (nb_pierre[0] > 0 && Math.abs(pos_troll) < nb_case/2) {
                pos_troll = pos_troll + 1;
                nb_pierre[0]--;
            }
            return resultPosTroll();
        }
        else if (pos_troll == (nb_case / 2)) {
            return 1;
        }
        else if (pos_troll == -(nb_case / 2) ) {
            return 2;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append("J1: ").append(nb_pierre[0]).append(" - J2: ").append(nb_pierre[1]).append("\n");
        for (int i = 0; i < nb_case; i++) {
            if (i == pos_troll + nb_case / 2)
                str.append("|T");
            else {
                if (i == 0) {
                    str.append("|1");
                }
                else if (i == nb_case - 1) {
                    str.append("|2");
                }
                else {
                    str.append("|_");
                }
            }
        }
        str.append("|\n");
        return str.toString();
    }
    
}
