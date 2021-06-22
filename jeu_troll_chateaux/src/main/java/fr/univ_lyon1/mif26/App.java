package fr.univ_lyon1.mif26;

import java.util.Random;

public class App {
    public static void main( String[] args ) {


        /*ChoixStrategie[] strategies = new ChoixStrategie[2];
        strategies[0] = ChoixStrategie.PRUDENTE;
        strategies[1] = ChoixStrategie.ALEATOIRE;
        Jeu jeu = new Jeu(7,20, strategies);
        while(jeu.finJeu() == -1) {
            jeu.joueTour();
        }*/




        Simulation simulation = new Simulation(7, 20, ChoixStrategie.ALEATOIREIMPAIR, ChoixStrategie.PRUDENTEIMPAIR);
        simulation.deroule();

        /*simulation = new Simulation(7, 15, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(7, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 50, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();*/
    }
}