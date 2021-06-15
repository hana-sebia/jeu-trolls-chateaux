package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {

        Simulation simulation = new Simulation(15, 30, ChoixStrategie.PRUDENTE, ChoixStrategie.ALEATOIRE);
        simulation.deroule();

    }
}






/*
probas(15,15,0)
    nb_cases = 5 : (0.0647, 0.1778,0.0, 0.179,0.0123,0.236...)
    nb_cases = 7 : (0,0.46,0.05,0.49,0,0,...)*/