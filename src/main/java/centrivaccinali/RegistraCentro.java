/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraCentro extends Registrazioni {

    public RegistraCentro() {
        initRegistraCentro();
    }

    /**
     * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
     *
     * @author Daniele Caspani
     */
    private void initRegistraCentro() {
        settings("Registra Centro Vaccinale");

        conferma_registrazione_centro = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        jtipologia = new JComboBox<>(array_tipologia);
        jqualificatore = new JComboBox<>(array_qualificatori);

        layered_pane.add(annulla, 2, 0);
        layered_pane.add(conferma_registrazione_centro, 2, 0);

        nome_label = new JLabel("Nome Centro e Tipologia:");
        JLabel indirizzo_label = new JLabel("Indirizzo:");

        nome = new PTextField("Nome centro");
        via = new PTextField("Nome via");
        numcivico = new PTextField("Numero civico");
        comune = new PTextField("Comune");
        sigla = new PTextField("Sigla");
        cap = new PTextField("CAP");

        layered_pane.add(nome_label, 2, 0);
        layered_pane.add(indirizzo_label, 2, 0);

        layered_pane.add(jtipologia, 2, 0);
        layered_pane.add(jqualificatore, 2, 0);

        layered_pane.add(nome, 2, 0);
        layered_pane.add(via, 2, 0);
        layered_pane.add(numcivico, 2, 0);
        layered_pane.add(cap, 2, 0);
        layered_pane.add(comune, 2, 0);
        layered_pane.add(sigla, 2, 0);

        layeredPaneSettings(0, new Rectangle(940, 530, 110, 40), 15, 0, true);
        layeredPaneSettings(1, new Rectangle(680, 530, 250, 40), 15, 0, true);
        layeredPaneSettings(2, new Rectangle(1200, 430, 110, 40), 15, 0, true);
        layeredPaneSettings(3, new Rectangle(1060, 430, 100, 40), 15, 0, true);
        layeredPaneSettings(4, new Rectangle(800, 430, 250, 40), 15, 0, true);
        layeredPaneSettings(5, new Rectangle(680, 200, 310, 40), 15, 0, true);
        layeredPaneSettings(6, new Rectangle(680, 430, 110, 40), 12, 0, false);
        layeredPaneSettings(7, new Rectangle(990, 200, 310, 40), 12, 0, false);
        layeredPaneSettings(8, new Rectangle(450, 390, 520, 120), 16, 1, false);
        layeredPaneSettings(9, new Rectangle(450, 160, 520, 120), 16, 1, false);
        layeredPaneSettings(10, new Rectangle(650, 700, 200, 100), 15, 1, false);
        layeredPaneSettings(11, new Rectangle(1050, 700, 200, 100), 15, 1, false);

        border = nome.getBorder();

        annulla.addActionListener(this);
        conferma_registrazione_centro.addActionListener(this);

        setVisible(true);
    }

    /**
     * metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma_registrazione_centro) {
                String message = null;
                IndirizzoComposto Ic;
                int codice = Integer.parseInt(numcivico.getText());
                String centro = nome.getText();
                String Via = via.getText();
                String Comune = comune.getText().toUpperCase();
                String Sigla = sigla.getText().toUpperCase();
                String Cap = cap.getText();
                numcivico.setBorder(border);
                if (!centro.equals("") && !Via.equals("") && !Comune.equals("") && !Sigla.equals("") && !Cap.equals("") && !centro.equals("Nome centro") && !Via.equals("Nome Via") && !Comune.equals("comune") && !Sigla.equals("sigla") && !Cap.equals("cap")) {
                    swing_awt.Bordo(centro, nome, border);
                    swing_awt.Bordo(Via, via, border);
                    comune.setBorder(border);
                    sigla.setBorder(border);
                    try {
                        Ic = new IndirizzoComposto(swing_awt.DecidiQualificatore(jqualificatore), Via, codice, Comune, Sigla, Cap);

                        if (!Ic.controllaCap(Cap)) {
                            cap.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Il cap contiene 5 cifre decimali";
                            throw new Eccezione();
                        } else
                            swing_awt.Bordo(Cap, cap, border);

                        if (!Ic.controllaComune(Comune)) {
                            comune.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Un comune puo' contenere solo caratteri letterali";
                            throw new Eccezione();
                        } else
                            swing_awt.Bordo(Comune, comune, border);

                        if (!Ic.controllaSigla(Sigla)) {
                            sigla.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Una sigla di provincia contiene solo 2 caratteri letterali";
                            throw new Eccezione();
                        } else
                            swing_awt.Bordo(Sigla, sigla, border);

                        StruttureVaccinali sv = new StruttureVaccinali(centro, swing_awt.DecidiTipologia(jtipologia), Ic);
                        if (utility.EsisteCentro(0, sv.getNome_centro(), "./data/CentriVaccinali.dati.txt")) {
                            JOptionPane.showMessageDialog(this, "Centro gia' esistente nell'applicazione; Cambiare Nome", "error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            utility.ScriviFile("./data/CentriVaccinali.dati.txt", sv.toString());

                            nome.setBorder(border);
                            via.setBorder(border);
                            JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                            CentriVaccinaliGUI Cv = new CentriVaccinaliGUI();
                            this.dispose();
                        }
                    } catch (Eccezione ex) {
                        JOptionPane.showMessageDialog(this, message, "errore", JOptionPane.ERROR_MESSAGE);
                    } catch (IOException | URISyntaxException ex) {
                        Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    swing_awt.Bordo(centro, nome, border);
                    swing_awt.Bordo(Via, via, border);
                    swing_awt.Bordo(Sigla, sigla, border);
                    swing_awt.Bordo(Comune, comune, border);
                    swing_awt.Bordo(Cap, cap, border);
                    JOptionPane.showMessageDialog(this, " Riempire Tutti i Campi", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ec) {
            numcivico.setBorder(new LineBorder(Color.RED, 3, true));
            JOptionPane.showMessageDialog(this, "Errore di formattazione numerico nel numero civico", "Eror112", JOptionPane.WARNING_MESSAGE);
        }

        if (e.getSource() == annulla) {
            new OperazioniCentroGUI();
            this.dispose();
        }
    }

}
