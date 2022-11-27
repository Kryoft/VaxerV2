/*
 * Manuel Marceca, 746494, CO
 * Cristian Corti, 744359, CO
 * Daniele Caspani, 744628, CO
 */
package centrivaccinali;


import cittadini.CittadiniGUI;
import shared.Utility;

import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La classe <strong>CentriVaccinaliGUI</strong> estende la classe <code>JFrame</code> e implementa
 * l'interfaccia <code>ActionListener</code>;
 * Si tratta della schermata del main e viene utilizzata per la scelta del tipo di utente
 * (Cittadino o Centro Vaccinale) attraverso il bottone <code>centro</code> e il bottone <code>cittadini</code>
 *
 * @author Daniele Caspani
 */
public class CentriVaccinaliGUI extends JFrame implements ActionListener {

    private int display_width = Utility.getDisplayWidth(),
                display_height = Utility.getDisplayHeight();
    private JButton centro_button = new JButton(),
                    cittadini_button = new JButton();

    public CentriVaccinaliGUI() {
        initWindow();
    }

    public static void main(String[] args) {
        new CentriVaccinaliGUI();
    }

    /**
     * Metodo per settare la finestra che sviluppa l'interfaccia grafica
     *
     * @author Daniele Caspani
     */
    private void initWindow() {
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setBounds(100, 100, (int) (display_width / 1.5), (int) (display_height / 1.5));  // istruzione necessaria nel caso l'utente voglia ridimensionare la finestra
        setTitle("Seleziona Tipo di Utente");
        this.setFocusable(true);
        this.requestFocusInWindow();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // assegnazione immagine al bottone centro
        ImageIcon icon_centro = new ImageIcon(ClassLoader.getSystemResource("Person.png"));
        Image img_centro = icon_centro.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        icon_centro = new ImageIcon(img_centro);
        centro_button.setIcon(icon_centro);

        // assegnazione immagine al bottone centro
        ImageIcon icon_cittadini = new ImageIcon(ClassLoader.getSystemResource("Hospital.png"));
        Image img_cittadini = icon_cittadini.getImage().getScaledInstance(300, 300, Image.SCALE_SMOOTH);
        icon_cittadini = new ImageIcon(img_cittadini);
        cittadini_button.setIcon(icon_cittadini);

        GroupLayout groupLayout = new GroupLayout(getContentPane());
        groupLayout.setHorizontalGroup(
                groupLayout.createParallelGroup(Alignment.LEADING)
                        .addGroup(groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(centro_button, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                                .addGap(22)
                                .addComponent(cittadini_button, GroupLayout.DEFAULT_SIZE, 643, Short.MAX_VALUE)
                                .addContainerGap())
        );
        groupLayout.setVerticalGroup(
                groupLayout.createParallelGroup(Alignment.TRAILING)
                        .addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
                                        .addComponent(cittadini_button, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE)
                                        .addComponent(centro_button, GroupLayout.DEFAULT_SIZE, 695, Short.MAX_VALUE))
                                .addContainerGap())
        );
        getContentPane().setLayout(groupLayout);

        centro_button.addActionListener(this);
        cittadini_button.addActionListener(this);

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
        if (e.getSource() == cittadini_button) {
            new OperazioniCentroGUI();
        } else if (e.getSource() == centro_button) {
            new CittadiniGUI();
        }
        this.dispose();
    }

}