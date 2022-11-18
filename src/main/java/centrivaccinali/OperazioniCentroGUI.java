/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe OperazioniCentro estende la classe <code>JFrame</code> e implementa l'interfaccia <code>ActionListener</code>;
 * Presenta un menu nel quale vi è la possibilità di scegliere se registrare un nuovo vaccinato o un nuovo centro vaccinale;
 *
 * @author daniele Caspani
 */
public class OperazioniCentroGUI extends JFrame implements ActionListener {

    private JPanel bg_panel, button_panel;
    private JButton menu_button, centro_button, vaccinato_button;
    private JLabel logo_label;

    public OperazioniCentroGUI() {
        setMinimumSize(new Dimension(1366, 768));
        initWindow();
        setLocationRelativeTo(null);
    }

    /**
     * metodo per settare la finestra che sviluppa l'interfaccia grafica
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        setSize(1366, 768);
        setTitle("Seleziona Tipo di Utente (Dopo aver selezionato un'opzione cliccare avanti)");
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        menu_button = new JButton("Menu");
        menu_button.setBounds(24, 669, 95, 35);
        menu_button.addActionListener(this);
        getContentPane().setLayout(null);
        getContentPane().add(menu_button);

        bg_panel = new JPanel();
        bg_panel.setLayout(null);
        bg_panel.setBackground(Color.GREEN);
        bg_panel.setBounds(182, 71, 985, 587);
        getContentPane().add(bg_panel);

        button_panel = new JPanel();
        button_panel.setLayout(null);
        button_panel.setBounds(12, 12, 961, 561);
        bg_panel.add(button_panel);

        centro_button = new JButton("Registra centro ");
        centro_button.setFont(new Font("Dialog", Font.PLAIN, 30));
        centro_button.setBounds(305, 207, 350, 50);
        button_panel.add(centro_button);
        centro_button.addActionListener(this);

        vaccinato_button = new JButton("Registra vaccinato");
        vaccinato_button.setFont(new Font("Dialog", Font.PLAIN, 30));
        vaccinato_button.setBounds(305, 269, 350, 50);
        vaccinato_button.addActionListener(this);
        button_panel.add(vaccinato_button);

        logo_label = new JLabel(new ImageIcon(ClassLoader.getSystemResource("logo.png")));
        logo_label.setBounds(12, 12, 163, 163);
        getContentPane().add(logo_label);

        this.setVisible(true);
    }

    /**
     * metodo appartenente all'interfaccia ActionListener
     *
     * @param e evento che si è verificato
     * @author Daniele Caspani
     * @see ActionListener
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == centro_button) {
            new RegistraCentro();  // new Registrazioni(1);
            this.dispose();
        } else if (e.getSource() == vaccinato_button) {
            new RegistraVaccinato();  // new Registrazioni(2);
            this.dispose();
        } else if (e.getSource() == menu_button) {
            new CentriVaccinaliGUI();
            this.dispose();
        }
    }

}