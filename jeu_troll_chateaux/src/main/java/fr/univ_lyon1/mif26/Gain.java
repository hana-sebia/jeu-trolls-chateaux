package fr.univ_lyon1.mif26;

public class Gain extends StrategiePrudente {

    private final int j0;
    private final int j1;
    private final int pos_troll;
    private final int m;
    private Double gain;
    private Double matriceGain[][];

    public Gain(final int j0, final int j1, final int pos_troll, final int m) {
        this.j0 = j0;
        this.j1 = j1;
        this.pos_troll = pos_troll;
        this.m = m;
        this.gain = null;
        this.matriceGain = new Double[j0][j1];
        for (int i = 0; i < j0; i++) {
            for (int j = 0; j < j1; j++) {
                if (gain(i,j,pos_troll) == null) {
                    this.matriceGain[i][j] = Double.valueOf(i + j);
                }
                else {
                    this.matriceGain[i][j] = gain(i,j,pos_troll);
                }

            }
        }
    }

    public int getJ0() {
        return j0;
    }

    public int getJ1() {
        return j1;
    }

    public int getPosTroll() {
        return pos_troll;
    }

    public Double getGain() {
        /*calcul du gain*/
        boolean different = false;
        int i, j;
        for (i = 0; i < j0; i++) {
            for (j = 0; j < j1; j++) {
                if (matriceGain[i][j] != matriceGain[0][0]) {
                    different = true;
                }
            }
        }
        if (!different) {
            gain = matriceGain[0][0];
        }
        else {
            /* IL FAUT ME CODER */
        }
        return gain;
    }

    public void calculeMatrice() {
        int i, j;
        for (i = j0 - 1; i >= 0; i++) {
            for (j = j1 - 1; j >= 0; j++) {
                if (i == 0 || j == 0 || (i == j && pos_troll == 0)) {
                    matriceGain[i][j] = gain(i, j, pos_troll);
                }
                else {
                    matriceGain[i][j] = Double.valueOf(8);
                }
            }
        }
    }

    private Double gain(final int x, final int y, final int t) {
        if (x == 0 && y == 0) {
            if (t > 0) {
                return 1.0;
            }
            else if (t < 0) {
                return -1.0;
            }
            else {    // t == 0
                return 0.0;
            }
        }
        else if (x == y && t == 0) {
            return 0.0;
        }
        else if (x == 0) {
            if (t > y) {
                return 1.0;
            }
            else if (t < y) {
                return -1.0;
            }
            else {    //y == t
                return 0.0;
            }
        }
        else if (y == 0) {
            if (x > Math.abs(t) || t > 0) {
                return 1.0;
            }
            else if (x < Math.abs(t) && t < 0) {
                return -1.0;
            }
            else {   // x = Math.abs(t) && t < 0
                return 0.0;
            }
        }
        else if (readGain(x,y,pos_troll) != null) {
            return readGain(x,y,pos_troll);
        }
        else {      // x > 0 && y > 0 && x != y
            /* IL FAUT ME CODER */
            return null;
        }
    }

    @Override
    public String toString() {
        String str = "";
        str += j0 + ";" + j1 + ";" + pos_troll + "\n\n";
        for (int i = 0; i < j0; i++) {
            for (int j = 0; j < j1 - 1; j++) {
                str += this.matriceGain[i][j] + ",";
            }
            str += this.matriceGain[i][j1 - 1] + "\n";
        }
        return str;
    }
}
