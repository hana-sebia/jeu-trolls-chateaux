package fr.univ_lyon1.mif26;

public class SimulationDouble {

    private final int nb_cases;
    private final int nb_pierres;
    private final ChoixStrategie[] choixStrategies;
    private static final int NB_JeuDoubleX = 10000; //nombre de SimulationDouble

    /**
     * Constructeur par défaut. Initialise les paramètres avec la configuration standard du JeuDouble.
     */
    public SimulationDouble() {
        this.nb_cases = 15;
        this.nb_pierres = 15;
        choixStrategies = new ChoixStrategie[2];
        choixStrategies[0] = ChoixStrategie.PRUDENTE;
        choixStrategies[1] = ChoixStrategie.ALEATOIRE;
    }

    /**
     * Constructeur avec paramètres.
     * @param nb_cases nombre de cases sur le plateau
     * @param nb_pierres nombre de pierres dont disposent les joueurs au début du JeuDouble
     * @param c0 stratégie du joueur 1
     * @param c1 stratégie du joueur 2
     */
    public SimulationDouble(final int nb_cases, final int nb_pierres, final ChoixStrategie c0, final ChoixStrategie c1) {
        this.nb_cases = nb_cases;
        this.nb_pierres = nb_pierres;
        this.choixStrategies = new ChoixStrategie[2];
        this.choixStrategies[0] = c0;
        this.choixStrategies[1] = c1;
    }

    /**
     * Lance la SimulationDouble et affiche les résultats.
     */
    public void deroule() {
        int v0 = 0, v1 = 0;
        JeuDouble jeuDouble = new JeuDouble(nb_cases, nb_pierres, choixStrategies);
        for (int i = 0; i < NB_JeuDoubleX; i++) {
            jeuDouble.init(nb_pierres);
            while(jeuDouble.finJeuDouble() == -1) {
                jeuDouble.joueTour();
            }
            if (jeuDouble.finJeuDouble() == 1) {
                v0 = v0 + 1;
            }
            else if (jeuDouble.finJeuDouble() == 2) {
                v1 = v1 + 1;
            }
        }
        System.out.println("\nRatios de victoires pour " + nb_cases + " cases et " + nb_pierres + " pierres : \n"
                + "     " + choixStrategies[0] + " : " + ((float)v0 * 100 / NB_JeuDoubleX) + " %\n"
                + "     " + choixStrategies[1] + " : " + ((float)v1 * 100 / NB_JeuDoubleX) + " %");
    }
}
