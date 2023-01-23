package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.Registrazioni;
import centrivaccinali.SwingAwt;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Login;
import cittadini.Vaccinato;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe contenente tutti i metodi utili alla comunicazione con il database per mezzo del server.
 */
public class DBClient {

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>CentroVaccinale</code> nel database, tabella CENTROVACCINI
     *
     * @param centro l'oggetto <code>CentroVaccinale</code> da inserire
     * @author Manuel Marceca, Daniele Caspani
     */
    public static void insertCentro(CentroVaccinale centro) throws RemoteException {
        ClientGUI.dbobj.upData(SelectQuery.insertCentro(centro),"CentroVaccinale");
    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo Vaccinato nel database, tabella VACCINATI,
     * e fornire l'identificativo unico rappresentante il vaccinato, il quale è generato dal database.
     *
     * @param vaccinato l'oggetto <code>Vaccinato</code> da inserire
     * @return L'identificativo univoco del vaccinato
     * @author Manuel Marceca, Daniele Caspani
     */
    public static int insertVaccinato(Vaccinato vaccinato) throws RemoteException {
        ClientGUI.dbobj.upData(SelectQuery.insertVaccinato(vaccinato),"Vaccinato");
        return getVaccinatoIdByCF(vaccinato.getCodiceFiscale());
    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>Cittadino</code> nel database, tabella <code>Iscritti</code>
     *
     * @param cittadino l'oggetto <code>Cittadino</code> da inserire
     * @author Manuel Marceca, Daniele Caspani
     */
    public static void insertIscritto(Cittadino cittadino) throws RemoteException {
        ClientGUI.dbobj.upData(SelectQuery.insertIscritto(cittadino),"Iscritto");
    }

    /**
     * Metodo utilizzato per inserire un oggetto di tipo <code>EventoAvverso</code> nel database, tabella <code>EVENTI</code>
     *
     * @param eventoAvverso l'oggetto <code>EventoAvverso</code> da inserire
     * @author Manuel Marceca, Daniele Caspani
     */
    public static void insertEvento(EventoAvverso eventoAvverso) throws RemoteException {
        ClientGUI.dbobj.upData(SelectQuery.insertEvento(eventoAvverso),"Evento");
    }

    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>CentroVaccinale</code> dato
     * il relativo campo <code>nome_centro</code>
     *
     * @param nome_centro il nome del centro vaccinale il cui oggetto si desidera ottenere
     * @return l'oggetto <code>CentroVaccinale</code> relativo al nome del centro dato. Ritorna <code>null</code>
     * se il centro non è stato trovato
     * @author Manuel Marceca, Daniele Caspani
     */
    public static CentroVaccinale getCentroVaccinaleByName(String nome_centro){

        CentroVaccinale centro = null;
        LinkedList<String[]> l=null;
        String[] s= {"Nome","Tipologia","Nome_via","Num_civico","Comune","Sigla","Cap"};
        try {
            l = ClientGUI.dbobj.selectData(SelectQuery.getCentroVaccinaleByName(nome_centro),s);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        if(l.size()>0){
            int num_civico =Integer.parseInt(l.get(0)[3]);
            CentroVaccinale.Tipologia tipologia_centro = Utility.decidiTipo(l.get(0)[1]);
            IndirizzoComposto.Qualificatore qualificatore = Utility.decidiQualificatore(l.get(0)[1]);
            IndirizzoComposto indirizzo_centro = new IndirizzoComposto(qualificatore,l.get(0)[2], num_civico, l.get(0)[4],l.get(0)[5],l.get(0)[6]);

            centro = new CentroVaccinale(l.get(0)[0], tipologia_centro, indirizzo_centro);
        }

        return centro;
    }

    /**
     * Metodo utilizzato per ottenere dal database il codice univoco di un centro vaccinale dato
     * il suo nome
     *
     * @param nome_centro il nome del centro vaccinale del quale si desidera ottenere il codice
     * @return Il codice del relativo nome del centro dato. Ritorna <code>-1</code> se il codice non è stato trovato
     * @author Manuel Marceca, Daniele Caspani
     */
    public static int getIdCentroByName(String nome_centro){

        long codice_centro = -1;
        LinkedList<String[]> l=null;
        String[] s={"Codice"};
        try {
            l=ClientGUI.dbobj.selectData(SelectQuery.getIdCentroByName(nome_centro),s);
            codice_centro= Long.parseLong(l.get(0)[0]);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return (int)codice_centro;
    }

    /**
     * Metodo utilizzato per ottenere dal database il codice fiscale di un utente registrato dato
     * il suo username.
     *
     * @param username l'username dell'utente in questione
     * @return il codice fiscale associato all'utente
     * @author Manuel Marceca, Daniele Caspani
     */
    public static String getCfFromUsername(String username){

        String cod_fiscale = "";
        LinkedList<String[]> l=null;
        String[] s ={"cod_fiscale"};
        try {
            l = ClientGUI.dbobj.selectData(SelectQuery.getCfFromUsername(username),s);
            cod_fiscale = l.get(0)[0];

        }catch ( RemoteException exc){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, exc);
        }

        return cod_fiscale;
    }


    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>Cittadino</code> dato
     * il suo username
     *
     * @param username l'username del cittadino che si desidera ottenere
     * @return Un oggetto di tipo <code>Cittadino</code> relativo all'username dato. Ritorna <code>null</code>
     *          se non è stata trovata alcuna corrispondenza
     * @author Manuel Marceca, Daniele Caspani
     */
    public static Cittadino getCittadinoByUsername(String username){

        Cittadino iscritto = null;
        String[] s ={"Email","Username","Password","Nome_Centro","Identificativo","Nome_Vaccinato","Cognome","Cf_Vaccinato"};
        LinkedList<String[]> l =null;
        try {
            l = ClientGUI.dbobj.selectData(SelectQuery.getCittadinoByUsername(username), s);
            if (l.size() > 0) {
                int identificativo = Integer.parseInt(l.get(0)[4]);
                Login login = new Login(l.get(0)[1], l.get(0)[2]);

                iscritto = new Cittadino(l.get(1)[0], login, l.get(0)[3], identificativo,
                        l.get(0)[5], l.get(0)[6], l.get(0)[7]);
            }
            } catch(RemoteException e){
                throw new RuntimeException(e);
            }

        return iscritto;
    }

    /**
     * Metodo che, dato il nome di un centro vaccinale, ne restituisce le relative segnalazioni
     * sotto forma di <code>ArrayList</code> di oggetti di tipo <code>EventoAvverso</code>.
     *
     * @param nome_centro Il nome del centro vaccinale del quale si vogliono recuperare le segnalazioni
     * @return Un'<code>ArrayList</code> di oggetti di tipo <code>EventoAvverso</code> relativi al centro
     * @author Manuel Marceca, Daniele Caspani
     */
    public static ArrayList<EventoAvverso> getSegnalazioniByCentro(String nome_centro){

        String[] s = {"nome_evento","indice","note","cod_fiscale"};
        LinkedList<String[]> l= null;
        int id_centro = getIdCentroByName(nome_centro);
        String id_centro_string = String.valueOf(id_centro);
        int indice = 0;
        ArrayList<EventoAvverso> eventi = new ArrayList();
        try{
           l= ClientGUI.dbobj.selectData(SelectQuery.getSegnalazioniByCentro(id_centro_string),s);

        }catch( RemoteException se){
            Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
        }
        if(l.size()>0) {
            for (int i = 0; i < l.size(); i++) {
                indice = Integer.parseInt(l.get(i)[1]);
                EventoAvverso evento = new EventoAvverso(SwingAwt.decidiEvento(l.get(i)[0]), indice, l.get(i)[2], nome_centro, l.get(i)[3]);
                eventi.add(evento);
            }
        }

        return eventi;
    }

    /**
     * Metodo che, dato un evento, controlla che tale evento non sia già stato segnalato altre
     * volte dallo stesso utente nello stesso centro vaccinale.
     *
     * @param evento L'oggetto <code>EventoAvverso</code> di cui si sta effettuando la verifica
     * @return <code>true</code> se l'evento è già stato segnalato dallo stesso utente nello
     * stesso centro vaccinale
     * @author Manuel Marceca, Daniele Caspani
     */
    public static boolean checkEventoGiaSegnalato(EventoAvverso evento){
        try {
            return ClientGUI.dbobj.resultExists(SelectQuery.checkEventoGiaSegnalato(evento));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo che, dato un codice fiscale, verifica che non ci sia già un account associato allo
     * stesso codice fiscale. Questo metodo ha lo scopo di confermare il constraint di unicità
     * del campo codice fiscale del database.
     *
     * @param cf Il codice fiscale di cui si vuole verificare l'unicità
     * @return <code>true</code> se esiste già un account associato al codice fiscale <code>cf</code>
     * @author Manuel Marceca, Daniele Caspani
     */
    public static boolean checkCFinRegistrati(String cf){
        try {
            return ClientGUI.dbobj.resultExists(SelectQuery.checkCFinRegistrati(cf));
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Metodo che, dato il nome di un centro vaccinale, trae, per ogni tipo di segnalazione trovata,
     * le seguenti informazioni:
     * <ul><ul>
     *      <li>Nome dell'evento</li>
     *      <li>Media degli indici dell'evento</li>
     *      <li>Numero di segnalazioni dell'evento</li>
     *      <li>Standard deviazione degli indici dell'evento</li>
     * </ul></ul>
     * Tali informazioni sono poi impacchettate in un oggetto di tipo <code>Quadrupla</code> e incodate
     * in un'<code>ArrayList</code>, la quale sarà restituita al termine del metodo.
     *
     * @param nome_centro Il nome del centro dal quale si desidera estrapolare le informazioni sugli
     *                    eventi segnalati
     * @return Un'<code>ArrayList</code> di oggetti <code>Quadrupla</code>
     * @see Quadrupla
     * @author Manuel Marceca, Daniele Caspani
     */
    public static ArrayList<Quadrupla<String, Float, Integer,Float>> getValoriPerEventoAvverso(String nome_centro){

        ArrayList<Quadrupla<String, Float, Integer,Float>> risultati = new ArrayList<>();
        String[] s = {"Nome_Evento","Media_Indici","Numero_segnalazioni","Std_popolazione"};
        LinkedList<String[]> l= null;
        Float media ;
        int n_segnalazioni;
        float std ;
        try {
            l=ClientGUI.dbobj.selectData(SelectQuery.getValoriPerEventoAvverso(nome_centro),s);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
            for(int i=0;i<l.size();i++) {
                media= Float.parseFloat(l.get(i)[1]);
                n_segnalazioni= Integer.parseInt(l.get(i)[2]);
                std = Float.parseFloat(l.get(i)[3]);
                Quadrupla info_evento = new Quadrupla(l.get(i)[0], media, n_segnalazioni,std);
                risultati.add(info_evento);
            }

        return risultati;
    }



    /**
     * Metodo utilizzato per ottenere dal database un oggetto di tipo <code>Vaccinato</code> dato
     * il suo codice fiscale
     *
     * @param cf il codice fiscale corrispondente al vaccinato che si desidera ottenere
     * @return Un oggetto di tipo <code>Vaccinato</code> relativo al codice fiscale dato. Ritorna <code>null</code>
     *          se non è stata trovata alcuna corrispondenza
     * @author Manuel Marceca, Daniele Caspani
     */
    public static Vaccinato getVaccinatoByCF(String cf){

        Vaccinato vaccinato = null;
        String[] s ={"Data","Vaccino","Nome_Centro","Identificativo","Nome_Vaccinato","cognome"};
        LinkedList<String[]> l = null;
        try {
            l= ClientGUI.dbobj.selectData(SelectQuery.getVaccinatoByCF(cf),s);

            if(!l.isEmpty()) {
                int id = Integer.parseInt(l.get(0)[3]);
                Vaccinato.Vaccino rs_vaccino = Utility.decidiVaccino(l.get(0)[1]);
                vaccinato = new Vaccinato(null, rs_vaccino, l.get(0)[2], id, l.get(0)[4], l.get(0)[5], cf);
            }
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
     * @author Manuel Marceca, Daniele Caspani
     */
    public static int getVaccinatoIdByCF(String cf){

        int identificativo = -1;
        String[] s ={"Identificativo"};
        LinkedList<String[]> l = null;
        try {
            l=ClientGUI.dbobj.selectData(SelectQuery.getVaccinatoIdByCF(cf),s);
            identificativo= Integer.parseInt(l.get(0)[0]);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return identificativo;
    }

    /**
     * Metodo utile alla ricerca di centri vaccinali che, dato un nome di un centro vaccinale,
     * restituisce un'<code>ArrayList</code> di nomi di centri vaccinali recuperati dal database
     * il quale nome corrisponde, anche solo in parte, al nome dato.
     *
     * @param nome_centro Il nome del centro che si desidera cercare
     * @return Un'<code>ArrayList</code> di oggetti <code>String</code> contenenti i nomi dei
     *         centri vaccinali trovati
     * @author Manuel Marceca, Daniele Caspani
     */
    public static ArrayList<String> cercaCentriByNome(String nome_centro){

        ArrayList<String> nomi_trovati = new ArrayList<>();
        String[] s ={"Nome"};
        LinkedList<String[]> l = null;
        try {
            l=ClientGUI.dbobj.selectData(SelectQuery.cercaCentri(nome_centro),s);
            for(int i=0;i<l.size();i++) {
                nomi_trovati.add(l.get(i)[0]);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return nomi_trovati;
    }

    /**
     * Metodo utile alla ricerca di centri vaccinali che, dati il comune e la tipologia di centro vaccinale,
     * restituisce un'<code>ArrayList</code> di nomi di centri vaccinali recuperati dal database
     * le cui caratteristiche corrispondono a quelle date.
     *
     * @param comune Il comune del centro che si desidera cercare
     * @param tipologia La tipologia del centro che si desidera cercare
     * @return Un'<code>ArrayList</code> di oggetti <code>String</code> contenenti i nomi dei
     *         centri vaccinali trovati
     * @author Manuel Marceca, Daniele Caspani
     */
    public static ArrayList<String> cercaCentriByComuneETipologia(String comune, String tipologia){

        ArrayList<String> nomi_trovati = new ArrayList<>();
        String[] s ={"Nome"};
        LinkedList<String[]> l = null;
        try {
            l=ClientGUI.dbobj.selectData(SelectQuery.cercaCentriByComuneETipologia(comune, tipologia),s);
            for(int i=0;i<l.size();i++) {
                nomi_trovati.add(l.get(i)[0]);
            }
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }

        return nomi_trovati;
    }
}
