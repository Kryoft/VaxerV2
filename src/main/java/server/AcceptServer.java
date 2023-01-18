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
    static DBManager obj = null;

    /**
     * espone l'oggetto sul server attraverso un apposito oggetto di tipo <code>Registry</code>
     * @return oggetto di tipo DBInterface
     * @throws RemoteException
     */
    protected static DBInterface create() throws RemoteException {
        obj = new DBManager();
        stub = (DBInterface) UnicastRemoteObject.exportObject(obj, Integer.parseInt(RemoteInformation.getPORT()));
        registry = LocateRegistry.createRegistry( Integer.parseInt(RemoteInformation.getPORT()));
        return stub;
    }

    /**
     * permette di collegare all'oggetto precedentemente esposto un nome riconoscibile dal client
     * @param stub
     * @throws AlreadyBoundException
     * @throws RemoteException
     * @throws UnknownHostException
     */
    protected static void attiva(DBInterface stub) throws AlreadyBoundException,RemoteException, UnknownHostException {
        registry.bind("DBInterface", stub);
    }

    /**
     * permette di eliminare il link esistente tra l'oggetto stub, di tipo <code>DBInterface</code> il nome ad esso associato
     * @param stub
     * @throws RemoteException
     * @throws NotBoundException
     */

    protected static void disattiva(DBInterface stub) throws RemoteException, NotBoundException {

        registry.unbind("DBInterface");
    }
}
