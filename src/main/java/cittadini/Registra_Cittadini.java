/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.*;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Classe che estende <code> JFrame </code> e implementa l'interfaccia <code> Action Listener </code>;
 * Contiene il codice per la funzionalità inserisciEventoAvverso e per
 * la registrazione dei cittadini.
 *
 * @author daniele Caspani
 */
public class Registra_Cittadini extends JFrame implements ActionListener {

    Utility u = new Utility();
    SwingAwt sw = new SwingAwt();
    private JLabel lblnome, lblcodice, lbllogin, lblcentro;
    private JTextField txtcodice, txtcentro;
    private PTextField txtNome, txtcognome, txtemail, txtpassword, txtuser, txtid;
    private Border border;
    private JButton c1, annulla;
    private JLabel lblnomeC, lblnote, lblevento;
    private JTextField txtNomeC, txtevento;
    private TextArea txtnote;
    private PTextField txtindice;
    private JButton c2;
    private JLayeredPane lpane;
    private JPanel background, panel1;

    public Registra_Cittadini(int i) {
        if (i == 1)
            init();
        if (i == 2)
            initial();
    }

    /**
     * metodo utile per inizializzare la finestra Jframe e il <strong> LayeredPane </strong>,
     * al quale verranno aggiunti tutti i vari componenti su diversi livelli.
     *
     * @author Daniele Caspani
     */
    private void settings(String title) {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        this.setPreferredSize(new Dimension(1870, 1040));
        background = new JPanel();
        add(background);
        background.setLayout(null);
        background.setBackground(Color.WHITE);
        lpane = new JLayeredPane();
        panel1 = new JPanel();
        background = new JPanel();
        add(lpane, BorderLayout.CENTER);
        lpane.setBounds(0, 0, 2000, 1000);
        panel1.setBackground(Color.GRAY);
        panel1.setBounds(0, 0, 2000, 1000);
        panel1.setOpaque(true);
        background.setBackground(Color.WHITE);
        background.setBorder(new LineBorder(Color.CYAN, 30, false));
        background.setBounds(300, 100, 1250, 800);
        background.setOpaque(true);
        lpane.add(panel1, 0, 0);
        lpane.add(background, 1, 0);
        pack();
    }

    /**
     * metodo utile per settare alcune caratteristiche dei componenti JFrame
     *
     * @param button definisce l' ordine di inserimento dei componenti JFrame
     * @param r      definisce la misura
     * @param d      definisce la dimensione della scritta
     * @param font   definisce il tipo di scritta(BOLD o PLAIN)
     * @param b      definisce il colore della scritta
     */
    public void Bsettings(int button, Rectangle r, int d, int font, boolean b) {
        lpane.getComponent(button).setBounds(r);
        lpane.getComponent(button).setFont(new Font("Arial", font, d));
        if (b)
            lpane.getComponent(button).setForeground(Color.lightGray);
    }

    /**
     * metodo utile per l'inizializzazione dei componenti JFrame riguardanti l'inserimento
     * degli eventi avversi.
     *
     * @author Daniele Caspani
     */
    public void init() {
        settings("INSERISCI EVENTO AVVERSO!");

        c2 = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");
        txtNomeC = new JTextField();
        txtevento = new JTextField();
        txtindice = new PTextField("Indice(da 1 a 5)");
        txtnote = new TextArea();

        lblnomeC = new JLabel("Nome_Centro:");
        lblevento = new JLabel("Evento:");
        lblnote = new JLabel("Note(opzionale):");

        lpane.add(c2, 2, 0);
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

        c2.addActionListener(this);
        annulla.addActionListener(this);

        setVisible(true);

        border = txtNomeC.getBorder();
    }

