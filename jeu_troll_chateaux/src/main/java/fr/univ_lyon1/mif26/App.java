package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {
        /*Jeu jeu = new Jeu();
        while(jeu.finJeu() == -1) {
            System.out.println(jeu.toString());
            jeu.joueTour();
        }
        System.out.println(jeu.toString());
        System.out.println("Le joueur " + jeu.finJeu() + " a gagné");*/



        Gain g = new Gain(1);
        g.calculeMatrice(5, 4, -1, true);




    }
}