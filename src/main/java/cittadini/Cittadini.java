/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * La classe comprende le informazioni relative ai cittadini registrati all'applicazione.
 *
 * @author daniele Caspani
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
     * restituisce la mail
     *
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     * assegna un valore a email
     *
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * restituisce il nome del centro.
     */
    public String getNomeCentro() {
        return nome_centro;
    }

    /**
     * assegna un valore a nome del centro
     *
     * @param nome_centro
     */
    public void setNomeCentro(String nome_centro) {
        this.nome_centro = nome_centro;
    }

    /**
     * restituisce il valore di id
     *
     * @return
     */
    public short getId() {
        return id;
    }

    /**
     * assegna un valore a id
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
     * assegna un valore a nome.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * restituisce il valore di cognome
     *
     * @return
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * assegna un valore a cognome
     *
     * @param cognome
     */
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    /**
     * restituisce il codice fiscale
     *
     * @return
     */
    public String getCodiceFiscale() {
        return codice_fiscale;
    }


    /**
     * assegna un valore a codice fiscale
     *
     * @param codice_fiscale
     */
    public void setCodiceFiscale(String codice_fiscale) {
        this.codice_fiscale = codice_fiscale;
    }

    /**
     * restituisce il valore di login di tipo Login
     *
     * @return
     * @see Login
     */
    public Login getLogin() {
        return login;
    }

    /**
     * assegna un valore a login
     *
     * @param login
     */
    public void setLogin(Login login) {
        this.login = login;
    }

    /**
     * converte un oggetto in stringa
     *
     * @return
     * @author daniele Caspani
     */
    @Override
    public String toString() {
        return id + "," + email + "," + login + "," + nome_centro + "," + nome + "," + cognome + "," + codice_fiscale;
    }

    /**
     * metodo che controlla la formattazione del codice fiscale servendosi di un <code>Pattern</code> preimpostato;
     *
     * @param codice_fiscale codice fiscale
     * @return restituisce vero se la formattazione è corretta
     * @author Daniele Caspani
     * @see Pattern
     */
    public boolean controllaCodiceFiscale(String codice_fiscale) {
        Pattern pattern = Pattern.compile("[A-Z]{6}[0-9]{2}[A-Z][0-9]{2}[A-Z][0-9]{3}[A-Z]");
        Matcher matcher = pattern.matcher(codice_fiscale);
        boolean matchFound = matcher.matches();
        return matchFound;
    }

    /**
     * metodo che controlla la formattazione della mail servendosi di un <code>Pattern</code> preimpostato;
     *
     * @param email email per la registrazione
     * @return restituisce vero se la formattazione è corretta
     * @author Daniele Caspani
     * @see Pattern
     */
    public final boolean mailSyntaxCheck(String email) {
        Pattern pattern = Pattern.compile(".+@.+\\.[a-z]+");
        Matcher matcher = pattern.matcher(email);
        boolean matchFound = matcher.matches();
//        String[] string_array = email.split("\\.");
//        String token = null;
//        for (String s : string_array)
//            token = s;
        StringTokenizer st = new StringTokenizer(email, ".");
        String lastToken = null;
        while (st.hasMoreTokens()) {
            lastToken = st.nextToken();
        }

        return matchFound && lastToken.length() >= 2
                && email.length() - 1 != lastToken.length();
    }
}