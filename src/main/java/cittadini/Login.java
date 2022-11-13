/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

/**
 * Classe utilizzata per controllare il login;
 *
 * @author Daniele Caspani
 */
public class Login {

    String userid;
    String password;

    public Login() {
    }

    public Login(String userid, String password) {
        this.password = password;
        this.userid = userid;
    }

    /**
     * restituisce l'userid preso in considerazione
     *
     * @return
     */
    public String getUserid() {
        return userid;
    }

    /**
     * assegna un valore a userid
     *
     * @param userid
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }

    /**
     * restituisce la password presa in considerazione
     *
     * @return
     */
    public String getPassword() {
        return password;
    }

    /**
     * assegna un valore a password
     *
     * @param password
     * @author Daniele Caspani
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * converte un oggetto in stringa
     *
     * @return
     */
    @Override
    public String toString() {
        return "Login{" + "userid=" + userid + ", password=" + password + '}';
    }
}