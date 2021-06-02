package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {

        final int NB_JEUX = 1;
        int n0 = 0;
        int n1 = 0;
        for (int i = 0; i < NB_JEUX; i++) {
            ChoixStrategie[] choixStrategies = new ChoixStrategie[2];
            choixStrategies[0] = ChoixStrategie.PRUDENTE;
            choixStrategies[1] = ChoixStrategie.ALEATOIRE;
            Jeu jeu = new Jeu(7, 15, choixStrategies);
            while(jeu.finJeu() == -1) {
//                System.out.println(jeu.toString());
                jeu.joueTour();
            }
            if (jeu.finJeu() == 1) {
                n0 = n0 + 1;
            }
            else if (jeu.finJeu() == 2) {
                n1 = n1 + 1;
            }

//            System.out.println(jeu.toString());
//            System.out.println("Le joueur " + jeu.finJeu() + " a gagné");
        }

        System.out.println(n0);
        System.out.println(n1);

    }
}