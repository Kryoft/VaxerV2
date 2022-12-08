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

    private final JComboBox<String> tipologia_combo = new JComboBox<>(array_tipologia);
    private final int width_tipologia_combo = 310;

    private final JComboBox<String> qualificatore_combo = new JComboBox<>(array_qualificatori);
    private final int width_qualificatore_combo = 110;

    private final JLabel nome_label = new JLabel("Nome Centro e Tipologia:");
    private final int width_nome_label = 250;

    private final JLabel indirizzo_label = new JLabel("Indirizzo:");
    private final int width_indirizzo_label = 250;

    private final PlaceholderTextField nome_centro = new PlaceholderTextField("Nome centro");
    private final int width_nome_centro = 310;

    private final PlaceholderTextField via = new PlaceholderTextField("Nome via");
    private final int width_via = 250;

    private final PlaceholderTextField num_civico = new PlaceholderTextField("Numero civico");
    private final int width_num_civico = 100;

    private final PlaceholderTextField comune = new PlaceholderTextField("Comune");
    private final int width_comune = 250;

    private final PlaceholderTextField sigla = new PlaceholderTextField("Sigla");
    private final int width_sigla = 100;

    private final PlaceholderTextField cap = new PlaceholderTextField("CAP");
    private final int width_cap = 110;


    //Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536 @Marceca
    private final int margin = display_width * 10 / 1536,
                        first_row_x = (display_width /2) - ((width_nome_label + width_nome_centro + width_tipologia_combo + (margin * 2))/2),
                        first_row_y = (int)(0.2 * display_height),
                        x_nome = first_row_x + width_nome_label + margin,
                        x_tipologia_combo = x_nome + width_nome_centro + margin,
                        second_row_x = (display_width /2) - ((width_indirizzo_label + width_qualificatore_combo +
                                                            width_via + width_num_civico + width_cap + (margin * 4))/2),
                        second_row_y = (int)(0.4 * display_height),
                        x_qualificatore_combo = second_row_x + width_indirizzo_label + margin,
                        x_via = x_qualificatore_combo + width_qualificatore_combo + margin,
                        x_num_civico = x_via + width_via + margin,
                        x_cap = x_num_civico + width_num_civico + margin,
                        third_row_x = second_row_x + width_indirizzo_label,
                        third_row_y = (int)(0.5 * display_height),
                        x_comune = x_via,
                        x_sigla = x_comune + width_comune + margin,
                        fourth_row_x = (display_width /2) - ((width_buttons * 2 + 200)/2),
                        fourth_row_y = (int)(0.7 * display_height);

    public RegistraCentro() {
        initWindow();
    }

    /**
     * Permette l'inizializzazione dei componenti JFrame per quanto riguarda la registrazione del centro vaccinale
     *
     * @author Daniele Caspani, Manuel Marceca
     */
    private void initWindow() {
        settings("Registra Centro Vaccinale");

        layered_pane.add(nome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //nome_label
                width_nome_label, base_height), 16, 1, false);

        layered_pane.add(nome_centro, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_nome, first_row_y,                   //nome_centro
                width_nome_centro, base_height), 15, 0, true);

        layered_pane.add(tipologia_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_tipologia_combo, first_row_y,             //jtipologia
                width_tipologia_combo, base_height), 12, 0, false);

        layered_pane.add(indirizzo_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_indirizzo_label, base_height), 16, 1, false);

        layered_pane.add(qualificatore_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_qualificatore_combo, second_row_y,        //jqualificatore
                width_qualificatore_combo, base_height), 12, 0, false);

        layered_pane.add(via, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_via, second_row_y,                   //via
                width_via, base_height), 15, 0, true);

        layered_pane.add(num_civico, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_num_civico, second_row_y,             //numcivico
                width_num_civico, base_height), 15, 0, true);

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

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons + 200, fourth_row_y,      //conferma
                width_buttons, height_buttons), 15, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_centro.getBorder();

        setVisible(true);
    }

    /**
     * Metodo ereditato dall'interfaccia <code>ActionListener</code>
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma) {
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
                    SwingAwt.modificaBordo(centro, nome_centro, border);
                    SwingAwt.modificaBordo(Via, via, border);
                    comune.setBorder(border);
                    sigla.setBorder(border);
                    try {
                        Ic = new IndirizzoComposto(SwingAwt.decidiQualificatore(qualificatore_combo), Via, codice, Comune, Sigla, Cap);

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
                            SwingAwt.modificaBordo(Cap, cap, border);

                        if (!Ic.controllaComune(Comune)) {
                            comune.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Un comune puo' contenere solo caratteri letterali";
                            throw new Eccezione();
                        } else
                            SwingAwt.modificaBordo(Comune, comune, border);

                        if (!Ic.controllaSigla(Sigla)) {
                            sigla.setBorder(new LineBorder(Color.RED, 3, true));
                            message = "Una sigla di provincia contiene solo 2 caratteri letterali";
                            throw new Eccezione();
                        } else
                            SwingAwt.modificaBordo(Sigla, sigla, border);

                        CentroVaccinale sv = new CentroVaccinale(centro, SwingAwt.decidiTipologia(tipologia_combo), Ic);
                        if (Utility.esisteCentro(0, sv.getNomeCentro(), "./data/CentriVaccinali.dati.txt")) {
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
                    SwingAwt.modificaBordo(centro, nome_centro, border);
                    SwingAwt.modificaBordo(Via, via, border);
                    SwingAwt.modificaBordo(Sigla, sigla, border);
                    SwingAwt.modificaBordo(Comune, comune, border);
                    SwingAwt.modificaBordo(Cap, cap, border);
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
