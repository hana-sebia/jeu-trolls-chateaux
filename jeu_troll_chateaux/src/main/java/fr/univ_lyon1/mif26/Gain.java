package fr.univ_lyon1.mif26;

import com.quantego.clp.CLP;
import com.quantego.clp.CLPConstraint;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

import java.io.Serializable;
import java.util.HashMap;

public class Gain implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final File file = new File();
    private final int m;
    private Double[] matriceProba;
    public HashMap<String, Double> map;

    public Gain(final int m) {
        this.m = m;
    }

    public void saveToFile() {
        file.WriteObjectToFile(this);
    }

    public void addGain(final int j0, final int j1, final int pos_troll, Double g) {
        String key = j0 + "," + j1 + "," + pos_troll;
        map.put(key, g);
    }

    public Double readGain(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll;
        if (map.containsKey(key))
            return map.get(key);
        return null;
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
        CLPVariable[] proba = new CLPVariable[j0];
        Double gOpt = resolutionSystemeLineaire(j0, j1, matriceGain, proba);
        addGain(j0, j1, pos_troll, gOpt);

        if (premierAppel) {
            matriceProba = new Double[j0];
            calculeProba(proba);
            System.out.println("gOpt(" + j0 + "," + j1 + "," + pos_troll + ") = " + gOpt);
            System.out.println(toString());
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

    public void calculeProba(final CLPVariable[] clpVariables) {
        for (int i = 0; i < clpVariables.length; i++) {
            matriceProba[i] = clpVariables[i].getSolution();
        }
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("");
        for (String i : map.keySet()) {
            str.append("g(").append(i).append(")=").append(map.get(i)).append("\n");
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



