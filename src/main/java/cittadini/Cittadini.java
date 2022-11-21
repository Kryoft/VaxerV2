/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * La classe Cittadini comprende le informazioni relative ai cittadini registrati all'applicazione.
 *
 * @author Daniele Caspani
 */
public class Cittadini {

    private String email;
    private Login login;
    private String nome_centro;
    private short id;
    private String nome;
    private String cognome;
    private String codice_fiscale;

    public Cittadini() {
    }

    public Cittadini(String email, Login login, String nome_centro, short id, String nome, String cognome, String codice_fiscale) {
        this.email = email;
        this.nome_centro = nome_centro;
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codice_fiscale = codice_fiscale;
        this.login = login;
    }

    public Cittadini(String nome_centro, short id, String nome, String cognome, String codice_fiscale) {
        this.nome_centro = nome_centro;
        this.id = id;
        this.nome = nome;
        this.cognome = cognome;
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * Restituisce la mail
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * Assegna un valore a email
     *
     * @param email email del cittadino
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Restituisce il nome del centro.
     *
     * @return
     */
    public String getNomeCentro() {
        return nome_centro;
    }

    /**
     * Assegna un valore a nome del centro
     *
     * @param nome_centro Nome del centro vaccinale
     */
    public void setNomeCentro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * Restituisce il valore di id
     *
     * @return
     */
    public short getId() {
        return id;
    }

    /**
     * Assegna un valore a id
     *
     * @author Daniele Caspani
     */
    public void setId(short id) {
        this.id = id;
    }

    /**
     * restituisce il valore di nome
     *
     * @return
     */
    public String getNome() {
        return nome;
    }

    /**
     * Assegna un valore a nome.
     *
     * @param nome Nome del cittadino
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Restituisce il valore di cognome
     *
     * @return
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Assegna un valore a cognome
     *
     * @param cognome Cognome del cittadino
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * Restituisce il codice fiscale
     *
     * @return
     */
    public String getCodiceFiscale() {
        return codice_fiscale;
    }


    /**
     * Assegna un valore a codice fiscale
     *
     * @param codice_fiscale codice fiscale
     */
    public void setCodiceFiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * Restituisce il valore di login di tipo Login
     *
     * @return
     * @see Login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * Assegna un valore a login
     *
     * @param login
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * Converte un oggetto in stringa
     *
     * @return una stringa composta dai dati memorizzati in questo oggetto
     * @author Daniele Caspani
     */
    @Override
    public String toString() {
        return id + "," + email + "," + login + "," + nome_centro + "," + nome + "," + cognome + "," + codice_fiscale;
    }

    /**
     * Metodo che controlla la formattazione del codice fiscale servendosi di un <code>Pattern</code> preimpostato;
     *
     * @param codice_fiscale codice fiscale
     * @return vero se la formattazione è corretta
     * @author Daniele Caspani
     * @see Pattern
     * @see Matcher
     */
    public boolean controllaCodiceFiscale(String codice_fiscale) {
        Pattern pattern = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]");
        Matcher matcher = pattern.matcher(codice_fiscale);
        boolean matchFound = matcher.matches();
        return matchFound;
    }

    /**
     * Metodo che controlla la formattazione della mail servendosi di un <code>Pattern</code> preimpostato;
     * Il pattern è un'espressione regolare fornita dall'OWASP Validation Regex Repository.
     * Per ulteriori informazioni consultare <a href="https://owasp.org/www-community/OWASP_Validation_Regex_Repository">questo link</a>.
     *
     * @param email email per la registrazione
     * @return vero se la formattazione è corretta
     * @author Daniele Caspani, Cristian Corti
     * @see Pattern
     * @see Matcher
     */
    public final boolean mailSyntaxCheck(String email) {
        // https://www.baeldung.com/java-email-validation-regex Ho usato il pattern #9
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.matches();
        return matchFound;
    }
}