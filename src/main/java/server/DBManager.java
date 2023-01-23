package server;

import interfaccia.DBInterface;
import client.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.ArrayList;

/**
 * Classe che implementa <code>DBInterface</code>, utilizzata per ridefinire
 * i metodi di quest'ultima e quindi esposta sul servizio remoto <code>RMI</code>
 */
public class DBManager implements DBInterface, Remote {

    /**
     * Esegue le operazioni di update riguardanti il database (in particolare le operazioni d'inserimento)
     *
     * @param query stringa che contiene la query da eseguire
     * @param source_window finestra da cui il comando viene chiamato
     * @throws RemoteException nel caso di problemi con RMI
     */
    @Override
    public void upData(String query, String source_window) throws RemoteException {
        Connection connection;
        try {
            connection = connected(source_window, CredenzialiDB.getUser(), CredenzialiDB.getPassword());
            Statement statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();
            connection.close();
        } catch (SQLException e) {
            new DBException(source_window, e.getSQLState(), e.getMessage());
        }
    }

    /**
     * Metodo che gestisce le query di Select
     *
     * @param query stringa che contiene la query da eseguire
     * @param attributi stringa contenente i campi/attributi del database
     * @return una struttura dati ArrayList contenente i risultati restituiti dalla query
     * @throws RemoteException nel caso di problemi con RMI
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
            new DBException("", e.getSQLState(), e.getMessage());
        }
        return lista;
    }

    /**
     * Metodo utilizzato per verificare che una data query non restituisca tuple
     *
     * @param query stringa contenente la query da eseguire
     * @return <code>true</code> nel caso esiste una tupla successiva nel Database, <code>false</code> altrimenti.
     * @throws RemoteException nel caso di problemi con RMI
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
            new DBException("", e.getSQLState(), e.getMessage());
            return false;
        }
    }

    /**
     * Metodo utilizzato per connettersi con il database
     *
     * @param source_window finestra da cui il comando viene chiamato
     * @param username username utilizzato dal server per accedere al database
     * @param password password del database
     * @return un oggetto di tipo <code>Connection</code> contenente un'istanza di connessione con il database
     * @throws RemoteException nel caso di problemi con RMI
     *
     * @see Connection
     */
    protected static Connection connected(String source_window, String username, String password) throws RemoteException {
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
            new DBException(source_window, e.getSQLState(), e.getMessage());
        }

        return connection;
    }
}
