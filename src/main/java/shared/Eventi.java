package shared;

public enum Eventi {
    Emicrania,
    Dolori_Addominali,
    Febbre,
    Brividi,
    Spossatezza,
    Tosse_e_o_catarro,
    Disturbi_respiratori,
    Disturbi_gastrointestinali,
    Dolori_muscolari_e_articolari,
    Tachicardia,
    Brachicardia,
    Crisi_ipertensiva,
    Linfoadenopatia,
    Lombalgia,
    Altro;

    @Override
    public String toString() {
        return switch (this) {
            case Dolori_Addominali -> "Dolori addominali";
            case Tosse_e_o_catarro -> "Tosse e/o catarro";
            case Disturbi_respiratori -> "Disturbi respiratori";
            case Disturbi_gastrointestinali -> "Disturbi gastrointestinali";
            case Dolori_muscolari_e_articolari -> "Dolori muscolari e articolari";
            case Crisi_ipertensiva -> "Crisi ipertensiva";
            default -> super.toString();
        };
    }
}
