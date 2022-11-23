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

/**
 * Classe che estende <code>JFrame</code> e implementa l'interfaccia <code>ActionListener</code>;
 * Contiene il codice per la funzionalità inserisciEventoAvverso e per
 * la registrazione dei cittadini.
 *
 * @author Daniele Caspani
 */
public class RegistraCittadini extends Registrazioni {

    public RegistraCittadini() {
        initWindow();
    }

    /**
     * Metodo utile per inizializzare le componenti del JFrame riguardanti la registrazione di
     * un cittadino.
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        settings("Inserisci Cittadino");

        conferma_registrazione_cittadino = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");

        nome_txt = new PTextField("nome");
        cognome_txt = new PTextField("cognome");
        codice_txt = new JTextField();
        email_txt = new PTextField("email");
        password_txt = new PTextField("password");
        user_txt = new PTextField("user id");
        id_txt = new PTextField(" Identificativo ");
        centro_txt = new JTextField();

        nome_label = new JLabel("Nome e cognome:");
        login_label = new JLabel("Codice Fiscale:");
        codice_label = new JLabel("Login:");
        centro_label = new JLabel("Nome Centro:");

        lpane.add(conferma_registrazione_cittadino, 2, 0);
        lpane.add(annulla, 2, 0);
        lpane.add(nome_label, 2, 0);
        lpane.add(codice_label, 2, 0);
        lpane.add(login_label, 2, 0);
        lpane.add(centro_label, 2, 0);
        lpane.add(nome_txt, 2, 0);
        lpane.add(cognome_txt, 2, 0);
        lpane.add(codice_txt, 2, 0);
        lpane.add(email_txt, 2, 0);
        lpane.add(password_txt, 2, 0);
        lpane.add(user_txt, 2, 0);
        lpane.add(id_txt, 2, 0);
        lpane.add(centro_txt, 2, 0);

        layeredPaneSettings(0, new Rectangle(610, 710, 310, 40), 15, 0, false);
        layeredPaneSettings(1, new Rectangle(930, 710, 160, 40), 15, 0, true);
        layeredPaneSettings(2, new Rectangle(610, 560, 200, 40), 15, 0, true);
        layeredPaneSettings(3, new Rectangle(820, 560, 200, 40), 15, 0, true);
        layeredPaneSettings(4, new Rectangle(1040, 560, 240, 40), 15, 0, true);
        layeredPaneSettings(5, new Rectangle(610, 410, 310, 40), 15, 0, false);
        layeredPaneSettings(6, new Rectangle(870, 260, 250, 40), 15, 0, true);
        layeredPaneSettings(7, new Rectangle(610, 260, 250, 40), 15, 0, true);
        layeredPaneSettings(8, new Rectangle(410, 670, 520, 120), 16, 1, false);
        layeredPaneSettings(9, new Rectangle(410, 370, 520, 120), 16, 1, false);
        layeredPaneSettings(10, new Rectangle(410, 520, 520, 120), 16, 1, false);
        layeredPaneSettings(11, new Rectangle(410, 220, 520, 120), 16, 1, false);
        layeredPaneSettings(12, new Rectangle(1250, 680, 200, 100), 18, 1, false);
        layeredPaneSettings(13, new Rectangle(1250, 230, 200, 100), 18, 1, false);

        border = nome_txt.getBorder();

        conferma_registrazione_cittadino.addActionListener(this);
        annulla.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == conferma_registrazione_cittadino) {
                Utility.run();
                String Nome = nome_txt.getText();
                String Cognome = cognome_txt.getText();
                String Codice = codice_txt.getText().toUpperCase();
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
                    codice_txt.setBorder(border);
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
                                codice_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                message = "Sintassi del codice fiscale errata";
                                throw new Eccezione();
                            }
                            codice_txt.setBorder(border);

                            if (Utility.controlloCF(Codice, id, "./data/Vaccinati_" + Centro + ".dati.txt")) {
                                l = new Login(user, password);
                                if (Utility.controlloLogin(l.toString(), "./data/log.txt") == false) {
                                    c = new Cittadini(email, l, Centro, id, Nome, Cognome, Codice);
                                    codice_txt.setBorder(border);
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
                                codice_txt.setBorder(border);
                                user_txt.setBorder(border);
                                email_txt.setBorder(border);
                                id_txt.setBorder(new LineBorder(Color.RED, 3, true));
                                codice_txt.setBorder(new LineBorder(Color.RED, 3, true));
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
                    swing_awt.Bordo(Nome, nome_txt, border);
                    swing_awt.Bordo(Cognome, cognome_txt, border);
                    swing_awt.Bordo(password, password_txt, border);
                    swing_awt.Bordo(Codice, codice_txt, border);
                    swing_awt.Bordo(user, user_txt, border);
                    swing_awt.Bordo(email, email_txt, border);
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