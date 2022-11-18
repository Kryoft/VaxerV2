/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Si tratta di una classe costituita da vari metodi
 * utili e ripetuti all' interno del programma come la lettura e scrittura da file.
 *
 * @author Daniele Caspani
 */
public class Utility {

    public Utility() {
    }

    /**
     * metodo utile per calcolare lo spazio in memoria in un dato momento
     *
     * @author Daniele Caspani
     */
    public void run() {
        Runtime runtime = Runtime.getRuntime();
        long usedMemoryBefore = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Used Memory before: " + usedMemoryBefore);
        // working code here
        long usedMemoryAfter = runtime.totalMemory() - runtime.freeMemory();
        System.out.println("Memory increased: " + (usedMemoryAfter - usedMemoryBefore));
    }

    /**
     * metodo utile per definire il valore di un enum preso una stringa in ingresso.
     *
     * @param tipo
     * @return
     * @author Daniele Caspani
     */
    public StruttureVaccinali.Tipologia Deciditipo(String tipo) {
        if (tipo.equals("OSPEDALIERO")) {
            return StruttureVaccinali.Tipologia.OSPEDALIERO;
        }
        if (tipo.equals("AZIENDALE")) {
            return StruttureVaccinali.Tipologia.AZIENDALE;
        }
        if (tipo.equals("HUB")) {
            return StruttureVaccinali.Tipologia.HUB;
        }
        return null;
    }

    /**
     * metodo che ottiene un valore enum da una stringa in ingresso
     *
     * @param tipo
     * @return
     * @author Daniele Caspani
     */
    public IndirizzoComposto.Qualificatore DecidiQualifier(String tipo) {
        if (tipo.equals("PIAZZA")) {
            return IndirizzoComposto.Qualificatore.PIAZZA;
        }
        if (tipo.equals("VIA")) {
            return IndirizzoComposto.Qualificatore.VIA;
        }
        if (tipo.equals("VIALE")) {
            return IndirizzoComposto.Qualificatore.VIALE;
        }
        return null;
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
    public void ScriviFile(String file, String o) throws IOException, URISyntaxException {
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
     * <strong> CaricaFile </strong> è una funzione utilizzata per inserire gli oggetti memorizzati in un file in una struttura dati
     *
     * @param nfile percorso del file di testo selezionato
     * @return
     * @author daniele caspani
     */
    public HashSet CaricaFile(String nfile) {
        HashSet h = new HashSet();
        try (final Stream<String> stream = Files.lines(Paths.get(nfile))) {
            h = stream.collect(Collectors.toCollection(HashSet::new));
        } catch (IOException ex) {
            Logger.getLogger(Utility.class.getName()).log(Level.SEVERE, null, ex);
        }

        return h;
    }

    /**
     * <strong> CaricaFile1 </strong> è una funzione utilizzata per inserire gli oggetti memorizzati in un file in un ArrayList
     *
     * @param nfile percorso del file di testo selezionato
     * @return
     * @author daniele caspani
     */
    public ArrayList CaricaFile1(String nfile) {
        ArrayList<String> h = new ArrayList();
        try (final Stream<String> stream = Files.lines(Paths.get(nfile))) {
            h = stream.collect(Collectors.toCollection(ArrayList::new));
        } catch (IOException ex) {
            System.err.println("Non esistono dati per questo Centro");
        }

        return h;
    }

    /**
     * funzione utilizzata per la lettura di un file attraverso alcune modifiche.
     *
     * @param sc    valore intero che si riferisce alla n-esima stringa ottenuta nella lettura da file tramite l'operazione split
     * @param nfile percorso del file
     * @return
     */
    public HashSet leggiFile(int sc, String nfile) {
        HashSet<String> h = new HashSet();
        String[] a;
        try (final BufferedReader bR = new BufferedReader(
                new InputStreamReader(new FileInputStream(nfile)));) {
            String line;
            while ((line = bR.readLine()) != null) {
                if (!line.equals("")) {
                    a = line.split(",");
                    if (!a[sc].equals(""))
                        h.add(a[sc]);
                }
            }
        } catch (final IOException e) {
        }
        return h;
    }

    /**
     * metodo utilizzato per controllare se è già stato registrato nell' applicazione un dato centro
     *
     * @param sc
     * @param centro centro vaccinale preso in considerazione
     * @param nfile  percorso del file preso in considerazione
     * @return
     * @throws IOException
     */
    public boolean EsisteCentro(int sc, String centro, String nfile) throws IOException {
        HashSet<String> v = leggiFile(sc, nfile);
        return v.contains(centro);
    }

    /**
     * metodo utilizzato per costruire l'id e verificare che quest'ultimo non esista già
     *
     * @param sc
     * @param id
     * @param nfile percorso file
     * @return
     * @throws IOException
     */
    public short Idcontrol(int sc, String id, String nfile) throws IOException {
        HashSet<String> v = leggiFile(sc, nfile);
        if (v.size() < 65534) {
            Short Id = Short.parseShort(id);
            while (v.contains(String.valueOf(Id)) || Id == 0) {
                Id++;
            }
            return Id;
        }
        return 0;
    }

    /**
     * metodo utilizzato per cotrollare che il login sia effettivamente regolare
     *
     * @param l
     * @param nfile percorso del file selezionato
     * @return
     * @author Daniele Caspani
     */
    public boolean ControlloLogin(String l, String nfile) {
        // TODO: Gestire il caso in cui il file non esiste (altrimenti viene lanciata un'eccezione)
        HashSet<String> hs = CaricaFile(nfile);
        return hs.contains(l);
    }

    /**
     * metodo utilizzato per controllare la correttezza della coppia codice fiscale id
     *
     * @param l     codice fiscale
     * @param id    identificativo
     * @param nfile percorso file
     * @return
     */
    public boolean controllocf(String l, short id, String nfile) {
        HashSet h = CaricaFile(nfile);
        Iterator it = h.iterator();
        String[] a;
        while (it.hasNext()) {
            String s = (String) it.next();
            a = s.split(",");
            if (a.length == 9) {
                if (a[8].equals(l) && Short.parseShort(a[2]) == id)
                    return true;
            }
        }
        return false;
    }

    /**
     * metodo utilizzato per ottenere il comune di provenienza del centro selezionato
     *
     * @param centro
     * @param nfile
     * @return
     * @author Daniele Caspani
     */
    public String ControllaComune(String centro, String nfile) {
        HashSet h = CaricaFile(nfile);
        Iterator it = h.iterator();
        String[] a;
        while (it.hasNext()) {
            String s = (String) it.next();
            if (s.contains(centro)) {
                a = s.split(",");
                return a[5];
            }
        }
        return null;
    }
}