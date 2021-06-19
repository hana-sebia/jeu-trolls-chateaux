package fr.univ_lyon1.mif26;

public class StrategiePrudente implements Strategie {

    public Gain gain;

    public StrategiePrudente(final int idJoueur, final int m) {
        //repartir de 0
        //gain = new Gain(idJoueur, m);

        //lecture fichier de sauvegarde
        gain = readFromFile(idJoueur);
        gain.setM(m);
    }

    private Gain readFromFile(final int idJoueur) {
        return (Gain)File.ReadObjectFromFile(idJoueur);
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return gain.choix(j0, j1, pos_troll);
    }
}
