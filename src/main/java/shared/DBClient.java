package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import cittadini.Vaccinati;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.Date;
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
    public static void insertCentro(DBInterface dbobj,String nome, String comune, String sigla, StruttureVaccinali.Tipologia tipologia, IndirizzoComposto.Qualificatore qualificatore, String nome_via, int num_civico, String cap) throws SQLException, RemoteException {
        String ins_centro = "INSERT INTO centrovaccini(nome,comune,sigla,qualificatore,nome_via,num_civico,cap,Tipologia)\n"
                + "VALUES(" + "'" + nome + "'" + "," + "'" + comune + "'" + "," + "'" + sigla + "'" + "," + "'" + qualificatore + "'\n"
                + "," + "'" + nome_via + "'" + "," + "'" + num_civico + "'" + "," + "'" + cap + "'" + "," + "'\n"
                + tipologia + "'" + ")";
        dbobj.upData(ins_centro);
    }
    public static void insertVaccinato(DBInterface dbobj,String cod_fiscale, String nome, String cognome, Date data, int identificativo, Vaccinati.Vaccino vaccino, int cod_centro) throws SQLException, RemoteException {
        String ins_vaccinato = "INSERT INTO Vaccinato\n" +
                "VALUES(" + "'" + cod_fiscale + "'" +  "," + "'" +  nome + "'" + "," + "'" +  cognome + "'" + "," + "'" + data + "'" + "," + "'\n" +
                identificativo + "'" + "," + "'" + vaccino + "'" + ",\n"
                + cod_centro + ")";
        dbobj.upData(ins_vaccinato);
    }

    public static void insertIscritto(DBInterface dbobj,String email,String username,String password, String cod_fiscale) throws SQLException, RemoteException {
        String ins_iscritto = "INSERT INTO Iscritto VALUES(" + "'" + email + "'" + "," + "'" +  username + "'" + "," + "'" + password + "'" + "," + "'" + cod_fiscale +  "'" + ")";
        dbobj.upData(ins_iscritto);
    }

    public static void insertEvento(DBInterface dbobj, int cod_centro,String evento,int indice, String note) throws SQLException, RemoteException {
        String ins_evento = "INSERT INTO Evento VALUES(" + "'" + cod_centro + "'" +  "," + "'" + evento + "'" + "," + "'\n"
                + indice + "'" + "," + "'" +  note + "'" +  ")";
        dbobj.upData(ins_evento);
    }
}
