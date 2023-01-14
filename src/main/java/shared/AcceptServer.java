package shared;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AcceptServer {

    private static Registry registry;
    static DBInterface stub;

    static DBManager obj = null;
    protected static DBInterface create() throws RemoteException {
         obj = new DBManager();
        stub = (DBInterface) UnicastRemoteObject.exportObject(obj, Integer.parseInt(RemoteInformation.getPORT()));
        registry = LocateRegistry.createRegistry( Integer.parseInt(RemoteInformation.getPORT()));
        return stub;
    }

    protected static void attiva(DBInterface stub) throws AlreadyBoundException,RemoteException, UnknownHostException {
        registry.bind("DBInterface", stub);
    }

    protected static void disattiva(DBInterface stub) throws RemoteException, NotBoundException {
        registry.unbind("DBInterface");
    }
}
