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
public class EventiAvversi {
    private String evento, nome_centro, note_opzionali;
    private int indice;

    public EventiAvversi(String evento, int indice, String note_opzionali, String nome_centro) {
        this.evento = evento;
        this.indice = indice;
        this.note_opzionali = note_opzionali;
        this.nome_centro = nome_centro;
    }

    /**
     * Restituisce il nome dell'evento
     *
     * @return Stringa con il nome dell'evento avverso
     */
    public String getEvento() {
        return evento;
    }

    /**
     * Assegna un valore a evento
     *
     * @param evento nome dell'evento avverso
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * Restituisce l'indice di severità
     *
     * @return Numero da 1 a 5 che rappresenta la severità dell'evento avverso
     */
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
}