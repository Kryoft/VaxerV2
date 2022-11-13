/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

/**
 * Si tratta della classe che memorizza tutte le informazioni relative ad un centro vaccinale:
 * il nome del centro, la <code> tipologia(tipo enumerativo con come valori (ospedaliero, aziendale, hub) </code>, l'oggetto di tipo <code>Indirizzo_composto,</code>.
 *
 * @author danie
 */
public class StruttureVaccinali {

    private String nome_centro;
    private Tipologia tipologia;
    private IndirizzoComposto Indirizzo;
    public StruttureVaccinali() {

    }
    public StruttureVaccinali(String nome_centro, Tipologia tipologia, IndirizzoComposto Indirizzo) {
        this.nome_centro = nome_centro;
        this.tipologia = tipologia;
        this.Indirizzo = Indirizzo;
    }

    /**
     * restituisce il valore di <code> nome_centro </code>
     *
     * @return
     * @author daniele Caspani
     */
    public String getNome_centro() {
        return nome_centro;
    }

    /**
     * assegna un valore a <code> nome_centro </code>
     *
     * @param nome_centro il nome del centro di tipo String
     * @author daniele Caspani
     */
    public void setNome_centro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * restituisce il valore di <code> tipologia </code> di tipo <code> Tipologia </code>
     *
     * @return
     * @author daniele Caspani
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * assegna un valore a <code> tipologia </code>
     *
     * @param tipologia la tipologia del centro vaccinale
     * @author daniele Caspani
     */
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * restituisce il valore di <code> Indirizzo </code> di tipo <code> Indirizzo_composto </code>
     *
     * @return
     * @author daniele Caspani
     */
    public IndirizzoComposto getIndirizzo() {
        return Indirizzo;
    }

    /**
     * assegna un valore a <code> Indirizzo_composto </code>
     *
     * @param Indirizzo l' oggetto di tipo indirizzo della classe <code> Indirizzo </code>
     * @author daniele Caspani
     */
    public void setIndirizzo(IndirizzoComposto Indirizzo) {
        this.Indirizzo = Indirizzo;

    }

    /**
     * converte un oggetto in stringa
     *
     * @return
     * @author daniele Caspani
     */
    @Override
    public String toString() {
        return nome_centro + "," + tipologia + "," + Indirizzo;
    }

    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell' attributo <code> tipologia </code> (AZIENDALE,HUB,OSPEDALIERO)
     *
     * @author daniele Caspani
     */
    public enum Tipologia {OSPEDALIERO, AZIENDALE, HUB}
}