/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package shared;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import cittadini.Cittadino;
import cittadini.EventoAvverso;
import cittadini.Login;
import cittadini.Vaccinato;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Si tratta di una classe costituita da vari metodi utili e ripetuti all'interno
 * del programma come la lettura e scrittura da file.
 *
 * @author Daniele Caspani
 */
public abstract class Utility {

    private static Dimension screen_size = Toolkit.getDefaultToolkit().getScreenSize();

    /**
     * Metodo utile per calcolare lo spazio in memoria in un dato momento
     *
     * @author Daniele Caspani
     */
    public static void run() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore);
        // working code here
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore));
    }

    public static int getDisplayWidth() {
        return (int) screen_size.getWidth();
    }

    public static int getDisplayHeight() {
        return (int) screen_size.getHeight();
    }

    public static void setWindowLogo(JFrame frame, String file_name) {
        ImageIcon logo = new ImageIcon(ClassLoader.getSystemResource(file_name));
        frame.setIconImage(logo.getImage());
    }

    /**
     * Metodo che ritorna un valore enum rappresentante la tipologia di centro vaccinale
     * data una stringa in ingresso
     *
     * @param tipo La tipologia di centro vaccinale selezionata
     * @return un enumerativo della classe Tipologia
     * @author Manuel Marceca
     */
    public static CentroVaccinale.Tipologia decidiTipo(String tipo) {
        switch (tipo.toUpperCase()){
            case "HUB":
                return CentroVaccinale.Tipologia.HUB;
            case "OSPEDALIERO":
                return CentroVaccinale.Tipologia.OSPEDALIERO;
            case "AZIENDALE":
                return CentroVaccinale.Tipologia.AZIENDALE;

            default:
                return CentroVaccinale.Tipologia.OSPEDALIERO;
        }
    }

    /**
     * Metodo che ritorna un valore enum rappresentante il qualificatore dell'indirizzo
     * data una stringa in ingresso
     *
     * @param tipo Il tipo di qualificatore di un luogo fisico
     * @return un enumerativo della classe Qualificatore
     * @author Manuel Marceca
     */
    public static IndirizzoComposto.Qualificatore decidiQualificatore(String tipo) {
        switch (tipo.toUpperCase()){
            case "VIA":
                return IndirizzoComposto.Qualificatore.VIA;
            case "PIAZZA":
                return IndirizzoComposto.Qualificatore.PIAZZA;
            case "VIALE":
                return IndirizzoComposto.Qualificatore.VIALE;

            default:
                return IndirizzoComposto.Qualificatore.VIA;
        }
    }

    /**
     * Metodo che ritorna un valore enum rappresentante la tipologia di vaccino
     * data una stringa in ingresso
     *
     * @param tipo Il tipo di vaccino
     * @return un enumerativo della classe <code>Vaccino</code>
     * @author Manuel Marceca
     */
    public static Vaccinato.Vaccino decidiVaccino(String tipo) {
        switch (tipo.toUpperCase()){
            case "JJ":
                return Vaccinato.Vaccino.JJ;
            case "MODERNA":
                return Vaccinato.Vaccino.Moderna;
            case "ASTRAZENECA":
                return Vaccinato.Vaccino.AstraZeneca;
            case "PFIZER":
                return Vaccinato.Vaccino.Pfizer;

            default:
                return Vaccinato.Vaccino.Pfizer;
        }
    }



    /**
     * Metodo che permette di scrivere in un file tramite l'utilizzo di un buffer
     *
     * @param file percorso file su cui lavorare
     * @param o    oggetto da scrivere
     * @throws IOException
     * @throws URISyntaxException
     * @author Daniele Caspani
     */
    public static void scriviFile(String file, String o) throws IOException, URISyntaxException {
        File f = new File(file);
        if (!f.exists())
            // TODO: Gestire il caso in cui il percorso specificato non esiste (ad esempio se non esiste la cartella "/data/" durante la registrazione di un centro vaccinale)
            f.createNewFile();

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(f, true))) {
            bufferedWriter.newLine();
            bufferedWriter.write(o);
        } catch (IOException e) {
            System.out.println("Exception occurred: " + e.getMessage());
        }
    }

    /**
     * <strong>CaricaFile</strong> è una funzione utilizzata per inserire gli oggetti memorizzati in un file in una struttura dati
     *
     * @param file percorso del file di testo selezionato
     * @return
     * @author Daniele Caspani
     */
    public static HashSet<String> caricaFileInHashSet(String file) {
        HashSet<String> hash_set = new HashSet<>();
        try (final Stream<String> stream = Files.lines(Paths.get(file))) {
            hash_set = stream.collect(Collectors.toCollection(HashSet::new));
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }

        return hash_set;
    }

    /**
     * <strong>CaricaFile1</strong> è una funzione utilizzata per inserire gli oggetti memorizzati in un file in un ArrayList
     *
     * @param file percorso del file di testo selezionato
     * @return
     * @author Daniele Caspani
     */
    public static ArrayList<String> caricaFileInArrayList(String file) {
        ArrayList<String> array_list = new ArrayList<>();
        try (final Stream<String> stream = Files.lines(Paths.get(file))) {
            array_list = stream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ex) {
            System.err.println("Non esistono dati per questo Centro");
        }

        return array_list;
    }

    /**
     * Funzione utilizzata per la lettura di un file attraverso alcune modifiche.
     *
     * @param string_index  valore intero che si riferisce alla n-esima stringa ottenuta nella lettura da file tramite l'operazione split
     * @param file          percorso del file
     * @return
     */
    public static HashSet<String> leggiFile(int string_index, String file) {
        HashSet<String> hash_set = new HashSet<>();
        String[] string_array;
        try (final BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream(file)))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.equals("")) {
                    string_array = line.split(",");
                    if (!string_array[string_index].equals(""))
                        hash_set.add(string_array[string_index]);
                }
            }
        } catch (final IOException e) {
        }
        return hash_set;
    }

    /**
     * Metodo utilizzato per controllare se un dato centro è già stato registrato nell'applicazione
     *
     * @param string_index  valore intero che si riferisce alla n-esima stringa ottenuta nella lettura da file tramite l'operazione split
     * @param centro        nome del centro vaccinale preso in considerazione
     * @param file          percorso del file preso in considerazione
     * @return
     * @throws IOException
     */
    /*
    public static boolean esisteCentro(int string_index, String centro, String file) throws SQLException{
        HashSet<String> hash_set = leggiFile(string_index, file);
        return hash_set.contains(centro);
    }
    */

    /**
     * Metodo utilizzato per controllare se un dato nome di centro è già stato registrato nel database
     *
     * @param nome_centro  Il nome del centro vaccinale
     * @return <code>true</code> se il dato nome è già presente nel database, altrimento <code>false</code>
     * @throws SQLException
     * @author Manuel Marceca
     */
    public static boolean esisteCentro(String nome_centro) throws SQLException{
        return DBClient.getCentroVaccinaleByName(nome_centro) != null;
    }

    /**
     * Metodo utilizzato per costruire l'id e verificare che quest'ultimo non esista già
     *
     * @param string_index  valore intero che si riferisce alla n-esima stringa ottenuta nella lettura da file tramite l'operazione split
     * @param id
     * @param file          percorso del file preso in considerazione
     * @return
     * @throws IOException
     */

    /* NON PIU' NECESSARIA!
    public static short idControl(int string_index, String id, String file) throws IOException {
        HashSet<String> hash_set = leggiFile(string_index, file);
        if (hash_set.size() < 65534) {
            Short id_number = Short.parseShort(id);
            while (hash_set.contains(String.valueOf(id_number)) || id_number == 0) {
                id_number++;
            }
            return id_number;
        }
        return 0;
    }

     */

    /*
     * Metodo utilizzato per controllare che il login sia effettivamente regolare
     *
     * @param login
     * @param file percorso del file selezionato
     * @return
     * @author Daniele Caspani
     */
    /*
    public static boolean controlloLogin(String login, String file) {
        HashSet<String> hash_set = caricaFileInHashSet(file);
        return hash_set.contains(login);
    }
     */

    /**
     * Metodo utilizzato per controllare che il login sia effettivamente regolare
     *
     * @param login
     * @return <code>true</code> se la coppia (username, password) è presente nel database.
     * @author Manuel Marceca
     */
    public static boolean loginOk(Login login) {
        Cittadino cittadino = DBClient.getCittadinoByUsername(login.getUserId());

        if(cittadino != null){
            return login.getPassword().equals(cittadino.getLogin().getPassword());
        }
        return false;
    }

    /**
     * Metodo utilizzato per controllare se l'username è già in uso.
     *
     * @param username
     * @return <code>true</code> se l'username è presente nel database.
     * @author Manuel Marceca
     */
    public static boolean esisteUsername(String username){
        return DBClient.getCittadinoByUsername(username) != null;
    }

    /*
     * Metodo utilizzato per controllare la correttezza della coppia codice fiscale id
     *
     * @param codice_fiscale  codice fiscale
     * @param id              identificativo
     * @param file            percorso file
     * @return
     */
    /*
    public static boolean controlloCF(String codice_fiscale, short id, String file) {
        HashSet<String> hash_set = caricaFileInHashSet(file);
        Iterator<String> iterator = hash_set.iterator();
        String[] string_array;
        while (iterator.hasNext()) {
            String s = iterator.next();
            string_array = s.split(",");
            if (string_array.length == 9) {
                if (string_array[8].equals(codice_fiscale) && Short.parseShort(string_array[2]) == id)
                    return true;
            }
        }
        return false;
    }
     */

    /**
     * Metodo utilizzato per controllare la correttezza della coppia codice fiscale id nel database
     *
     * @param codice_fiscale  codice fiscale
     * @param id              identificativo
     * @return <code>true</code> se la coppia è presente nel database
     * @author Manuel Marceca
     */
    public static boolean controlloCoppiaCFId(String codice_fiscale, int id) {
        Vaccinato vaccinato = DBClient.getVaccinatoByCF(codice_fiscale);
        return vaccinato != null && vaccinato.getId() == id;
    }

    /**
     * Metodo utilizzato per controllare la correttezza se un codice fiscale è già associato ad un account
     *
     * @param codice_fiscale  codice fiscale
     * @return <code>true</code> se la il codice fiscale è già associato ad un account
     * @author Manuel Marceca
     */
    public static boolean esisteCF(String codice_fiscale) {
        Vaccinato vaccinato = DBClient.getVaccinatoByCF(codice_fiscale);
        return vaccinato != null;
    }


    /**
     * Metodo utilizzato per ottenere il comune di provenienza del centro selezionato
     *
     * @param centro
     * @param file
     * @return
     * @author Daniele Caspani
     */

    //TODO METODO MAI USATO! DEFINIRE SE UTILE
    public static String controllaComune(String centro, String file) {
        HashSet<String> hash_set = caricaFileInHashSet(file);
        Iterator<String> iterator = hash_set.iterator();
        String[] string_array;
        while (iterator.hasNext()) {
            String s = iterator.next();
            if (s.contains(centro)) {
                string_array = s.split(",");
                return string_array[5];
            }
        }
        return null;
    }


    public static String inserisciNuovoCentro(CentroVaccinale nuovo_centro){
        try{
            DBClient.insertCentro(nuovo_centro);
            return "";
        }catch(RemoteException e){
            return "Errore nel database";
        }
    }


    public static int inserisciNuovoVaccinato(Vaccinato nuovo_vaccinato) throws  SQLException, RemoteException{

       return DBClient.insertVaccinato(nuovo_vaccinato);
    }

    public static String inserisciNuovoEvento(EventoAvverso evento) throws SQLException, RemoteException{
        try{
            DBClient.insertEvento(evento);
            return "";
        }catch(RemoteException e){
            return "Errore nel database";
        }
    }

    /*
    public static ArrayList<String> getVaccinatiNomiList(String nome_centro){
        ArrayList<Vaccinato> vaccinati = DBClient.getVaccinatiListByCentro(nome_centro);

        ArrayList<String> nomi_vaccinati = new ArrayList<>();
        for(Vaccinato vaccinato: vaccinati){
            nomi_vaccinati.add(vaccinato.getNome());
        }
    }

     */
}