package server;

import interfaccia.DBInterface;
import shared.DBException;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;

/**
 * classe che implementa <code>DBInterface</code>, utilizzata per ridefinire
 * i metodi di quest'ultima e quindi esposta sul servizio remoto <code>rmi</code>
 */
public class DBManager implements DBInterface, Remote {

    /**
     * esegue le operazioni di update riguardanti il database(In particolare le operazioni di inserimento)
     * @param query Stringa che contiene la Query da eseguire
     * @param type tipo di errore che si può verificare
     * @throws RemoteException
     */
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

    /**
     * metodo che gestisce le query di Select
     * @param query Stringa che contiene la query da eseguire
     * @param s stringa contenente i campi del database
     * @return Struttura dati contenente i risultati restituiti dalla query
     * @throws RemoteException
     */
    @Override
    public LinkedList<String[]> selectData(String query, String[] s) throws RemoteException{
        Connection conn;
        ResultSet rs;
        LinkedList<String[]> l= new LinkedList<String[]>();
        String[] appoggio;
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

    /**
     *metodo utilizzato per verificare che una data query non restituisca tuple
     * @param query Stringa contenente la query da eseguire
     * @return
     * @throws RemoteException
     */
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

    /**
     * metodo utilizzato per connetersi con il database
     * @param type tipo di errore che si può verificare
     * @param username username utilizzato dal server per accedere al database
     * @param password
     * @return oggetto contenente un'istanza di connessione con il database
     * @throws RemoteException
     */
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
