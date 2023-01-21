package server;

import interfaccia.DBInterface;
import shared.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * classe che implementa <code>DBInterface</code>, utilizzata per ridefinire
 * i metodi di quest'ultima e quindi esposta sul servizio remoto <code>rmi</code>
 */
public class DBManager implements DBInterface, Remote {

    /**
     * esegue le operazioni di update riguardanti il database (In particolare le operazioni di inserimento)
     * @param query Stringa che contiene la Query da eseguire
     * @param type tipo di errore che si può verificare
     * @throws RemoteException
     */
    @Override
    public void upData(String query, String type) throws RemoteException {
        Connection connection;
        try {
            connection = connected(type, CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            new DBException(type,e.getSQLState(),e.getMessage());
        }
    }

    /**
     * metodo che gestisce le query di Select
     * @param query Stringa che contiene la query da eseguire
     * @param attributi stringa contenente i campi del database
     * @return Struttura dati contenente i risultati restituiti dalla query
     * @throws RemoteException
     */
    @Override
    public ArrayList<String[]> selectData(String query, String[] attributi) throws RemoteException {
        Connection connection;
        ResultSet result_set;
        ArrayList<String[]> lista = new ArrayList<>();
        String[] appoggio;
        try {
            connection = connected("", CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement statement = connection.createStatement();
            result_set = statement.executeQuery(query);
            while (result_set.next()) {
                appoggio = attributi.clone();
                for (int i = 0; i < attributi.length; i++) {
                    appoggio[i]= result_set.getString(attributi[i]);
                }
                lista.add(appoggio);
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            new DBException("",e.getSQLState(),e.getMessage());
        }
        return lista;
    }

    /**
     *metodo utilizzato per verificare che una data query non restituisca tuple
     * @param query Stringa contenente la query da eseguire
     * @return
     * @throws RemoteException
     */
    @Override
     public boolean resultExists(String query) throws RemoteException {
        Connection connection;
        ResultSet result_set;
        boolean b;
        try {
            connection = connected("", CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement statement = connection.createStatement();
            result_set = statement.executeQuery(query);
            b = result_set.next();
            statement.close();
            connection.close();
            return b;
        } catch (SQLException e) {
            new DBException("",e.getSQLState(),e.getMessage());
            return false;
        }

    }

    /**
     * Metodo utilizzato per connettersi con il database
     * @param type tipo di errore che si può verificare
     * @param username username utilizzato dal server per accedere al database
     * @param password
     * @return oggetto contenente un'istanza di connessione con il database
     * @throws RemoteException
     */
    protected static Connection connected(String type, String username, String password) throws RemoteException {
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/ProgettoB", username, password);
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (SQLException e) {
            new DBException(type,e.getSQLState(),e.getMessage());
        }

        return connection;
    }
}
