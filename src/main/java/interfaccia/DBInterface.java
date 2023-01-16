package interfaccia;

import shared.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.LinkedList;

public interface DBInterface extends Remote {

    boolean resultIsNull(String query) throws RemoteException;
    LinkedList<String[]> selectData(String query,String[] s) throws RemoteException;
    void upData(String query,String type) throws RemoteException;

}

