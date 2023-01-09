/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.IndirizzoComposto;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.RegistraVaccinato;
//import centrivaccinali.StruttureVaccinali;
import centrivaccinali.SwingAwt;
import shared.DBClient;
import shared.Utility;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Iterator;

public class RicercaComuneTipologia extends Ricerca {

    private final JLabel comune_label = new JLabel("Comune", SwingConstants.CENTER),
                            tipologia_label = new JLabel("Tipologia Centro", SwingConstants.CENTER);
    private final JTextField comune_txt = new JTextField();

    /**
     * Questa classe implementa la ricerca di un centro vaccinale registrato all'applicazione tramite
     * il suo Comune di residenza e la sua tipologia (Ospedaliero, Aziendale o Hub).
     *
     * @param operazione Questo parametro viene utilizzato per "ricordare" l'operazione che va eseguita
     *                   dopo la selezione di un centro vaccinale. I valori possibili sono:
     *                   <p> 1 - Visualizzazione delle informazioni riguardanti il centro selezionato </p>
     *                   <p> 2 - Registrazione di un vaccinato </p>
     *                   <p> 3 - Registrazione di un cittadino all'applicazione </p>
     *                   <p> 4 - Inserimento eventi avversi post-vaccinazione </p>
     */
    public RicercaComuneTipologia(int operazione) {
        operazione_scelta = operazione;
        initWindow();
    }

    public RicercaComuneTipologia(int operazione, String cod_fiscale) {
        operazione_scelta = operazione;
        this.cod_fiscale = cod_fiscale;
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


        int tipologia_label_width = 180;
        int combo_width = 200;
        int comune_label_width = 180;
        int comune_txt_width = 200;
        int cerca_size = 40;
        //int cerca_height = 40;
        int first_row_height = 41;
        int button_height = 50;
        int button_width = 160;
        int margin = 20;


        int first_row_x = SwingAwt.centerItemOnXorY(display_width,
                tipologia_label_width + combo_width + comune_label_width + comune_txt_width + cerca_size +
                margin);
        int tipologia_x = first_row_x;
        int combo_x = tipologia_x + tipologia_label_width;
        int comune_label_x = combo_x + combo_width + margin;
        int comune_txt_x = comune_label_x + comune_label_width;
        int cerca_x = comune_txt_x + comune_txt_width + 2;

        int first_row_y = SwingAwt.centerItemOnXorY(display_height,
                cerca_size + HEIGHT_LISTA + button_height + margin * 2);

        int button_row_y = first_row_y + cerca_size + margin * 2 + HEIGHT_LISTA;

        int button_annulla_x = SwingAwt.centerItemOnXorY(display_width,
                button_width * 2 + (int)((1.0 / 3.0) * display_width));
        int button_conferma_x = button_annulla_x + button_width + (int)((1.0 / 3.0) * display_width);


        background.add(tipologia_label, 0);
        backgroundSettings(0, new Rectangle(tipologia_x, first_row_y,            //tipologia_label
                tipologia_label_width, first_row_height), 16, 1, false);

        background.add(centro_combo, 0);
        backgroundSettings(0, new Rectangle(combo_x, first_row_y,           //centro_combo
                combo_width, first_row_height), 15, 0, false);

        background.add(comune_label, 0);
        backgroundSettings(0, new Rectangle(comune_label_x, first_row_y,            //comune_label
                comune_label_width, first_row_height), 16, 1, false);

        background.add(comune_txt, 0);
        backgroundSettings(0, new Rectangle(comune_txt_x, first_row_y,          //comune_txt
                comune_txt_width, first_row_height), 15, 0, false);

        background.add(cerca, 0);
        backgroundSettings(0, new Rectangle(cerca_x, first_row_y,          //cerca
                cerca_size, cerca_size), 15, 0, true);

        background.add(annulla, 0);
        backgroundSettings(0, new Rectangle(button_annulla_x, button_row_y,            //annulla
                button_width, button_height), 15, 1, false);

        background.add(conferma, 0).setEnabled(false);
        backgroundSettings(0, new Rectangle(button_conferma_x, button_row_y,          //conferma
                button_width, button_height), 15, 1, false);


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
                String centro_selezionato = lista_centri.getSelectedValue();
                strutture_vaccinali = DBClient.getCentroVaccinaleByName(centro_selezionato);
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
            centri_trovati.clear();
            list_model.removeAllElements();
            String comune = comune_txt.getText().toUpperCase();
            //boolean s1 = false;
            //if (SwingAwt.decidiTipologia(centro_combo) != null && !comune.equals("")) {
            CentroVaccinale.Tipologia tipologia = SwingAwt.decidiTipologia(centro_combo);
            if (tipologia != null) {

                //centri_trovati = Utility.caricaFileInHashSet("./data/CentriVaccinali.dati.txt");

                centri_trovati = DBClient.cercaCentriByComuneETipologia(comune, tipologia.toString());
                //Iterator<String> it = hash_set.iterator();
                String[] a;


                /*
                while (it.hasNext()) {

                    String s = it.next();
                    if (!s.equals("")) {
                        a = s.split(",");
                        if (comune.equals(a[5]) && SwingAwt.decidiTipologia(centro_combo) == Utility.decidiTipo(a[1])) {
                            list_model.addElement(a[0]);
                        }
                    }
                }
                 */

                int num_risultati = centri_trovati.size();

                    for(String nome: centri_trovati) {
                        list_model.addElement(nome);
                    }

                if (num_risultati > 0) {
                    JOptionPane.showMessageDialog(this, "Operazione completata con Successo, Elementi trovati: " + num_risultati);
                    conferma.setEnabled(true);
                } else
                    JOptionPane.showMessageDialog(this, " Nessun elemento trovato");
            } else
                JOptionPane.showMessageDialog(this, "Tipologia centro e/o comune non selezionato");

        }

        else if (e.getSource() == conferma) {
            if (strutture_vaccinali == null)
                JOptionPane.showMessageDialog(this, "Non e' stato selezionato alcun elemento", "Errore", JOptionPane.INFORMATION_MESSAGE);
            else {
                switch (operazione_scelta) {
                    case 1 -> new VisualizzaInfo(strutture_vaccinali);
                    case 2 -> new RegistraVaccinato(strutture_vaccinali);
                    case 3 -> new RegistraCittadini(strutture_vaccinali);
                    case 4 -> new RegistraEventiAvversi(strutture_vaccinali, cod_fiscale);
                    default -> { }
                }
                this.dispose();
            }

        } else if (e.getSource() == annulla) {
            new CittadiniGUI();
            this.dispose();
        }
    }

}
