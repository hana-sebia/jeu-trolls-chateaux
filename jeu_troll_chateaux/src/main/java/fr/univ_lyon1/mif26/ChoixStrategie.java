package fr.univ_lyon1.mif26;

public enum ChoixStrategie {
    ALEATOIRE, PRUDENTE, ALEATOIREQ3, PRUDENTEQ3, ALEATOIREIMPAIR, PRUDENTEIMPAIR;

    @Override
    public String toString() {
        switch (this) {
            case ALEATOIRE: return "Aléatoire";
            case PRUDENTE: return "Prudente";
            case ALEATOIREQ3: return "AléatoireQ3";
            case PRUDENTEQ3: return "PrudenteQ3";
            case ALEATOIREIMPAIR: return "Aléatoire impair";
            case PRUDENTEIMPAIR: return "Prudente impair";
            default: return "UNKNOWN";
        }
    }
}
