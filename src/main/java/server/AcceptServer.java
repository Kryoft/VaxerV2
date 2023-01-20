package server;

import interfaccia.DBInterface;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * classe costituita da metodi statici che usa la tecnologia <code>rmi</code> per esporre l'oggetto sul server nell'apposito oggetto <code>Registry</code>
 * opportunamente creato.
 * @author  Daniele Caspani
 */

public class AcceptServer {

    /**
     * Registro che contiene tutti i bind creati
     */
    private static Registry registry;

    /**
     * interfaccia utilizzata per la comunicazione tra client e server
     */
    static DBInterface stub;

    /**
     * oggetto della classe <code>DBManager</code> che estende <code>DBInterface</code> e implementa i metodi utilizzati per l'accesso al database
     */
    static DBManager obj;

    /**
     * permette di esporre l'oggetto di tipo <code> DBManager</code> sulla porta dove il <code> Registry</code> è allocato
     * @throws AlreadyBoundException per evitare che il bind sia già esistente
     * @throws RemoteException per l'utilizzo di RMI
     * @throws UnknownHostException richiamato quando l'host è irraggiungibile
     */
    protected static void attiva() throws AlreadyBoundException,RemoteException, UnknownHostException {
        registry = LocateRegistry.createRegistry( Integer.parseInt(RemoteInformation.getPORT()));
        obj = new DBManager();
        stub = (DBInterface) UnicastRemoteObject.exportObject(obj, Integer.parseInt(RemoteInformation.getPORT()));
        registry.rebind("DBInterface", stub);
    }

    /**
     * permette di eliminare il link esistente tra l'oggetto stub, di tipo <code>DBInterface</code> il nome ad esso associato
     * @throws RemoteException
     * @throws NotBoundException
     */

    protected static void disattiva() throws RemoteException, NotBoundException {
        UnicastRemoteObject.unexportObject(registry,true);
        UnicastRemoteObject.unexportObject(obj,true);
    }
}
