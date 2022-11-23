/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
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
 * Visualizzare informazioni relative ad un dato centro (con una ricerca per nome centro o per comune e tipologia)
 * attraverso il bottone <code>Visualizza informazioni</code>;
 * Inserire eventi avversi a seguito del login (con userid e password) selezionando il bottone <code>Evento Avverso</code>;
 * Il bottone <code>Menu</code> permette di tornare al menù
 *
 * @author daniele Caspani
 */
public class CittadiniGUI extends JFrame implements ActionListener {

    private JTextField username = new JTextField();
    private JTextField password = new JPasswordField();
    private JPanel bg_panel, button_panel;
    private JLabel logo_label, error = new JLabel();
    /**
     * Oggetto inserito in un <code>JOptionPane</code>. Utilizzato per il login
     */
    private Object[] message = {
            "Username:", username,
            "Password:", password,
            error
    };

    /**
     * Viene utilizzata per lanciare la classe registrazioni(i) dove i rappresenta l'opzione scelta
     */
    private JButton registrati_button, informazioni_button, evento_avverso_button, menu_button;
    /**
     * Oggetto inserito in un <code>JOptionPane</code>. Utilizzato per la scelta del tipo di ricerca che si vuole effettuare
     */
    private Object[] ricerca = {"Ricerca per Comune e Tipologia", "Ricerca per Nome Centro"};

    public CittadiniGUI() {
        initWindow();
    }

    public static void main(String[] args) {
        new CittadiniGUI();
    }

    /**
     * Metodo utilizzato per l'inizializzazione della finestra JFrame.
     *
     * @param title
     */
    public void settings(String title) {
        setSize(1366, 768);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username.setSize(150, 80);
        password.setSize(150, 80);
        setResizable(false);
    }

    /**
     * Metodo utilizzato per l'inizializzazione dei componenti JFrame
     *
     * @author Daniele Caspani
     */
    public void initWindow() {
        settings("Scegli Funzionalità");

        getContentPane().setLayout(null);

        menu_button = new JButton("Menu");
        menu_button.addActionListener(this);
        menu_button.setFont(new Font("Dialog", Font.BOLD, 19));
        menu_button.setBounds(28, 678, 95, 35);
        getContentPane().add(menu_button);

        bg_panel = new JPanel();
        bg_panel.setBackground(Color.GREEN);
        bg_panel.setBounds(187, 76, 985, 587);
        getContentPane().add(bg_panel);
        bg_panel.setLayout(null);

        button_panel = new JPanel();
        button_panel.setBounds(12, 12, 961, 561);
        bg_panel.add(button_panel);

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
     * Metodo appartenente all'interfaccia ActionListener
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == menu_button) {
            new CentriVaccinaliGUI();
            this.dispose();
        } else if (e.getSource() == registrati_button) {
            new RegistraCittadini();
            this.dispose();
        } else if (e.getSource() == informazioni_button) {
            JComboBox<Object> comboBox = new JComboBox<>(ricerca);
            int option = JOptionPane.showConfirmDialog(null, comboBox, "Scegli Tipo di Ricerca", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                if (comboBox.getSelectedIndex() == 0) {
                    new Ricerca(1);
                    dispose();
                } else if (comboBox.getSelectedIndex() == 1) {
                    new Ricerca(2);
                    dispose();
                }
            }
        } else if (e.getSource() == evento_avverso_button) {
            int chosen_option;
            boolean logged_in = false;

            do {
                chosen_option = JOptionPane.showConfirmDialog(null, message, "Esegui il Login per Segnalare un Evento Avverso", JOptionPane.OK_CANCEL_OPTION);
                if (chosen_option == JOptionPane.OK_OPTION) {
                    Login login = new Login(username.getText(), password.getText());
                    if (Utility.controlloLogin(login.toString(), "./data/log.txt")) {
                        logged_in = true;
                        new RegistraEventiAvversi();
                        dispose();
                    } else {
                        password.setBorder(new LineBorder(Color.RED, 3, true));
                        username.setBorder(new LineBorder(Color.RED, 3, true));
                        error.setText("UserID o Password non corretti");
                        error.setForeground(Color.RED);
                    }
                } else
                    // TODO: Resettare il colore dei campi username e password dopo che si clicca 'Cancel'
                    break;
            } while (!logged_in);
        }
    }
}