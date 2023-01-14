package shared;

import java.rmi.RemoteException;
import java.sql.Connection;
import java.util.LinkedList;

public interface DBInterface {

    boolean resultIsNull(String query) throws RemoteException;
    LinkedList<String[]> selectData(String query, String[] s) throws RemoteException;
    void upData(String query,String type) throws RemoteException;
    Connection connected(String type) throws RemoteException;
