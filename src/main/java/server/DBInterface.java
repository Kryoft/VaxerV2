package server;

import shared.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;

public interface DBInterface extends Remote {

    boolean resultIsNull(String query) throws RemoteException;
    LinkedList<String[]> selectData(String query,String[] s) throws RemoteException;
    void upData(String query,String type) throws RemoteException;
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

