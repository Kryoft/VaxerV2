/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Login;
import cittadini.Vaccinato;

import javax.swing.*;
import java.awt.*;
import java.rmi.RemoteException;
import java.sql.SQLException;


/**
 * Si tratta di una classe costituita da vari metodi utili e ripetuti all'interno
 * del programma come la lettura e scrittura da file.
 *
 * @author Daniele Caspani
 */
public abstract class Utility {

    private static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();
    /**
     * Metodo utile per calcolare lo spazio in memoria in un dato momento
     *
     * @author Daniele Caspani
     */
    public static void run() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore);

        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore));
    }

    public static int getDisplayWidth() {
        return (int) screen_size.getWidth();
    }

    public static int getDisplayHeight() { return (int) screen_size.getHeight(); }

    public static void setWindowLogo(JFrame frame, String file_name) {
        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource(file_name));
        frame.setIconImage(logo.getImage());
    }

    /**
     * Metodo che ritorna un valore enum rappresentante la tipologia di centro vaccinale
     * data una stringa in ingresso
     *
     * @param tipo La tipologia di centro vaccinale selezionata
     * @return un enumerativo della classe Tipologia
     * @author Manuel Marceca
     */
    public static CentroVaccinale.Tipologia decidiTipo(String tipo) {
        switch (tipo.toUpperCase()){
            case "HUB":
                return CentroVaccinale.Tipologia.HUB;
            case "OSPEDALIERO":
                return CentroVaccinale.Tipologia.OSPEDALIERO;
            case "AZIENDALE":
                return CentroVaccinale.Tipologia.AZIENDALE;

            default:
                return CentroVaccinale.Tipologia.OSPEDALIERO;
        }
    }

    /**
     * Metodo che ritorna un valore enum rappresentante il qualificatore dell'indirizzo
     * data una stringa in ingresso
     *
     * @param tipo Il tipo di qualificatore di un luogo fisico
     * @return un enumerativo della classe Qualificatore
     * @author Manuel Marceca
     */
    public static IndirizzoComposto.Qualificatore decidiQualificatore(String tipo) {
        switch (tipo.toUpperCase()){
            case "VIA":
                return IndirizzoComposto.Qualificatore.VIA;
            case "PIAZZA":
                return IndirizzoComposto.Qualificatore.PIAZZA;
            case "VIALE":
                return IndirizzoComposto.Qualificatore.VIALE;

            default:
                return IndirizzoComposto.Qualificatore.VIA;
        }
    }

    /**
     * Metodo che ritorna un valore enum rappresentante la tipologia di vaccino
     * data una stringa in ingresso
     *
     * @param tipo Il tipo di vaccino
     * @return un enumerativo della classe <code>Vaccino</code>
     * @author Manuel Marceca
     */
    public static Vaccinato.Vaccino decidiVaccino(String tipo) {
        switch (tipo.toUpperCase()){
            case "JJ":
                return Vaccinato.Vaccino.JJ;
            case "MODERNA":
                return Vaccinato.Vaccino.Moderna;
            case "ASTRAZENECA":
                return Vaccinato.Vaccino.AstraZeneca;
            case "PFIZER":
                return Vaccinato.Vaccino.Pfizer;

            default:
                return Vaccinato.Vaccino.Pfizer;
        }
    }

    /**
     * Metodo utilizzato per controllare se un dato nome di centro è già stato registrato nel database
     *
     * @param nome_centro  Il nome del centro vaccinale
     * @return <code>true</code> se il dato nome è già presente nel database, altrimento <code>false</code>
     * @throws SQLException
     * @author Manuel Marceca
     */
    public static boolean esisteCentro(String nome_centro) throws SQLException{
        return DBClient.getCentroVaccinaleByName(nome_centro) != null;
    }

    /**
     * Metodo utilizzato per controllare che il login sia effettivamente regolare
     *
     * @param login
     * @return <code>true</code> se la coppia (username, password) è presente nel database.
     * @author Manuel Marceca
     */
    public static boolean loginOk(Login login) {
        Cittadino cittadino = DBClient.getCittadinoByUsername(login.getUserId());

        if(cittadino != null){
            return login.getPassword().equals(cittadino.getLogin().getPassword());
        }
        return false;
    }

    /**
     * Metodo utilizzato per controllare se l'username è già in uso.
     *
     * @param username
     * @return <code>true</code> se l'username è presente nel database.
     * @author Manuel Marceca
     */
    public static boolean esisteUsername(String username){
        return DBClient.getCittadinoByUsername(username) != null;
    }


    /**
     * Metodo utilizzato per controllare la correttezza della coppia codice fiscale id nel database
     *
     * @param codice_fiscale  codice fiscale
     * @param id              identificativo
     * @return <code>true</code> se la coppia è presente nel database
     * @author Manuel Marceca
     */
    public static boolean controlloCoppiaCFId(String codice_fiscale, int id) {
        Vaccinato vaccinato = DBClient.getVaccinatoByCF(codice_fiscale);
        return vaccinato != null && vaccinato.getId() == id;
    }


    /**
     * Metodo utile all'inserzione di un nuovo centro vaccinale nel database.
     *
     * @param nuovo_centro un oggetto <code>CentroVaccinale</code>
     * @return un oggetto <code>String</code> rappresentante l'esito dell'operazione
     * @see CentroVaccinale
     * @author Manuel Marceca
     */
    public static String inserisciNuovoCentro(CentroVaccinale nuovo_centro){
        try{
            DBClient.insertCentro(nuovo_centro);
            return "";
        }catch(RemoteException e){
            return "Errore nel database";
        }
    }

    /**
     *
     * @param nuovo_vaccinato
     * @return
     * @throws SQLException
     * @throws RemoteException
     */
    public static int inserisciNuovoVaccinato(Vaccinato nuovo_vaccinato) throws  SQLException, RemoteException{

       return DBClient.insertVaccinato(nuovo_vaccinato);
    }

    public static String inserisciNuovoEvento(EventoAvverso evento) throws SQLException, RemoteException{
        try{
            DBClient.insertEvento(evento);
            return "";
        }catch(RemoteException e){
            return "Errore nel database";
        }
    }

}