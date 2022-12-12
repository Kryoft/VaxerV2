package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.Registrazioni;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Login;
import cittadini.Vaccinato;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBClient {

    DBManager db = new DBManager();
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
    public static int insertVaccinato(DBInterface dbobj, Vaccinato vaccinato, String cod_centro) throws SQLException, RemoteException {

        String cod_fiscale = putApices(vaccinato.getCodiceFiscale());
        String nome = putApices(vaccinato.getNome());
        String cognome = putApices(vaccinato.getCognome());
        String data = putApices(vaccinato.getData().toString());
        String identificativo = putApices(Integer.toString(vaccinato.getId()));
        String vaccino = putApices(vaccinato.getVaccino().toString());
        cod_centro = putApices(cod_centro);

        String ins_vaccinato = "INSERT INTO Vaccinati(cod_fiscale,nome,cognome,data,identificativo,vaccino,cod_centro)\n" +
                "VALUES(" + cod_fiscale +","+ nome +","+ cognome +","+ data +","+ identificativo +","+ vaccino +","+ cod_centro + ")";
        dbobj.executeQuery(ins_vaccinato);

        //TODO recuperare l'id generato dal db e restituirlo!
    }

    public static void insertIscritto(DBInterface dbobj, Cittadino cittadino) throws SQLException, RemoteException {

        String email = putApices(cittadino.getEmail());
        String username = putApices(cittadino.getLogin().getUserId());
        String password = putApices(cittadino.getLogin().getPassword());
        String cod_fiscale = putApices(cittadino.getCodiceFiscale());

        String ins_iscritto = "INSERT INTO Iscritti VALUES(" + email +","+ username +","+ password +","+ cod_fiscale + ")";
        dbobj.executeQuery(ins_iscritto);
    }

    public static void insertEvento(DBInterface dbobj, EventoAvverso eventoAvverso, String codice_centro) throws SQLException, RemoteException {

        String cod_centro = putApices(codice_centro);
        String evento = putApices(eventoAvverso.getEvento());
        String indice = putApices(Integer.toString(eventoAvverso.getIndice()));
        String note = putApices(eventoAvverso.getNoteOpzionali());




        String ins_evento = "INSERT INTO Eventi VALUES(" + cod_centro +","+ evento +","+ indice +","+ note + ")";
        dbobj.executeQuery(ins_evento);
    }

    public static CentroVaccinale getCentroVaccinaleByName(String nome_centro){
        String select_centro = "SELECT * FROM CentroVaccini WHERE Nome = " + nome_centro + ";";
        CentroVaccinale centro = null;
        try {
            ResultSet result_centri = DBInterface.executeQuery(select_centro);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (result_centri.next()){

                long codice_centro = result_centri.getLong("Codice");
                String rs_nome = result_centri.getString("Nome");
                String rs_sigla = result_centri.getString("Sigla");
                String rs_tipologia = result_centri.getString("Tipologia");

                String rs_comune = result_centri.getString("Comune");

                String rs_nome_via = result_centri.getString("Nome_via");
                int rs_num_civico = result_centri.getInt("Num_civico");
                String rs_cap = result_centri.getString("Cap");

                CentroVaccinale.Tipologia tipologia_centro = Utility.decidiTipo(rs_tipologia);

                IndirizzoComposto.Qualificatore qualificatore = Utility.decidiQualificatore(rs_tipologia);

                IndirizzoComposto indirizzo_centro = new IndirizzoComposto(qualificatore, rs_nome_via,
                        rs_num_civico, rs_comune, rs_sigla, rs_cap);

                centro = new CentroVaccinale(rs_nome, tipologia_centro, indirizzo_centro);
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return centro;
    }

    public static Cittadino getCittadinoByUsername(String username){
        String select_iscritto = "SELECT * FROM Iscritti, Vaccinati, Centrovaccini WHERE Username = " + username + ";";
        Cittadino iscritto = null;
        try {
            ResultSet rs_iscritto = DBInterface.executeQuery(select_iscritto);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (rs_iscritto.next()){

                String rs_email = rs_iscritto.getString("Email");
                String rs_nome_centro = rs_iscritto.getString("Centrovaccini(Nome)");
                String rs_nome = rs_iscritto.getString("Vaccinati(Nome)");
                String rs_cognome = rs_iscritto.getString("Cognome");
                String rs_cf = rs_iscritto.getString("Cod_Fiscale");

                String rs_username = rs_iscritto.getString("Username");
                String rs_password = rs_iscritto.getString("Password");

                int rs_id = rs_iscritto.getInt("Identificativo");

                Login login = new Login(rs_username, rs_password);

                iscritto = new Cittadino(rs_email, login, rs_nome_centro, rs_id,
                        rs_nome, rs_cognome, rs_cf);
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return iscritto;
    }

    public static Vaccinato getVaccinatoByCF(String cf){
        String select_vaccinato = "SELECT * FROM Vaccinati, CentroVaccini WHERE Cod_Fiscale = " + cf + ";";
        Vaccinato vaccinato = null;
        try {
            ResultSet rs_vaccinato = DBInterface.executeQuery(select_vaccinato);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (rs_vaccinato.next()){

                Date rs_data = rs_vaccinato.getDate("Data");
                Vaccinato.Vaccino rs_vaccino =
                        Utility.decidiVaccino(rs_vaccinato.getString("Vaccino"));
                String rs_nome_centro = rs_vaccinato.getString("CentroVaccini(Nome)");
                int rs_id = rs_vaccinato.getInt("Identificativo");
                String rs_nome = rs_vaccinato.getString("Vaccinati(Nome)");
                String rs_cognome = rs_vaccinato.getString("Cognome");


                vaccinato = new Vaccinato(rs_data, rs_vaccino, rs_nome_centro, id, rs_nome,
                        rs_cognome, cf);
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return vaccinato;
    }


    ///Utils

    private static String putApices(String s){ return "'" + s + "'";}


}
