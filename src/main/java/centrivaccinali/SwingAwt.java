/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import cittadini.EventoAvverso;
import cittadini.Vaccinato;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * Classe utilizzata per effettuare alcune operazioni riguardanti la classe JFrame
 *
 * @author Daniele Caspani
 */
public abstract class SwingAwt {

    /**
     * Metodo che permette di gestire la scelta del tipo di vaccino tramite un item JComboBox
     *
     * @param vaccino_combo JComboBox contenente i valori che può assumere una variabile di tipo <code>Vaccino</code>
     * @return tipo di vaccino scelto
     * @author Daniele Caspani
     */
    public static Vaccinato.Vaccino decidiVaccino(JComboBox<String> vaccino_combo) {
        Vaccinato.Vaccino vaccino = null;

        if (vaccino_combo.getSelectedItem() == "JJ") {
            vaccino = Vaccinato.Vaccino.JJ;
        }
        if (vaccino_combo.getSelectedItem() == "AstraZeneca") {
            vaccino = Vaccinato.Vaccino.AstraZeneca;
        }
        if (vaccino_combo.getSelectedItem() == "Moderna") {
            vaccino = Vaccinato.Vaccino.Moderna;
        }
        if (vaccino_combo.getSelectedItem() == "Pfizer") {
            vaccino = Vaccinato.Vaccino.Pfizer;
        }
        return vaccino;
    }

    /**
     * Metodo che controlla la stringa fornita come parametro e la confronta con un enumerativo di eventi avversi
     * per determinare quale è stato scelto e restituirlo.
     *
     * @param nome_evento stringa rappresentante l'evento avverso selezionato
     * @return tipo enumerativo corrispondente all'evento avverso scelto
     * @author Manuel Marceca
     */
    public static EventoAvverso.Eventi decidiEvento(String nome_evento) {
        EventoAvverso.Eventi evento = null;

        for (EventoAvverso.Eventi e: EventoAvverso.Eventi.values()) {
            if (e.toString().equals(nome_evento)) {
                evento = e;
            }
        }

        return evento;
    }

    /**
     * Metodo che permette di gestire la scelta del tipo di centro vaccinale tramite un item JComboBox
     *
     * @param tipologia_combo JComboBox contenente tutti i valori che può assumere una variabile di tipo <code>Tipologia</code>
     * @return Tipologia centro scelta
     * @author Daniele Caspani
     */
    public static CentroVaccinale.Tipologia decidiTipologia(JComboBox<String> tipologia_combo) {
        CentroVaccinale.Tipologia tipologia = null;
        if (tipologia_combo.getSelectedItem() == "Ospedaliero") {
            tipologia = CentroVaccinale.Tipologia.OSPEDALIERO;
        }
        if (tipologia_combo.getSelectedItem() == "Aziendale") {
            tipologia = CentroVaccinale.Tipologia.AZIENDALE;
        }
        if (tipologia_combo.getSelectedItem() == "Hub") {
            tipologia = CentroVaccinale.Tipologia.HUB;
        }
        return tipologia;
    }

    /**
     * Metodo che permette di gestire la scelta del tipo di qualificatore per l'indirizzo tramite un item JComboBox
     *
     * @param qualifier_combo JComboBox con tutti i valori di una variabile di tipo <code>Qualificatore</code>
     * @return Qualificatore scelto
     * @author Daniele Caspani
     */
    public static IndirizzoComposto.Qualificatore decidiQualificatore(JComboBox<String> qualifier_combo) {
        IndirizzoComposto.Qualificatore qualifier = null;

        if (qualifier_combo.getSelectedItem() == "Via") {
            qualifier = IndirizzoComposto.Qualificatore.VIA;
        }
        if (qualifier_combo.getSelectedItem() == "Viale") {
            qualifier = IndirizzoComposto.Qualificatore.VIALE;
        }
        if (qualifier_combo.getSelectedItem() == "Piazza") {
            qualifier = IndirizzoComposto.Qualificatore.PIAZZA;
        }
        return qualifier;
    }

    /**
     * Metodo utile per rendere il bordo di un JTextField rosso se il suo
     * contenuto è vuoto o se il colore del testo è grigio chiaro
     *
     * @param testo JTextField selezionato
     * @author Daniele Caspani
     * @author Cristian Corti
     */
    public static void modificaBordo(JTextField testo) {
        if (testo.getText().isBlank() || testo.getForeground() == Color.LIGHT_GRAY)
            testo.setBorder(new LineBorder(Color.RED, 3, true));
        else
            testo.setBorder(new JTextField().getBorder());
    }

    /**
     * Metodo di utilità per ottenere la posizione <code>x</code> o <code>y</code> dove
     * inserire un item per far sì che sia al centro dello schermo
     *
     * @param display_width_or_height la larghezza o lunghezza dello schermo
     * @param item_width_or_height la larghezza o lunghezza dell'item
     * @return un intero rappresentante la posizione <code>x</code> o <code>y</code> dove
     * inserire un item per far sì che sia al centro dello schermo
     * @author Manuel Marceca
     */
    public static int centerItemOnXorY(int display_width_or_height, int item_width_or_height) {
        return (display_width_or_height / 2) - (item_width_or_height / 2);
    }
}