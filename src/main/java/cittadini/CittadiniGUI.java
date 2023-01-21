/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
import centrivaccinali.SwingAwt;
import shared.DBClient;
import shared.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * La classe CittadiniGUI estende <code>JFrame</code> e implementa <code>ActionListener</code>.
 * Offre 3 opzioni per i cittadini:
 * Registrazione all'applicazione attraverso il bottone <code>Registrati</code>;
 * Visualizzare informazioni relative a un dato centro (con una ricerca per nome centro o per comune e tipologia)
 * attraverso il bottone <code>Visualizza informazioni</code>;
 * Inserire eventi avversi a seguito del login (con userid e password) selezionando il bottone <code>Evento Avverso</code>;
 * Il bottone <code>Menu</code> permette di tornare al menù
 *
 * @author daniele Caspani
 */
public class CittadiniGUI extends JFrame implements ActionListener {

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight();
    private JTextField username = new JTextField(),
                        password = new JPasswordField();
    private JPanel background_panel, button_panel;
    private JLabel logo_label,
                    error = new JLabel();
    /**
     * Oggetto inserito in un <code>JOptionPane</code>. Utilizzato per il login
     */
    private Object[] message = {
            "Username:", username,
            "Password:", password,
            error
    };
    /**
     * Oggetto inserito in un <code>JOptionPane</code>. Utilizzato per la scelta del tipo di ricerca che si vuole effettuare
     */
    public static Object[] ricerca = {
            "Ricerca per Nome Centro",
            "Ricerca per Comune e Tipologia"
    };

    /**
     * Viene utilizzata per lanciare la classe registrazioni(i) dove i rappresenta l'opzione scelta
     */
    private JButton registrati_button, informazioni_button, evento_avverso_button, menu_button;

    public CittadiniGUI() {
        initWindow();
    }

    public static void main(String[] args) {
        new CittadiniGUI();
    }

    /**
     * Metodo utilizzato per l'inizializzazione della finestra JFrame.
     */
    private void settings() {
        setSize(1366, 768);
        setLocationRelativeTo(null);
        setTitle("Scegli Funzionalità");
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username.setSize(150, 80);
        password.setSize(150, 80);
        setResizable(false);

        Utility.setWindowLogo(this, "logo.png");
    }

    /**
     * Metodo utilizzato per l'inizializzazione dei componenti JFrame
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings();

        getContentPane().setLayout(null);

        menu_button = new JButton("Menu");
        menu_button.addActionListener(this);
        menu_button.setFont(new Font("Dialog", Font.BOLD, 19));
        menu_button.setBounds(28, 678, 95, 35);
        getContentPane().add(menu_button);

        background_panel = new JPanel();
        background_panel.setBackground(Color.GREEN);
        background_panel.setBounds(187, 76, 985, 587);
        getContentPane().add(background_panel);
        background_panel.setLayout(null);

        button_panel = new JPanel();
        button_panel.setBounds(12, 12, 961, 561);
        background_panel.add(button_panel);

        registrati_button = new JButton("Registrati");
        registrati_button.addActionListener(this);
        registrati_button.setBounds(305, 195, 350, 50);
        registrati_button.setHorizontalTextPosition(SwingConstants.LEFT);
        registrati_button.setFont(new Font("Dialog", Font.PLAIN, 30));

        informazioni_button = new JButton("Visualizza informazioni");
        informazioni_button.addActionListener(this);
        informazioni_button.setBounds(305, 255, 350, 50);
        informazioni_button.setFont(new Font("Dialog", Font.PLAIN, 30));

        evento_avverso_button = new JButton("Evento avverso");
        evento_avverso_button.addActionListener(this);
        evento_avverso_button.setBounds(305, 315, 350, 50);
        evento_avverso_button.setFont(new Font("Dialog", Font.PLAIN, 30));

        button_panel.setLayout(null);
        button_panel.add(registrati_button);
        button_panel.add(informazioni_button);
        button_panel.add(evento_avverso_button);

        logo_label = new JLabel(new ImageIcon(ClassLoader.getSystemResource("logo.png")));
        logo_label.setBounds(12, 12, 163, 163);
        getContentPane().add(logo_label);

        setVisible(true);
    }

    /**
     * Metodo appartenente all'interfaccia ActionListener, equivalente alla pressione di un pulsante.
     *
     * @param e evento che si è verificato
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == menu_button) {
            this.goToMenu();
        }

        else if (e.getSource() == registrati_button) {
            this.avviaRegistrazioneCittadino();
        }

        else if (e.getSource() == informazioni_button) {
            this.avviaInfoCentro();
        }

        else if (e.getSource() == evento_avverso_button) {
            this.avviaSegnalazioneEventi();
        }
    }

    /**
     * Metodo chiamato alla pressione del tasto "Menu". Reindirizza al menu principale.
     * @author Manuel Marceca
     */
    private void goToMenu(){
        new CentriVaccinaliGUI();
        this.dispose();
    }

