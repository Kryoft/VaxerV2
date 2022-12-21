package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import cittadini.Vaccinati;
import org.postgresql.util.PSQLException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class DBClient {

    private static final String DEFAULT_IP = "localhost";
    private static final int DEFAULT_PORT = 54234;

    public static void main(String[] args) throws SQLException, RemoteException {
        String ControllaIdVax = "SELECT Identificativo,cod_centro  FROM Centro"; // per il controllo id in vaccinato
        String ControllaIdFisc = "SELECT V.Identificativo,V.Cod_Fiscale  FROM Vaccinato V";// per il controllo id in cittadino
        String ControllaIdIscritto="";
        //String InsVaccinato ="INSERT INTO Vaccinato\n" +
        // "VALUES('CSPDNL01M11I577Q','Daniele','Caspani','11/10/2001','31500','Moderna','3')";
        //String InsIscritto ="INSERT INTO Iscritto\n" +
        // "VALUES('danielec1108@gmail.com','Dani','1234','CSPDNL01M11I577W')";
        // insertEvento(2,"CIAO",3,"");
        // InsertCentro("Ospedale","Erba", "er", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Dei caduti",3,"22036");

        try {
            String ip = (args.length >= 1) ? args[0] : DEFAULT_IP;
            int port = (args.length >= 2) ? Integer.parseInt(args[1]) : DEFAULT_PORT;

            // Getting the registry
            Registry registry = LocateRegistry.getRegistry(ip, port);

            // Looking up the registry for the remote object
            DBInterface dbobj = (DBInterface) registry.lookup("DBInterface");
            insertCentro(dbobj, "Joe", "Erba", "ER", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA, "Alserio", 11, "22036");
            System.out.println("Remote method invoked ");

        } catch (NumberFormatException nfe) {
            nfe.printStackTrace();
        } catch (Exception e) {  // non è il massimo catchare la classe generale Exception
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }
    }
    public static void insertCentro(DBInterface dbobj,String nome, String comune, String sigla, StruttureVaccinali.Tipologia tipologia, IndirizzoComposto.Qualificatore qualificatore, String nome_via, int num_civico, String cap) throws RemoteException, SQLException {
        String ins_centro = "INSERT INTO centrovaccini(nome,comune,Tipologia,sigla,qualificatore,nome_via,num_civico,cap)\n"
                + "VALUES(" + "'" + nome + "'" + "," + "'" + comune + "'" + "," + "'" + tipologia + "'" + "," + "'" + sigla + "'\n"
                + "," + "'" + qualificatore + "'" + "," + "'" + nome_via + "'" + "," + "'" + num_civico + "'" + "," + "'"
                + cap + "'" + ")";
        try {
            dbobj.upData(ins_centro);}
        catch(PSQLException p){
            throw new DBException("Centro",Integer.parseInt(p.getSQLState()),p.getMessage());
        }
    }
    public static void insertVaccinato(DBInterface dbobj,String cod_fiscale, String nome, String cognome, String data, int identificativo, Vaccinati.Vaccino vaccino, int cod_centro) throws RemoteException, SQLException {
        String ins_vaccinato = "INSERT INTO Vaccinati\n" +
                "VALUES(" + "'" + cod_fiscale + "'" +  "," + "'" +  nome + "'" + "," + "'" +  cognome + "'" + "," + "'" + data + "'" + "," + "'\n" +
                identificativo + "'" + "," + "'" + vaccino + "'" + ",\n"
                + cod_centro + ")";
            try {
            dbobj.upData(ins_vaccinato);}
            catch(PSQLException p){
                throw new DBException("Vaccinato",Integer.parseInt(p.getSQLState()),p.getMessage());
            }
    }

    public static void insertIscritto(DBInterface dbobj,String email,String username,String password, String cod_fiscale) throws RemoteException, SQLException {
        String ins_iscritto = "INSERT INTO Iscritti VALUES(" + "'" + email + "'" + "," + "'" +  username + "'" + "," + "'" + password + "'" + "," + "'" + cod_fiscale +  "'" + ")";
        try {
            dbobj.upData(ins_iscritto);}
        catch(PSQLException p){
            throw new DBException("Iscritto",Integer.parseInt(p.getSQLState()),p.getMessage());
        }
    }

    public static void insertLogEvento(DBInterface dbobj, int cod_evento,String email,int indice, String note) throws RemoteException, SQLException {
        String ins_evento = "INSERT INTO Evento(Cod_evento,Email,Indice,Note)" +
                "VALUES(" + "'" + cod_evento + "'" +  "," + "'" + email + "'" + "," + "'\n"
                + indice + "'" + "," + "'" +  note + "'" +  ")";
        try {
            dbobj.upData(ins_evento);}
        catch(PSQLException p){
            throw new DBException("Log_Evento",Integer.parseInt(p.getSQLState()),p.getMessage());
        }
    }
    public static void insertEvento(DBInterface dbobj,String nome_evento) throws RemoteException, SQLException {
        String ins_evento = "INSERT INTO Eventi(nome_evento) VALUES(" + "'" + nome_evento + "')";
        try {
            dbobj.upData(ins_evento);}
        catch(PSQLException p){
            throw new DBException("Eventi",Integer.parseInt(p.getSQLState()),p.getMessage());
        }
    }
}
