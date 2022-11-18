/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import cittadini.Vaccinati;

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

    public RegistraVaccinato() {
        initRegistraVaccinato();
    }

    /**
     * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione di un vaccinato
     *
     * @author Daniele Caspani
     */
    private void initRegistraVaccinato() {
        settings("Registra Vaccinato");

        int base_height = 40;

        conferma_registrazione_vaccinato = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        int width_buttons = 200;
        int height_buttons = 100;

        jvaccino = new JComboBox<>(array_vaccini);


        layered_pane.add(annulla, 2, 0);
        layered_pane.add(conferma_registrazione_vaccinato, 2, 0);
        layered_pane.add(jvaccino, 2, 0);


        JLabel lblnomeC = new JLabel("Nome_Centro:");
        JLabel tipologia_label = new JLabel("Tipologia");
        nome_label = new JLabel("Nome:");
        JLabel lblcognome = new JLabel("Cognome:");
        JLabel lblcodice = new JLabel("Codice Fiscale");
        JLabel lbldata = new JLabel("Data Vaccinazione(dd/mm/yy):");

        txtNomeC = new JTextField();
        txtnome = new JTextField();
        txtcognome = new JTextField();
        txtcodice = new JTextField();
        txtdata = new JTextField();

        layered_pane.add(lblnomeC, 2, 0);
        layered_pane.add(tipologia_label, 2, 0);
        layered_pane.add(nome_label, 2, 0);
        layered_pane.add(lblcognome, 2, 0);
        layered_pane.add(lblcodice, 2, 0);
        layered_pane.add(lbldata, 2, 0);

        layered_pane.add(txtNomeC, 2, 0);
        layered_pane.add(txtnome, 2, 0);
        layered_pane.add(txtcognome, 2, 0);
        layered_pane.add(txtcodice, 2, 0);
        layered_pane.add(txtdata, 2, 0);

        layeredPaneSettings(0, new Rectangle(1090, 530, 310, 40), 15, 1, false);
        layeredPaneSettings(1, new Rectangle(530, 530, 310, 40), 15, 1, false);
        layeredPaneSettings(2, new Rectangle(1090, 380, 310, 40), 15, 1, false);
        layeredPaneSettings(3, new Rectangle(530, 380, 310, 40), 15, 1, false);
        layeredPaneSettings(4, new Rectangle(530, 230, 310, 40), 15, 1, false);
        layeredPaneSettings(5, new Rectangle(870, 490, 520, 120), 16, 0, false);
        layeredPaneSettings(6, new Rectangle(410, 490, 520, 120), 16, 0, false);
        layeredPaneSettings(7, new Rectangle(930, 340, 520, 120), 16, 0, false);
        layeredPaneSettings(8, new Rectangle(410, 340, 520, 120), 16, 0, false);
        layeredPaneSettings(9, new Rectangle(930, 190, 520, 120), 16, 0, false);
        layeredPaneSettings(10, new Rectangle(410, 190, 520, 120), 16, 0, false);
        layeredPaneSettings(11, new Rectangle(1090, 230, 310, 40), 12, 1, false);
        layeredPaneSettings(12, new Rectangle(1100, 700, 200, 100), 18, 1, false);
        layeredPaneSettings(13, new Rectangle(630, 700, 200, 100), 18, 1, false);

        conferma_registrazione_vaccinato.addActionListener(this);
        annulla.addActionListener(this);
        border = txtNomeC.getBorder();

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
            String centro = txtNomeC.getText();
            String Nome = txtnome.getText();
            String Cognome = txtcognome.getText();
            String SData = txtdata.getText();
            DateFormat fData = DateFormat.getDateInstance(DateFormat.SHORT);
            Date Data = null;
            String Codice = txtcodice.getText().toUpperCase();
            short id = 0;
            Vaccinati va = new Vaccinati();
            try {
                Data = fData.parse(SData);
                if (!centro.equals("") && !Nome.equals("") && !Cognome.equals("") && !SData.equals("") && !Codice.equals("")) {
                    txtNomeC.setBorder(border);
                    txtdata.setBorder(border);
                    txtnome.setBorder(border);
                    txtcognome.setBorder(border);

                    if (utility.EsisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {
                        if (va.controllaCodiceFiscale(Codice)) {
                            txtcodice.setBorder(border);

                            Random random = new Random();
                            id = (short) ((short) random.nextInt(65534) - 32767);
                            id = utility.Idcontrol(2, String.valueOf(id), "./data/Vaccinati_" + centro + ".dati.txt");

                            if (id != 0) {
                                va = new Vaccinati(Data, swing_awt.DecidiVaccino(jvaccino), centro, id, Nome, Cognome, Codice);
                                utility.ScriviFile("./data/Vaccinati_" + va.getNomeCentro() + ".dati.txt", va.toString());
                            } else
                                JOptionPane.showMessageDialog(this, "Non e' possibile inserire più vaccinati per questo centro", "Errore", JOptionPane.WARNING_MESSAGE);
                            txtdata.setBorder(border);
                            txtnome.setBorder(border);
                            txtcognome.setBorder(border);
                            JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                            JOptionPane.showMessageDialog(this, "L' Identificativo Associato e' " + (int) (id + 32767));
                            CentriVaccinaliGUI cv = new CentriVaccinaliGUI();
                            this.dispose();
                        } else {
                            txtcodice.setBorder(new LineBorder(Color.RED, 3, true));
                            JOptionPane.showMessageDialog(this, "Errore nel formato del codice fiscale", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        txtNomeC.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Il centro da lei indicato non esiste o non si e' registarto all'applicazione", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    swing_awt.Bordo(centro, txtNomeC, border);
                    swing_awt.Bordo(Nome, txtnome, border);
                    swing_awt.Bordo(Cognome, txtcognome, border);
                    swing_awt.Bordo(Codice, txtcodice, border);
                    swing_awt.Bordo(SData, txtdata, border);
                    JOptionPane.showMessageDialog(this, "Riempire Tutti i Campi", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (ParseException ex) {
                txtdata.setBorder(new LineBorder(Color.RED, 3, true));
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
