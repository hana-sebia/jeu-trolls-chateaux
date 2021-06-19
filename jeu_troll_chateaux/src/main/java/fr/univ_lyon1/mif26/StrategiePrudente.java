package fr.univ_lyon1.mif26;

public class StrategiePrudente implements Strategie {

    public Gain gain;

    /**
     * Constructeur.
     * @param idJoueur identifiant du joueur (1 ou 2)
     * @param m paramètre m du plateau
     */
    public StrategiePrudente(final int idJoueur, final int m) {
        //repartir de 0
        //gain = new Gain(idJoueur, m);

        //lecture fichier de sauvegarde pour éviter les calculs déjà connus
        gain = readFromFile(idJoueur);
        gain.setM(m);
    }

    /**
     * Lecture du fichier de sauvegarde.
     * @param idJoueur identifiant du joueur
     * @return Gain lu
     */
    private Gain readFromFile(final int idJoueur) {
        return (Gain)File.ReadObjectFromFile(idJoueur);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return gain.choix(j0, j1, pos_troll);
    }
}
