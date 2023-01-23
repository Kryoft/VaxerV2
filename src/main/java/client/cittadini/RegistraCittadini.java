/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package client.cittadini;

import client.ClientToServerRequests;
import client.centrivaccinali.*;

import client.ClientGUI;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Classe dedicata alla registrazione di un cittadino vaccinato al sistema per mezzo di un account.
 */
public class RegistraCittadini extends Registrazioni {

    private final JLabel nome_cognome_label = new JLabel("Nome e cognome:", SwingConstants.CENTER),
                            login_label = new JLabel("Login:", SwingConstants.CENTER),
                            cf_label = new JLabel("Codice Fiscale:", SwingConstants.CENTER),
                            centro_label = new JLabel("Nome Centro:", SwingConstants.CENTER);

    private final JTextField cf_txt = new JTextField(),
                                centro_txt = new JTextField();

    private final PlaceholderTextField nome_txt = new PlaceholderTextField("nome"),
                                        cognome_txt = new PlaceholderTextField("cognome"),
                                        email_txt = new PlaceholderTextField("email"),
                                        password_txt = new PlaceholderTextField("password"),
                                        user_txt = new PlaceholderTextField("user id"),
                                        id_txt = new PlaceholderTextField(" Identificativo ");

    private final int labels_width = 180,
            base_txt_width = 220,
            button_width = 200,
            cf_and_nomecentro_txt_width = 200;

    private final int  base_height = 40,
            button_height = 100;

    private final int margin_x = 20,
            margin_x_buttons = 200,
            margin_y = 40,
            margin_y_buttons = 50;

    private final int labels_x = SwingAwt.centerItemOnXorY(display_width,
            labels_width + base_txt_width * 3 + margin_x * 2),
            first_txt_x = labels_x + labels_width,
            cognome_txt_x = first_txt_x + base_txt_width + margin_x,
            first_row_y = SwingAwt.centerItemOnXorY(display_height,
                            base_height * 4 + margin_y * 3 + margin_y_buttons + button_height),
            second_row_y = first_row_y + base_height + margin_y,
            psw_txt_x = first_txt_x + base_txt_width + margin_x,
            email_txt_x = psw_txt_x + base_txt_width + margin_x,
            third_row_y = second_row_y + base_height + margin_y,
            id_txt_x = first_txt_x + cf_and_nomecentro_txt_width + margin_x,
            fourth_row_y = third_row_y + base_height + margin_y,
            annulla_x = SwingAwt.centerItemOnXorY(display_width,
                    button_width * 2 + margin_x_buttons),
            conferma_x = annulla_x + button_width + margin_x_buttons,
            buttons_y = fourth_row_y + base_height + margin_y_buttons;

    public RegistraCittadini(CentroVaccinale struttura_vaccinale) {
        this.struttura_vaccinale = struttura_vaccinale;
        initWindow();
    }

    /**
     * Metodo utile per inizializzare le componenti del JFrame riguardanti la registrazione di
     * un cittadino.
     *
     * @author Daniele Caspani, Cristian Corti
     */
    private void initWindow() {
        settings("Inserisci Cittadino");
        ClientGUI.setCurrentWindow(this);

        layered_pane.add(nome_cognome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, first_row_y,                  //nome_cognome_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(nome_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_txt_x, first_row_y,                  //nome_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(cognome_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(cognome_txt_x, first_row_y,                  //cognome_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(conferma_x, buttons_y,                 //conferma_registrazione_cittadino
                button_width, button_height), 18, 1, false);

