package fr.univ_lyon1.mif26;

import com.quantego.clp.CLP;
import com.quantego.clp.CLPConstraint;
import com.quantego.clp.CLPExpression;
import com.quantego.clp.CLPVariable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class GainQ3 implements Serializable {

    private static final long serialVersionUID = 1L;

    private int m;
    private final int idJoueur;
    private final HashMap<String, Double> mapGainQ3;
    private final HashMap<String, Double[]> mapProba;

    /**
     * Constructeur.
     *
     * @param idJoueur identifiant du joueur (1 ou 2)
     * @param m        paramètre m du plateau
     */
    public GainQ3(final int idJoueur, final int m) {
        this.m = m;
        this.mapGainQ3 = new HashMap<>();
        this.mapProba = new HashMap<>();
        this.idJoueur = idJoueur;
    }

    /**
     * Setteur.
     *
     * @param m paramètre m du plateau
     */
    public void setM(int m) {
        this.m = m;
    }

    /**
     * Ajout d'un GainQ3 dans mapGainQ3.
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @param g         G(j0, j1, pos_troll)
     */
    private void addGainQ3(final int j0, final int j1, final int pos_troll, Double g) {
        String key = j0 + "," + j1 + "," + pos_troll + ";" + m;
        mapGainQ3.put(key, g);
    }

    /**
     * Ajout d'un tableau de probabilité dans mapProba.
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @param tab       tableau de probabilités
     */
    private void addProba(final int j0, final int j1, final int pos_troll, Double[] tab) {
        String key = j0 + "," + j1 + "," + pos_troll + ";" + m;
        mapProba.put(key, tab);
    }

    /**
     * Lecture du GainQ3 dans mapGainQ3.
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @return G(j0, j1, pos_troll) si le GainQ3 de cette configuration est connu, null sinon
     */
    private Double readGainQ3(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll + ";" + m;
        if (mapGainQ3.containsKey(key))
            return mapGainQ3.get(key);
        return null;
    }

    /**
     * Lecture du tableau de probabilités dans mapProba.
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @return tableau du probabilités si cette configuration est connue, null sinon
     */
    private Double[] readProba(final int j0, final int j1, final int pos_troll) {
        String key = j0 + "," + j1 + "," + pos_troll + ";" + m;
        if (mapProba.containsKey(key))
            return mapProba.get(key);
        return null;
    }

    /**
     * Calcul du GainQ3 (cas de bases).
     *
     * @param j0 nombre de pierres du joueur 1
     * @param j1 nombre de pierres du joueur 2
     * @param t  position du troll
     * @return G(j0, j1, t) si cette configuration correspond à un cas de base, null sinon
     */
    private Double gainQ3(final int j0, final int j1, final int t) {
        if (t == -(m + 1)) {
            return -1.0;
        }
        if (t == m + 1) {
            return 1.0;
        } else if (j0 == 0 && j1 == 0) {
            if (t > 0) {
                return 1.0;
            } else if (t < 0) {
                return -1.0;
            } else {    // t == 0
                return 0.0;
            }
        } else if (j0 == j1 && t == 0) {
            return 0.0;
        } else if (j0 == 0) {
            if (t > j1) {
                return 1.0;
            } else if (t < j1) {
                return -1.0;
            } else {    //y == t
                return 0.0;
            }
        } else if (j1 == 0) {
            if (j0 > Math.abs(t) || t > 0) {
                return 1.0;
            } else if (j0 < Math.abs(t) && t < 0) {
                return -1.0;
            } else {   // x = Math.abs(t) && t < 0
                return 0.0;
            }
        } else {      // x > 0 && y > 0 && x != y
            return null;
        }
    }

    /**
     * Résolution du système linéaire.
     *
     * @param j0      nombre de pierres du joueur 1
     * @param j1      nombre de pierres du joueur 2
     * @param matrice matrice de gains
     * @param proba   tableau de probabilités
     * @return maximisation du gain
     */
    private Double resolutionSystemeLineaire(final int j0, final int j1, final Double[][] matrice, CLPVariable[] proba) {
        int i, j;
        CLP equationSolver = new CLP();

        //Contrainte x1 + .....+ x_j0 = 1

        for (i = 0; i < j0; i++) {
            proba[i] = equationSolver.addVariable();
            proba[i].lb(0.0);
            proba[i].ub(1.0);
        }

        CLPExpression sommeProba = equationSolver.createExpression();
        for (i = 0; i < j0; i++) {
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
        int new_j1 = j1/3;
        if (j1 < 3) {
            if (j1 > 0){
                new_j1 = 1;
            }
        }
        for (j = 0; j < new_j1; j++) {
            CLPExpression expr = equationSolver.createExpression();
            for (i = 0; i < j0; i++) {
                expr.add(proba[i], matrice[i][j]);
            }
            expr.add(g, -1);
            CLPConstraint c = expr.geq(0);
            equationSolver.setConstraintName(c, "contrainte" + j);
        }

        // Resolution du système
        equationSolver.maximization();
        equationSolver.solve();

        return equationSolver.getSolution(g);
    }

    /**
     * Calcul du tableau de probabilités.
     *
     * @param clpVariables variables du système
     * @return tableau de probabilités
     */
    public Double[] calculeProba(final CLPVariable[] clpVariables) {
        Double[] matriceProba = new Double[clpVariables.length];
        for (int i = 0; i < clpVariables.length; i++) {
            matriceProba[i] = clpVariables[i].getSolution();
        }
        return matriceProba;
    }

    /**
     * Calcul de la matrice de gain
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @return
     */
    public Double calculeMatrice(final int j0, final int j1, final int pos_troll) {
        int i, j, new_pos_troll, new_j0, new_j1;
        Double[][] matriceGain = new Double[j0][j1];

        for (i = 0; i < j0; i++) {
            new_j0 = j0 - i - 1;
            for (j = 0; j < j1; j++) {
                new_j1 = j1 - j - 1;
                // mise à jour de la position du troll en fonction des pierres lancées
                if (i > j) {
                    new_pos_troll = pos_troll + 1;
                } else if (i < j) {
                    new_pos_troll = pos_troll - 1;
                } else {
                    new_pos_troll = pos_troll;
                }

                // résultat connu
                if (readGainQ3(new_j0, new_j1, new_pos_troll) != null) {
                    matriceGain[i][j] = readGainQ3(new_j0, new_j1, new_pos_troll);
                }
                // cas trivial
                else if (new_j0 == 0 || new_j1 == 0
                        || Math.abs(new_pos_troll) == m + 1) {
                    matriceGain[i][j] = gainQ3(new_j0, new_j1, new_pos_troll);
                } else {
                    matriceGain[i][j] = calculeMatrice(new_j0, new_j1, new_pos_troll);
                    matriceGain[i][j] = calculeMatrice(new_j0, new_j1, new_pos_troll);
                }
            }
        }

        //résolution du système
        CLPVariable[] proba = new CLPVariable[j0];
        Double gOpt = resolutionSystemeLineaire(j0, j1, matriceGain, proba);
        addGainQ3(j0, j1, pos_troll, gOpt);

        //calcul coup d'après la proba et ajout dans la mapProbas
        addProba(j0, j1, pos_troll, calculeProba(proba));
        return gOpt;
    }

    /**
     * Calcul du coup optimal.
     *
     * @param j0 nombre de pierres du joueur 1
     * @param j1 nombre de pierres du joueur 2
     * @param t  position du troll
     * @return
     */
    public int calculeCoupOpt(final int j0, final int j1, final int t) {
        int i = 0;
        Double[] matriceProba = readProba(j0, j1, t);
        double somme = matriceProba[0] * 100;
        double randomNum = (Math.random() * 100);
        while (randomNum > somme && i < matriceProba.length - 1) {
            i = i + 1;
            somme = somme + matriceProba[i] * 100;
        }
        return i + 1;
    }

    /**
     * Calcule la matrice de gain
     *
     * @param j0        nombre de pierres du joueur 1
     * @param j1        nombre de pierres du joueur 2
     * @param pos_troll position du troll
     * @return
     */
    public int choix(final int j0, final int j1, final int pos_troll) {
        if (readGainQ3(j0, j1, pos_troll) == null) {
            calculeMatrice(j0, j1, pos_troll);
        }
        // Vérifier le gain et le tableau de probabilité de (x , y, pos_troll)
        /*if (j0 == 20) {
            System.out.println(readGainQ3(20, 20, 0));
            System.out.println(Arrays.toString(readProba(20,  20, 0)));

        }*/

        /*System.out.print("g(20,x,0) = [");
        for (int i = 0; i < 21; i++) {
            calculeMatrice(20, i, 0);
            System.out.print(i + ": " + readGainQ3(20, i, 0) + " ; ");
        }
        System.out.println("\n");*/

        return calculeCoupOpt(j0, j1, pos_troll);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (String i : mapGainQ3.keySet()) {
            str.append("g(").append(i).append(")=").append(mapGainQ3.get(i)).append("\n");
        }
        str.append("\n");
        for (String i : mapProba.keySet()) {
            str.append("p(").append(i).append(")=").append(Arrays.toString(mapProba.get(i)));
        }
        return str.toString();
    }
}