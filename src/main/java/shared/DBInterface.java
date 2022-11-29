package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.SQLException;

public interface DBInterface extends Remote {

    void upData(String query) throws RemoteException, SQLException;
    void selectData(String query) throws RemoteException, SQLException;
    Connection connected() throws RemoteException,SQLException;
}
