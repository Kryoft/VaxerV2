package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Vaccinato;
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
        //String InsVaccinato ="INSERT INTO Vaccinato\n" +
        // "VALUES('CSPDNL01M11I577Q','Daniele','Caspani','11/10/2001','31500','Moderna','3')";
        //String InsIscritto ="INSERT INTO Iscritto\n" +
        // "VALUES('danielec1108@gmail.com','Dani','1234','CSPDNL01M11I577W')";
        // insertEvento(2,"CIAO",3,"");
        // InsertCentro("Ospedale","Erba", "er", StruttureVaccinali.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Dei caduti",3,"22036");



        try {
            // Getting the registry
            Registry registry = LocateRegistry.getRegistry("192.168.178.76", PORT);

            // Looking up the registry for the remote object
            DBInterface dbobj = (DBInterface) registry.lookup("DBInterface");
            //insertCentro(dbobj,"Joe","Erba","ER", CentroVaccinale.Tipologia.OSPEDALIERO, IndirizzoComposto.Qualificatore.VIA,"Alserio",11,"22036");
            System.out.println("Remote method invoked ");


        } catch (Exception e) {
            System.err.println("Client exception: " + e.toString());
            e.printStackTrace();
        }

    }
    public static void insertCentro(DBInterface dbobj, CentroVaccinale centro) throws SQLException, RemoteException {

        IndirizzoComposto indirizzo = centro.getIndirizzo();

        String nome = putApices( centro.getNomeCentro() );
        String comune = putApices( indirizzo.getComune() );
        String sigla = putApices( indirizzo.getSiglaProvincia() );
        String qualificatore = putApices( indirizzo.getQualificatore().toString() );
        String nome_via = putApices( indirizzo.getNomeVia() );
        String num_civico = putApices( Integer.toString(indirizzo.getNumCivico()) );
        String cap = putApices( indirizzo.getCap() );
        String tipologia = putApices(centro.getTipologia().toString());

        String ins_centro = "INSERT INTO centrovaccini(nome,comune,sigla,qualificatore,nome_via,num_civico,cap,Tipologia)\n"
                + "VALUES(" + nome +","+ comune +","+ sigla +","+ qualificatore +","
                + nome_via +","+ num_civico +","+ cap +","+ tipologia + ")";
        dbobj.executeQuery(ins_centro);

    }
    public static void insertVaccinato(DBInterface dbobj, Vaccinato vaccinato, String cod_centro) throws SQLException, RemoteException {

        String cod_fiscale = putApices(vaccinato.getCodiceFiscale());
        String nome = putApices(vaccinato.getNome());
        String cognome = putApices(vaccinato.getCognome());
        String data = putApices(vaccinato.getData().toString());
        String identificativo = putApices(Integer.toString(vaccinato.getId()));
        String vaccino = putApices(vaccinato.getVaccino().toString());
        cod_centro = putApices(cod_centro);

        String ins_vaccinato = "INSERT INTO Vaccinato(cod_fiscale,nome,cognome,data,identificativo,vaccino,cod_centro)\n" +
                "VALUES(" + cod_fiscale +","+ nome +","+ cognome +","+ data +","+ identificativo +","+ vaccino +","+ cod_centro + ")";
        dbobj.executeQuery(ins_vaccinato);
    }

    public static void insertIscritto(DBInterface dbobj, Cittadino cittadino) throws SQLException, RemoteException {

        String email = putApices(cittadino.getEmail());
        String username = putApices(cittadino.getLogin().getUserId());
        String password = putApices(cittadino.getLogin().getPassword());
        String cod_fiscale = putApices(cittadino.getCodiceFiscale());

        String ins_iscritto = "INSERT INTO Iscritto VALUES(" + email +","+ username +","+ password +","+ cod_fiscale + ")";
        dbobj.executeQuery(ins_iscritto);
    }

    public static void insertEvento(DBInterface dbobj, EventoAvverso eventoAvverso, String codice_centro) throws SQLException, RemoteException {

        String cod_centro = putApices(codice_centro);
        String evento = putApices(eventoAvverso.getEvento());
        String indice = putApices(Integer.toString(eventoAvverso.getIndice()));
        String note = putApices(eventoAvverso.getNoteOpzionali());




        String ins_evento = "INSERT INTO Evento VALUES(" + cod_centro +","+ evento +","+ indice +","+ note + ")";
        dbobj.executeQuery(ins_evento);
    }


    ///Utils

    private static String putApices(String s){ return "'" + s + "'";}


}
