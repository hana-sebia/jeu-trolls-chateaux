package fr.univ_lyon1.mif26;

public class Simulation {

    private final int nb_cases;
    private final int nb_pierres;
    private final ChoixStrategie[] choixStrategies;
    private static final int NB_JEUX = 1000;


    public Simulation() {
        this.nb_cases = 15;
        this.nb_pierres = 15;
        choixStrategies = new ChoixStrategie[2];
        choixStrategies[0] = ChoixStrategie.PRUDENTE;
        choixStrategies[1] = ChoixStrategie.ALEATOIRE;
    }

    public Simulation(final int nb_cases, final int nb_pierres, final ChoixStrategie c0, final ChoixStrategie c1) {
        this.nb_cases = nb_cases;
        this.nb_pierres = nb_pierres;
        this.choixStrategies = new ChoixStrategie[2];
        this.choixStrategies[0] = c0;
        this.choixStrategies[1] = c1;
    }

    public void deroule() {
        int v0 = 0, v1 = 0;
        Jeu jeu = new Jeu(nb_cases, nb_pierres, choixStrategies);
        for (int i = 0; i < NB_JEUX; i++) {
            jeu.init(nb_pierres);
            while(jeu.finJeu() == -1) {
                //System.out.println(jeu.toString());
                jeu.joueTour();
            }
            if (jeu.finJeu() == 1) {
                v0 = v0 + 1;
            }
            else if (jeu.finJeu() == 2) {
                v1 = v1 + 1;
            }
            //System.out.println(jeu.toString());
            //System.out.println("Le joueur " + jeu.finJeu() + " a gagné");
        }
        System.out.println("\nRatios de victoires pour " + nb_cases + " cases  et " + nb_pierres + " pierres : ");
        System.out.println("     " + choixStrategies[0] + " : " + ((float)v0 * 100 / NB_JEUX) + "%");
        System.out.println("     " + choixStrategies[1] + " : " + ((float)v1 * 100 / NB_JEUX) + "%");
        System.out.println();
    }
}
