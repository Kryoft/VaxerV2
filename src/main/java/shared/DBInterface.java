package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;

public interface DBInterface extends Remote {

    static ResultSet executeQuery(String query) throws RemoteException, SQLException{
        Connection conn = connected();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        st.close();
        conn.close();
        return rs;
    }
    //void selectData(String query) throws RemoteException, SQLException;
    static Connection connected() throws RemoteException,SQLException{
        Connection conn = null;
        conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "pastyyna");

        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }
}
