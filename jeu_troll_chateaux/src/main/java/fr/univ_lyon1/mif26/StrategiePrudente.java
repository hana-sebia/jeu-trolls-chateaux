package fr.univ_lyon1.mif26;

public class StrategiePrudente implements Strategie {

    private static final File file = new File();

    public Gain gain;

    public StrategiePrudente(final int m) {
        gain = readFromFile();
        //gain = new Gain(m);
        gain.setM(m);
    }

    private Gain readFromFile() {
        return (Gain)file.ReadObjectFromFile();
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        int g = gain.choix(j0, j1, pos_troll);
        return g;
    }
}
