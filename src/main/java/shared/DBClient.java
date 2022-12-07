package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import cittadini.Vaccinati;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class DBClient {

    static int PORT = 54234;

    public static void main(String[] args) throws SQLException, RemoteException {
        String ControllaIdVax = "SELECT Identificativo,cod_centro  FROM Centro"; // per il controllo id in vaccinato
        String ControllaIdFisc = "SELECT V.Identificativo,V.Cod_Fiscale  FROM Vaccinato V";// per il controllo id in cittadino
        String ControllaIdIscritto="";
        String stats= "SELECT evento, ROUND(AVG(indice),2) AS Media, \n" +
                "ROUND(VARIANCE(indice)/COUNT(*),2) AS Varianza from evento group by evento ";
        String stats2="SELECT evento, COUNT(*) FROM evento GROUP BY evento \n" +
                "HAVING COUNT(*)>=ALL(Select COUNT(*) FROM evento group by evento);";
        //String InsVaccinato ="INSERT INTO Vaccinato\n" +
        // "VALUES('CSPDNL01M11I577Q','Daniele','Caspani','11/10/2001','31500','Moderna','3')";
        //String InsIscritto ="INSERT INTO Iscritto\n" +
        // "VALUES('danielec1108@gmail.com','Dani','1234','CSPDNL01M11I577W')";
        // InsertCentro("Ospedale","Erba", "er", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Dei caduti",3,"22036");

        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry("192.168.178.76", PORT);

            // Looking up the registry for the remote object
            DBInterface dbobj = (DBInterface) registry.lookup("DBInterface");
            //insertVaccinato(dbobj,"CSPDNL01M11I577Q","CIAO222","XAO","10-10-2010",31311, Vaccinati.Vaccino.AstraZeneca,3);
           insertCentro(dbobj,"Bastianich","Erba","ER", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Alserio",11290022,"22036");
            //insertIscritto(dbobj,"danielec1108@gmail.com","CIAO","11","CSPDNL01M11I577Q");
            System.out.println("Remote method invoked ");
        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
    public static void insertCentro(DBInterface dbobj,String nome, String comune, String sigla, StruttureVaccinali.Tipologia tipologia, IndirizzoComposto.Qualificatore qualificatore, String nome_via, int num_civico, String cap) throws RemoteException {
        String ins_centro = "INSERT INTO centrovaccini(nome,comune,sigla,qualificatore,nome_via,num_civico,cap,Tipologia)\n"
                + "VALUES(" + "'" + nome + "'" + "," + "'" + comune + "'" + "," + "'" + sigla + "'" + "," + "'" + qualificatore + "'\n"
                + "," + "'" + nome_via + "'" + "," + "'" + num_civico + "'" + "," + "'" + cap + "'" + "," + "'\n"
                + tipologia + "'" + ")";
        try {
            dbobj.upData(ins_centro);
        } catch (SQLException e) {
            //chiave primaria:23505
            //sqlstate chiave esterna: 23503
            //vincoli check:23514
            //vincoli constraint unique:23505
            //outofbound stringhe:22001
            //outofbound intero:22003
            DBException dbe = new DBException(1,e.getSQLState(),e.getMessage());

        }
    }
    public static void insertVaccinato(DBInterface dbobj,String cod_fiscale, String nome, String cognome, String data, int identificativo, Vaccinati.Vaccino vaccino, int cod_centro) throws RemoteException {
        String ins_vaccinato = "INSERT INTO Vaccinato\n" +
                "VALUES(" + "'" + cod_fiscale + "'" +  "," + "'" +  nome + "'" + "," + "'" +  cognome + "'" + "," + "'" + data + "'" + "," + "'\n" +
                identificativo + "'" + "," + "'" + vaccino + "'" + ",\n"
                + cod_centro + ")";
        try {
            dbobj.upData(ins_vaccinato);
        } catch (SQLException e) {
            System.err.println("Codice di errore:" + e.getSQLState());
        }
    }

    public static void insertIscritto(DBInterface dbobj,String email,String username,String password, String cod_fiscale) throws RemoteException {
        String ins_iscritto = "INSERT INTO Iscritto VALUES(" + "'" + email + "'" + "," + "'" +  username + "'" + "," + "'" + password + "'" + "," + "'" + cod_fiscale +  "'" + ")";
        try {
            dbobj.upData(ins_iscritto);
        } catch (SQLException e) {
            System.err.println("Codice di errore:" + e.getSQLState());
        }
    }

    public static void insertEvento(DBInterface dbobj, int cod_centro,String evento,int indice, String note) throws RemoteException {
        String ins_evento = "INSERT INTO Evento VALUES(" + "'" + cod_centro + "'" +  "," + "'" + evento + "'" + "," + "'\n"
                + indice + "'" + "," + "'" +  note + "'" +  ")";
        try {
            dbobj.upData(ins_evento);
        } catch (SQLException e) {
            System.err.println("Codice di errore:" + e.getSQLState());
        }
    }
}
