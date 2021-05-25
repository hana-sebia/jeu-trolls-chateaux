package fr.univ_lyon1.mif26;

import com.quantego.clp.CLP;
import com.quantego.clp.CLPConstraint;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;

public class Gain implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final File file = new File();
    private final int m;
    private final HashMap<String, Double> mapGain;
    private final HashMap<String, Double[]> mapProba;

    public Gain(final int m) {
        this.m = m;
        this.mapGain = new HashMap<>();
        this.mapProba = new HashMap<>();
    }

    public void saveToFile() {
        file.WriteObjectToFile(this);
    }

    public void addGain(final int j0, final int j1, final int pos_troll, Double g) {
        String key = j0 + "," + j1 + "," + pos_troll;
        mapGain.put(key, g);
    }

    public void addProba(final int j0, final int j1, final int pos_troll, Double[] tab) {
        String key = j0 + "," + j1 + "," + pos_troll;
        mapProba.put(key, tab);
    }

    public Double readGain(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll;
        if (mapGain.containsKey(key))
            return mapGain.get(key);
        return null;
    }

    public Double[] readProba(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll;
        if (mapProba.containsKey(key))
            return mapProba.get(key);
        return null;
    }

    public int choix(final int j0, final int j1, final int pos_troll) {
        if (readGain(j0, j1, pos_troll) == null) {
            calculeMatrice(j0, j1, pos_troll, true);
        }
        //System.out.println(Arrays.toString(readProba(j0, j1, pos_troll)));
        return calculeCoupOpt(j0, j1, pos_troll);
    }

    public Double calculeMatrice(final int j0, final int j1, final int pos_troll, boolean premierAppel) {
        int i, j, new_pos_troll, new_j0, new_j1;
        Double matriceGain[][] = new Double[j0][j1];

        for (i = 0; i < j0; i++) {
            new_j0 = j0 - i - 1;
            for (j = 0; j < j1; j++) {
                new_j1 = j1 - j - 1;
                // mise à jour de la position du troll en fonction des pierres lancées
                if (i > j) {
                    new_pos_troll = pos_troll + 1;
                }
                else if (i < j) {
                    new_pos_troll = pos_troll - 1;
                }
                else {
                    new_pos_troll = pos_troll;
                }

                // résultat connu
                if (readGain(new_j0, new_j1, new_pos_troll) != null) {
                    matriceGain[i][j] = readGain(new_j0, new_j1, new_pos_troll);
                }
                // cas trivial
                else if (new_j0 == 0 || new_j1 == 0 || (new_j0 == new_j1 && new_pos_troll == 0) || Math.abs(new_pos_troll) == m + 1) {
                    matriceGain[i][j] = gain(new_j0, new_j1, new_pos_troll);
                }
                else {
                    matriceGain[i][j] = calculeMatrice(new_j0, new_j1, new_pos_troll, false);
                }
            }
        }

        //résolution du système
        CLPVariable[] proba = new CLPVariable[j0];
        Double gOpt = resolutionSystemeLineaire(j0, j1, matriceGain, proba);
        addGain(j0, j1, pos_troll, gOpt);

        //calcul coup d'après la proba et ajout dans la mapProbas
        addProba(j0, j1, pos_troll, calculeProba(proba));

        if (premierAppel) {
            //System.out.println("gOpt(" + j0 + "," + j1 + "," + pos_troll + ") = " + gOpt);
            //System.out.println(Arrays.toString(readProba(j0, j1, pos_troll)));
        }
        return gOpt;

    }

    private Double resolutionSystemeLineaire(final int j0, final int j1, final Double[][] matrice, CLPVariable[] proba) {
        int i,j;
        CLP equationSolver = new CLP();

        //Contrainte x1 + .....+ x_j0 = 1
        for(i = 0; i < j0; i++){
            proba[i] = equationSolver.addVariable();
            proba[i].lb(0.0);
            proba[i].ub(1.0);
        }

        CLPExpression sommeProba = equationSolver.createExpression();
        for(i = 0; i < j0; i++){
            sommeProba.add(proba[i], 1);
        }
        CLPConstraint sommeContrainte = sommeProba.eq(1);
        equationSolver.setConstraintName(sommeContrainte, "sommeContrainte");

        //Declaration et ajout de la variable g
        CLPVariable g = equationSolver.addVariable();
        g.ub(1.0);
        g.lb(-1.0);
        equationSolver.setObjectiveCoefficient(g, 1);

        //Contraintes sur les stratégies
        for(j = 0; j < j1; j++){
            CLPExpression expr = equationSolver.createExpression();
            for(i = 0; i < j0; i++){
                expr.add(proba[i], matrice[i][j]);
            }
            expr.add(g,-1);
            CLPConstraint c = expr.geq(0);
            equationSolver.setConstraintName(c, "contrainte" + j);
        }

        // Resolution du système
        equationSolver.maximization();
        equationSolver.solve();

        return equationSolver.getSolution(g);
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
        else if (t == -(m + 1)) {
            return -1.0;
        }
        else if (t == m + 1) {
            return 1.0;
        }
        else {      // x > 0 && y > 0 && x != y
            return null;
        }
    }

    public Double[] calculeProba(final CLPVariable[] clpVariables) {
        Double[] matriceProba = new Double[clpVariables.length];
        for (int i = 0; i < clpVariables.length; i++) {
            matriceProba[i] = clpVariables[i].getSolution();
        }
        return matriceProba;
    }

    public int calculeCoupOpt(final int j0, final int j1, final int t) {
        int i = 0;
        Double[] matriceProba = readProba(j0, j1, t);
        double somme = matriceProba[0] * 100;
        double randomNum = (Math.random() * 100);
        //System.out.println(randomNum / 100);
        while (randomNum > somme && i < matriceProba.length - 1) {
            i = i + 1;
            somme = somme + matriceProba[i] * 100;
        }
        return i + 1;
}
//s([}=

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (String i : mapGain.keySet()) {
            str.append("g(").append(i).append(")=").append(mapGain.get(i)).append("\n");
        }
        return str.toString();
    }

    /*@Override
    public String toString() {
        String str = matriceProba[0].toString();
        for (int i = 1; i < matriceProba.length; i++) {
            str += ", " + matriceProba[i].toString();
        }
        return str;
    }*/



}



