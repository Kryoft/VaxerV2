/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.centrivaccinali;


import client.ClientGUI;
import client.ClientToServerRequests;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che estende la classe <code>Registrazioni</code>. Contiene metodi per la creazione dell'interfaccia
 * grafica e per la logica riguardante la registrazione di un centro vaccinale
 *
 * @author Daniele Caspani
 * @author Cristian Corti
 * @author Manuel Marceca
 */
public class RegistraCentro extends Registrazioni {

    private final JComboBox<String> tipologia_combo = new JComboBox<>(array_tipologia);
    private final int width_tipologia_combo = 310;

    private final JComboBox<String> qualificatore_combo = new JComboBox<>(array_qualificatori);
    private final int width_qualificatore_combo = 110;

    private final JLabel nome_label = new JLabel("Nome Centro e Tipologia:");
    private final int width_nome_label = 250;

    private final JLabel indirizzo_label = new JLabel("Indirizzo:");
    private final int width_indirizzo_label = 250;

    private final PlaceholderTextField nome_centro_text = new PlaceholderTextField("Nome centro");
    private final int width_nome_centro_text = 310;

    private final PlaceholderTextField via_text = new PlaceholderTextField("Nome via");
    private final int width_via_text = 250;

    private final PlaceholderTextField num_civico_text = new PlaceholderTextField("Numero civico");
    private final int width_num_civico_text = 100;

    private final PlaceholderTextField comune_text = new PlaceholderTextField("Comune");
    private final int width_comune_text = 250;

    private final PlaceholderTextField sigla_text = new PlaceholderTextField("Sigla");
    private final int width_sigla_text = 100;

    private final PlaceholderTextField cap_text = new PlaceholderTextField("CAP");
    private final int width_cap_text = 110;


    //Margine standardizzato e proporzionato a partire dal mio schermo, che ha display width pari a 1536 @Marceca
    private final int margin = display_width * 10 / 1536,
                        first_row_x = (display_width /2) - ((width_nome_label + width_nome_centro_text + width_tipologia_combo + (margin * 2))/2),
                        first_row_y = (int)(0.2 * display_height),
                        x_nome = first_row_x + width_nome_label + margin,
                        x_tipologia_combo = x_nome + width_nome_centro_text + margin,
                        second_row_x = (display_width /2) - ((width_indirizzo_label + width_qualificatore_combo +
                                width_via_text + width_num_civico_text + width_cap_text + (margin * 4))/2),
                        second_row_y = (int)(0.4 * display_height),
                        x_qualificatore_combo = second_row_x + width_indirizzo_label + margin,
                        x_via = x_qualificatore_combo + width_qualificatore_combo + margin,
                        x_num_civico = x_via + width_via_text + margin,
                        x_cap = x_num_civico + width_num_civico_text + margin,
                        third_row_x = second_row_x + width_indirizzo_label,
                        third_row_y = (int)(0.5 * display_height),
                        x_comune = x_via,
                        x_sigla = x_comune + width_comune_text + margin,
                        fourth_row_x = (display_width /2) - ((width_buttons * 2 + 200)/2),
                        fourth_row_y = (int)(0.7 * display_height);

    public RegistraCentro() {
        initWindow();
    }

    /**
     * Inserisce i componenti necessari alla registrazione del centro vaccinale nella finestra e la rende visibile.
     *
     * @author Daniele Caspani
     * @author Manuel Marceca
     * @author Cristian Corti
     */
    private void initWindow() {
        settings("Registra Centro Vaccinale");
        ClientGUI.setCurrentWindow(this);

        layered_pane.add(nome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_row_x, first_row_y,              //nome_label
                width_nome_label, base_height), 16, 1, false);

