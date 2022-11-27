/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.StruttureVaccinali;
import shared.Utility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class RicercaNomeCentro extends Ricerca {

    private final JLabel centro_label = new JLabel("Nome Centro:");
    private final JTextField centro_txt = new JTextField();

    public RicercaNomeCentro() {
        initWindow();
    }

    /**
     * Metodo utilizzato per inizializzare i vari componenti JFrame nella finestra relativa alla ricerca per nome del centro
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Ricerca per Nome del Centro Vaccinale");

//        centro_combo.setSelectedIndex(0);   // istruzione inutile?

        background.add(centro_label, 0);
        backgroundSettings(0, new Rectangle(210, 30,            //centro_label
                520, 120), 16, 1, false);

        background.add(centro_txt, 0);
        backgroundSettings(0, new Rectangle(350, 70,            //centro_txt
                310, 40), 16, 0, false);

        background.add(cerca, 0);
        backgroundSettings(0, new Rectangle(660, 70,            //cerca
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
                        strutture_vaccinali = new StruttureVaccinali(a[0], Utility.decidiTipo(a[1]), ic);
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
            list_model.removeAllElements();
            hash_set.clear();
            String copy;
            String s = centro_txt.getText();
            String[] a;
            hash_set = Utility.caricaFileInHashSet("./data/CentriVaccinali.dati.txt");
            Iterator<String> it = hash_set.iterator();

            int i = 0;
            if (!s.equals("")) {
                while (it.hasNext()) {
                    copy = it.next();
                    a = copy.split(",");
                    if (a[0].toLowerCase().contains(s.toLowerCase())) {
                        list_model.addElement(a[0]);
                        i++;
                    }
                }

                if (i == 0) {
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Nessun elemento trovato");
                } else if (i > 0) {
                    lista_centri.setBackground(Color.LIGHT_GRAY);
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Elementi trovati: " + i);
                    conferma.setEnabled(true);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Errore: Campo nome centro vaccinale non valorizzato");
            }
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