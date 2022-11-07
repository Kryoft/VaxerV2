/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package centrivaccinali;

/**
 * Si tratta della classe che memorizza tutte le informazioni relative ad un centro vaccinale:  
 *  il nome del centro, la <code> tipologia(tipo enumerativo con come valori (ospedaliero, aziendale, hub) </code>, l'oggetto di tipo <code>Indirizzo_composto,</code>.
 * @author danie
 */

public class StruttureVaccinali  {

    
    public StruttureVaccinali() {
        
    }

    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell' attributo <code> tipologia </code> (AZIENDALE,HUB,OSPEDALIERO)
     * @author daniele Caspani
     */
    public enum Tipologia {OSPEDALIERO,AZIENDALE,HUB}
    private String nome_centro;
    private Tipologia tipologia;
    private Indirizzo_composto Indirizzo;
   

    public StruttureVaccinali(String nome_centro, Tipologia tipologia, Indirizzo_composto Indirizzo) {
        this.nome_centro = nome_centro;
        this.tipologia = tipologia;
        this.Indirizzo = Indirizzo;
        
    }

     /**
     * restituisce il valore di <code> nome_centro </code>
     * @author daniele Caspani
     * @return  
     */
    
    public String getNome_centro() {
        return nome_centro;
    }
   /**
     * assegna un valore a <code> nome_centro </code>
     * @author daniele Caspani
     * @param nome_centro il nome del centro di tipo String
     */
    public void setNome_centro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

     /**
     * restituisce il valore di <code> tipologia </code> di tipo <code> Tipologia </code>
     * @author daniele Caspani
     * @return 
     */
    public Tipologia getTipologia() {
        return tipologia;
    }

    /**
     * assegna un valore a <code> tipologia </code>
     * @author daniele Caspani
     * @param tipologia la tipologia del centro vaccinale
     */
    public void setTipologia(Tipologia tipologia) {
        this.tipologia = tipologia;
    }

     /**
     * restituisce il valore di <code> Indirizzo </code> di tipo <code> Indirizzo_composto </code>
     * @author daniele Caspani
     * @return 
     */
    
    public Indirizzo_composto getIndirizzo() {
        return Indirizzo;
    }
    
    /**
     * assegna un valore a <code> Indirizzo_composto </code>
     * @author daniele Caspani
     * @param Indirizzo l' oggetto di tipo indirizzo della classe <code> Indirizzo </code>
     */
    
    public void setIndirizzo(Indirizzo_composto Indirizzo) {
        this.Indirizzo = Indirizzo;
        
    }
    
    
    /**
     * converte un oggetto in stringa
     * @author daniele Caspani
     * @return 
     */
    
    @Override
    public String toString() {
        return  nome_centro + "," + tipologia + "," + Indirizzo;
    }
        
}

    
   
    
    
    