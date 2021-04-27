package fr.univ_lyon1.mif26;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args ) {
        //System.out.println( "Hello World!" );

        Jeu jeu = new Jeu();
        while(jeu.finJeu() == -1) {
            System.out.println(jeu.toString());
            jeu.joueTour();
        }
        System.out.println(jeu.toString());
        System.out.println("Le joueur " + jeu.finJeu() + " a gagné");

    }
}