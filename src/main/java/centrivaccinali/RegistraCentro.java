/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RegistraCentro extends Registrazioni {

    private final int base_height = 40;

    private final JButton conferma_registrazione_centro = new JButton("CONFERMA");
    private final JButton annulla = new JButton("TORNA INDIETRO");
    private final int width_buttons = 200;
    private final int height_buttons = 100;

    private final JComboBox<String> jtipologia = new JComboBox<>(array_tipologia);
    private final int width_jtipologia = 310;

    private final JComboBox<String> jqualificatore = new JComboBox<>(array_qualificatori);
    private final int width_jqualificatore = 110;

    private final JLabel nome_label = new JLabel("Nome Centro e Tipologia:");
    private final int width_nome_label = 250;

    private final JLabel indirizzo_label = new JLabel("Indirizzo:");
    private final int width_indirizzo_label = 250;

    private final PTextField nome_centro = new PTextField("Nome centro");
    private final int width_nome = 310;

    private final PTextField via = new PTextField("Nome via");
    private final int width_via = 250;

    private final PTextField num_civico = new PTextField("Numero civico");
    private final int width_numcivico = 100;

    private final PTextField comune = new PTextField("Comune");
    private final int width_comune = 250;

    private final PTextField sigla = new PTextField("Sigla");
    private final int width_sigla = 100;

    private final PTextField cap = new PTextField("CAP");
    private final int width_cap = 110;

    private final int margin = displayWidth * 10 / 1536;      //Margine standardizzato e proporzionato a partire dal mio schermo,
    //che ha display width pari a 1536 @Marceca

    private final int first_row_x = (displayWidth/2) - ((width_nome_label + width_nome + width_jtipologia + (margin * 2))/2);
    private final int first_row_y = (int)(0.2 * displayHeight);

    private final int x_nome = first_row_x + width_nome_label + margin;
    private final int x_jtipologia = x_nome + width_nome + margin;

    private final int second_row_x = (displayWidth/2) -
            ((width_indirizzo_label + width_jqualificatore + width_via + width_numcivico + width_cap + (margin * 4))/2);
    private final  int second_row_y = (int)(0.4 * displayHeight);

    private final int x_jqualificatore = second_row_x + width_indirizzo_label + margin;
    private final int x_via = x_jqualificatore + width_jqualificatore + margin;
    private final int x_numcivico = x_via + width_via + margin;
    private final int x_cap = x_numcivico + width_numcivico + margin;


    private final int third_row_x = second_row_x + width_indirizzo_label;
    private final int third_row_y = (int)(0.5 * displayHeight);

    private final int x_comune = x_via;
    private final int x_sigla = x_comune + width_comune + margin;


    private final int fourth_row_x = (displayWidth/2) -
            ((width_buttons * 2 + 200)/2);
    private final int fourth_row_y = (int)(0.7 * displayHeight);

    public RegistraCentro() {
        initRegistraCentro();
    }

    /**
     * permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initRegistraCentro() {

        settings("Registra Centro Vaccinale");

        layered_pane.add(nome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //nome_label
                width_nome_label, base_height), 16, 1, false);

        layered_pane.add(nome_centro, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_nome, first_row_y,                   //nome_centro
                width_nome, base_height), 15, 0, true);

        layered_pane.add(jtipologia, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_jtipologia, first_row_y,             //jtipologia
                width_jtipologia, base_height), 12, 0, false);

        layered_pane.add(indirizzo_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_indirizzo_label, base_height), 16, 1, false);

        layered_pane.add(jqualificatore, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_jqualificatore, second_row_y,        //jqualificatore
                width_jqualificatore, base_height), 12, 0, false);

        layered_pane.add(via, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_via, second_row_y,                   //via
                width_via, base_height), 15, 0, true);

        layered_pane.add(num_civico, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_numcivico, second_row_y,             //numcivico
                width_numcivico, base_height), 15, 0, true);

        layered_pane.add(cap, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_cap, second_row_y,                   //cap
                width_cap, base_height), 15, 0, true);

        layered_pane.add(comune, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_comune, third_row_y,                 //comune
                width_comune, base_height), 15, 0, true);

        layered_pane.add(sigla, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_sigla, third_row_y,                  //sigla
                width_sigla, base_height), 15, 0, true);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x, fourth_row_y,            //annulla
                width_buttons, height_buttons), 15, 1, false);

        layered_pane.add(conferma_registrazione_centro, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons + 200, fourth_row_y,      //conferma
                width_buttons, height_buttons), 15, 1, false);


        border = nome_centro.getBorder();

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
                int codice = Integer.parseInt(num_civico.getText());
                String centro = nome_centro.getText();
                String Via = via.getText();
                String Comune = comune.getText().toUpperCase();
                String Sigla = sigla.getText().toUpperCase();
                String Cap = cap.getText();
                num_civico.setBorder(border);
                if (!centro.equals("") && !Via.equals("") && !Comune.equals("") && !Sigla.equals("") && !Cap.equals("") && !centro.equals("Nome centro") && !Via.equals("Nome Via") && !Comune.equals("comune") && !Sigla.equals("sigla") && !Cap.equals("cap")) {
                    swing_awt.Bordo(centro, nome_centro, border);
                    swing_awt.Bordo(Via, via, border);
                    comune.setBorder(border);
                    sigla.setBorder(border);
                    try {
                        Ic = new IndirizzoComposto(swing_awt.DecidiQualificatore(jqualificatore), Via, codice, Comune, Sigla, Cap);

                        if(!Ic.controllaNumeroCivico(codice)){
                            num_civico.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Il numero civico è invalido";
                            throw new Eccezione();
                        }

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
                        if (Utility.esisteCentro(0, sv.getNome_centro(), "./data/CentriVaccinali.dati.txt")) {
                            JOptionPane.showMessageDialog(this, "Centro gia' esistente nell'applicazione; Cambiare Nome", "error", JOptionPane.ERROR_MESSAGE);
                        } else {
                            Utility.scriviFile("./data/CentriVaccinali.dati.txt", sv.toString());

                            nome_centro.setBorder(border);
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
                    swing_awt.Bordo(centro, nome_centro, border);
                    swing_awt.Bordo(Via, via, border);
                    swing_awt.Bordo(Sigla, sigla, border);
                    swing_awt.Bordo(Comune, comune, border);
                    swing_awt.Bordo(Cap, cap, border);
                    JOptionPane.showMessageDialog(this, " Riempire Tutti i Campi", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException ec) {
            num_civico.setBorder(new LineBorder(Color.RED, 3, true));
            JOptionPane.showMessageDialog(this, "Errore di formattazione numerico nel numero civico", "Eror112", JOptionPane.WARNING_MESSAGE);
        }

        if (e.getSource() == annulla) {
            new OperazioniCentroGUI();
            this.dispose();
        }
    }

}
