/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/

package centrivaccinali;


/**
 * La classe <strong> Indirizzo_Composto </strong> istanzia un oggetto contenente un dato indirizzo.
 * @author daniele Caspani
 */


public class Indirizzo_composto {
    
    /**
     * Utilizzata per dare tre opzioni di scelta nell'inserimento dell' attributo <code> Qualificatore </code> (VIA,VIALE,PIAZZA)
     * @author daniele Caspani
     */
    public enum Qualificatore {VIA,VIALE,PIAZZA}
    private Qualificatore qualificatore;
    private String nome_via;
    private int num_civico;
    private String comune;
    private String sigla_provincia;
    private String cap;
    

    public Indirizzo_composto(Qualificatore qualificatore, String nome_via, int num_civico, String comune, String sigla_provincia, String cap) {
        this.qualificatore = qualificatore;
        this.nome_via = nome_via;
        this.num_civico = num_civico;
        this.comune = comune;
        this.sigla_provincia = sigla_provincia;
        this.cap = cap;
    }
    
     /**
     * restituisce il valore di <code> Qualificatore </code>
     * @author daniele Caspani
     * @return 
     */
    
    public Qualificatore getQualificatore() {
        return qualificatore;
    }

     /**
     * assegna un valore a <code> Qualificatore </code>
     * @author daniele Caspani
     * @param qualificatore
     */
    
    public void setQualificatore(Qualificatore qualificatore) {
        this.qualificatore = qualificatore;
    }
    
    /**
     * restituisce il valore di <code> nome_via </code>
     * @author daniele Caspani
     */
    
    public String getNome_via() {
        return nome_via;
    }

     /**
     * assegna un valore a <code> nome_via </code>
     * @author daniele Caspani
     * @param nome_via
     */
    
    public void setNome_via(String nome_via) {
        this.nome_via = nome_via;
    }

    
    /**
     * restituisce il valore di <code> num_civico </code>
     * @author daniele Caspani
     */
    
    public int getNum_civico() {
        return num_civico;
    }
    
    /**
     * assegna un valore a <code> num_civico </code>
     * @author daniele Caspani
     * @param num_civico il numero civico della via 
     */
    
    public void setNum_civico(int num_civico) {
        this.num_civico = num_civico;
    }

    /**
     * restituisce il valore di <code> comune </code>
     * @author daniele Caspani
     * @return 
     */
    
    public String getComune() {
        return comune;
    }

    /**
     * assegna un valore a <code> comune </code>
     * @author daniele Caspani il comune dell' indirizzo
     * @param comune
     */
    
    public void setComune(String comune) {
        this.comune = comune;
    }

    /**
     * restituisce il valore di <code> sigla_provincia </code>
     * @author daniele Caspani
     * @return 
     */
    
    public String getSigla_provincia() {
        return sigla_provincia;
    }

    /**
     * assegna un valore a <code> sigla_provincia </code>
     * @author daniele Caspani
     * @param sigla_provincia la sigla della provincia
     */
    
    public void setSigla_provincia(String sigla_provincia) {
        this.sigla_provincia = sigla_provincia;
    }

    /**
     * restituisce il valore di <code> cap </code>
     * @author daniele Caspani
     * @return 
     */
    
    public String getCap() {
        return cap;
    }

    /**
     * assegna un valore a <code> cap </code>
     * @author daniele Caspani
     * @param cap il cap del comune dell'indirizzo implementato
     */
    
    public void setCap(String cap) {
        this.cap = cap;
    }
    
    
    /**
     * converte un oggetto in stringa
     * @author daniele Caspani
     * @return 
     */
    
    @Override
    public String toString(){
        
        return qualificatore + "," + nome_via + "," + num_civico + "," + comune + "," + sigla_provincia + "," + cap;
        
    }

    /**
     * metodo utilizzato per verificare se <code> cap </code> è stato inserito nella maniera corretta(5 caretteri numerici)
     * @param cap contiene il valore effettivo del cap da inserire(tipo String)
     * @author Daniele Caspani
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata 
     */
    public boolean controllacap(String cap) {
        if(cap.length()==5)
        {
            for(int i=0; i<5;i++){
                if(cap.charAt(i) > '9' || cap.charAt(i) < '0'){
                    return false;
                }  
            }
        
            return true;
        }
        else
            return false;
    }
    
     /**
     * metodo utilizzato per verificare se <code> sigla </code> è stata inserita nella maniera corretta(2 caratteri letterali)
     * @param sigla contiene il valore effettivo della sigla della provincia da inserire(tipo String)
     * @author Daniele Caspani
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata
     */
    
    public boolean controllasigla(String sigla){
        
        if(sigla.length()==2)
        {
            for(int i=0; i<2;i++){
                if(sigla.charAt(i) > 'Z' || sigla.charAt(i) < 'A')
                    return false;
            }
        
            return true;
        }
        else
            return false;
    }
    
    /**
     * metodo utilizzato per verificare se <code> comune </code> è stato inserito nella maniera corretta(solo caratteri letterali)
     * @param comune contiene il valore effettivo del comune da inserire(tipo String)
     * @author Daniele Caspani
     * @return valore booleano vero solo nel caso in cui la formattazione viene rispettata
     */
    
    public boolean controllacomune(String comune){
        for(int i=0; i< comune.length();i++){
            
            if(comune.charAt(i) > 'Z' || comune.charAt(i) < 'A')
                return false;
        }
        
        return true;
    }
    
}