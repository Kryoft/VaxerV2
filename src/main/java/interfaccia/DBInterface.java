package interfaccia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.LinkedList;

public interface DBInterface extends Remote {

    boolean resultExists(String query) throws RemoteException;
    LinkedList<String[]> selectData(String query,String[] s) throws RemoteException;
    void upData(String query,String type) throws RemoteException;

}

