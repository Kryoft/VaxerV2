/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.centrivaccinali;

import client.ClientToServerRequests;
import client.cittadini.Vaccinato;
import client.ClientGUI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class RegistraVaccinato extends Registrazioni {

    private final JComboBox<String> vaccino_combo = new JComboBox<>(array_vaccini);

    private final JLabel nome_centro_label = new JLabel("Nome Centro:", SwingConstants.CENTER),
                            tipologia_label = new JLabel("Tipologia:", SwingConstants.CENTER),
                            nome_label = new JLabel("Nome:", SwingConstants.CENTER),
                            cognome_label = new JLabel("Cognome:", SwingConstants.CENTER),
                            cf_label = new JLabel("Codice Fiscale", SwingConstants.CENTER),
                            data_label = new JLabel("Data Vaccinazione(dd/mm/yy):", SwingConstants.CENTER);
    private final int width_label = 250;

    private final CentroVaccinale centro_vaccinale;

    private final JTextField nome_centro_text = new JTextField(),
                                nome_text = new JTextField(),
                                cognome_text = new JTextField(),
                                cf_text = new JTextField(),
                                data_text = new JTextField();
    private final int width_text = 310;


    // Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536 @Marceca
    private final int secondColumnMargin = display_width * 100 / 1536,
                        first_row_x = (display_width /2) - ((width_text * 2 + width_label * 2 + secondColumnMargin)/2),
                        first_row_y = (int)(0.15 * display_height),
                        x_txt1 = first_row_x + width_label,
                        x_label2 = secondColumnMargin + x_txt1 + width_text,
                        x_txt2 = x_label2 + width_label,
                        second_row_y = (int)(0.3 * display_height),
                        third_row_y = (int)(0.45  * display_height),
                        fourth_row_x = (display_width /2) - ((width_buttons * 2 + 200)/2),
                        fourth_row_y = (int)(0.7 * display_height);


    public RegistraVaccinato(CentroVaccinale centro_vaccinale) {
        this.centro_vaccinale = centro_vaccinale;
        initWindow();
    }

    /**
     * Inizializza i componenti JFrame per quanto riguarda la registrazione di un vaccinato
     *
     * @author Daniele Caspani
     * @author Manuel Marceca
     * @author Cristian Corti
     */
    private void initWindow() {
        settings("Registra Vaccinato");
        ClientGUI.setCurrentWindow(this);

        layered_pane.add(nome_centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y, width_label, base_height),
                16, 0, false);              // nome_centro_label

        layered_pane.add(nome_centro_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, first_row_y, width_text, base_height),
                15, 1, false);              // nome_centro_text
        nome_centro_text.setEditable(false);
        nome_centro_text.setText(centro_vaccinale.getNomeCentro());

        layered_pane.add(tipologia_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, first_row_y, width_label, base_height),
                16, 0, false);              // tipologia_label

        layered_pane.add(vaccino_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, first_row_y, width_text, base_height),
                12, 1, false);              // vaccino_combo


        layered_pane.add(nome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, second_row_y, width_label, base_height),
                16, 0, false);              // nome_label

        layered_pane.add(nome_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, second_row_y, width_text, base_height),
                15, 1, false);              // nome_text

        layered_pane.add(cognome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, second_row_y, width_label, base_height),
                16, 0, false);              // cognome_label

        layered_pane.add(cognome_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, second_row_y, width_text, base_height),
                15, 1, false);              // cognome_text


        layered_pane.add(cf_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, third_row_y, width_label, base_height),
                16, 0, false);              // cf_label

        layered_pane.add(cf_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, third_row_y, width_text, base_height),
                15, 1, false);              // cf_text

        layered_pane.add(data_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, third_row_y, width_label, base_height),
                16, 0, false);              // data_label

        layered_pane.add(data_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, third_row_y, width_text, base_height),
                15, 1, false);              // data_text


        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x, fourth_row_y, width_buttons, height_buttons),
                18, 1, false);              // annulla

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0,
                new Rectangle(fourth_row_x + width_buttons + 200, fourth_row_y, width_buttons, height_buttons),
                18, 1, false);              // conferma


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_centro_text.getBorder();

        setVisible(true);
    }

    /**
     * Metodo che si occupa di controllare che i dati inseriti in ogni campo testuale siano validi.
     * In caso favorevole, permette la registrazione del cittadino vaccinato.
     * In caso contrario, rende rosso il bordo di tali campi e fornisce una spiegazione dell'errore.
     *
     * @author Daniele Caspani
     * @author Cristian Corti
     * @author Manuel Marceca
     */
    private void registraVaccinato() {
        String centro = centro_vaccinale.getNomeCentro().strip();
        String nome = nome_text.getText().strip();
        String cognome = cognome_text.getText().strip();
        String data_string = data_text.getText().strip();
        DateFormat data_formatted = DateFormat.getDateInstance(DateFormat.SHORT);
        Date data;
        String codice_fiscale = cf_text.getText().toUpperCase().strip();
        //short id = 0;
        Vaccinato nuovo_vaccinato = new Vaccinato();
        try {
            data = data_formatted.parse(data_string);
            if (!nome.equals("") && !cognome.equals("") && !data_string.equals("") && !codice_fiscale.equals("")) {
                nome_centro_text.setBorder(border);
                data_text.setBorder(border);
                nome_text.setBorder(border);
                cognome_text.setBorder(border);

                if (nuovo_vaccinato.controllaCodiceFiscale(codice_fiscale, nome, cognome)) {
                    cf_text.setBorder(border);

                    /*
                    Random random = new Random();
                    id = (short) ((short) random.nextInt(65534) - 32767);
                    id = Utility.idControl(2, String.valueOf(id), "./data/Vaccinati_" + centro + ".dati.txt");

                    if (id != 0) {
                        va = new Vaccinato(Data, SwingAwt.decidiVaccino(vaccino_combo), centro, id, Nome, Cognome, Codice);
                        Utility.scriviFile("./data/Vaccinati_" + va.getNomeCentro() + ".dati.txt", va.toString());
                    } else
                        JOptionPane.showMessageDialog(this, "Non e' possibile inserire più vaccinati per questo centro", "Errore", JOptionPane.WARNING_MESSAGE);

                    */
                    nuovo_vaccinato = new Vaccinato(data, SwingAwt.decidiVaccino(vaccino_combo), centro, nome, cognome, codice_fiscale);
                    int id = ClientToServerRequests.insertVaccinato(nuovo_vaccinato);

                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                    JOptionPane.showMessageDialog(this, "L'Identificativo associato e' " + (id));

                    new CentriVaccinaliGUI();
                    this.dispose();
                } else {
                    cf_text.setBorder(new LineBorder(Color.RED, 3, true));
                    JOptionPane.showMessageDialog(this, "Errore nel formato del codice fiscale", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                SwingAwt.modificaBordo(nome_text);
                SwingAwt.modificaBordo(cognome_text);
                SwingAwt.modificaBordo(cf_text);
                SwingAwt.modificaBordo(data_text);
                JOptionPane.showMessageDialog(this, "Riempire Tutti i Campi", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        } catch (ParseException ex) {
            data_text.setBorder(new LineBorder(Color.RED, 3, true));
            JOptionPane.showMessageDialog(this, "Formato della data errato", "Errore", JOptionPane.ERROR_MESSAGE);
        } //catch (IOException | URISyntaxException ex) {

        //  Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, ex);
    }

    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @author Cristian Corti
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == conferma) {
            registraVaccinato();
        }

        else if (e.getSource() == annulla) {
            new OperazioniCentroGUI();
            this.dispose();
        }
    }

}
