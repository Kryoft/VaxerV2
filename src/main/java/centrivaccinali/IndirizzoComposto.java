/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


/**
 * La classe <strong>Indirizzo_Composto</strong> istanzia un oggetto contenente un dato indirizzo.
 *
 * @author daniele Caspani
 */
public class IndirizzoComposto {

    private Qualificatore qualificatore;
    private String nome_via;
    private int num_civico;
    private String comune;
    private String sigla_provincia;
    private String cap;

    public IndirizzoComposto(Qualificatore qualificatore, String nome_via, int num_civico, String comune, String sigla_provincia, String cap) {
        this.qualificatore = qualificatore;
        this.nome_via = nome_via;
        this.num_civico = num_civico;
        this.comune = comune;
        this.sigla_provincia = sigla_provincia;
        this.cap = cap;
    }

    /**
     * restituisce il valore di <code>Qualificatore</code>
     *
     * @return
     * @author daniele Caspani
     */
    public Qualificatore getQualificatore() {
        return qualificatore;
    }

    /**
     * assegna un valore a <code>Qualificatore</code>
     *
     * @param qualificatore
     * @author daniele Caspani
     */
    public void setQualificatore(Qualificatore qualificatore) {
        this.qualificatore = qualificatore;
    }

    /**
     * restituisce il valore di <code>nome_via</code>
     *
     * @author daniele Caspani
     */
    public String getNomeVia() {
        return nome_via;
    }

    /**
     * assegna un valore a <code>nome_via</code>
     *
     * @param nome_via
     * @author daniele Caspani
     */
    public void setNomeVia(String nome_via) {
        this.nome_via = nome_via;
    }

    /**
     * restituisce il valore di <code>num_civico</code>
     *
     * @author daniele Caspani
     */
    public int getNumCivico() {
        return num_civico;
    }

    /**
     * assegna un valore a <code>num_civico</code>
     *
     * @param num_civico il numero civico della via
     * @author daniele Caspani
     */
    public void setNumCivico(int num_civico) {
        this.num_civico = num_civico;
    }

    /**
     * restituisce il valore di <code>comune</code>
     *
     * @return
     * @author daniele Caspani
     */
    public String getComune() {
        return comune;
    }

    /**
     * assegna un valore a <code>comune</code>
     *
     * @param comune
     * @author daniele Caspani il comune dell' indirizzo
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * restituisce il valore di <code>sigla_provincia</code>
     *
     * @return
     * @author daniele Caspani
     */
    public String getSiglaProvincia() {
        return sigla_provincia;
    }

    /**
     * assegna un valore a <code>sigla_provincia</code>
     *
     * @param sigla_provincia la sigla della provincia
     * @author daniele Caspani
     */
    public void setSiglaProvincia(String sigla_provincia) {
        this.sigla_provincia = sigla_provincia;
    }

    /**
     * restituisce il valore di <code>cap</code>
     *
     * @return
     * @author daniele Caspani
     */
    public String getCap() {
        return cap;
    }

    /**
     * assegna un valore a <code>cap</code>
     *
     * @param cap il cap del comune dell'indirizzo implementato
     * @author daniele Caspani
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    /**
     * converte un oggetto in stringa
     *
     * @return
     * @author daniele Caspani
     */
    @Override
    public String toString() {
        return qualificatore + "," + nome_via + "," + num_civico + "," + comune + "," + sigla_provincia + "," + cap;
    }

    /**
     * metodo utilizzato per verificare se <code>cap</code> è stato inserito nella maniera corretta (5 caretteri numerici)
     *
     * @param cap contiene il valore effettivo del cap da inserire (tipo String)
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata
     * @author Daniele Caspani
     */
    public boolean controllaCap(String cap) {
        if (cap.length() == 5) {
            for (char n : cap.toCharArray()) {
                // se un carattere del CAP non è un numero, restituisce false
                if (!Character.isDigit(n))
                    return false;
            }
            return true;
        } else
            return false;
    }

    public boolean controllaNumeroCivico(int numcivico){
        return numcivico > 0;
    }

    /**
     * metodo utilizzato per verificare se <code>sigla</code> è stata inserita nella maniera corretta (2 caratteri letterali)
     *
     * @param sigla contiene il valore effettivo della sigla della provincia da inserire (tipo String)
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata
     * @author Daniele Caspani
     */
    public boolean controllaSigla(String sigla) {
        if (sigla.length() == 2) {
            for (char c: sigla.toCharArray()) {
                if (Character.isDigit(c))
                    return false;
            }
            return true;
        } else
            return false;
    }

    /**
     * metodo utilizzato per verificare se <code>comune</code> è stato inserito nella maniera corretta (solo caratteri letterali)
     *
     * @param comune contiene il valore effettivo del comune da inserire (tipo String)
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata
     * @author Daniele Caspani
     */
    public boolean controllaComune(String comune) {
        for (char c: comune.toCharArray()) {
            if (Character.isDigit(c))
                return false;
        }
        return true;
    }

    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell'attributo <code>Qualificatore</code> (VIA,VIALE,PIAZZA)
     *
     * @author daniele Caspani
     */
    public enum Qualificatore {VIA, VIALE, PIAZZA}

}