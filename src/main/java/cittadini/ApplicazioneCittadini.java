/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package cittadini;

import centrivaccinali.CentriVaccinaliGUI;
import centrivaccinali.Utility;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * La classe ApplicazioneCittadini estende <code> jFrame </code> e implementa <code> ActionListener </code> e <code> MouseListener </code>
 * Offre 3 opzioni per i cittadini:
 * Registrazione all' applicazione attraverso il bottone <code> registra </code>,
 * Inserire Evento Avverso a seguito del login(password e userid) selezionando il bottone <code> evento </code> ,
 * Visualizzare informazioni relative ad un dato centro( Attraverso una ricerca per nome centro o per comune e tipologia) con il bottone <code> cerca1 </code>;
 * il bottone <code> Menu </code> permette di tornare al menù
 *
 * @author daniele Caspani
 */
public class ApplicazioneCittadini extends JFrame implements ActionListener {

    private JTextField username = new JTextField();
    private JTextField password = new JPasswordField();
    private JPanel bgPanel, buttonPanel;
    private JLabel logolabel, error = new JLabel();
    /**
     * oggetto inserito in un <code> JOptionPane </code> utilizzato per il login
     *
     * @author Daniele Caspani
     */
    private Object[] message = {
            "Username:", username,
            "Password:", password,
            error
    };

    /**
     * viene utilizzata per lanciare la classe registrazioni(i) dove i rappresenta l'opzione scelta
     *
     * @author Daniele Caspani
     */
    private JButton evento, cerca1, registra, goBack;
    /**
     * oggetto inserito in un <code> JOptionPane </code> utilizzato la scelta del tipo di ricerca che si vuole effettuare
     *
     * @author Daniele Caspani
     */
    private Object[] ricerca = {"Ricerca per Comune e Tipologia", "Ricerca per Nome Centro"};
    private Utility u = new Utility();

    public ApplicazioneCittadini() {
        init();
    }

    public static void main(String[] args) {
        ApplicazioneCittadini Ac = new ApplicazioneCittadini();
    }

    /**
     * metodo utilizzato per l'inizializzazione della finestra JFrame.
     *
     * @param title
     */
    public void settings(String title) {
        setSize(1366, 768);
        setTitle(title);
        this.setFocusable(true);
        this.requestFocus();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        username.setSize(150, 80);
        password.setSize(150, 80);
        setResizable(false);
    }

    /**
     * metodo utilizzato per l' inizializzazione dei componenti jframe
     *
     * @author Daniele Caspani
     */
    public void init() {
        settings("SCEGLI FUNZIONALITA'!");

        getContentPane().setLayout(null);

        goBack = new JButton("Menu ");
        goBack.addActionListener(this);
        goBack.setFont(new Font("Dialog", Font.BOLD, 19));
        goBack.setBounds(28, 678, 95, 35);
        getContentPane().add(goBack);

        bgPanel = new JPanel();
        bgPanel.setBackground(Color.GREEN);
        bgPanel.setBounds(187, 76, 985, 587);
        getContentPane().add(bgPanel);
        bgPanel.setLayout(null);

        buttonPanel = new JPanel();
        buttonPanel.setBounds(12, 12, 961, 561);
        bgPanel.add(buttonPanel);

        registra = new JButton("Registrati");
        registra.addActionListener(this);
        registra.setBounds(305, 195, 350, 50);
        registra.setHorizontalTextPosition(SwingConstants.LEFT);
        registra.setFont(new Font("Dialog", Font.PLAIN, 30));

        cerca1 = new JButton("Visualizza informazioni");
        cerca1.addActionListener(this);
        cerca1.setBounds(305, 255, 350, 50);
        cerca1.setFont(new Font("Dialog", Font.PLAIN, 30));

        evento = new JButton("Evento avverso");
        evento.addActionListener(this);
        evento.setBounds(305, 315, 350, 50);
        evento.setFont(new Font("Dialog", Font.PLAIN, 30));

        buttonPanel.setLayout(null);
        buttonPanel.add(registra);
        buttonPanel.add(cerca1);
        buttonPanel.add(evento);

        logolabel = new JLabel(new ImageIcon(ClassLoader.getSystemResource("logo.png")));
        logolabel.setBounds(12, 12, 163, 163);
        getContentPane().add(logolabel);
        setVisible(true);
    }

    /**
     * metodo appartenente all' interfaccia ActionListener
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == goBack) {
            CentriVaccinaliGUI c = new CentriVaccinaliGUI();
            this.dispose();
        }

        if (e.getSource() == registra) {
            Registra_Cittadini d = new Registra_Cittadini(2);
            this.dispose();
        }

        if (e.getSource() == cerca1) {
            JComboBox comboBox = new JComboBox(ricerca);
            int option = JOptionPane.showConfirmDialog(null, comboBox, "Scelta", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {

                if (comboBox.getSelectedIndex() == 0) {
                    Ricerca r = new Ricerca(1);
                    dispose();
                }

                if (comboBox.getSelectedIndex() == 1) {
                    Ricerca r = new Ricerca(2);
                    dispose();
                }
            }
        }
        if (e.getSource() == evento) {
            boolean b = false;
            int option;
            do {
                option = JOptionPane.showConfirmDialog(null, message, "Login", JOptionPane.OK_CANCEL_OPTION);
                Login l = new Login(username.getText(), password.getText());
                if (option == JOptionPane.OK_OPTION) {

                    if (u.ControlloLogin((String) l.toString(), "./data/log.txt"))
                        b = true;

                    if (b == false) {
                        password.setBorder(new LineBorder(Color.RED, 3, true));
                        username.setBorder(new LineBorder(Color.RED, 3, true));
                        error.setText("Password o userid non corretto");
                        error.setForeground(Color.RED);
                    }
                } else
                    b = true;

            } while (b == false);

            if (option == JOptionPane.OK_OPTION) {
                Registra_Cittadini d = new Registra_Cittadini(1);
                dispose();
            }
        }
    }
}