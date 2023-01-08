package shared;

import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class DBManager implements DBInterface, Remote {
    static int PORT = 54323;
    private static Registry registry;
    static DBInterface stub;
    @Override
    public boolean executeQuery(String query) throws RemoteException, SQLException {

        Connection conn = DBInterface.connected();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        return rs.next();
    }


    protected static DBInterface create() throws RemoteException{
        stub = (DBInterface) UnicastRemoteObject.exportObject(new DBManager(), 54323);
        registry = LocateRegistry.createRegistry(54323);
        return stub;
    }

    protected static void attiva(DBInterface stub) throws AlreadyBoundException,RemoteException, UnknownHostException {
        registry.bind("DBInterface", stub);
    }

    protected static void disattiva(DBInterface stub) throws RemoteException, NotBoundException {
        registry.unbind("DBInterface");
    }
}