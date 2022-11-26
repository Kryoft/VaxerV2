/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import cittadini.Vaccinati;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraVaccinato extends Registrazioni {

    private final int base_height = 40;

    private final JButton conferma_registrazione_vaccinato = new JButton("CONFERMA");
    private final JButton annulla = new JButton("TORNA INDIETRO");
    private final int width_buttons = 200;
    private final int height_buttons = 100;

    private final JComboBox<String> jvaccino = new JComboBox<>(array_vaccini);

    private final JLabel nome_centro_label = new JLabel("Nome Centro:", SwingConstants.CENTER);
    private final JLabel tipologia_label = new JLabel("Tipologia:", SwingConstants.CENTER);
    private final JLabel nome_label = new JLabel("Nome:", SwingConstants.CENTER);
    private final JLabel cognome_label = new JLabel("Cognome:", SwingConstants.CENTER);
    private final JLabel cf_label = new JLabel("Codice Fiscale", SwingConstants.CENTER);
    private final JLabel data_label = new JLabel("Data Vaccinazione(dd/mm/yy):", SwingConstants.CENTER);
    private final int width_label = 250;

    private final JTextField txt_nome_centro = new JTextField();
    private final JTextField txt_nome = new JTextField();
    private final JTextField txt_cognome = new JTextField();
    private final JTextField txt_codice = new JTextField();
    private final JTextField txt_data = new JTextField();
    private final int width_txt = 310;



    private final int secondColumnMargin = display_width * 100 / 1536;         // Margine standardizzato e proporzionato a partire
    // dal mio schermo, che ha display width pari a
    // 1536 @Marceca

    private final int first_row_x = (display_width /2) - ((width_txt * 2 + width_label * 2 + secondColumnMargin)/2);
    private final int first_row_y = (int)(0.15 * display_height);

    private final int x_label1 = first_row_x;
    private final int x_txt1 = first_row_x + width_label;
    private final int x_label2 = secondColumnMargin + x_txt1 + width_txt;
    private final int x_txt2 = x_label2 + width_label;

    private final int second_row_y = (int)(0.3 * display_height);

    private final int third_row_y = (int)(0.45  * display_height);

    private final int fourth_row_x = (display_width /2) - ((width_buttons * 2 + 200)/2);
    private final int fourth_row_y = (int)(0.7 * display_height);


    public RegistraVaccinato() {
        initWindow();
    }

    /**
     * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione di un vaccinato
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initWindow() {
        settings("Registra Vaccinato");

        layered_pane.add(nome_centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label1, first_row_y, width_label, base_height),
                16, 0, false);              //nomeCentro_label

        layered_pane.add(txt_nome_centro, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, first_row_y, width_txt, base_height),
                15, 1, false);              //txtnomeC

        layered_pane.add(tipologia_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, first_row_y, width_label, base_height),
                16, 0, false);              //tipologia_label

        layered_pane.add(jvaccino, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, first_row_y, width_txt, base_height),
                12, 1, false);              //jvaccino


        layered_pane.add(nome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label1, second_row_y, width_label, base_height),
                16, 0, false);              //nome_label

        layered_pane.add(txt_nome, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, second_row_y, width_txt, base_height),
                15, 1, false);              //txtnome

        layered_pane.add(cognome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, second_row_y, width_label, base_height),
                16, 0, false);              //cognome_label

        layered_pane.add(txt_cognome, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, second_row_y, width_txt, base_height),
                15, 1, false);              //txtcognome


        layered_pane.add(cf_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label1, third_row_y, width_label, base_height),
                16, 0, false);              //codice_label

        layered_pane.add(txt_codice, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt1, third_row_y, width_txt, base_height),
                15, 1, false);              //txtcodice

        layered_pane.add(data_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_label2, third_row_y, width_label, base_height),
                16, 0, false);              //data_label

        layered_pane.add(txt_data, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_txt2, third_row_y, width_txt, base_height),
                15, 1, false);              //txtdata


        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x, fourth_row_y, width_buttons, height_buttons),
                18, 1, false);              //annulla

        layered_pane.add(conferma_registrazione_vaccinato, 2, 0);
        layeredPaneSettings(0,
                new Rectangle(fourth_row_x + width_buttons + 200, fourth_row_y, width_buttons, height_buttons),
                18, 1, false);              //conferma


        conferma_registrazione_vaccinato.addActionListener(this);
        annulla.addActionListener(this);
        border = txt_nome_centro.getBorder();

        setVisible(true);
    }

    /**
     * metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == conferma_registrazione_vaccinato) {
            String centro = txt_nome_centro.getText();
            String Nome = txt_nome.getText();
            String Cognome = txt_cognome.getText();
            String SData = txt_data.getText();
            DateFormat fData = DateFormat.getDateInstance(DateFormat.SHORT);
            Date Data = null;
            String Codice = txt_codice.getText().toUpperCase();
            short id = 0;
            Vaccinati va = new Vaccinati();
            try {
                Data = fData.parse(SData);
                if (!centro.equals("") && !Nome.equals("") && !Cognome.equals("") && !SData.equals("") && !Codice.equals("")) {
                    txt_nome_centro.setBorder(border);
                    txt_data.setBorder(border);
                    txt_nome.setBorder(border);
                    txt_cognome.setBorder(border);

                    if (Utility.esisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {
                        if (va.controllaCodiceFiscale(Codice)) {
                            txt_codice.setBorder(border);

                            Random random = new Random();
                            id = (short) ((short) random.nextInt(65534) - 32767);
                            id = Utility.idControl(2, String.valueOf(id), "./data/Vaccinati_" + centro + ".dati.txt");

                            if (id != 0) {
                                va = new Vaccinati(Data, swing_awt.decidiVaccino(jvaccino), centro, id, Nome, Cognome, Codice);
                                Utility.scriviFile("./data/Vaccinati_" + va.getNomeCentro() + ".dati.txt", va.toString());
                            } else
                                JOptionPane.showMessageDialog(this, "Non e' possibile inserire più vaccinati per questo centro", "Errore", JOptionPane.WARNING_MESSAGE);
                            txt_data.setBorder(border);
                            txt_nome.setBorder(border);
                            txt_cognome.setBorder(border);
                            JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                            JOptionPane.showMessageDialog(this, "L' Identificativo Associato e' " + (int) (id + 32767));
                            CentriVaccinaliGUI cv = new CentriVaccinaliGUI();
                            this.dispose();
                        } else {
                            txt_codice.setBorder(new LineBorder(Color.RED, 3, true));
                            JOptionPane.showMessageDialog(this, "Errore nel formato del codice fiscale", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        txt_nome_centro.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Il centro da lei indicato non esiste o non si e' registarto all'applicazione", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    swing_awt.modificaBordo(centro, txt_nome_centro, border);
                    swing_awt.modificaBordo(Nome, txt_nome, border);
                    swing_awt.modificaBordo(Cognome, txt_cognome, border);
                    swing_awt.modificaBordo(Codice, txt_codice, border);
                    swing_awt.modificaBordo(SData, txt_data, border);
                    JOptionPane.showMessageDialog(this, "Riempire Tutti i Campi", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException ex) {
                txt_data.setBorder(new LineBorder(Color.RED, 3, true));
                JOptionPane.showMessageDialog(this, "Formato della data errato", "Error112", JOptionPane.ERROR_MESSAGE);
            } catch (IOException | URISyntaxException ex) {
                Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        else if (e.getSource() == annulla) {
            new OperazioniCentroGUI();
            this.dispose();
        }
    }

}