        layered_pane.add(cf_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, second_row_y,                  //cf_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(cf_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_txt_x, second_row_y,                  //cf_txt
                cf_and_nomecentro_txt_width, base_height), 15, 0, false);

        layered_pane.add(login_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, third_row_y,                  //login_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(user_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_txt_x, third_row_y,                  //user_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(password_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(psw_txt_x, third_row_y,                  //password_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(email_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(email_txt_x, third_row_y,                 //email_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(labels_x, fourth_row_y,                  //centro_label
                labels_width, base_height), 16, 1, false);

        layered_pane.add(centro_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(first_txt_x, fourth_row_y,                  //centro_txt
                cf_and_nomecentro_txt_width, base_height), 15, 0, false);
        centro_txt.setEditable(false);
        centro_txt.setText(struttura_vaccinale.getNomeCentro());

        layered_pane.add(id_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(id_txt_x, fourth_row_y,                  //id_txt
                base_txt_width, base_height), 15, 0, true);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(annulla_x, buttons_y,                 //annulla
                button_width, button_height), 18, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_txt.getBorder();

        setVisible(true);
    }

    /**
     * Metodo necessario alla registrazione di un nuovo utente compreso di vari controlli
     * di correttezza dei dati inseriti dall'utente.
     */
    private void registraCittadino() {
        try {
            Utility.run();
            String nome = nome_txt.getText().strip();
            String cognome = cognome_txt.getText().strip();
            String cf = cf_txt.getText().toUpperCase().strip();
            int id = Integer.parseInt(id_txt.getText().strip());
            String user = user_txt.getText().strip();
            String password = password_txt.getText();
            String email = email_txt.getText().strip();
            String centro = struttura_vaccinale.getNomeCentro().strip();
            String message = null;
            Cittadino c = new Cittadino();
            Login l;
            if (!user.equals("user id") && !nome.equals("nome") && !cognome.equals("cognome") && !password.equals("password") && !cf.equals("") && !email.equals("email") && !user.equals("") && !nome.equals("") && !cognome.equals("") && !password.equals("") && !email.equals("")) {
                nome_txt.setBorder(border);
                cognome_txt.setBorder(border);
                email_txt.setBorder(border);
                cf_txt.setBorder(border);
                password_txt.setBorder(border);
                user_txt.setBorder(border);

                centro_txt.setBorder(border);
                try {
                    if (!c.mailSyntaxCheck(email)) {
                        email_txt.setBorder(new LineBorder(Color.RED, 3, true));
                        message = "Sintassi della mail errata";
                        throw new Eccezione();
                    }
                    email_txt.setBorder(border);
                    if (!c.controllaCodiceFiscale(cf, nome, cognome)) {
                        cf_txt.setBorder(new LineBorder(Color.RED, 3, true));
                        message = "Sintassi del codice fiscale errata";
                        throw new Eccezione();
                    }
                    cf_txt.setBorder(border);

                    if (Utility.controlloCoppiaCFId(cf, id)) {
                        l = new Login(user, password);
                        if (!Utility.esisteUsername(l.getUserId())) {

                            if(!ClientToServerRequests.checkCFinRegistrati(cf)) {
                                c = new Cittadino(email, l, centro, id, nome, cognome, cf);
                                cf_txt.setBorder(border);
                                id_txt.setBorder(border);
                                user_txt.setBorder(border);
                                password_txt.setBorder(border);

                                ClientToServerRequests.insertIscritto(c);

                                JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                new CentriVaccinaliGUI();
                                this.dispose();
                            }
                            else{
                                cf_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                JOptionPane.showMessageDialog(this, "Codice fiscale già associato ad un account");
                            }
                        } else {
                            user_txt.setBorder(new LineBorder(Color.RED, 3, true));
                            JOptionPane.showMessageDialog(this, "Username già in uso");
                        }
                    } else {
                        nome_txt.setBorder(border);
                        cognome_txt.setBorder(border);
                        password_txt.setBorder(border);
                        cf_txt.setBorder(border);
                        user_txt.setBorder(border);
                        email_txt.setBorder(border);
                        id_txt.setBorder(new LineBorder(Color.RED, 3, true));
                        cf_txt.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Identificativo o Codice Fiscale errato");
                    }
                } catch (Eccezione exc) {
                    JOptionPane.showMessageDialog(this, message, "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                SwingAwt.modificaBordo(nome_txt);
                SwingAwt.modificaBordo(cognome_txt);
                SwingAwt.modificaBordo(password_txt);
                SwingAwt.modificaBordo(cf_txt);
                SwingAwt.modificaBordo(user_txt);
                SwingAwt.modificaBordo(email_txt);
                JOptionPane.showMessageDialog(this, "Riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ec) {
            JOptionPane.showMessageDialog(this, "Errore di formattazione dovuto a valore numerico non rispettato", "Errore", JOptionPane.WARNING_MESSAGE);
        }
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener, equivalente alla pressione di un pulsante.
     *
     * @param e evento che si è verificato
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == conferma) {
            registraCittadino();
        }

        else if (e.getSource() == annulla) {
            new CittadiniGUI();
            this.dispose();
        }
    }
}