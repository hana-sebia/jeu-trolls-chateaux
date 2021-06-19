package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {

        ChoixStrategie[] choixStrategies = new ChoixStrategie[2];
        choixStrategies[0] = ChoixStrategie.PRUDENTE;
        choixStrategies[1] = ChoixStrategie.ALEATOIRE;
        Jeu jeu = new Jeu(15, 30, choixStrategies);
        while(jeu.finJeu() == -1) {
            System.out.println(jeu.toString());
            jeu.joueTour();
        }
        System.out.println(jeu.toString());
        if (jeu.finJeu() == 1) {
            System.out.println("Le joueur 1 ayant la stratégie " + choixStrategies[0] + " a gagné!");
        }
        else if (jeu.finJeu() == 2) {
            System.out.println("Le joueur 2 ayant la stratégie " + choixStrategies[1] + " a gagné!");
        }

        /*Simulation simulation = new Simulation(5, 15, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(7, 15, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(7, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 50, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();*/

       /* Gain gain = new Gain(4);
        //gain.calculeMatrice(5,4,-1, true);
        gain.choix(15,15,0);*/
    }
}






/*
probas(15,15,0)
    nb_cases = 5 : (0.0647, 0.1778,0.0, 0.179,0.0123,0.236...)
    nb_cases = 7 : (0,0.46,0.05,0.49,0,0,...)*/