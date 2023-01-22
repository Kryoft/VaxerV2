package interfaccia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * ...
 *
 * @author Daniele Caspani
 */
public interface DBInterface extends Remote {

    /**
     * L'implementazione di questo metodo ha lo scopo di eseguire le operazioni di update
     * riguardanti il database (in particolare le operazioni d'inserimento)
     *
     * @param query stringa che contiene la query da eseguire
     * @param source_window finestra da cui il comando viene chiamato
     * @throws RemoteException nel caso di problemi con RMI
     */
    void upData(String query,String source_window) throws RemoteException;

    /**
     * L'implementazione di questo metodo ha lo scopo di gestire le query di Select
     *
     * @param query stringa che contiene la query da eseguire
     * @param attributi stringa contenente i campi/attributi del database
     * @return una struttura dati ArrayList contenente i risultati restituiti dalla query
     * @throws RemoteException nel caso di problemi con RMI
     */
    ArrayList<String[]> selectData(String query, String[] attributi) throws RemoteException;

    /**
     * L'implementazione di questo metodo ha lo scopo di verificare che una data query non restituisca tuple
     *
     * @param query stringa contenente la query da eseguire
     * @return <code>true</code> nel caso esiste una tupla successiva nel Database, <code>false</code> altrimenti.
     * @throws RemoteException nel caso di problemi con RMI
     */
    boolean resultExists(String query) throws RemoteException;

}

