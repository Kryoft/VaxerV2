package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
import centrivaccinali.PTextField;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraEventiAvversi extends Registrazioni {

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

        conferma_registrazione_evento_avverso = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        nome_centro_text = new JTextField();
        evento_text = new JTextField();
        indice_severita_text = new PTextField("Indice(da 1 a 5)");
        note_text = new TextArea();

        nome_centro_label = new JLabel("Nome_Centro:");
        evento_label = new JLabel("Evento:");
        note_label = new JLabel("Note(opzionale):");

        lpane.add(conferma_registrazione_evento_avverso, 2, 0);
        lpane.add(annulla, 2, 0);
        lpane.add(nome_centro_label, 2, 0);
        lpane.add(evento_label, 2, 0);
        lpane.add(note_label, 2, 0);
        lpane.add(nome_centro_text, 2, 0);
        lpane.add(evento_text, 2, 0);
        lpane.add(indice_severita_text, 2, 0);
        lpane.add(note_text, 2, 0);

        layeredPaneSettings(0, new Rectangle(750, 540, 500, 100), 15, 0, false);
        layeredPaneSettings(1, new Rectangle(1080, 390, 150, 40), 15, 1, true);
        layeredPaneSettings(2, new Rectangle(750, 390, 310, 40), 15, 0, false);
        layeredPaneSettings(3, new Rectangle(750, 240, 310, 40), 15, 0, false);
        layeredPaneSettings(4, new Rectangle(600, 500, 520, 120), 16, 1, false);
        layeredPaneSettings(5, new Rectangle(600, 350, 520, 120), 16, 1, false);
        layeredPaneSettings(6, new Rectangle(600, 200, 520, 120), 16, 1, false);
        layeredPaneSettings(7, new Rectangle(1000, 720, 200, 100), 16, 1, false);
        layeredPaneSettings(8, new Rectangle(580, 720, 200, 100), 16, 1, false);

        conferma_registrazione_evento_avverso.addActionListener(this);
        annulla.addActionListener(this);

        setVisible(true);

        border = nome_centro_text.getBorder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma_registrazione_evento_avverso) {
                String centro = nome_centro_text.getText();
                String Note = note_text.getText();
                String Evento = evento_text.getText();
                int Indice = Integer.parseInt(indice_severita_text.getText());
                EventiAvversi ev = new EventiAvversi(Evento, Indice, Note, centro);

                if (utility.EsisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {

                    if (!centro.equals("") && !Evento.equals("")) {
                        evento_text.setBorder(border);
                        if (Indice >= 1 && Indice <= 5) {
                            if (Note.length() < 256) {
                                utility.ScriviFile("./data/Vaccinati_" + centro + ".dati.txt", ev.toString());
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

                        swing_awt.Bordo(Evento, evento_text, border);
                        swing_awt.Bordo(centro, nome_centro_text, border);
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
