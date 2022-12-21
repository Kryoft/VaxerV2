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
import java.util.ArrayList;
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

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>CentroVaccinale</code> nel database, tabella CENTROVACCINI
     *
     * @param centro l'oggetto <code>CentroVaccinale</code> da inserire
     * @author Manuel Marceca
     */
    public static void insertCentro(CentroVaccinale centro) throws SQLException, RemoteException {

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
        DBInterface.executeQuery(ins_centro);

    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo Vaccinato nel database, tabella VACCINATI,
     * e fornire l'identificativo unico rappresentante il vaccinato, il quale è generato dal database.
     *
     * @param vaccinato l'oggetto <code>Vaccinato</code> da inserire
     * @return L'identificativo univoco del vaccinato
     * @author Manuel Marceca
     */

    //TODO Gestione cod_centro restituito non valido (valore -1)
    public static int insertVaccinato(Vaccinato vaccinato) throws SQLException, RemoteException {

        String cod_centro = Integer.toString(getIdCentroByName(vaccinato.getNomeCentro()));

        String cod_fiscale = putApices(vaccinato.getCodiceFiscale());
        String nome = putApices(vaccinato.getNome());
        String cognome = putApices(vaccinato.getCognome());
        String data = putApices(vaccinato.getData().toString());
        //String identificativo = putApices(Integer.toString(vaccinato.getId()));
        String vaccino = putApices(vaccinato.getVaccino().toString());
        cod_centro = putApices(cod_centro);

        //String ins_vaccinato = "INSERT INTO Vaccinati(cod_fiscale,nome,cognome,data,identificativo,vaccino,cod_centro)\n" +
        //        "VALUES(" + cod_fiscale +","+ nome +","+ cognome +","+ data +","+ identificativo +","+ vaccino +","+ cod_centro + ")";
        String ins_vaccinato = "INSERT INTO Vaccinati(cod_fiscale,nome,cognome,data,vaccino,cod_centro)\n" +
                "VALUES(" + cod_fiscale +","+ nome +","+ cognome +","+ data +","+ vaccino +","+ cod_centro + ")";

        DBInterface.executeQuery(ins_vaccinato);

        return getVaccinatoIdByCF(cod_fiscale);
    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>Cittadino</code> nel database, tabella <code>Iscritti</code>
     *
     * @param cittadino l'oggetto <code>Cittadino</code> da inserire
     * @author Manuel Marceca
     */
    public static void insertIscritto(Cittadino cittadino) throws SQLException, RemoteException {

        String email = putApices(cittadino.getEmail());
        String username = putApices(cittadino.getLogin().getUserId());
        String password = putApices(cittadino.getLogin().getPassword());
        String cod_fiscale = putApices(cittadino.getCodiceFiscale());

        String ins_iscritto = "INSERT INTO Iscritti VALUES(" + email +","+ username +","+ password +","+ cod_fiscale + ")";
        DBInterface.executeQuery(ins_iscritto);
    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>EventoAvverso</code> nel database, tabella <code>EVENTI</code>
     *
     * @param eventoAvverso l'oggetto <code>EventoAvverso</code> da inserire
     * @author Manuel Marceca
     */
    public static void insertEvento(EventoAvverso eventoAvverso) throws SQLException, RemoteException {

        String codice_centro = Integer.toString(getIdCentroByName(eventoAvverso.getNomeCentro()));

        String cod_centro = putApices(codice_centro);
        String evento = putApices(eventoAvverso.getEvento());
        String indice = putApices(Integer.toString(eventoAvverso.getIndice()));
        String note = putApices(eventoAvverso.getNoteOpzionali());




        String ins_evento = "INSERT INTO Eventi VALUES(" + cod_centro +","+ evento +","+ indice +","+ note + ")";
        //dbobj.executeQuery(ins_evento);
        DBInterface.executeQuery(ins_evento);
    }

    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>CentroVaccinale</code> dato
     * il relativo campo <code>nome_centro</code>
     *
     * @param nome_centro il nome del centro vaccinale il cui oggetto si desidera ottenere
     * @return l'oggetto <code>CentroVaccinale</code> relativo al nome del centro dato. Ritorna <code>null</code>
     * se il centro non è stato trovato
     * @author Manuel Marceca
     */
    public static CentroVaccinale getCentroVaccinaleByName(String nome_centro){
        nome_centro = putApices(nome_centro);

        String select_centro = "SELECT * FROM CentroVaccini WHERE Nome = " + nome_centro;
        CentroVaccinale centro = null;
        try {
            Statement st = DBInterface.connected().createStatement();
            ResultSet result_centri = st.executeQuery(select_centro);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (result_centri.next()){

                //long codice_centro = result_centri.getLong("Codice");
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

    /**
     * Metodo utilizzato per ottenere dal database il codice univoco di un centro vaccinale dato
     * il suo nome
     *
     * @param nome_centro il nome del centro vaccinale del quale si desidera ottenere il codice
     * @return Il codice del relativo nome del centro dato. Ritorna <code>-1</code> se il codice non è stato trovato
     * @author Manuel Marceca
     */
    public static int getIdCentroByName(String nome_centro){
        String select_centro = "SELECT Codice FROM CentroVaccini WHERE Nome = " + nome_centro + ";";
        long codice_centro = -1;
        try {
            ResultSet result_centro = DBInterface.executeQuery(select_centro);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (result_centro.next()){
                codice_centro = result_centro.getLong("Codice");
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return (int)codice_centro;
    }

    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>Cittadino</code> dato
     * il suo username
     *
     * @param username l'username del cittadino che si desidera ottenere
     * @return Un oggetto di tipo <code>Cittadino</code> relativo all'username dato. Ritorna <code>null</code>
     *          se non è stata trovata alcuna corrispondenza
     * @author Manuel Marceca
     */
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

    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>Vaccinato</code> dato
     * il suo codice fiscale
     *
     * @param cf il codice fiscale corrispondente al vaccinato che si desidera ottenere
     * @return Un oggetto di tipo <code>Vaccinato</code> relativo al codice fiscale dato. Ritorna <code>null</code>
     *          se non è stata trovata alcuna corrispondenza
     * @author Manuel Marceca
     */
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


                vaccinato = new Vaccinato(rs_data, rs_vaccino, rs_nome_centro, rs_id, rs_nome,
                        rs_cognome, cf);
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return vaccinato;
    }

    /**
     * Metodo utilizzato per ottenere dal database l'identificativo univoco di un vaccinato dato il
     * suo codice fiscale
     *
     * @param cf il codice fiscale corrispondente al vaccinato che si desidera ottenere
     * @return l'identificativo univoco del vaccinato relativo al codice fiscale dato. Ritorna <code>-1</code>
     *          se l'identificativo non è stato trovato
     * @author Manuel Marceca
     */
    public static int getVaccinatoIdByCF(String cf){
        String select_vaccinato = "SELECT Identificativo FROM Vaccinati WHERE Cod_Fiscale = " + cf + ";";
        int identificativo = -1;
        try {
            ResultSet rs_vaccinato = DBInterface.executeQuery(select_vaccinato);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            if (rs_vaccinato.next()){
                long rs_id = rs_vaccinato.getLong("Identificativo");
                identificativo = (int)rs_id;
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return identificativo;
    }


    ///Utils

    /**
     * Metodo utile alla preparazione delle stringhe in modo che soddisfino la sintassi necessaria all'insert
     * dei dati nel database.
     *
     * @param s una stringa
     * @return la stringa data in input, con apici posti prima e dopo di essa
     * @author Manuel Marceca
     */
    private static String putApices(String s){ return "'" + s + "'";}

    public static ArrayList<String> cercaCentri(String nome_centro){
        final String select_centri = "SELECT Nome FROM CentroVaccini WHERE Nome LIKE '" + nome_centro + "%';";
        ArrayList<String> nomi_trovati = new ArrayList<>();
        try {
            ResultSet rs_centri = DBInterface.executeQuery(select_centri);

            //int num_colonne = result_centri.getMetaData().getColumnCount();
            while (rs_centri.next()){
                nomi_trovati.add(rs_centri.getString("Nome"));
            }

        }catch(SQLException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return nomi_trovati;
    }


}
