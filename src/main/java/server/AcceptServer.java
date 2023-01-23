package server;

import interfaccia.DBInterface;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * Classe astratta costituita da metodi statici che usa la tecnologia <code>RMI</code> (Remote Method Invocation)
 * per esporre l'oggetto sul server nell'apposito oggetto <code>Registry</code> opportunamente creato.
 * @author Daniele Caspani
 */
public abstract class AcceptServer {

    /**
     * Registro che contiene tutti i bind creati
     */
    private static Registry registry;

    /**
     * Interfaccia utilizzata per la comunicazione tra client e server
     */
    static DBInterface stub;

    /**
     * Oggetto della classe <code>DBManager</code> che estende <code>DBInterface</code> e
     * implementa i metodi utilizzati per l'accesso al database
     */
    static DBManager obj;

    /**
     * Permette di esporre l'oggetto di tipo <code>DBManager</code> sulla porta dove il <code>Registry</code> è allocato
     *
     * @throws AlreadyBoundException nel caso il bind sia già esistente
     * @throws RemoteException nel caso di problemi remoti con l'utilizzo di RMI
     * @throws UnknownHostException nel caso l'host sia irraggiungibile
     */
    protected static void attiva() throws AlreadyBoundException,RemoteException, UnknownHostException {
        registry = LocateRegistry.createRegistry(RemoteInformation.getPORT());
        obj = new DBManager();
        stub = (DBInterface) UnicastRemoteObject.exportObject(obj, RemoteInformation.getPORT());
        registry.rebind("DBInterface", stub);
    }

    /**
     * Permette di eliminare il link esistente tra l'oggetto stub, di tipo <code>DBInterface</code>, e il nome a esso associato
     *
     * @throws RemoteException nel caso di problemi remoti con l'utilizzo di RMI
     */

    protected static void disattiva() throws RemoteException {
        UnicastRemoteObject.unexportObject(registry,true);
        UnicastRemoteObject.unexportObject(obj,true);
    }
}
