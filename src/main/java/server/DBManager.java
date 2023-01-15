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
        Connection conn = null;
        try {
            conn = DBInterface.connected(type);
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
        Connection conn ;
        ResultSet rs;
        LinkedList<String[]> l= new LinkedList<String[]>();
        String[] appoggio;
        System.out.println(query);
        try {
            conn = DBInterface.connected("");
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
        Connection conn = null;
        ResultSet rs=null;
        boolean b;
        try {
            conn = DBInterface.connected("");
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
}
