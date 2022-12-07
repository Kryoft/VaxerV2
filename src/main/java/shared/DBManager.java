package shared;

import org.postgresql.util.PSQLException;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.sql.*;

public class DBManager implements DBInterface{
    static int PORT = 54234;
    public static void main(String[] args) throws RemoteException {
        DBInterface stub = null;
        //TODO:fare query per ricerca per comune e tipologia,nome centro  e visualizzainfo()
        //TODO:eventi avversi?
        //TODO:gestire controlloLogin() nel db (mettere unique username)
        //TODO: gestire esistecentro() (fare nome centro unique?)
        //TODO: gestire eccezioni Primary key, Foreign Key(per codice fiscale email), Check Costraint
        //TODO:statistiche per visualizza eventi moda mediana dev. standard
        //TODO:sistemare utility e tutto il programma
        //TODO: scrivere query mancanti
        //TODO: fare uml

        DBManager obj = new DBManager();
        try {
            stub = (DBInterface) UnicastRemoteObject.exportObject(
                    obj, PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        Registry registry = null;
        try {
            registry = LocateRegistry.createRegistry(PORT);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            registry.bind("DBInterface", stub);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

        System.err.println("Server ready");
    }

    @Override
    public Connection connected() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "Antananarivo01");

        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }
    public void selectData(String query) throws SQLException {
        // crea il java statement
        Statement st = connected().createStatement();

        // esegue la query e mette i risultati in rs
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            String nome = rs.getString("nome");
            String comune = rs.getString("comune");
            String sigla = rs.getString("sigla");
            String nome_via= rs.getString("nome_via");
            String id = rs.getString("codice");
            String qualificatore=rs.getString("qualificatore");
            // print the results
            System.out.format("%s, %s, %s, %s, %s, %s\n", id,nome,comune,sigla,qualificatore,nome_via);
        }
        st.close();
    }
    public void upData(String query) throws SQLException {
        Statement st = connected().createStatement();
        st.executeUpdate(query);
        st.close();
    }
}
