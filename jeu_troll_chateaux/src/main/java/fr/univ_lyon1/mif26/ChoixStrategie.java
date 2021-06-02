package fr.univ_lyon1.mif26;

public enum ChoixStrategie {
    ALEATOIRE, PRUDENTE;

    @Override
    public String toString() {
        switch (this) {
            case ALEATOIRE: return "Aléatoire";
            case PRUDENTE: return "Prudente";
            default: return "UNKNOWN";
        }
    }
}
