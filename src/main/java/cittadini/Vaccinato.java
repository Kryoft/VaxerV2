/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import java.util.Date;

/**
 * Classe utilizzata per l'inserimento dei vaccinati da parte dei centri vaccinali;
 * Estende la classe <code>Cittadini</code>.
 *
 * @author Daniele Caspani
 */
public class Vaccinato extends Cittadino {

    private Date data = new Date();
    private Vaccino vaccino;

    public Vaccinato() {
    }

    public Vaccinato(Date data, Vaccino vaccino, String nome_centro, int id, String nome, String cognome, String codice_fiscale) {
        super(nome_centro, id, nome, cognome, codice_fiscale);
        this.data = data;
        this.vaccino = vaccino;
    }

    public Vaccinato(Date data, Vaccino vaccino, String nome_centro, String nome, String cognome, String codice_fiscale){
        super(nome_centro, nome, cognome, codice_fiscale);
        this.data = data;
        this.vaccino = vaccino;
    }

    /**
     * Restituisce il valore della data di vaccinazione
     *
     * @return Data di vaccinazione del cittadino
     */
    public Date getData() {
        return data;
    }

    /**
     * Assegna un valore alla data
     *
     * @param data data in cui il cittadino ha effettuato la vaccinazione
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * Restituisce il tipo di vaccino
     *
     * @return Tipo di vaccino somministrato al cittadino
     * @author Daniele Caspani
     */
    public Vaccino getVaccino() {
        return vaccino;
    }

    /**
     * Assegna un valore a vaccino
     *
     * @param vaccino il vaccino somministrato al cittadino
     */
    public void setVaccino(Vaccino vaccino) {
        this.vaccino = vaccino;
    }

    /**
     * Converte un oggetto in stringa
     *
     * @return
     */
    @Override
    public String toString() {
        return data + "," + vaccino + "," + super.toString();
    }

    /**
     * Classe enumerativa che può assumere solo i valori dei diversi tipi di vaccino che possono essere somministrati
     *
     * @author Daniele Caspani
     */
    public enum Vaccino {JJ, Moderna, AstraZeneca, Pfizer}
}