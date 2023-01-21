package interfaccia;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface DBInterface extends Remote {

    boolean resultExists(String query) throws RemoteException;
    ArrayList<String[]> selectData(String query, String[] s) throws RemoteException;
    void upData(String query,String type) throws RemoteException;

}

