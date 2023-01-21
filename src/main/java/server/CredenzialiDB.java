package server;

/**
 * Si tratta di una classe utile per memorizzare user e password del db
 */

public abstract class CredenzialiDB {

    /**
     * user del database
     */
    private static String user = "";

    /**
     * password del database
     */
    private static String password = "";

    /**
     * array di stringhe che contiene gli errori
     */
    public static String[] message = new String[2];

    /**
     * restituisce password
     * @return password
     */
    public static String getPassword() {
        return password;
    }

    /**
     * metodo che restituisce un array di stringhe contenente i vari errori
     * @param valore
     * @param indice
     */
    public static void setMessage(int indice, String valore){
        message[indice] = valore ;
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

    /**
     * metodo per verificare se i campi siano effettivamente stati riempiti
     * @return
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
     * metodo utilizzato per verificare se non ci sono errori relativi alla conferma della password
     * @param password
     * @param conferma_password
     * @return
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