        layered_pane.add(nome_centro_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_nome, first_row_y,                   //nome_centro
                width_nome_centro_text, base_height), 15, 0, true);

        layered_pane.add(tipologia_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_tipologia_combo, first_row_y,             //tipologia_combo
                width_tipologia_combo, base_height), 12, 0, false);

        layered_pane.add(indirizzo_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(second_row_x, second_row_y,            //indirizzo_label
                width_indirizzo_label, base_height), 16, 1, false);

        layered_pane.add(qualificatore_combo, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_qualificatore_combo, second_row_y,        //qualificatore_combo
                width_qualificatore_combo, base_height), 12, 0, false);

        layered_pane.add(via_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_via, second_row_y,                   //via
                width_via_text, base_height), 15, 0, true);

        layered_pane.add(num_civico_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_num_civico, second_row_y,             //num_civico
                width_num_civico_text, base_height), 15, 0, true);

        layered_pane.add(cap_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_cap, second_row_y,                   //cap
                width_cap_text, base_height), 15, 0, true);

        layered_pane.add(comune_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_comune, third_row_y,                 //comune
                width_comune_text, base_height), 15, 0, true);

        layered_pane.add(sigla_text, 2, 0);
        layeredPaneSettings(0, new Rectangle(x_sigla, third_row_y,                  //sigla
                width_sigla_text, base_height), 15, 0, true);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x, fourth_row_y,            //annulla
                width_buttons, height_buttons), 15, 1, false);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(fourth_row_x + width_buttons + 200, fourth_row_y,      //conferma
                width_buttons, height_buttons), 15, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_centro_text.getBorder();

        setVisible(true);
    }

    /**
     * Funzione che si occupa di controllare che i dati inseriti in ogni campo testuale siano validi.
     * In caso favorevole, permette la registrazione del centro vaccinale.
     * In caso contrario, rende rosso il bordo di tali campi e fornisce una spiegazione dell'errore.
     *
     * @author Daniele Caspani
     * @author Cristian Corti
     * @author Manuel Marceca
     */
    private void registraCentroVaccinale() {
        String message = null;
        IndirizzoComposto indirizzo;
        String nome_centro = nome_centro_text.getText().strip();
        String via = via_text.getText().strip();
        int numero_civico;
        String comune = comune_text.getText().toUpperCase().strip();
        String sigla = sigla_text.getText().toUpperCase().strip();
        String cap = cap_text.getText().strip();
        num_civico_text.setBorder(border);
        if (!nome_centro.equals("") && !via.equals("") && !comune.equals("") && !sigla.equals("") && !cap.equals("") && !nome_centro.equals("Nome centro") && !via.equals("Nome Via") && !comune.equals("COMUNE") && !sigla.equals("SIGLA") && !cap.equals("CAP")) {
            SwingAwt.modificaBordo(nome_centro_text);
            SwingAwt.modificaBordo(via_text);
            comune_text.setBorder(border);
            sigla_text.setBorder(border);
            try {
                numero_civico = Integer.parseInt(num_civico_text.getText());
                indirizzo = new IndirizzoComposto(SwingAwt.decidiQualificatore(qualificatore_combo), via, numero_civico, comune, sigla, cap);

                if(!indirizzo.controllaNumeroCivico(numero_civico)){
                    num_civico_text.setBorder(new LineBorder(Color.RED, 3, true));
                    message = "Il numero civico deve essere maggiore di zero";
                    throw new Eccezione();
                } else
                    SwingAwt.modificaBordo(num_civico_text);

                if (!indirizzo.controllaCap(cap)) {
                    cap_text.setBorder(new LineBorder(Color.RED, 3, true));
                    message = "Il cap deve contenere 5 cifre decimali";
                    throw new Eccezione();
                } else
                    SwingAwt.modificaBordo(cap_text);

                if (!indirizzo.controllaComune(comune)) {
                    comune_text.setBorder(new LineBorder(Color.RED, 3, true));
                    message = "Il comune puo' contenere solo caratteri letterali";
                    throw new Eccezione();
                } else
                    SwingAwt.modificaBordo(comune_text);

                if (!indirizzo.controllaSigla(sigla)) {
                    sigla_text.setBorder(new LineBorder(Color.RED, 3, true));
                    message = "La sigla di provincia deve contenere solo 2 caratteri letterali";
                    throw new Eccezione();
                } else
                    SwingAwt.modificaBordo(sigla_text);

                CentroVaccinale nuovo_centro = new CentroVaccinale(nome_centro, SwingAwt.decidiTipologia(tipologia_combo), indirizzo);

                if (Utility.esisteCentro(nuovo_centro.getNomeCentro())) {
                    JOptionPane.showMessageDialog(this, "Centro gia' esistente nell'applicazione; Cambiare Nome", "Errore", JOptionPane.ERROR_MESSAGE);
                } else {

                    // Dati validi! Insert nel database...

                    ClientToServerRequests.insertCentro(nuovo_centro);

                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                    new CentriVaccinaliGUI();
                    this.dispose();
                }
            } catch (NumberFormatException ec) {
                num_civico_text.setBorder(new LineBorder(Color.RED, 3, true));
                JOptionPane.showMessageDialog(this, "Il numero civico deve essere un numero", "Errore", JOptionPane.WARNING_MESSAGE);
            } catch (Eccezione ex) {
                JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException se) {
                Logger.getLogger(Registrazioni.class.getName()).log(Level.SEVERE, null, se);
            }
        } else {
            SwingAwt.modificaBordo(nome_centro_text);
            SwingAwt.modificaBordo(via_text);
            SwingAwt.modificaBordo(sigla_text);
            SwingAwt.modificaBordo(num_civico_text);
            SwingAwt.modificaBordo(comune_text);
            SwingAwt.modificaBordo(cap_text);
            JOptionPane.showMessageDialog(this, " Riempire Tutti i Campi", "Errore", JOptionPane.ERROR_MESSAGE);
        }
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
            registraCentroVaccinale();
        }

        else if (e.getSource() == annulla) {
            new OperazioniCentroGUI();
            this.dispose();
        }
    }

}
