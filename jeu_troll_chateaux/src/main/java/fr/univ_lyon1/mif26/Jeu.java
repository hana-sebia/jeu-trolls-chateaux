package fr.univ_lyon1.mif26;

public class Jeu {

    private final int nb_case;
    private int pos_troll;
    private final Joueur[] joueurs;

    /**
     * Constructeur par défaut.
     */
    public Jeu() {
        this.nb_case = 7;
        this.pos_troll = 0;
        joueurs = new Joueur[2];
        for (int i = 0; i < 2; i++) {
            joueurs[i] = new Joueur(15);
        }
    }

    /**
     * Constructeur.
     * @param nb_case nombre de cases du jeu
     */
    public Jeu(int nb_case) {
        if (nb_case % 2 == 0) {
            System.out.print("Nombre de cases pair!");
            System.exit(1);
        }
        this.nb_case = nb_case;
        this.pos_troll = 0;
        joueurs = new Joueur[2];
        for (int i = 0; i < 2; i++) {
            joueurs[i] = new Joueur(15);
        }
    }

    /**
     * Constructeur.
     * @param nb_case nombre de cases du jeu
     * @param nb_pierre nombre de pierre pour chacun des chateaux en début de partie
     */
    public Jeu(final int nb_case, final int nb_pierre) {
        if (nb_case % 2 == 0) {
            System.out.print("Nombre de cases pair!");
            System.exit(1);
        }
        this.nb_case = nb_case;
        this.pos_troll = 0;
        joueurs = new Joueur[2];
        for (int i = 0; i < 2; i++) {
            joueurs[i] = new Joueur(nb_pierre);
        }
    }

    /**
     * Constructeur.
     * @param nb_case nombre de cases du jeu
     * @param nb_pierre nombre de pierre pour chacun des chateaux en début de partie
     * @param choixStrategie tableau contenant la stratégie du joueur à l'indice associé
     */
    public Jeu(final int nb_case, final int nb_pierre, final ChoixStrategie[] choixStrategie) {
        if (nb_case % 2 == 0) {
            System.out.print("Nombre de cases pair!");
            System.exit(1);
        }
        this.nb_case = nb_case;
        this.pos_troll = 0;
        joueurs = new Joueur[2];
        for (int i = 0; i < 2; i++) {
            joueurs[i] = new Joueur(nb_pierre, choixStrategie[i], (nb_case - 3) / 2);
        }
    }

    public void init(final int nb_pierres) {
        this.pos_troll = 0;
        for (int i = 0; i < 2; i++) {
            joueurs[i].setNbPierre(nb_pierres);
        }
    }

    /**
     * Joue un tour du jeu.
     * Choix du nombre de pierres lancées et maj de pos_troll
     */
    public void joueTour() {
        int[] adversaire = new int[2];
        for (int i = 0; i < adversaire.length; i++) {
            adversaire[i] = joueurs[(i+1)%2].getNbPierre();
        }
        int[] choix_joueurs = new int[2];
        for (int i = 0; i < choix_joueurs.length; i++) {
            choix_joueurs[i] = joueurs[i].joue(adversaire[i], pos_troll);
        }

        if (choix_joueurs[0] > choix_joueurs[1]) {
            pos_troll++;
        }
        else if (choix_joueurs[0] < choix_joueurs[1]) {
            pos_troll--;
        }
    }

    /**
     * Détermine le vainqueur
     * @return 0 : match nul
     *          1 : le joueur 1 a gagné
     *          2 : le joueur 2 a gagné
     */
    private int resultPosTroll() {
        if (pos_troll > 0) {
            return 1;
        }
        else if (pos_troll < 0) {
            return 2;
        }
        // pos_troll == 0
        return 0;
    }

    /**
     * Détermine l'état du jeu
     * @return -1 : partie non terminée
     *         0 : fin de partie, match nul
     *         1 : fin de partie, le joueur 1 a gagné
     *         2 : fin de partie, le joueur 2 a gagné
     */
    public int finJeu() {
        if (joueurs[0].getNbPierre() == 0) {
            while (joueurs[1].getNbPierre() > 0 && Math.abs(pos_troll) < nb_case/2) {
                pos_troll = pos_troll - 1;
                joueurs[1].retireNbPierre(1);
            }
            return resultPosTroll();
        }
        else if (joueurs[1].getNbPierre() == 0) {
            while (joueurs[0].getNbPierre() > 0 && Math.abs(pos_troll) < nb_case/2) {
                pos_troll = pos_troll + 1;
                joueurs[0].retireNbPierre(1);
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
        str.append("J1: ").append(joueurs[0].getNbPierre())
                .append(" - J2: ").append(joueurs[1].getNbPierre())
                .append("\n");
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
