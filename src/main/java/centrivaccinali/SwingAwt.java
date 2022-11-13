/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import cittadini.Vaccinati;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;

/**
 * classe utilizzata per effettuare alcune operazioni riguardanti la classe JFrame
 *
 * @author daniele Caspani
 */
public class SwingAwt {

    /**
     * metodo che permette di gestire la scelta del tipo di vaccino tramite un item combobox
     *
     * @param jvaccino combobox contenente i valori che può assumere una variabile di tipo <code> Vaccino </code>
     * @return tipo di vaccino scelto
     * @author Daniele Caspani
     */
    public Vaccinati.Vaccino DecidiVaccino(JComboBox<String> jvaccino) {
        Vaccinati.Vaccino vaccino = null;

        if (jvaccino.getSelectedItem() == "JJ") {
            vaccino = Vaccinati.Vaccino.JJ;
        }
        if (jvaccino.getSelectedItem() == "AstraZeneca") {
            vaccino = Vaccinati.Vaccino.AstraZeneca;
        }
        if (jvaccino.getSelectedItem() == "Moderna") {
            vaccino = Vaccinati.Vaccino.Moderna;
        }
        if (jvaccino.getSelectedItem() == "Pfizer") {
            vaccino = Vaccinati.Vaccino.Pfizer;
        }
        return vaccino;
    }

    /**
     * metodo che permette di gestire la scelta del tipo di centro vaccinale tramite un item combobox
     *
     * @param jtipologia combobox contenente tutti i valori che può assumere una variabile di tipo <code> Tipologia </code>
     * @return Tipologia centro scelta
     * @author Daniele Caspani
     */
    public StruttureVaccinali.Tipologia DecidiTipologia(JComboBox<String> jtipologia) {
        StruttureVaccinali.Tipologia tipologia = null;
        if (jtipologia.getSelectedItem() == "Ospedaliero") {
            tipologia = StruttureVaccinali.Tipologia.OSPEDALIERO;
        }
        if (jtipologia.getSelectedItem() == "Aziendale") {
            tipologia = StruttureVaccinali.Tipologia.AZIENDALE;
        }
        if (jtipologia.getSelectedItem() == "Hub") {
            tipologia = StruttureVaccinali.Tipologia.HUB;
        }
        return tipologia;
    }

    /**
     * metodo che permette di gestire la scelta del tipo di qualificatore per l'indirizzo tramite un item combobox
     *
     * @param jqualifier combobox con tutti i valori di una variabile di tipo qualificatore
     * @return qualificatore scelto
     * @author Daniele Caspani
     */
    public IndirizzoComposto.Qualificatore DecidiQualificatore(JComboBox<String> jqualifier) {
        IndirizzoComposto.Qualificatore qualifier = null;

        if (jqualifier.getSelectedItem() == "Via") {
            qualifier = IndirizzoComposto.Qualificatore.VIA;
        }
        if (jqualifier.getSelectedItem() == "Viale") {
            qualifier = IndirizzoComposto.Qualificatore.VIALE;
        }
        if (jqualifier.getSelectedItem() == "Piazza") {
            qualifier = IndirizzoComposto.Qualificatore.PIAZZA;
        }
        return qualifier;
    }

    /**
     * Metodo utile per selezionare il bordo di un jtextfield se il suo contenuto è vuoto
     *
     * @param casuale stringa contenente il valore da analizzare
     * @param testo   jTextfield selezionato
     * @param border  bordo di default
     * @author Daniele Caspani
     */
    public void Bordo(String casuale, JTextField testo, Border border) {
        if (casuale.equals("") || testo.getForeground() == Color.lightGray)
            testo.setBorder(new LineBorder(Color.RED, 3, true));
        else
            testo.setBorder(border);
    }
}