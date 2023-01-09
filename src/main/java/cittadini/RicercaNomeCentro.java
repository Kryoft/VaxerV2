/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.RegistraVaccinato;
//import centrivaccinali.StruttureVaccinali;
import centrivaccinali.SwingAwt;
import shared.DBClient;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RicercaNomeCentro extends Ricerca {

    private final JLabel centro_label = new JLabel("Nome Centro:");
    private final JTextField centro_txt = new JTextField();

    /**
     * Questa classe implementa la ricerca di un centro vaccinale registrato all'applicazione tramite
     * il suo nome.
     *
     * @param operazione Questo parametro viene utilizzato per "ricordare" l'operazione che va eseguita
     *                   dopo la selezione di un centro vaccinale. I valori possibili sono:
     *                   <p> 1 - Visualizzazione delle informazioni riguardanti il centro selezionato </p>
     *                   <p> 2 - Registrazione di un vaccinato </p>
     *                   <p> 3 - Registrazione di un cittadino all'applicazione </p>
     *                   <p> 4 - Inserimento eventi avversi post-vaccinazione </p>
     */
    public RicercaNomeCentro(int operazione) {
        operazione_scelta = operazione;
        initWindow();
    }

    public RicercaNomeCentro(int operazione, String cod_fiscale){
        operazione_scelta = operazione;
        this.cod_fiscale = cod_fiscale;
        initWindow();
    }

    /**
     * Metodo utilizzato per inizializzare i vari componenti JFrame nella finestra relativa alla ricerca per nome del centro
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Ricerca per Nome del Centro Vaccinale");

        int centro_txt_width = 310;

        int first_row_height = 41;

        int centro_label_width = 300;
        int button_height = 50;
        int button_width = 160;
        int margin = 20;
        int cerca_size = 40;

        int centro_txt_x = SwingAwt.centerItemOnXorY(display_width, centro_txt_width);
        int centro_label_x = centro_txt_x - centro_label_width;
        int cerca_x = centro_txt_x + centro_txt_width + 2;


        int first_row_y = SwingAwt.centerItemOnXorY(display_height,
                cerca_size + HEIGHT_LISTA + button_height + margin * 2);
        int button_row_y = first_row_y + cerca_size + margin * 2 + HEIGHT_LISTA;

        int button_annulla_x = SwingAwt.centerItemOnXorY(display_width,
                button_width * 2 + (int)((1.0 / 3.0) * display_width));
        int button_conferma_x = button_annulla_x + button_width + (int)((1.0 / 3.0) * display_width);

//        centro_combo.setSelectedIndex(0);   // istruzione inutile?

        background.add(centro_label, 0);
        backgroundSettings(0, new Rectangle(centro_label_x, first_row_y,            //centro_label
                centro_label_width, first_row_height), 16, 1, false);

        background.add(centro_txt, 0);
        backgroundSettings(0, new Rectangle(centro_txt_x, first_row_y,            //centro_txt
                centro_txt_width, first_row_height), 16, 0, false);

        background.add(cerca, 0);
        backgroundSettings(0, new Rectangle(cerca_x, first_row_y,            //cerca
                cerca_size, cerca_size), 15, 0, true);

        background.add(annulla, 0);
        backgroundSettings(0, new Rectangle(button_annulla_x, button_row_y,            //annulla
                button_width, button_height), 15, 1, false);

        background.add(conferma, 0).setEnabled(false);
        //backgroundSettings(0, new Rectangle(1790, 965,          //conferma
        //        120, 35), 15, 1, false);

        backgroundSettings(0, new Rectangle(button_conferma_x, button_row_y,          //conferma
                        button_width, button_height), 15, 1, false);


        cerca.addActionListener(this);
        conferma.addActionListener(this);
        annulla.addActionListener(this);

        creaLista();

        /*
         * A lista_centri viene aggiunto SelectionListener per assegnare all'oggetto di tipo
         * CentroVaccinale strutture_vaccinali l'elemento selezionato
         */
        lista_centri.addListSelectionListener(new ListSelectionListener() {

            //TODO! Rivedere indici, che ora non hanno più una logica, non utilizzando più gli array.
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
            list_model.removeAllElements();
            centri_trovati.clear();
            //String copy;
            String nome_centro = centro_txt.getText();
            //String[] a;
            centri_trovati = DBClient.cercaCentriByNome(nome_centro);
            //Iterator<String> it = centri_trovati.iterator();

            int num_risultati = centri_trovati.size();
            //if (!nome_centro.isBlank()) {
                for(String nome: centri_trovati) {
                    list_model.addElement(nome);
                }

                if (num_risultati == 0) {
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Nessun elemento trovato");
                } else if (num_risultati > 0) {
                    lista_centri.setBackground(Color.LIGHT_GRAY);
                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo, Elementi trovati: " + num_risultati);
                    conferma.setEnabled(true);
                }
            //} else
            //    JOptionPane.showMessageDialog(this, "Errore: Campo nome centro vaccinale non valorizzato");
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
