/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import cittadini.CittadiniGUI;
import cittadini.RicercaComuneTipologia;
import cittadini.RicercaNomeCentro;
import shared.Utility;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe OperazioniCentro estende la classe <code>JFrame</code> e implementa l'interfaccia <code>ActionListener</code>;
 * Presenta un menu nel quale vi è la possibilità di scegliere se registrare un nuovo vaccinato o un nuovo centro vaccinale;
 *
 * @author Daniele Caspani
 */
public class OperazioniCentroGUI extends JFrame implements ActionListener {

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight();
    private JPanel background_panel, button_panel;
    private JButton menu_button, registra_centro_button, registra_vaccinato_button;
    private JLabel logo_label;

    public OperazioniCentroGUI() {
        initWindow();
    }

    private void settings() {
        setMinimumSize(new Dimension(1366, 768));
//        setSize((int) (display_width / 1.5), (int) (display_height / 1.5));
        setLocationRelativeTo(null);
        setTitle("Seleziona Tipo di Utente (Dopo aver selezionato un'opzione cliccare avanti)");
        setFocusable(true);
        requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Utility.setWindowLogo(this, "logo.png");
    }

    /**
     * Metodo per settare la finestra che sviluppa l'interfaccia grafica
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        settings();

        menu_button = new JButton("Menu");
        menu_button.setBounds(24, 669, 95, 35);
        menu_button.addActionListener(this);
        getContentPane().setLayout(null);
        getContentPane().add(menu_button);

        background_panel = new JPanel();
        background_panel.setLayout(null);
        background_panel.setBackground(Color.GREEN);
        background_panel.setBounds(182, 71, 985, 587);
        getContentPane().add(background_panel);

        button_panel = new JPanel();
        button_panel.setLayout(null);
        button_panel.setBounds(12, 12, 961, 561);
        background_panel.add(button_panel);

        registra_centro_button = new JButton("Registra centro ");
        registra_centro_button.setFont(new Font("Dialog", Font.PLAIN, 30));
        registra_centro_button.setBounds(305, 207, 350, 50);
        button_panel.add(registra_centro_button);
        registra_centro_button.addActionListener(this);

        registra_vaccinato_button = new JButton("Registra vaccinato");
        registra_vaccinato_button.setFont(new Font("Dialog", Font.PLAIN, 30));
        registra_vaccinato_button.setBounds(305, 269, 350, 50);
        registra_vaccinato_button.addActionListener(this);
        button_panel.add(registra_vaccinato_button);

        logo_label = new JLabel(new ImageIcon(ClassLoader.getSystemResource("logo.png")));
        logo_label.setBounds(12, 12, 163, 163);
        getContentPane().add(logo_label);

        this.setVisible(true);
    }

    /**
     * Metodo appartenente all'interfaccia <code>ActionListener</code>
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registra_centro_button) {
            new RegistraCentro();  // new Registrazioni(1);
            this.dispose();
        } else if (e.getSource() == registra_vaccinato_button) {
//            new RegistraVaccinato();  // new Registrazioni(2);
//            this.dispose();
            JComboBox<Object> comboBox = new JComboBox<>(CittadiniGUI.ricerca);
            int option = JOptionPane.showConfirmDialog(null, comboBox, "Ricerca del Centro di Vaccinazione", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                if (comboBox.getSelectedIndex() == 0) {
                    new RicercaNomeCentro(2);
                    dispose();
                } else if (comboBox.getSelectedIndex() == 1) {
                    new RicercaComuneTipologia(2);
                    dispose();
                }
            }
        } else if (e.getSource() == menu_button) {
            new CentriVaccinaliGUI();
            this.dispose();
        }
    }

}