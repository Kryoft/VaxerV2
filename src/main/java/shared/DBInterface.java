package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

public interface DBInterface extends Remote {

    ArrayList<String[]> executeQuery(String query) throws RemoteException, SQLException;

    //void selectData(String query) throws RemoteException, SQLException;
    static Connection connected() throws RemoteException,SQLException{
        Connection conn = null;
        conn = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/ProgettoB",CredenzialiDB.getUser(), CredenzialiDB.getPassword());

        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }
}