    /**
     * Metodo chiamato alla pressione del tasto "Registrati". Avvia la procedura di registrazione utenti.
     * @author Manuel Marceca
     */
    private void avviaRegistrazioneCittadino(){
        JComboBox<Object> comboBox = new JComboBox<>(ricerca);
        int option = JOptionPane.showConfirmDialog(null, comboBox,
                "Ricerca del centro in cui hai effettuato la vaccinazione",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (comboBox.getSelectedIndex() == 0) {
                new RicercaNomeCentro(3);
                dispose();
            } else if (comboBox.getSelectedIndex() == 1) {
                new RicercaComuneTipologia(3);
                dispose();
            }
        }
    }

    /**
     * Metodo chiamato alla pressione del tasto "Visualizza informazioni". Avvia la procedura di
     * visualizzazione delle informazioni per centro vaccinale, partendo dalla ricerca del centro
     * in questione.
     * @author Manuel Marceca
     */
    private void avviaInfoCentro(){
        JComboBox<Object> comboBox = new JComboBox<>(ricerca);
        int option = JOptionPane.showConfirmDialog(null, comboBox,
                "Ricerca del centro di cui visualizzare le informazioni",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            if (comboBox.getSelectedIndex() == 0) {
                new RicercaNomeCentro(1);
                dispose();
            } else if (comboBox.getSelectedIndex() == 1) {
                new RicercaComuneTipologia(1);
                dispose();
            }
        }
    }

    /**
     * Metodo chiamato alla pressione del tasto "Evento Avverso". Avvia la procedura di segnalazione
     * degli eventi avversi, partendo dal login utente.
     * @author Manuel Marceca
     */
    private void avviaSegnalazioneEventi(){
        int chosen_option;
        boolean logged_in = false;

        do {
            chosen_option = JOptionPane.showConfirmDialog(null, message, "Esegui il Login per Segnalare un Evento Avverso", JOptionPane.OK_CANCEL_OPTION);
            if (chosen_option == JOptionPane.OK_OPTION) {
                Login login = new Login(username.getText(), password.getText());
                if (Utility.loginOk(login)) {
                    logged_in = true;

                    JComboBox<Object> comboBox = new JComboBox<>(ricerca);
                    int option = JOptionPane.showConfirmDialog(null, comboBox, "Ricerca del centro in cui hai effettuato la vaccinazione", JOptionPane.OK_CANCEL_OPTION);

                    String cod_fiscale = DBClient.getCfFromUsername(login.getUserId());

                    if (option == JOptionPane.OK_OPTION) {
                        if (comboBox.getSelectedIndex() == 0) {
                            new RicercaNomeCentro(4, cod_fiscale);
                            dispose();
                        } else if (comboBox.getSelectedIndex() == 1) {
                            new RicercaComuneTipologia(4, cod_fiscale);
                            dispose();
                        }
                    }
                } else {
                    password.setBorder(new LineBorder(Color.RED, 3, true));
                    username.setBorder(new LineBorder(Color.RED, 3, true));
                    error.setText("UserID o Password non corretti");
                    error.setForeground(Color.RED);
                }
            } else if(chosen_option == JOptionPane.CANCEL_OPTION || chosen_option == JOptionPane.CLOSED_OPTION) {
                password.setText("nd");
                username.setText("nd");
                SwingAwt.modificaBordo(password);
                SwingAwt.modificaBordo(username);
                password.setText("");
                username.setText("");
                error.setText("");
                break;
            }
        } while (!logged_in);
    }
}