/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


/**
 * La classe <strong>IndirizzoComposto</strong> istanzia un oggetto contenente un dato indirizzo.
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
     * Restituisce il valore di <code>Qualificatore</code>
     *
     * @return un oggetto di tipo Qualificatore con all'interno il qualificatore di questo Indirizzo
     * @author Daniele Caspani
     */
    public Qualificatore getQualificatore() {
        return qualificatore;
    }

    /**
     * assegna un valore a <code>Qualificatore</code>
     *
     * @param qualificatore il qualificatore dell'indirizzo a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setQualificatore(Qualificatore qualificatore) {
        this.qualificatore = qualificatore;
    }

    /**
     * Restituisce il valore di <code>nome_via</code>
     *
     * @author Daniele Caspani
     */
    public String getNomeVia() {
        return nome_via;
    }

    /**
     * Assegna un valore a <code>nome_via</code>
     *
     * @param nome_via il nome della via a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setNomeVia(String nome_via) {
        this.nome_via = nome_via;
    }

    /**
     * Restituisce il valore di <code>num_civico</code>
     *
     * @author Daniele Caspani
     */
    public int getNumCivico() {
        return num_civico;
    }

    /**
     * Assegna un valore a <code>num_civico</code>
     *
     * @param num_civico il numero civico della via a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setNumCivico(int num_civico) {
        this.num_civico = num_civico;
    }

    /**
     * Restituisce il valore di <code>comune</code>
     *
     * @return Una stringa contenente il valore del comune di questo Indirizzo
     * @author Daniele Caspani
     */
    public String getComune() {
        return comune;
    }

    /**
     * Assegna un valore a <code>comune</code>
     *
     * @param comune il comune della via a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * Restituisce il valore di <code>sigla_provincia</code>
     *
     * @return Una stringa contenente il valore della sigla della provincia di questo Indirizzo
     * @author Daniele Caspani
     */
    public String getSiglaProvincia() {
        return sigla_provincia;
    }

    /**
     * Assegna un valore a <code>sigla_provincia</code>
     *
     * @param sigla_provincia la sigla della provincia della via a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setSiglaProvincia(String sigla_provincia) {
        this.sigla_provincia = sigla_provincia;
    }

    /**
     * Restituisce il valore di <code>cap</code>
     *
     * @return Una stringa contenente il valore del CAP di questo Indirizzo
     * @author Daniele Caspani
     */
    public String getCap() {
        return cap;
    }

    /**
     * Assegna un valore a <code>cap</code>
     *
     * @param cap il CAP del comune della via a cui si fa riferimento
     * @author Daniele Caspani
     */
    public void setCap(String cap) {
        this.cap = cap;
    }

    /**
     * Converte un oggetto in stringa
     *
     * @return
     * @author Daniele Caspani
     */
    @Override
    public String toString() {
        return qualificatore + "," + nome_via + "," + num_civico + "," + comune + "," + sigla_provincia + "," + cap;
    }

    /**
     * Metodo utilizzato per verificare se <code>cap</code> è stato inserito nella maniera corretta (5 caretteri numerici)
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

    public boolean controllaNumeroCivico(int num_civico){
        return num_civico > 0;
    }

    /**
     * Metodo utilizzato per verificare se <code>sigla</code> è stata inserita nella maniera corretta (2 caratteri letterali)
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
     * Metodo utilizzato per verificare se <code>comune</code> è stato inserito nella maniera corretta (solo caratteri letterali)
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
     * @author Daniele Caspani
     */
    public enum Qualificatore {VIA, VIALE, PIAZZA}

}