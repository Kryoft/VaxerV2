package shared;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class AcceptServer implements DBInterface, Remote {
    private static DBInterface stub;
    private static Registry registry;

    static int PORT = 54323;


    protected static DBInterface create() throws RemoteException{
            stub = (DBInterface) UnicastRemoteObject.exportObject(new DBManager() , 54323);
            registry = LocateRegistry.createRegistry(54323);
            return stub;
    }

    protected static void attiva(DBInterface stub) throws AlreadyBoundException,RemoteException,UnknownHostException {
            registry.bind("DBInterface", stub);
    }

    protected static void disattiva(DBInterface stub) throws RemoteException,NotBoundException{
        registry.unbind("DBInterface");
    }
}
