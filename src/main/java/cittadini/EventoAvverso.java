/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

/**
 * Classe contenente le informazioni relative all' evento avverso:
 * tipo di evento, indice di severità, nome del centro in cui si è verificato l'evento e note opzionali
 *
 * @author Daniele Caspani
 */
public class EventoAvverso {

    /**
     * Campo di tipo enum contenente l'enumerativo che rappresenta un evento avverso.
     */
    private EventoAvverso.Eventi evento;
    private String nome_centro, cod_fiscale, note_opzionali;

    /**
     * Un valore da 1 a 5 che rappresenta la gravità dell'evento avverso.
     */
    private int indice;

    public EventoAvverso(Eventi evento, int indice, String note_opzionali, String nome_centro, String cod_fiscale) {
        this.evento = evento;
        this.indice = indice;
        this.note_opzionali = note_opzionali;
        this.nome_centro = nome_centro;
        this.cod_fiscale = cod_fiscale;
    }

    /**
     * Restituisce il nome dell'evento
     *
     * @return Stringa con il nome dell'evento avverso
     */
    public EventoAvverso.Eventi getEvento() {
        return evento;
    }

    /**
     * Assegna un valore a evento
     *
     * @param evento nome dell'evento avverso
     */
    public void setEvento(Eventi evento) {
        this.evento = evento;
    }

    /**
     * Restituisce l'indice di severità
     *
     * @return Numero da 1 a 5 che rappresenta la severità dell'evento avverso
     */

    public String getCod_fiscale(){
        return cod_fiscale;
    }

    public void setCod_fiscale(String cod_fiscale){
        this.cod_fiscale = cod_fiscale;
    }
    public int getIndice() {
        return indice;
    }

    /**
     * Assegna un valore a indice
     *
     * @param indice numero da 1 a 5 che rappresenta la severità dell'evento avverso
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * Restituisce le note opzionali
     *
     * @return Le note opzionali associate all'evento avverso
     */
    public String getNoteOpzionali() {
        return note_opzionali;
    }

    /**
     * Assegna un valore alle note opzionali
     *
     * @param note_opzionali note opzionali associate all'evento avverso
     */
    public void setNoteOpzionali(String note_opzionali) {
        this.note_opzionali = note_opzionali;
    }

    /**
     * Restituisce il nome del centro
     *
     * @return Nome del centro dove il cittadino ha effettuato la vaccinazione
     */
    public String getNomeCentro() {
        return nome_centro;
    }

    /**
     * Assegna un valore a nome_centro
     *
     * @param nome_centro nome del centro dove il cittadino ha effettuato la vaccinazione
     */
    public void setNomeCentro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * Converte un oggetto in stringa
     *
     * @author Daniele Caspani
     */
    @Override
    public String toString() {
        return evento + "," + indice + "," + note_opzionali + "," + nome_centro;
    }


    /**
     * Classe enumerativa contenente i vari tipi di evento avverso riconosciuti dall'applicazione.
     * @author Manuel Marceca
     */
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

        /**
         * Metodo utile alla corretta formattazione testuale dei vari tipi enumerativi.
         * @author Manuel Marceca
         */
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

        /**
         * Metodo che restituisce i vari eventi avversi sotto forma di array di stringhe.
         * @author Manuel Marceca
         */
        public static String[] getEventiToStringArray(){
            String[] out = new String[Eventi.values().length];
            int i = 0;
            for(Eventi evento: Eventi.values()){
                out[i] = evento.toString();
                i++;
            }
            return out;
        }
    }

}