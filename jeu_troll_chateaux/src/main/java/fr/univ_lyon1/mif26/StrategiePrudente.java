package fr.univ_lyon1.mif26;

import java.io.Serializable;
import java.util.HashMap;

public class StrategiePrudente implements Serializable, Strategie {

    private static final long serialVersionUID = 1L;

    private static final File file = new File();
    protected HashMap<String, Double> map;
    private Gain gain;

    public StrategiePrudente() {
        map = new HashMap<>();
    }

    public void saveToFile() {
        file.WriteObjectToFile(this);
    }

    public void addGain(final Gain gain) {
        String key = gain.getJ0() + "," + gain.getJ1() + "," + gain.getPosTroll();
        Double value = gain.getGain();
        map.put(key, value);
    }

    public Double readGain(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll;
        if (map.containsKey(key))
            return map.get(key);
        return null;
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        return 0;
    }

    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (String i : map.keySet()) {
            str.append("g(").append(i).append(")=").append(map.get(i)).append("\n");
        }
        return str.toString();
    }

}
