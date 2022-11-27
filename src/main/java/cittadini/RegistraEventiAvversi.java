/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
import centrivaccinali.PlaceholderTextField;

import centrivaccinali.SwingAwt;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraEventiAvversi extends Registrazioni {

    private final JTextField nome_centro_text = new JTextField(),
                                evento_text = new JTextField();

    private final PlaceholderTextField indice_severita_text = new PlaceholderTextField("Severità (da 1 a 5)");

    private final JLabel nome_centro_label = new JLabel("Nome_Centro:"),
                            evento_label = new JLabel("Evento:"),
                            note_label = new JLabel("Note (opzionale):");

    private final TextArea note_text = new TextArea();

    public RegistraEventiAvversi() {
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

        layered_pane.add(nome_centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(600, 200,              //nome_centro_label
                520, 120), 16, 1, false);

        layered_pane.add(nome_centro_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(750, 240,              //nome_centro_text
                310, 40), 15, 0, false);

        layered_pane.add(evento_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(600, 350,              //evento_label
                520, 120), 16, 1, false);

        layered_pane.add(evento_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(750, 390,              //evento_text
                310, 40), 15, 0, false);

        layered_pane.add(indice_severita_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(1080, 390,             //indice_severita_text
                150, 40), 15, 1, true);

        layered_pane.add(note_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(600, 500,              //note_label
                520, 120), 16, 1, false);

        layered_pane.add(note_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(750, 540,              //note_text
                500, 100), 15, 0, false);

        layered_pane.add(conferma, 2, 0);  //conferma
        layeredPaneSettings(0, new Rectangle(580, 720,
                200, 100), 16, 1, false);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(1000, 720,             //annulla
                200, 100), 16, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_centro_text.getBorder();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma) {
                String centro = nome_centro_text.getText();
                String Note = note_text.getText();
                String Evento = evento_text.getText();
                int Indice = Integer.parseInt(indice_severita_text.getText());
                EventiAvversi ev = new EventiAvversi(Evento, Indice, Note, centro);

                if (Utility.esisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {

                    if (!centro.equals("") && !Evento.equals("")) {
                        evento_text.setBorder(border);
                        if (Indice >= 1 && Indice <= 5) {
                            if (Note.length() < 256) {
                                Utility.scriviFile("./data/Vaccinati_" + centro + ".dati.txt", ev.toString());
                                evento_text.setBorder(border);
                                nome_centro_text.setBorder(border);
                                indice_severita_text.setBorder(border);
                                JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                new CentriVaccinaliGUI();
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "I caratteri delle note opzionali non possono essere più di 256", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            indice_severita_text.setBorder(new LineBorder(Color.RED, 3, true));
                            JOptionPane.showMessageDialog(this, "Inserire un indice che va da 1 a 5", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {

                        SwingAwt.modificaBordo(Evento, evento_text, border);
                        SwingAwt.modificaBordo(centro, nome_centro_text, border);
                        JOptionPane.showMessageDialog(this, "riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    nome_centro_text.setBorder(new LineBorder(Color.RED, 3, true));
                    JOptionPane.showMessageDialog(this, "Il centro da lei indicato non esiste o non si e' registrato all'applicazione", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == annulla) {
                new CittadiniGUI();
                this.dispose();
            }
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(RegistraCittadini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
