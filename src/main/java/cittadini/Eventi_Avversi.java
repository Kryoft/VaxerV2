/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

/**
 * Classe contenente le informazioni relative all' evento avverso:
 * tipo di evento,indice di gravità, nome del centro in cui si è verificato l'evento e note opzionali
 *
 * @author daniele Caspani
 */
public class Eventi_Avversi {
    private String evento;
    private int indice;
    private String note;
    private String nome_centro;

    public Eventi_Avversi(String evento, int indice, String note, String nome_centro) {
        this.evento = evento;
        this.indice = indice;
        this.note = note;
        this.nome_centro = nome_centro;
    }

    /**
     * restituisce il nome dell' evento
     *
     * @return
     */
    public String getEvento() {
        return evento;
    }

    /**
     * assegna un valore a evento
     *
     * @param evento
     */
    public void setEvento(String evento) {
        this.evento = evento;
    }

    /**
     * restituisce l'indice di gravità
     *
     * @return
     */
    public int getIndice() {
        return indice;
    }

    /**
     * assegna un valore a indice
     *
     * @param indice
     */
    public void setIndice(int indice) {
        this.indice = indice;
    }

    /**
     * restituisce le note opzionali
     *
     * @return
     */
    public String getNote() {
        return note;
    }

    /**
     * assegna un valore a note
     *
     * @param note
     */
    public void setNote(String note) {
        this.note = note;
    }

    /**
     * restituisce il nome del centro
     *
     * @return
     */
    public String getNome_centro() {
        return nome_centro;
    }

    /**
     * assegna un valore a nome_centro
     *
     * @param nome_centro
     */
    public void setNome_centro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * converte un oggetto in stringa
     *
     * @author daniele Caspani
     */

    @Override
    public String toString() {
        return evento + "," + indice + "," + note + "," + nome_centro;
    }
}