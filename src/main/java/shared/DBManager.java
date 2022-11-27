package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import cittadini.Vaccinati;
import org.postgresql.util.PSQLException;

import java.sql.*;

public class DBManager {
    public static void main(String[] args) throws SQLException {
        //TODO:fare query per ricerca per comune e tipologia,nome centro  e visualizzainfo()
        //TODO:eventi avversi?
        //TODO:gestire controlloLogin() nel db
        //TODO: gestire esistecentro() (fare nome centro unique?)
        //TODO: gestire eccezioni Primary key, Foreign Key(per codice fiscale email), Check Costraint
        String ControllaIdVax = "SELECT Identificativo,cod_centro  FROM Centro"; // per il controllo id in vaccinato
        String ControllaIdFisc = "SELECT V.Identificativo,V.Cod_Fiscale  FROM Vaccinato V";// per il controllo id in cittadino
        String ControllaIdIscritto="";
        //String InsVaccinato ="INSERT INTO Vaccinato\n" +
       // "VALUES('CSPDNL01M11I577Q','Daniele','Caspani','11/10/2001','31500','Moderna','3')";
        //String InsIscritto ="INSERT INTO Iscritto\n" +
               // "VALUES('danielec1108@gmail.com','Dani','1234','CSPDNL01M11I577W')";
        insertEvento(2,"CIAO",3,"");
       // InsertCentro("Ospedale","Erba", "er", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Dei caduti",3,"22036");
    }

    public static Connection connected() throws SQLException {
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
    public static void selectData(String query) throws SQLException {
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
    public static void UpData(String query) throws SQLException {
        Statement st = connected().createStatement();
        st.executeUpdate(query);
        st.close();
    }

    public static void insertCentro(String nome, String comune, String sigla, StruttureVaccinali.Tipologia tipologia, IndirizzoComposto.Qualificatore qualificatore, String nome_via, int num_civico, String cap) throws SQLException {
        String ins_centro = "INSERT INTO centrovaccini(nome,comune,sigla,qualificatore,nome_via,num_civico,cap,Tipologia)\n"
                + "VALUES(" + "'" + nome + "'" + "," + "'" + comune + "'" + "," + "'" + sigla + "'" + "," + "'" + qualificatore + "'\n"
                + "," + "'" + nome_via + "'" + "," + "'" + num_civico + "'" + "," + "'" + cap + "'" + "," + "'\n"
                + tipologia + "'" + ")";
        UpData(ins_centro);
    }
    public static void insertVaccinato(String cod_fiscale, String nome, String cognome, Date data, int identificativo, Vaccinati.Vaccino vaccino, int cod_centro) throws SQLException {
        String ins_vaccinato = "INSERT INTO Vaccinato\n" +
                "VALUES(" + "'" + cod_fiscale + "'" +  "," + "'" +  nome + "'" + "," + "'" +  cognome + "'" + "," + "'" + data + "'" + "," + "'\n" +
                identificativo + "'" + "," + "'" + vaccino + "'" + ",\n"
                + cod_centro + ")";
        UpData(ins_vaccinato);
    }

    public static void insertIscritto(String email,String username,String password, String cod_fiscale) throws SQLException {
        String ins_iscritto = "INSERT INTO Iscritto VALUES(" + "'" + email + "'" + "," + "'" +  username + "'" + "," + "'" + password + "'" + "," + "'" + cod_fiscale +  "'" + ")";
        UpData(ins_iscritto);
    }

    public static void insertEvento(int cod_centro,String evento,int indice, String note) throws SQLException {
        String ins_evento = "INSERT INTO Evento VALUES(" + "'" + cod_centro + "'" +  "," + "'" + evento + "'" + "," + "'\n"
                + indice + "'" + "," + "'" +  note + "'" +  ")";
        UpData(ins_evento);
    }
}
