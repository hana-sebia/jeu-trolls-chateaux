package fr.univ_lyon1.mif26;

public class App {
    public static void main( String[] args ) {

        final int NB_JEUX = 1000;
        int n0 = 0;
        int n1 = 0;
        for (int i = 0; i < NB_JEUX; i++) {
            Jeu jeu = new Jeu();
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

       // =(s

        System.out.println(n0);
        System.out.println(n1);


        /*StrategiePrudente p = new StrategiePrudente(1);
        p.choix(5, 4, -1);*/




    }
}