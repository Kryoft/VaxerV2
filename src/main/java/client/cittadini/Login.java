/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.cittadini;

/**
 * Classe utilizzata per controllare il login;
 *
 * @author Daniele Caspani
 */
public class Login {

    private String user_id, password;

    public Login() {
    }

    public Login(String user_id, String password) {
        this.password = password;
        this.user_id = user_id;
    }

    /**
     * Restituisce l'user_id preso in considerazione
     *
     * @return L'userID dell'utente
     */
    public String getUserId() {
        return user_id;
    }

    /**
     * Assegna un valore a user_id
     *
     * @param user_id l'user ID dell'utente
     */
    public void setUserId(String user_id) {
        this.user_id = user_id;
    }

    /**
     * Restituisce la password presa in considerazione
     *
     * @return Password dell'utente
     */
    public String getPassword() {
        return password;
    }

    /**
     * Assegna un valore a password
     *
     * @param password password dell'utente
     * @author Daniele Caspani
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Converte un oggetto in stringa
     *
     * @author Daniele Caspani
     */
    @Override
    public String toString() {
        return "Login{" + "userid=" + user_id + ", password=" + password + '}';
    }
}