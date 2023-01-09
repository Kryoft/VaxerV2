/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
import centrivaccinali.CentroVaccinale;
import centrivaccinali.PlaceholderTextField;

//import centrivaccinali.StruttureVaccinali;
import centrivaccinali.SwingAwt;
import jdk.jfr.Event;
import shared.DBClient;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraEventiAvversi extends Registrazioni {

    private final JComboBox<String> evento_combo = new JComboBox<>(array_eventi);

    private final JTextField nome_centro_text = new JTextField(),
                                evento_text = new JTextField();

    private final PlaceholderTextField indice_severita_text = new PlaceholderTextField("Severità (da 1 a 5)");

    private final JLabel nome_centro_label = new JLabel("Nome_Centro:", SwingConstants.CENTER),
                            evento_label = new JLabel("Evento:", SwingConstants.CENTER),
                            note_label = new JLabel("Note (opzionale):", SwingConstants.CENTER);

    private final TextArea note_text = new TextArea();

    public RegistraEventiAvversi(CentroVaccinale struttura_vaccinale) {
        this.struttura_vaccinale = struttura_vaccinale;
        initWindow();
    }

    public RegistraEventiAvversi(CentroVaccinale struttura_vaccinale, String cod_fiscale) {
        this.struttura_vaccinale = struttura_vaccinale;
        this.cod_fiscale = cod_fiscale;
        initWindow();
    }

    /**
     * Metodo utile per l'inizializzazione dei componenti JFrame riguardanti l'inserimento
     * degli eventi avversi.
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Inserisci Evento Avverso");

        //display_width = layered_pane.getWidth();
        //display_height = layered_pane.getHeight();

        int labels_width = 180;
        int text_width = 310;
        int severita_width = 150;
        int note_width = 500;
        int button_width = 160;

        int base_height = 40;
        int note_height = 100;
        int button_height = 50;

        int labels_x = SwingAwt.centerItemOnXorY(display_width, labels_width + note_width);
        int y_margin = 60;
        int button_margin = 50;

        int txt_x = labels_x + labels_width;
        int severita_x = txt_x + text_width + 10;

        int first_row_y = SwingAwt.centerItemOnXorY(display_height,
                base_height * 2 + note_height + y_margin * 2 + button_margin + button_height);

        int second_row_y = first_row_y + base_height + y_margin;
        int third_row_y = second_row_y + base_height + y_margin;
        int fourth_row_y = third_row_y + note_height + button_margin;

        int annulla_x = SwingAwt.centerItemOnXorY(display_width, button_width * 2 + button_margin);
        int conferma_x = annulla_x + button_width + (display_width/2 - (annulla_x + button_width));



        layered_pane.add(nome_centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, first_row_y,              //nome_centro_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(nome_centro_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(txt_x, first_row_y,              //nome_centro_text
                text_width, base_height), 15, 0, false);
        nome_centro_text.setEditable(false);
        nome_centro_text.setText(struttura_vaccinale.getNomeCentro());

        layered_pane.add(evento_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, second_row_y,              //evento_label
                labels_width, base_height), 16, 1, false);

        /*
        layered_pane.add(evento_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(750, 390,              //evento_text
                310, 40), 15, 0, false);
        */

        layered_pane.add(evento_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(txt_x, second_row_y,              //evento_combo
                text_width, base_height), 15, 0, false);

        layered_pane.add(indice_severita_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(severita_x, second_row_y,             //indice_severita_text
                severita_width, base_height), 15, 1, true);

        layered_pane.add(note_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, third_row_y,              //note_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(note_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(txt_x, third_row_y,              //note_text
                note_width, note_height), 15, 0, false);

        layered_pane.add(conferma, 2, 0);  //conferma
        layeredPaneSettings(0, new Rectangle(annulla_x, fourth_row_y,
                button_width, button_height), 16, 1, false);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(conferma_x, fourth_row_y,             //annulla
                button_width, button_height), 16, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_centro_text.getBorder();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma) {
                String centro = struttura_vaccinale.getNomeCentro();
                String note = note_text.getText();
                EventoAvverso.Eventi evento = SwingAwt.decidiEvento((String)evento_combo.getSelectedItem());

                int Indice = Integer.parseInt(indice_severita_text.getText());
                EventoAvverso ev = new EventoAvverso(evento, Indice, note, centro, cod_fiscale);

                if (!evento.equals("")) {
                    evento_text.setBorder(border);
                    if (Indice >= 1 && Indice <= 5) {
                        if (note.length() < 256) {
                            ArrayList<EventoAvverso> segnalazioni = DBClient.getSegnalazioniByCentro(centro);
                            boolean nuovo = true;
                            for(EventoAvverso segnalazione: segnalazioni){
                                if(DBClient.checkEventoGiaSegnalato(ev)){
                                    nuovo = false;
                                }
                            }
                            if(nuovo) {

                                Utility.inserisciNuovoEvento(ev);
                                //evento_combo.setBorder(border);
                                nome_centro_text.setBorder(border);
                                indice_severita_text.setBorder(border);
                                JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                new CentriVaccinaliGUI();
                                this.dispose();
                            } else{
                                JOptionPane.showMessageDialog(this, "Questo evento avverso è già stato segnalato", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "I caratteri delle note opzionali non possono essere più di 256", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        indice_severita_text.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Inserire un indice che va da 1 a 5", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                    }
                } else {

                    SwingAwt.modificaBordo(evento.toString(), evento_text, border);
                    JOptionPane.showMessageDialog(this, "Riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == annulla) {
                new CittadiniGUI();
                this.dispose();
            }
        }catch (SQLException | RemoteException ex){
            System.err.println("DATABASE ERROR");
        }

    }

}
