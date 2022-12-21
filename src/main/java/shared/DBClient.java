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

    static int PORT = 54234;
    public static void main(String[] args) throws RemoteException, NotBoundException, SQLException {
        String nome_centro = null;
        /*String stats= "SELECT nome_evento, ROUND(AVG(indice),2) AS Media_Gravità, \n" +
                "ROUND(VARIANCE(indice)/COUNT(*),2) AS Varianza_indice from OPTIMIZE  WHERE '" + nome_centro + "'=nome_centro group by nome_evento";
        String stats2="SELECT nome_evento, COUNT(*) AS Numero_Istanze FROM OPTIMIZE WHERE nome_centro = '" + nome_centro + "' GROUP BY nome_evento \n" +
                "HAVING COUNT(*)>=ALL(Select COUNT(*) FROM OPTIMIZE " +
                "WHERE nome_centro = '" + nome_centro + "' group by nome_evento) ";*/
       try {
           Registry registry = LocateRegistry.getRegistry("192.168.178.76", PORT);
           DBInterface dbobj = (DBInterface) registry.lookup("DBInterface");
           insertCentro(dbobj, "Bastianich", "Erba", "ER", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA, "Alserio", 11, "22036");
       }catch(NumberFormatException e){
            System.err.println("Credenziali di accesso al database errate");
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
