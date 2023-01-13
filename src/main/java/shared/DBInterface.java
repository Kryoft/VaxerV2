package shared;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;

public interface DBInterface extends Remote {

    static boolean resultIsNull(String query) throws RemoteException{
        Connection conn = null;
        ResultSet rs=null;
        boolean b;
        try {
            conn = connected("");
            Statement st = conn.createStatement();
           rs= st.executeQuery(query);
           b= rs.next();
            st.close();
            conn.close();
            return b;
        } catch (SQLException e) {
            new DBException("",e.getSQLState(),e.getMessage());
            return false;
        }

    }
    static LinkedList<String[]> selectData(String query,String[] s) throws RemoteException{
        Connection conn ;
        ResultSet rs;
        LinkedList<String[]> l= new LinkedList<String[]>();
        String[] appoggio = s.clone();
        System.out.println(query);
        try {
            conn = connected("");
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                for(int i=0;i<s.length;i++){
                    appoggio[i]= rs.getString(s[i]);
                }
                l.add(appoggio);
            }
            st.close();
            conn.close();
        } catch (SQLException e) {
            new DBException("",e.getSQLState(),e.getMessage());
        }
        return l;
    }
     static void upData(String query,String type) throws RemoteException{
         Connection conn = null;
         try {
             conn = connected(type);
             Statement st = conn.createStatement();
             st.executeUpdate(query);
             st.close();
             conn.close();
         } catch (SQLException e) {
             new DBException(type,e.getSQLState(),e.getMessage());
         }
    }
    //void selectData(String query) throws RemoteException, SQLException;
    static Connection connected(String type) throws RemoteException{
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "Antananarivo01");
        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        } catch (SQLException e) {
            new DBException(type,e.getSQLState(),e.getMessage());
        }

        return conn;
    }
}
