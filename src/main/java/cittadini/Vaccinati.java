/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import java.util.Date;

/**
 * classe utilizzata per l'inserimento dei vaccinati da parte dei centri vaccinali;
 * Estende la classe <code> Cittadini </code>.
 *
 * @author daniele Caspani
 */
public class Vaccinati extends Cittadini {

    private Date data = new Date();
    private Vaccino vaccino;

    ;
    public Vaccinati() {

    }
    public Vaccinati(Date data, Vaccino vaccino, String nome_centro, short id, String nome, String cognome, String codicefiscale) {
        super(nome_centro, id, nome, cognome, codicefiscale);
        this.data = data;
        this.vaccino = vaccino;
    }

    /**
     * restituisce il valore della data di vaccinazione
     *
     * @return
     */
    public Date getData() {
        return data;
    }

    /**
     * assegna un valore a data
     *
     * @param data
     */
    public void setData(Date data) {
        this.data = data;
    }

    /**
     * restituisce il tipo di vaccino
     *
     * @return
     * @author Daniele Caspani
     */
    public Vaccino getVaccino() {
        return vaccino;
    }

    /**
     * assegna un valore a vaccino
     *
     * @param vaccino
     */
    public void setVaccino(Vaccino vaccino) {
        this.vaccino = vaccino;
    }

    /**
     * converte un oggetto in stringa
     *
     * @return
     */
    @Override
    public String toString() {
        return data + "," + vaccino + "," + super.toString();
    }

    /**
     * classe enumerativa che può assumere solo i valori dei diversi tipi di vaccino che possono essere somministrati
     *
     * @author Daniele Caspani
     */
    public enum Vaccino {JJ, Moderna, AstraZeneca, Pfizer}
}