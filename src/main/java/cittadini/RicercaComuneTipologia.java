/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.SwingAwt;
import shared.Utility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class RicercaComuneTipologia extends Ricerca {

    private final JLabel comune_label = new JLabel("Comune"),
                            tipologia_label = new JLabel("Tipologia Centro");
    private final JTextField comune_txt = new JTextField();

    public RicercaComuneTipologia() {
        initWindow();
    }

    /**
     * Metodo utilizzato per l'inizializzazione dei componenti JFrame per quanto riguarda la ricerca
     * per comune e tipologia
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Ricerca per Comune e Tipologia");

        background.add(tipologia_label, 0);
        backgroundSettings(0, new Rectangle(270, 90,            //tipologia_label
                520, 120), 16, 1, false);

        background.add(centro_combo, 0);
        backgroundSettings(0, new Rectangle(410, 130,           //centro_combo
                310, 40), 15, 0, false);

        background.add(comune_label, 0);
        backgroundSettings(0, new Rectangle(910, 90,            //comune_label
                520, 120), 16, 1, false);

        background.add(comune_txt, 0);
        backgroundSettings(0, new Rectangle(1040, 130,          //comune_txt
                310, 40), 15, 0, false);

        background.add(cerca, 0);
        backgroundSettings(0, new Rectangle(1350, 130,          //cerca
                40, 40), 15, 0, true);

        background.add(annulla, 0);
        backgroundSettings(0, new Rectangle(24, 965,            //annulla
                120, 35), 15, 1, false);

        background.add(conferma, 0).setEnabled(false);
        backgroundSettings(0, new Rectangle(1790, 965,          //conferma
                120, 35), 15, 1, false);


        cerca.addActionListener(this);
        conferma.addActionListener(this);
        annulla.addActionListener(this);

        creaLista();

        /*
         * A lista_centri viene aggiunto SelectionListener per assegnare all'oggetto di tipo
         * StruttureVaccinali strutture_vaccinali l'elemento selezionato
         */
        lista_centri.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Iterator<String> it = hash_set.iterator();
                String[] a;
                IndirizzoComposto ic;
                while (it.hasNext()) {
                    String s = it.next();
                    a = s.split(",");
                    if (a[0].equals(lista_centri.getSelectedValue())) {
                        ic = new IndirizzoComposto(Utility.decidiQualificatore(a[2]), a[3], Integer.parseInt(a[4]), a[5], a[6], a[7]);
                        strutture_vaccinali = new CentroVaccinale(a[0], Utility.decidiTipo(a[1]), ic);
                        break;
                    }
                }
            }
        });

        setVisible(true);
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener
     *
     * @param e
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == cerca) {
            conferma.setEnabled(false);
            lista_centri.setBackground(Color.LIGHT_GRAY);
            hash_set.clear();
            list_model.removeAllElements();
            String comune = comune_txt.getText().toUpperCase();
            boolean s1 = false;
            if (SwingAwt.decidiTipologia(centro_combo) != null && !comune.equals("")) {

                hash_set = Utility.caricaFileInHashSet("./data/CentriVaccinali.dati.txt");
                Iterator<String> it = hash_set.iterator();
                String[] a;
                while (it.hasNext()) {

                    String s = it.next();
                    if (!s.equals("")) {
                        a = s.split(",");
                        if (comune.equals(a[5]) && SwingAwt.decidiTipologia(centro_combo) == Utility.decidiTipo(a[1])) {
                            list_model.addElement(a[0]);
                            s1 = true;
                        }
                    }
                }

                if (s1) {
                    JOptionPane.showMessageDialog(this, "Operazione completata con Successo, Elementi trovati: " + list_model.size());
                    conferma.setEnabled(true);
                } else
                    JOptionPane.showMessageDialog(this, " Nessun elemento trovato");
            } else
                JOptionPane.showMessageDialog(this, "Tipologia centro e/o comune non selezionato");
        } else if (e.getSource() == conferma) {
            if (strutture_vaccinali == null)
                JOptionPane.showMessageDialog(this, "Non e' stato selezionato alcun elemento", "Errore", JOptionPane.INFORMATION_MESSAGE);
            else {
                new VisualizzaInfo(strutture_vaccinali);
                this.dispose();
            }
        } else if (e.getSource() == annulla) {
            new CittadiniGUI();
            this.dispose();
        }
    }

}
