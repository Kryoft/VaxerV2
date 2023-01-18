package server;

/**
 * Si tratta di una classe utile per memorizzare user e password del db
 */

public class CredenzialiDB {

    /**
     * user del database
     */
    private static String user = "";

    /**
     * password del database
     */
    private static String password ="";

    /**
     * restituisce password
     * @return password
     */

    private String[] messaggi;
    public static String getPassword() {
        return password;
    }

    /**
     * permette di settare la password
     * @param password
     */
    public static void setPassword(String password) {
        CredenzialiDB.password = password;
    }

    /**
     * restituisce lo username inserito
     * @return user
     */
    public static String getUser() {
        return user;
    }

    /**
     * permette di settare il valore della variabile <code>user</code>
     * @param user
     */

    public static void setUser(String user) {
        CredenzialiDB.user = user;
    }

}
