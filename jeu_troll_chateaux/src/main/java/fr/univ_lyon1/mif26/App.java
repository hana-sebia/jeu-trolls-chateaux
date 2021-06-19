package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {
        Simulation simulation = new Simulation(5, 15, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(7, 15, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(7, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

        simulation = new Simulation(15, 50, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();
    }
}