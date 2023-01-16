package server;

import interfaccia.DBInterface;
import shared.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;

public class DBManager implements DBInterface, Remote {

    @Override
    public void upData(String query, String type) throws RemoteException{
        Connection conn;
        try {
            conn = connected(type, CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement st = conn.createStatement();
            st.executeUpdate(query);
            st.close();
            conn.close();
        } catch (SQLException e) {
            new DBException(type,e.getSQLState(),e.getMessage());
        }
    }

    @Override
    public LinkedList<String[]> selectData(String query, String[] s) throws RemoteException{
        Connection conn;
        ResultSet rs;
        LinkedList<String[]> l= new LinkedList<String[]>();
        String[] appoggio;
        System.out.println(query);
        try {
            conn = connected("", CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement st = conn.createStatement();
            rs = st.executeQuery(query);
            while(rs.next()){
                appoggio = s.clone();
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
    @Override
     public boolean resultIsNull(String query) throws RemoteException{
        Connection conn;
        ResultSet rs;
        boolean b;
        try {
            conn = connected("", CredenzialiDB.getUser(), CredenzialiDB.getPassword());
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

    protected static Connection connected(String type, String username, String password) throws RemoteException{
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/ProgettoB", username, password);
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
