/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.*;

import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistraCittadini extends Registrazioni {

    private final JLabel nome_cognome_label = new JLabel("Nome e cognome:"),
                            login_label = new JLabel("Login:"),
                            cf_label = new JLabel("Codice Fiscale:"),
                            centro_label = new JLabel("Nome Centro:");

    private final JTextField cf_txt = new JTextField(),
                                centro_txt = new JTextField();

    private final PlaceholderTextField nome_txt = new PlaceholderTextField("nome"),
                                        cognome_txt = new PlaceholderTextField("cognome"),
                                        email_txt = new PlaceholderTextField("email"),
                                        password_txt = new PlaceholderTextField("password"),
                                        user_txt = new PlaceholderTextField("user id"),
                                        id_txt = new PlaceholderTextField(" Identificativo ");

    public RegistraCittadini() {
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

        layered_pane.add(nome_cognome_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(410, 220,                  //nome_cognome_label
                520, 120), 16, 1, false);

        layered_pane.add(nome_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(610, 260,                  //nome_txt
                250, 40), 15, 0, true);

        layered_pane.add(cognome_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(870, 260,                  //cognome_txt
                250, 40), 15, 0, true);

        layered_pane.add(conferma, 2, 0);
        layeredPaneSettings(0, new Rectangle(1250, 230,                 //conferma_registrazione_cittadino
                200, 100), 18, 1, false);

        layered_pane.add(cf_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(410, 370,                  //cf_label
                520, 120), 16, 1, false);

        layered_pane.add(cf_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(610, 410,                  //cf_txt
                310, 40), 15, 0, false);

        layered_pane.add(login_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(410, 520,                  //login_label
                520, 120), 16, 1, false);

        layered_pane.add(user_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(610, 560,                  //user_txt
                200, 40), 15, 0, true);

        layered_pane.add(password_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(820, 560,                  //password_txt
                200, 40), 15, 0, true);

        layered_pane.add(email_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(1040, 560,                 //email_txt
                240, 40), 15, 0, true);

        layered_pane.add(centro_label, 2, 0);
        layeredPaneSettings(0, new Rectangle(410, 670,                  //centro_label
                520, 120), 16, 1, false);

        layered_pane.add(centro_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(610, 710,                  //centro_txt
                310, 40), 15, 0, false);

        layered_pane.add(id_txt, 2, 0);
        layeredPaneSettings(0, new Rectangle(930, 710,                  //id_txt
                160, 40), 15, 0, true);

        layered_pane.add(annulla, 2, 0);
        layeredPaneSettings(0, new Rectangle(1250, 680,                 //annulla
                200, 100), 18, 1, false);


        conferma.addActionListener(this);
        annulla.addActionListener(this);
        border = nome_txt.getBorder();

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma) {
                Utility.run();
                String Nome = nome_txt.getText();
                String Cognome = cognome_txt.getText();
                String Codice = cf_txt.getText().toUpperCase();
                short id = (short) (Integer.parseInt(id_txt.getText()) - 32767);  // ?
                String user = user_txt.getText();
                String password = password_txt.getText();
                String email = email_txt.getText();
                String Centro = centro_txt.getText();
                String message = null;
                Cittadini c = new Cittadini();
                Login l;
                if (!user.equals("user id") && !Nome.equals("nome") && !Cognome.equals("cognome") && !password.equals("password") && !Codice.equals("") && !email.equals("email") && !user.equals("") && !Nome.equals("") && !Cognome.equals("") && !password.equals("") && !email.equals("")) {
                    nome_txt.setBorder(border);
                    cognome_txt.setBorder(border);
                    email_txt.setBorder(border);
                    cf_txt.setBorder(border);
                    password_txt.setBorder(border);
                    user_txt.setBorder(border);

                    if (Utility.esisteCentro(0, Centro, "./data/CentriVaccinali.dati.txt")) {
                        centro_txt.setBorder(border);
                        try {
                            if (!c.mailSyntaxCheck(email)) {
                                email_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                message = "Sintassi della mail errata";
                                throw new Eccezione();
                            }
                            email_txt.setBorder(border);
                            if (!c.controllaCodiceFiscale(Codice)) {
                                cf_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                message = "Sintassi del codice fiscale errata";
                                throw new Eccezione();
                            }
                            cf_txt.setBorder(border);

                            if (Utility.controlloCF(Codice, id, "./data/Vaccinati_" + Centro + ".dati.txt")) {
                                l = new Login(user, password);
                                if (Utility.controlloLogin(l.toString(), "./data/log.txt") == false) {
                                    c = new Cittadini(email, l, Centro, id, Nome, Cognome, Codice);
                                    cf_txt.setBorder(border);
                                    id_txt.setBorder(border);
                                    user_txt.setBorder(border);
                                    password_txt.setBorder(border);

                                    Utility.scriviFile("./data/log.txt", l.toString());
                                    Utility.scriviFile("./data/Cittadini_Registrati.dati.txt", c.toString());
                                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                    new CentriVaccinaliGUI();
                                    this.dispose();
                                } else {
                                    user_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                    password_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                    JOptionPane.showMessageDialog(this, "Coppia user e password gia' inserita");
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
                                JOptionPane.showMessageDialog(this, "Operazione fallita Identificativo o codice Fiscale errato!!");
                            }
                        } catch (Eccezione exc) {
                            JOptionPane.showMessageDialog(this, message, "errore", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException | URISyntaxException ex) {
                            Logger.getLogger(RegistraCittadini.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        centro_txt.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Centro Insesistente o non registrato all'applicazione", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    SwingAwt.modificaBordo(Nome, nome_txt, border);
                    SwingAwt.modificaBordo(Cognome, cognome_txt, border);
                    SwingAwt.modificaBordo(password, password_txt, border);
                    SwingAwt.modificaBordo(Codice, cf_txt, border);
                    SwingAwt.modificaBordo(user, user_txt, border);
                    SwingAwt.modificaBordo(email, email_txt, border);
                    JOptionPane.showMessageDialog(this, "Riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else if (e.getSource() == annulla) {
                new CittadiniGUI();
                this.dispose();
            }

        } catch (NumberFormatException ec) {
            JOptionPane.showMessageDialog(this, "Errore di formattazione dovuto a valore numerico non rispettato", "Error112", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) {
            Logger.getLogger(RegistraCittadini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}