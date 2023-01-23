/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.centrivaccinali;

/**
 * Si tratta della classe che memorizza tutte le informazioni relative a un centro vaccinale:
 * il nome del centro, la tipologia (tipo enumerativo con valori quali: ospedaliero, aziendale, hub)
 * e il suo indirizzo (in un oggetto di tipo <code>IndirizzoComposto</code>).
 *
 * @author Daniele Caspani
 */
public class CentroVaccinale {

    private String nome_centro;
    private Tipologia tipologia;
    private IndirizzoComposto indirizzo;

    public CentroVaccinale() {
    }

    /**
     * Crea un oggetto di tipo <code>CentroVaccinale</code> utilizzando i parametri specificati
     *
     * @param nome_centro Il nome del centro sotto forma di stringa
     * @param tipologia Un tipo enumerativo che rappresenta la tipologia del centro: ospedaliero, aziendale o hub
     * @param indirizzo L'indirizzo geografico del centro vaccinale
     * @author Daniele Caspani
     */
    public CentroVaccinale(String nome_centro, Tipologia tipologia, IndirizzoComposto indirizzo) {
        this.nome_centro = nome_centro;
        this.tipologia = tipologia;
        this.indirizzo = indirizzo;
    }

    /**
     * Restituisce il nome di questo centro vaccinale
     *
     * @return il nome del centro vaccinale
     * @author Daniele Caspani
     */
    public String getNomeCentro() {
        return nome_centro;
    }

    /**
     * Assegna un valore al nome di questo centro vaccinale
     *
     * @param nome_centro il nome del centro di tipo String
     * @author Daniele Caspani
     */
    public void setNomeCentro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * Restituisce la tipologia di questo centro (ospedaliero, aziendale o hub), di tipo <code>Tipologia</code>
     *
     * @return la tipologia del centro vaccinale (ospedaliero, aziendale, hub)
     * @author Daniele Caspani
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * Assegna un valore alla tipologia di questo centro vaccinale
     *
     * @param tipologia la tipologia del centro vaccinale
     * @author Daniele Caspani
     */
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

    /**
     * Restituisce l'indirizzo geografico di questo centro vaccinale
     *
     * @return l'indirizzo del centro vaccinale
     * @author Daniele Caspani
     */
    public IndirizzoComposto getIndirizzo() {
        return indirizzo;
    }

    /**
     * Assegna un valore all'indirizzo geografico di questo centro vaccinale
     *
     * @param indirizzo l'indirizzo del centro vaccinale
     * @author Daniele Caspani
     */
    public void setIndirizzo(IndirizzoComposto indirizzo) {
        this.indirizzo = indirizzo;

    }

    /**
     * Produce e restituisce una stringa contenente i dati di questo centro vaccinale
     *
     * @return una stringa contenente i dati di questo centro vaccinale nella forma: "nome_del_centro,tipologia,indirizzo"
     * @author Daniele Caspani
     */
    @Override
    public String toString() {
        return nome_centro + "," + tipologia + "," + indirizzo;
    }

    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell'attributo
     * <code>tipologia</code> (AZIENDALE,HUB,OSPEDALIERO)
     *
     * @author Daniele Caspani
     */
    public enum Tipologia {OSPEDALIERO, AZIENDALE, HUB}
}