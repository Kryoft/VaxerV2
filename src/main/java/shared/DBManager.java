package shared;

import java.rmi.RemoteException;
import java.sql.*;
import java.util.LinkedList;

public class DBManager implements DBInterface{

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

    /* DBCOMPLETE:


    static int PORT = 54234;
    public static void main(String[] args) {
        DBInterface stub = null;

        DBManager obj = new DBManager();
        Registry registry = null;
        try {
            stub = (DBInterface) UnicastRemoteObject.exportObject(
                    obj, PORT);
            registry = LocateRegistry.createRegistry(PORT);
            registry.bind("DBInterface", stub);
        } catch (RemoteException e) {
            System.err.println(e.getMessage());
        } catch (AlreadyBoundException e) {
            System.err.println(e.getMessage());
        }

        System.err.println("Server ready");
    }

    /*@Override
    public Connection connected() throws SQLException {
        Connection conn = null;
            conn = DriverManager.getConnection(
                    "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "Antananarivo01");
        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.err.println("Failed to make connection!");
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

     */
    /*
    public void executeQuery(String query) throws SQLException {
        Statement st = connected().createStatement();
        st.executeUpdate(query);
        st.close();
    public void upData(String query) throws SQLException {
        Statement st = null;
            st = connected().createStatement();
            st.executeUpdate(query);
            st.close();
            connected().close();
    }

     */



    /* code-cleanup

    static int PORT = 54323;

    @Override
    public ArrayList<String[]> executeQuery(String query) throws RemoteException, SQLException {
        ArrayList<String[]> a = new ArrayList<String[]>();
        String[] s= new String[4];
        String rs_mail=null,rs_username=null,rs_password=null,rs_cod_fisc=null;
        Connection conn = DBInterface.connected();
        Statement st = conn.createStatement();
        ResultSet rs = st.executeQuery(query);
        int i=0;
        while(rs.next()){
            rs_mail = rs.getString("email");
            rs_username=rs.getString("username");
            rs_password=rs.getString("password");
            rs_cod_fisc=rs.getString("cod_fiscale");
            s[0] = rs_mail;
            s[1] = rs_username;
            s[2]= rs_password;
            s[3]=rs_cod_fisc;
            a.add(s);
            i++;
        }
        return a;
    }
     */
}
