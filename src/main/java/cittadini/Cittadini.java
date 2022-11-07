/*
    * Daniele Caspani - Matricola n. 744628 - CO
    * Tommaso Valente - Matricola n.744505 - CO
*/
package cittadini;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *la classe comprende le informazioni relative ai cittadini registrati all'applicazione.
 * @author daniele Caspani
 */
public class Cittadini {
     

    private String email;
    private Login login;
    private String nome_centro;
    private short id;
    private String nome;
    private String cognome;
    private String codicefiscale;

    public Cittadini() {
    }
    

    public Cittadini(String email,Login login, String nome_centro, short id, String nome, String cognome, String codicefiscale) {
        this.email = email;
        this.nome_centro = nome_centro;
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codicefiscale = codicefiscale;
        this.login=login;
    }

    public Cittadini(String nome_centro, short id, String nome, String cognome, String codicefiscale) {
        this.nome_centro = nome_centro;
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codicefiscale = codicefiscale;
    }
    
    /**
     * restituisce la mail
     * @return 
     */

    public String getEmail() {
        return email;
    }
    
    /**
     * assegna un valore a email
     * @param email 
     */

    public void setEmail(String email) {
        this.email = email;
    }
    
    /**
     * restituisce il nome del centro.
     */

    public String getNome_centro() {
        return nome_centro;
    }
    
    /**
     * assegna un valore a nome del centro
     * @param nome_centro 
     */

    public void setNome_centro(String nome_centro) {
        this.nome_centro = nome_centro;
    }
    
    /**
     * restituisce il valore di id
     * @return 
     */

    public short getId() {
        return id;
    }
    
    /**
     * assegna un valore a id
     * @author Daniele Caspani
     */

    public void setId(short id) {
        this.id = id;
    }
    
    /**
     * restituisce il valore di nome
     * @return 
     */

    public String getNome() {
        return nome;
    }
    
    /**
     * assegna un valore a nome.
    */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * restituisce il valore di cognome
     * @return 
     */
    public String getCognome() {
        return cognome;
    }
    
    /**
     * assegna un valore a cognome
     * @param cognome 
     */

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }
    
    /**
     * restituisce il codice fiscale
     * @return 
     */

    public String getCodicefiscale() {
        return codicefiscale;
    }

    
    /**
     * assegna un valore a codice fiscale
     * @param codicefiscale 
     */
    
    public void setCodicefiscale(String codicefiscale) {
        this.codicefiscale = codicefiscale;
    }
    
    /**
     * restituisce il valore di login di tipo Login
     * @return 
     * @see Login
     */

    public Login getLogin() {
        return login;
    }
    
    /**
     * assegna un valore a login
     * @param login 
     */

    public void setLogin(Login login) {
        this.login = login;
    }
    
    /**
     * converte un oggetto in stringa
     * @author daniele Caspani
     * @return 
     */
    
    @Override
    public String toString() {
        return  id + "," + email + "," + login + "," + nome_centro + "," + nome + "," + cognome + "," + codicefiscale;
    }
    
    /** metodo che controlla la formattazione del codice fiscale servendosi di un <code> Pattern </code> preimpostato; 
 * @author Daniele Caspani
     * @param cf codice fiscale
     * @return restituisce vero se la formattazione è corretta
     * @see Pattern
 */
    
    public boolean controllacf(String cf){
         Pattern p = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]");
         Matcher m = p.matcher(cf);
         boolean matchFound = m.matches();
         if(matchFound)
             return true;
         else            
            return false;
        
    }
   
    
    /** metodo che controlla la formattazione della mail servendosi di un <code> Pattern </code> preimpostato; 
 * @author Daniele Caspani
     * @param email email per la registrazione
     * @return restirtuisce vero se la formattazione è corretta 
     * @see Pattern
 */
    public final boolean mailSyntaxCheck(String email)
   {
        
        Pattern p = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher m = p.matcher(email);
        boolean matchFound = m.matches();
        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }
 
        if (matchFound && lastToken.length() >= 2
            && email.length() - 1 != lastToken.length()) {
 
            return true;
        } else {
            return false;
        }
 
    }

    
}
