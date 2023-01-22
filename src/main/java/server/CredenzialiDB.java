package server;

/**
 * Classe astratta utile a memorizzare username e password per l'accesso al Database
 */
public abstract class CredenzialiDB {

    /**
     * Username utilizzato per accedere al database
     */
    private static String user = "";

    /**
     * Password del database
     */
    private static String password = "";

    /**
     * Array di stringhe utilizzato a contenere gli errori
     */
    public static String[] message = new String[2];

    /**
     * Restituisce la password del database
     *
     * @return la password del database
     */
    public static String getPassword() {
        return password;
    }

    /**
     * Metodo che imposta un messaggio d'errore in un array di stringhe
     *
     * @param indice l'indice dell'array di cui si vuole modificare il messaggio
     * @param valore il messaggio d'errore
     *
     * @return <code>true</code> o <code>false</code> rispettivamente nel caso in cui il messaggio
     * è stato impostato oppure no. Il messaggio non viene impostato (e quindi questo metodo restituisce
     * <code>false</code>) nel caso l'indice specificato come parametro sia < 0 o maggiore dei
     * valori indice validi per l'array di stringhe.
     */
    public static boolean setMessage(int indice, String valore) {
        if (indice >= 0 && indice < message.length) {
            message[indice] = valore;
            return true;
        }
        return false;
    }

    /**
     * Permette di settare la password
     *
     * @param password la password da impostare
     */
    public static void setPassword(String password) {
        CredenzialiDB.password = password;
    }

    /**
     * Restituisce lo username inserito
     *
     * @return lo username memorizzato da questo oggetto
     */
    public static String getUser() {
        return user;
    }

    /**
     * Permette di settare il valore dello username
     *
     * @param user lo username desiderato
     */

    public static void setUser(String user) {
        CredenzialiDB.user = user;
    }

    /**
     * Metodo per verificare se i campi (user e password) siano effettivamente stati riempiti
     *
     * @return <code>false</code> nel caso almeno uno dei campi (user o password) sia vuoto, <code>true</code> altrimenti
     */
    public static boolean isFull() {
        setMessage(0, "");
        setMessage(1, "");

        boolean b = true;
        if (user.isBlank()) {
            setMessage(0, "Campo username vuoto");
            b = false;
        }

        if (password.isBlank()) {
            setMessage(1, "Campo password vuoto");
            b = false;
        }
        return b;
    }

    /**
     * Metodo utilizzato per verificare se non ci sono errori relativi alla conferma della password
     *
     * @param password la password scritta nel campo di testo relativo alla password
     * @param conferma_password la password scritta nel campo di testo relativo alla conferma della password
     * @return <code>true</code> nel caso tutti i campi siano stati riempiti correttamente e le due
     * password coincidano, <code>false</code> altrimenti
     */
    public static boolean isValid(String password, String conferma_password) {
        boolean b = false;

        if (isFull()) {
            b = true;
            setMessage(0, "");
            setMessage(1, "");
            if (!password.equals(CredenzialiDB.password)) {
                setMessage(0, "Password incorretta");
                b = false;
            } else if (!password.equals(conferma_password)) {
                setMessage(1, "Le due password non coincidono");
                b = false;
            }
        }

        return b;
    }

}
