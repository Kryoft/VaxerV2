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
        txtNomeC = new JTextField();
        txtevento = new JTextField();
        txtindice = new PTextField("Indice(da 1 a 5)");
        txtnote = new TextArea();

        lblnomeC = new JLabel("Nome_Centro:");
        lblevento = new JLabel("Evento:");
        lblnote = new JLabel("Note(opzionale):");

        lpane.add(conferma_registrazione_evento_avverso, 2, 0);
        lpane.add(annulla, 2, 0);
        lpane.add(lblnomeC, 2, 0);
        lpane.add(lblevento, 2, 0);
        lpane.add(lblnote, 2, 0);
        lpane.add(txtNomeC, 2, 0);
        lpane.add(txtevento, 2, 0);
        lpane.add(txtindice, 2, 0);
        lpane.add(txtnote, 2, 0);

        Bsettings(0, new Rectangle(750, 540, 500, 100), 15, 0, false);
        Bsettings(1, new Rectangle(1080, 390, 150, 40), 15, 1, true);
        Bsettings(2, new Rectangle(750, 390, 310, 40), 15, 0, false);
        Bsettings(3, new Rectangle(750, 240, 310, 40), 15, 0, false);
        Bsettings(4, new Rectangle(600, 500, 520, 120), 16, 1, false);
        Bsettings(5, new Rectangle(600, 350, 520, 120), 16, 1, false);
        Bsettings(6, new Rectangle(600, 200, 520, 120), 16, 1, false);
        Bsettings(7, new Rectangle(1000, 720, 200, 100), 16, 1, false);
        Bsettings(8, new Rectangle(580, 720, 200, 100), 16, 1, false);

        conferma_registrazione_evento_avverso.addActionListener(this);
        annulla.addActionListener(this);

        setVisible(true);

        border = txtNomeC.getBorder();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma_registrazione_evento_avverso) {
                String centro = txtNomeC.getText();
                String Note = txtnote.getText();
                String Evento = txtevento.getText();
                int Indice = Integer.parseInt(txtindice.getText());
                EventiAvversi ev = new EventiAvversi(Evento, Indice, Note, centro);

                if (utility.EsisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {

                    if (!centro.equals("") && !Evento.equals("")) {
                        txtevento.setBorder(border);
                        if (Indice >= 1 && Indice <= 5) {
                            if (Note.length() < 256) {
                                utility.ScriviFile("./data/Vaccinati_" + centro + ".dati.txt", ev.toString());
                                txtevento.setBorder(border);
                                txtNomeC.setBorder(border);
                                txtindice.setBorder(border);
                                JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                CentriVaccinaliGUI cv = new CentriVaccinaliGUI();
                                this.dispose();
                            } else {
                                JOptionPane.showMessageDialog(this, "I caratteri delle note opzionali non possono essere più di 256", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            txtindice.setBorder(new LineBorder(Color.RED, 3, true));
                            JOptionPane.showMessageDialog(this, "Inserire un indice che va da 1 a 5", "Errore Formato", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {

                        swing_awt.Bordo(Evento, txtevento, border);
                        swing_awt.Bordo(centro, txtNomeC, border);
                        JOptionPane.showMessageDialog(this, "riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    txtNomeC.setBorder(new LineBorder(Color.RED, 3, true));
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