    /**
     * metodo utile per inizializzare le componenti del jframe riguardanti la registrazione di
     * un cittadino.
     *
     * @author Daniele Caspani
     */
    private void initial() {
        settings("INSERISCI CITTADINO");

        c1 = new JButton("CONFERMA");
        annulla = new JButton("TORNA INDIETRO");

        txtNome = new PTextField("nome");
        txtcognome = new PTextField("cognome");
        txtcodice = new JTextField();
        txtemail = new PTextField("email");
        txtpassword = new PTextField("password");
        txtuser = new PTextField("user id");
        txtid = new PTextField(" Identificativo ");
        txtcentro = new JTextField();

        lblnome = new JLabel("Nome e cognome:");
        lbllogin = new JLabel("Codice Fiscale:");
        lblcodice = new JLabel("Login:");
        lblcentro = new JLabel("Nome Centro:");

        lpane.add(c1, 2, 0);
        lpane.add(annulla, 2, 0);
        lpane.add(lblnome, 2, 0);
        lpane.add(lblcodice, 2, 0);
        lpane.add(lbllogin, 2, 0);
        lpane.add(lblcentro, 2, 0);
        lpane.add(txtNome, 2, 0);
        lpane.add(txtcognome, 2, 0);
        lpane.add(txtcodice, 2, 0);
        lpane.add(txtemail, 2, 0);
        lpane.add(txtpassword, 2, 0);
        lpane.add(txtuser, 2, 0);
        lpane.add(txtid, 2, 0);
        lpane.add(txtcentro, 2, 0);

        Bsettings(0, new Rectangle(610, 710, 310, 40), 15, 0, false);
        Bsettings(1, new Rectangle(930, 710, 160, 40), 15, 0, true);
        Bsettings(2, new Rectangle(610, 560, 200, 40), 15, 0, true);
        Bsettings(3, new Rectangle(820, 560, 200, 40), 15, 0, true);
        Bsettings(4, new Rectangle(1040, 560, 240, 40), 15, 0, true);
        Bsettings(5, new Rectangle(610, 410, 310, 40), 15, 0, false);
        Bsettings(6, new Rectangle(870, 260, 250, 40), 15, 0, true);
        Bsettings(7, new Rectangle(610, 260, 250, 40), 15, 0, true);
        Bsettings(8, new Rectangle(410, 670, 520, 120), 16, 1, false);
        Bsettings(9, new Rectangle(410, 370, 520, 120), 16, 1, false);
        Bsettings(10, new Rectangle(410, 520, 520, 120), 16, 1, false);
        Bsettings(11, new Rectangle(410, 220, 520, 120), 16, 1, false);
        Bsettings(12, new Rectangle(1250, 680, 200, 100), 18, 1, false);
        Bsettings(13, new Rectangle(1250, 230, 200, 100), 18, 1, false);

        border = txtNome.getBorder();
        c1.addActionListener(this);
        annulla.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            if (e.getSource() == c1) {
                u.run();
                String Nome = txtNome.getText();
                String Cognome = txtcognome.getText();
                String Codice = txtcodice.getText().toUpperCase();
                short id = (short) (Integer.parseInt(txtid.getText()) - 32767);
                String user = txtuser.getText();
                String password = txtpassword.getText();
                String email = txtemail.getText();
                String Centro = txtcentro.getText();
                String message = null;
                Cittadini c = new Cittadini();
                Login l;
                if (!user.equals("user id") && !Nome.equals("nome") && !Cognome.equals("cognome") && !password.equals("password") && !Codice.equals("") && !email.equals("email") && !user.equals("") && !Nome.equals("") && !Cognome.equals("") && !password.equals("") && !email.equals("")) {
                    txtNome.setBorder(border);
                    txtcognome.setBorder(border);
                    txtemail.setBorder(border);
                    txtcodice.setBorder(border);
                    txtpassword.setBorder(border);
                    txtuser.setBorder(border);

                    if (u.EsisteCentro(0, Centro, "./data/CentriVaccinali.dati.txt")) {
                        txtcentro.setBorder(border);
                        try {
                            if (!c.mailSyntaxCheck(email)) {
                                txtemail.setBorder(new LineBorder(Color.RED, 3, true));
                                message = "Sintassi della mail errata";
                                throw new Eccezione();
                            }
                            txtemail.setBorder(border);
                            if (!c.controllaCodiceFiscale(Codice)) {
                                txtcodice.setBorder(new LineBorder(Color.RED, 3, true));
                                message = "Sintassi del codice fiscale errata";
                                throw new Eccezione();
                            }
                            txtcodice.setBorder(border);

                            if (u.controllocf(Codice, id, "./data/Vaccinati_" + Centro + ".dati.txt")) {
                                l = new Login(user, password);
                                if (u.ControlloLogin(l.toString(), "./data/log.txt") == false) {
                                    c = new Cittadini(email, l, Centro, id, Nome, Cognome, Codice);
                                    txtcodice.setBorder(border);
                                    txtid.setBorder(border);
                                    txtuser.setBorder(border);
                                    txtpassword.setBorder(border);

                                    u.ScriviFile("./data/log.txt", l.toString());
                                    u.ScriviFile("./data/Cittadini_Registrati.dati.txt", c.toString());
                                    JOptionPane.showMessageDialog(this, "Operazione Completata Con Successo");
                                    CentriVaccinaliGUI cv = new CentriVaccinaliGUI();
                                    this.dispose();
                                } else {
                                    txtuser.setBorder(new LineBorder(Color.RED, 3, true));
                                    txtpassword.setBorder(new LineBorder(Color.RED, 3, true));
                                    JOptionPane.showMessageDialog(this, "Coppia user e password gia' inserita");
                                }
                            } else {
                                txtNome.setBorder(border);
                                txtcognome.setBorder(border);
                                txtpassword.setBorder(border);
                                txtcodice.setBorder(border);
                                txtuser.setBorder(border);
                                txtemail.setBorder(border);
                                txtid.setBorder(new LineBorder(Color.RED, 3, true));
                                txtcodice.setBorder(new LineBorder(Color.RED, 3, true));
                                JOptionPane.showMessageDialog(this, "Operazione fallita Identificativo o codice Fiscale errato!!");
                            }
                        } catch (Eccezione exc) {
                            JOptionPane.showMessageDialog(this, message, "errore", JOptionPane.ERROR_MESSAGE);
                        } catch (IOException | URISyntaxException ex) {
                            Logger.getLogger(Registra_Cittadini.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        txtcentro.setBorder(new LineBorder(Color.RED, 3, true));
                        JOptionPane.showMessageDialog(this, "Centro Insesistente o non registrato all'applicazione", "Errore", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    sw.Bordo(Nome, txtNome, border);
                    sw.Bordo(Cognome, txtcognome, border);
                    sw.Bordo(password, txtpassword, border);
                    sw.Bordo(Codice, txtcodice, border);
                    sw.Bordo(user, txtuser, border);
                    sw.Bordo(email, txtemail, border);
                    JOptionPane.showMessageDialog(this, "Riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (e.getSource() == c2) {
                String centro = txtNomeC.getText();
                String Note = txtnote.getText();
                String Evento = txtevento.getText();
                int Indice = Integer.parseInt(txtindice.getText());
                Eventi_Avversi ev = new Eventi_Avversi(Evento, Indice, Note, centro);

                if (u.EsisteCentro(0, centro, "./data/CentriVaccinali.dati.txt")) {

                    if (!centro.equals("") && !Evento.equals("")) {
                        txtevento.setBorder(border);
                        if (Indice >= 1 && Indice <= 5) {
                            if (Note.length() < 256) {
                                u.ScriviFile("./data/Vaccinati_" + centro + ".dati.txt", ev.toString());
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

                        sw.Bordo(Evento, txtevento, border);
                        sw.Bordo(centro, txtNomeC, border);
                        JOptionPane.showMessageDialog(this, "riempire tutti i campi", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    txtNomeC.setBorder(new LineBorder(Color.RED, 3, true));
                    JOptionPane.showMessageDialog(this, "Il centro da lei indicato non esiste o non si e' registrato all'applicazione", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            if (e.getSource() == annulla) {
                CittadiniGUI ac = new CittadiniGUI();
                this.dispose();
            }

        } catch (NumberFormatException ec) {
            JOptionPane.showMessageDialog(this, "Errore di formattazione dovuto a valore numerico non rispettato", "Error112", JOptionPane.WARNING_MESSAGE);
        } catch (IOException | URISyntaxException ex) {
            Logger.getLogger(Registra_Cittadini.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}