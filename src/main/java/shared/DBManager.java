package shared;

import java.sql.*;

public class DBManager {
    public static void main(String[] args) throws SQLException {
        //TODO:decidere se mettere i controlli esistecentro(), controlloid(), ControllaCF(), ControllaLogin() per cittadino su db e come gestire eccezioni
        //String CercaCentro = "SELECT * FROM Centro";
        String InsVaccinato ="INSERT INTO Vaccinato\n" +
        "VALUES('CSPDNL01M11I577Q','Daniele','Caspani','11/10/2001','31500','Moderna','3')";
        UpData(InsVaccinato);
        String InsIscritto ="INSERT INTO Iscritto\n" +
                "VALUES('danielec1108@gmail.com','Dani','1234','CSPDNL01M11I577W')";
        UpData(InsVaccinato);
        String InsEvento ="INSERT INTO Evento\n" +
                "VALUES('2','Mal di testa','Caspani','4','Lieve')";
        UpData(InsVaccinato);
    }

    public static Connection Connected() throws SQLException {
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

    public static void InsertCentro(String nome,String comune,String sigla, String tipologia,String qualificatore,String nome_via, int num_civico,String cap) throws SQLException {
        String InsCentro = "INSERT INTO centrovaccini(nome,comune,sigla,tipologia,qualificatore,nome_via,num_civico,cap)\n" +
                "VALUES(" + nome + "," + comune + "," + sigla + "," + tipologia + "," + qualificatore + "," + nome_via + ",\n"
        + num_civico + "," + cap + ")";
        UpData(InsCentro);
    }
    public static void InsertVaccinato(String cod_fiscale,String nome,String cognome, String data,int identificativo,String vaccino, String cod_centro) throws SQLException {
        String InsVaccinato = "INSERT INTO Vaccinato\n" +
                "VALUES(" + cod_fiscale + "," + nome + "," + cognome + "," + data + "," + identificativo + "," + vaccino + ",\n"
                + cod_centro + ")";
        UpData(InsVaccinato);
    }

    public static void InsertIscritto(String email,String username,String password, String cod_fiscale) throws SQLException {
        String InsIscritto = "INSERT INTO Iscritto VALUES(" + email + "," + username + "," + password + "," + cod_fiscale +  ")";
        UpData(InsIscritto);
    }

    public static void InsertEvento(String cod_centro,String evento,String indice, String note) throws SQLException {
        String InsEvento = "INSERT INTO Iscritto VALUES(" + cod_centro + "," + evento + "," + indice + "," + note +  ")";
        UpData(InsEvento);
    }
}
