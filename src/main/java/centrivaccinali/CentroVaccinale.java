/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

/**
 * Si tratta della classe che memorizza tutte le informazioni relative a un centro vaccinale:
 * il nome del centro, la <code>tipologia</code> (tipo enumerativo con come valori (ospedaliero, aziendale, hub)
 * e l'oggetto di tipo <code>IndirizzoComposto</code>.
 *
 * @author Daniele Caspani
 */
public class CentroVaccinale {

    private String nome_centro;
    private Tipologia tipologia;
    private IndirizzoComposto indirizzo;

    public CentroVaccinale() {
    }

    public CentroVaccinale(String nome_centro, Tipologia tipologia, IndirizzoComposto indirizzo) {
        this.nome_centro = nome_centro;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
    }

    /**
     * Restituisce il valore di <code>nome_centro</code>
     *
     * @return il nome del centro vaccinale
     * @author Daniele Caspani
     */
    public String getNomeCentro() {
        return nome_centro;
    }

    /**
     * Assegna un valore a <code>nome_centro</code>
     *
     * @param nome_centro il nome del centro di tipo String
     * @author Daniele Caspani
     */
    public void setNomeCentro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * Restituisce il valore di <code>tipologia</code> di tipo <code>Tipologia</code>
     *
     * @return la tipologia del centro vaccinale (ospedaliero, aziendale, hub)
     * @author Daniele Caspani
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * Assegna un valore a <code>tipologia</code>
     *
     * @param tipologia la tipologia del centro vaccinale
     * @author Daniele Caspani
     */
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * Restituisce il valore di <code>Indirizzo</code> di tipo <code>IndirizzoComposto</code>
     *
     * @return l'indirizzo del centro vaccinale
     * @author Daniele Caspani
     */
    public IndirizzoComposto getIndirizzo() {
        return indirizzo;
    }

    /**
     * Assegna un valore a <code>indirizzo</code>
     *
     * @param indirizzo l'indirizzo del centro vaccinale
     * @author Daniele Caspani
     */
    public void setIndirizzo(IndirizzoComposto indirizzo) {
        this.indirizzo = indirizzo;

    }

    /**
     * Converte un oggetto in stringa
     *
     * @return
     * @author daniele Caspani
     */
    @Override
    public String toString() {
        return nome_centro + "," + tipologia + "," + indirizzo;
    }

    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell'attributo <code>tipologia</code> (AZIENDALE,HUB,OSPEDALIERO)
     *
     * @author Daniele Caspani
     */
    public enum Tipologia {OSPEDALIERO, AZIENDALE, HUB}
}