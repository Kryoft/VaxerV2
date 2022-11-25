package shared;

import java.sql.*;

public class DBManager {

    public static Connection Connected() throws SQLException {
        Connection conn = null;
        conn = DriverManager.getConnection(
                "jdbc:postgresql://127.0.0.1:5432/ProgettoB", "postgres", "password");

        if (conn != null) {
            System.out.println("Connected to the database!");
        } else {
            System.out.println("Failed to make connection!");
        }
        return conn;
    }
    public static void SelectData(String query) throws SQLException {
        // crea il java statement
        Statement st = Connected().createStatement();

        // esegue la query e mette i risultati in rs
        ResultSet rs = st.executeQuery(query);
        // iterate through the java resultset
        while (rs.next()) {
            String Nome = rs.getString("nome");
            String comune = rs.getString("comune");
            String sigla = rs.getString("sigla");
            String nome_via= rs.getString("nome_via");
            String id = rs.getString("codice");
            String qualificatore=rs.getString("qualificatore");
            // print the results
            System.out.format("%s, %s, %s, %s, %s, %s\n", id,Nome,comune,sigla,qualificatore,nome_via);
        }
        st.close();
    }
    public static void UpData(String query) throws SQLException {
        Statement st = Connected().createStatement();
        st.executeUpdate(query);
        st.close();
    }
}
